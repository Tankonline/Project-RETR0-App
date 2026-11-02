package controlplane

import (
	"bytes"
	"context"
	"crypto/ed25519"
	"crypto/rand"
	"encoding/base64"
	"encoding/json"
	"errors"
	"fmt"
	"io"
	"log"
	"net"
	"net/http"
	"net/url"
	"os"
	"path/filepath"
	"strings"
	"sync"
	"time"

	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/auth"
	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/protocol"
)

var (
	ErrStagingOnly   = errors.New("control-plane operation is staging-only")
	ErrRevoked       = errors.New("node is revoked")
	ErrAdminApproval = errors.New("staging admin approval failed")
)

// HTTPError preserves bounded, non-sensitive control-plane diagnostics.
type HTTPError struct {
	StatusCode int
	Status     string
	Code       string
}

func (e *HTTPError) Error() string {
	if e.Code != "" {
		return fmt.Sprintf("control-plane returned %s (%s)", e.Status, e.Code)
	}
	return fmt.Sprintf("control-plane returned %s", e.Status)
}

type Config struct {
	BaseURL                  string `json:"baseUrl"`
	Environment              string `json:"environment"`
	EnrollmentCode           string `json:"enrollmentCode,omitempty"`
	HeartbeatIntervalSeconds int    `json:"heartbeatIntervalSeconds"`
}
type State struct {
	Status          string    `json:"status"`
	LastHeartbeatAt time.Time `json:"lastHeartbeatAt,omitempty"`
	NextRetryAt     time.Time `json:"nextRetryAt,omitempty"`
	RetryCount      int       `json:"retryCount"`
}
type CommandEnvelope struct {
	Version   string               `json:"version"`
	CommandID string               `json:"commandId"`
	NodeID    string               `json:"nodeId"`
	Type      protocol.CommandType `json:"type"`
	IssuedAt  time.Time            `json:"issuedAt"`
	ExpiresAt time.Time            `json:"expiresAt"`
	Nonce     string               `json:"nonce"`
	Payload   json.RawMessage      `json:"payload"`
	Signature string               `json:"signature"`
}

type CommandAck struct {
	NodeID    string `json:"nodeId"`
	CommandID string `json:"commandId"`
	Success   bool   `json:"success"`
	SizeBytes int64  `json:"sizeBytes,omitempty"`
	SHA256    string `json:"sha256,omitempty"`
	Error     string `json:"error,omitempty"`
}

type Heartbeat struct {
	NodeID        string    `json:"nodeId"`
	Fingerprint   string    `json:"fingerprint"`
	CapacityBytes int64     `json:"capacityBytes"`
	FreeBytes     int64     `json:"freeBytes"`
	ObjectCount   int64     `json:"objectCount"`
	SentAt        time.Time `json:"sentAt,omitempty"`
}
type Client struct {
	HTTP      *http.Client
	Identity  auth.Identity
	Config    Config
	State     State
	StatePath string
	mu        sync.Mutex
}

func (c *Client) ensureStaging() error {
	if strings.ToLower(strings.TrimSpace(c.Config.Environment)) != "staging" {
		return ErrStagingOnly
	}
	u, err := url.Parse(strings.TrimSpace(c.Config.BaseURL))
	if err != nil || u.Scheme != "https" || u.Host == "" || u.User != nil || u.RawQuery != "" || u.Fragment != "" {
		return fmt.Errorf("staging control-plane URL must be an absolute HTTPS URL without credentials, query, or fragment")
	}
	host := strings.ToLower(strings.TrimSuffix(u.Hostname(), "."))
	if host == "save-files-staging.armaandeep08-ap.workers.dev" {
		return nil
	}
	if host == "save-files.armaandeep08-ap.workers.dev" || strings.HasSuffix(host, ".workers.dev") {
		return fmt.Errorf("staging control-plane URL must target the deployed staging host")
	}
	if strings.TrimRight(u.Path, "/") == "" {
		return fmt.Errorf("staging control-plane URL must include an explicit staging path")
	}
	if strings.Contains(host, "localhost") || host == "::1" || net.ParseIP(host) != nil && net.ParseIP(host).IsLoopback() || net.ParseIP(host) != nil && isPrivateIP(net.ParseIP(host)) {
		return fmt.Errorf("staging control-plane URL must not target a local or private host")
	}
	return nil
}

func isPrivateIP(ip net.IP) bool {
	privateRanges := []*net.IPNet{
		{IP: net.ParseIP("10.0.0.0"), Mask: net.CIDRMask(8, 32)},
		{IP: net.ParseIP("172.16.0.0"), Mask: net.CIDRMask(12, 32)},
		{IP: net.ParseIP("192.168.0.0"), Mask: net.CIDRMask(16, 32)},
		{IP: net.ParseIP("169.254.0.0"), Mask: net.CIDRMask(16, 32)},
		{IP: net.ParseIP("fc00::"), Mask: net.CIDRMask(7, 128)},
		{IP: net.ParseIP("fe80::"), Mask: net.CIDRMask(10, 128)},
	}
	for _, network := range privateRanges {
		if network.Contains(ip) {
			return true
		}
	}
	return false
}

type ApprovalResult struct {
	NodeID    string `json:"nodeId"`
	Status    string `json:"status"`
	RequestID string `json:"requestId"`
	ErrorCode string `json:"-"`
}

// BuildApprovalEnvelope constructs the exact Worker enrollment approval body.
// It intentionally contains no private key or admin token.
func BuildApprovalEnvelope(id auth.Identity, enrollmentCode string, now time.Time) (map[string]any, error) {
	pub, err := id.Public()
	if err != nil {
		return nil, err
	}
	return map[string]any{
		"nodeId":         id.NodeID,
		"publicKey":      base64.RawURLEncoding.EncodeToString(pub),
		"enrollmentCode": enrollmentCode,
		"nonce":          randomID(),
		"issuedAt":       now.UTC().Format(time.RFC3339Nano),
		"expiresAt":      now.UTC().Add(15 * time.Minute).Format(time.RFC3339Nano),
		"signature":      "staging-admin",
	}, nil
}

func (c *Client) Approve(ctx context.Context, adminToken, enrollmentCode string) (ApprovalResult, error) {
	if err := c.ensureStaging(); err != nil {
		return ApprovalResult{}, err
	}
	if adminToken == "" {
		return ApprovalResult{}, ErrAdminApproval
	}
	value, err := BuildApprovalEnvelope(c.Identity, enrollmentCode, time.Now())
	if err != nil {
		return ApprovalResult{}, err
	}
	path := "/v1/storage/nodes/" + url.PathEscape(c.Identity.NodeID) + "/enroll/approve"
	return c.postAdmin(ctx, path, adminToken, value)
}

func (c *Client) postAdmin(ctx context.Context, path, adminToken string, value any) (ApprovalResult, error) {
	data, err := json.Marshal(value)
	if err != nil {
		return ApprovalResult{}, err
	}
	req, err := http.NewRequestWithContext(ctx, http.MethodPost, strings.TrimRight(c.Config.BaseURL, "/")+path, bytes.NewReader(data))
	if err != nil {
		return ApprovalResult{}, err
	}
	req.Header.Set("Content-Type", "application/json")
	req.Header.Set("X-Storage-Admin", adminToken)
	req.Header.Set("X-Request-ID", randomID())
	cl := c.HTTP
	if cl == nil {
		cl = http.DefaultClient
	}
	resp, err := cl.Do(req)
	if err != nil {
		return ApprovalResult{}, err
	}
	defer resp.Body.Close()
	var envelope struct {
		NodeID    string `json:"nodeId"`
		Status    string `json:"status"`
		RequestID string `json:"requestId"`
		Error     struct {
			Code string `json:"code"`
		} `json:"error"`
	}
	_ = json.NewDecoder(io.LimitReader(resp.Body, 64*1024)).Decode(&envelope)
	result := ApprovalResult{NodeID: envelope.NodeID, Status: envelope.Status, RequestID: envelope.RequestID, ErrorCode: envelope.Error.Code}
	if resp.StatusCode >= 300 {
		if result.ErrorCode == "" {
			result.ErrorCode = "admin_approval_failed"
		}
		return result, ErrAdminApproval
	}
	return result, nil
}

func (c *Client) Enroll(ctx context.Context) error {
	if err := c.ensureStaging(); err != nil {
		return err
	}
	pub, err := c.Identity.Public()
	if err != nil {
		return err
	}
	now := time.Now().UTC()
	value := map[string]any{"nodeId": c.Identity.NodeID, "displayName": c.Identity.NodeID, "endpoint": "", "publicKey": base64.RawURLEncoding.EncodeToString(pub), "fingerprint": c.Identity.Fingerprint(), "enrollmentCode": c.Config.EnrollmentCode, "expiresAt": now.Add(15 * time.Minute).Format(time.RFC3339Nano)}
	var out struct {
		Status string `json:"status"`
	}
	if err = c.post(ctx, "/v1/storage/nodes/"+url.PathEscape(c.Identity.NodeID)+"/enroll/request", value, &out); err != nil {
		return err
	}
	c.mu.Lock()
	c.State.Status = out.Status
	c.State.RetryCount = 0
	c.State.NextRetryAt = time.Time{}
	err = c.saveStateLocked()
	c.mu.Unlock()
	return err
}

func (c *Client) Status(ctx context.Context) (State, error) {
	if err := c.ensureStaging(); err != nil {
		return State{}, err
	}
	var out struct {
		Status          string    `json:"status"`
		LastHeartbeatAt time.Time `json:"lastHeartbeatAt"`
	}
	if err := c.get(ctx, "/v1/storage/nodes/"+url.PathEscape(c.Identity.NodeID)+"/status", &out); err != nil {
		return State{}, err
	}
	c.mu.Lock()
	c.State.Status = out.Status
	c.State.LastHeartbeatAt = out.LastHeartbeatAt
	err := c.saveStateLocked()
	state := c.State
	c.mu.Unlock()
	return state, err
}

func (c *Client) PollCommands(ctx context.Context) ([]CommandEnvelope, error) {
	if err := c.ensureStaging(); err != nil {
		return nil, err
	}
	var out struct {
		Commands []CommandEnvelope `json:"commands"`
	}
	if err := c.get(ctx, "/v1/storage/nodes/"+url.PathEscape(c.Identity.NodeID)+"/commands", &out); err != nil {
		return nil, err
	}
	return out.Commands, nil
}

func (c *Client) AckCommand(ctx context.Context, ack CommandAck) error {
	if err := c.ensureStaging(); err != nil {
		return err
	}
	return c.post(ctx, "/v1/storage/nodes/"+url.PathEscape(c.Identity.NodeID)+"/commands/"+url.PathEscape(ack.CommandID)+"/ack", ack, nil)
}

func (c *Client) RunCommandLoop(ctx context.Context, execute func(context.Context, CommandEnvelope) CommandAck) error {
	if err := c.ensureStaging(); err != nil {
		return err
	}
	for {
		if ctx.Err() != nil {
			return ctx.Err()
		}
		commands, err := c.PollCommands(ctx)
		if err != nil {
			time.Sleep(backoff(1))
			continue
		}
		for _, command := range commands {
			ack := execute(ctx, command)
			if err := c.AckCommand(ctx, ack); err != nil {
				return err
			}
		}
		timer := time.NewTimer(5 * time.Second)
		select {
		case <-ctx.Done():
			timer.Stop()
			return ctx.Err()
		case <-timer.C:
		}
	}
}

func (c *Client) Heartbeat(ctx context.Context, capacity, free, count int64) error {
	if err := c.ensureStaging(); err != nil {
		return err
	}
	c.mu.Lock()
	current := c.State.Status
	c.mu.Unlock()
	if current == "revoked" {
		return ErrRevoked
	}
	if current == "draining" {
		return fmt.Errorf("node state draining does not heartbeat")
	}
	now := time.Now().UTC()
	payload, err := json.Marshal(struct {
		CapacityBytes int64 `json:"capacityBytes"`
		FreeBytes     int64 `json:"freeBytes"`
		ObjectCount   int64 `json:"objectCount"`
	}{capacity, free, count})
	if err != nil {
		return err
	}
	cmd := protocol.Command{Version: "v1", CommandID: randomID(), NodeID: c.Identity.NodeID, Type: protocol.Heartbeat, IssuedAt: now, ExpiresAt: now.Add(15 * time.Minute), Nonce: randomID(), Payload: payload}
	priv, err := c.Identity.Private()
	if err != nil {
		return err
	}
	cmd.Signature = base64.RawURLEncoding.EncodeToString(ed25519.Sign(priv, cmd.CanonicalBytes()))
	if err = c.post(ctx, "/v1/storage/nodes/"+url.PathEscape(c.Identity.NodeID)+"/heartbeat", cmd, nil); err != nil {
		c.mu.Lock()
		c.State.RetryCount++
		c.State.NextRetryAt = time.Now().UTC().Add(backoff(c.State.RetryCount))
		_ = c.saveStateLocked()
		c.mu.Unlock()
		return err
	}
	c.mu.Lock()
	c.State.LastHeartbeatAt = now
	c.State.RetryCount = 0
	c.State.NextRetryAt = time.Time{}
	err = c.saveStateLocked()
	c.mu.Unlock()
	return err
}

func (c *Client) RunHeartbeatLoop(ctx context.Context, metrics func() (int64, int64, int64)) error {
	if err := c.ensureStaging(); err != nil {
		return err
	}
	interval := time.Duration(c.Config.HeartbeatIntervalSeconds) * time.Second
	if interval <= 0 {
		interval = time.Minute
	}
	if interval < 10*time.Second {
		interval = 10 * time.Second
	}
	for {
		if ctx.Err() != nil {
			return ctx.Err()
		}
		a, f, n := metrics()
		logHeartbeat := func(result string, err error) {
			if err == nil {
				log.Printf("staging heartbeat result node=%s result=%s", c.Identity.NodeID, result)
				return
			}
			var httpErr *HTTPError
			if errors.As(err, &httpErr) {
				log.Printf("staging heartbeat result node=%s result=%s status_code=%d error_code=%s retry_count=%d", c.Identity.NodeID, result, httpErr.StatusCode, httpErr.Code, c.State.RetryCount)
			} else {
				log.Printf("staging heartbeat result node=%s result=%s error=%v retry_count=%d", c.Identity.NodeID, result, err, c.State.RetryCount)
			}
		}
		log.Printf("staging heartbeat attempt node=%s", c.Identity.NodeID)
		if err := c.Heartbeat(ctx, a, f, n); err != nil {
			logHeartbeat("rejected_or_failed", err)
			if errors.Is(err, ErrRevoked) {
				return err
			}
		} else {
			logHeartbeat("accepted", nil)
		}
		wait := interval
		c.mu.Lock()
		if !c.State.NextRetryAt.IsZero() {
			wait = time.Until(c.State.NextRetryAt)
			if wait < time.Second {
				wait = time.Second
			}
		}
		c.mu.Unlock()
		timer := time.NewTimer(wait)
		select {
		case <-ctx.Done():
			timer.Stop()
			return ctx.Err()
		case <-timer.C:
		}
	}
}

func (c *Client) post(ctx context.Context, path string, value any, response any) error {
	return c.do(ctx, http.MethodPost, path, value, response)
}
func (c *Client) get(ctx context.Context, path string, response any) error {
	return c.do(ctx, http.MethodGet, path, nil, response)
}
func (c *Client) do(ctx context.Context, method, path string, value any, response any) error {
	data := []byte{}
	var err error
	if value != nil {
		data, err = json.Marshal(value)
		if err != nil {
			return err
		}
	}
	req, err := http.NewRequestWithContext(ctx, method, strings.TrimRight(c.Config.BaseURL, "/")+path, bytes.NewReader(data))
	if err != nil {
		return err
	}
	req.Header.Set("Content-Type", "application/json")
	req.Header.Set("X-Node-ID", c.Identity.NodeID)
	req.Header.Set("X-Request-ID", randomID())
	cl := c.HTTP
	if cl == nil {
		cl = http.DefaultClient
	}
	resp, err := cl.Do(req)
	if err != nil {
		return err
	}
	defer resp.Body.Close()
	if resp.StatusCode >= 300 {
		var envelope struct {
			Error struct {
				Code string `json:"code"`
			} `json:"error"`
		}
		_ = json.NewDecoder(io.LimitReader(resp.Body, 4096)).Decode(&envelope)
		if envelope.Error.Code == "node_revoked" || envelope.Error.Code == "revoked" {
			return ErrRevoked
		}
		return &HTTPError{StatusCode: resp.StatusCode, Status: resp.Status, Code: envelope.Error.Code}
	}
	if response != nil {
		return json.NewDecoder(resp.Body).Decode(response)
	}
	return nil
}
func (c *Client) saveStateLocked() error {
	if c.StatePath == "" {
		return nil
	}
	data, err := json.MarshalIndent(c.State, "", "  ")
	if err != nil {
		return err
	}
	if err = os.MkdirAll(filepath.Dir(c.StatePath), 0750); err != nil {
		return err
	}
	tmp, err := os.CreateTemp(filepath.Dir(c.StatePath), ".state-")
	if err != nil {
		return err
	}
	name := tmp.Name()
	defer os.Remove(name)
	if _, err = tmp.Write(data); err != nil {
		tmp.Close()
		return err
	}
	if err = tmp.Chmod(0600); err != nil {
		tmp.Close()
		return err
	}
	if err = tmp.Close(); err != nil {
		return err
	}
	return os.Rename(name, c.StatePath)
}
func (c *Client) saveState() error { c.mu.Lock(); defer c.mu.Unlock(); return c.saveStateLocked() }
func LoadState(path string) (State, error) {
	data, e := os.ReadFile(path)
	if e != nil {
		return State{}, e
	}
	var s State
	e = json.Unmarshal(data, &s)
	return s, e
}
func SaveConfig(path string, c Config) error {
	data, e := json.MarshalIndent(c, "", "  ")
	if e != nil {
		return e
	}
	if e = os.MkdirAll(filepath.Dir(path), 0750); e != nil {
		return e
	}
	return os.WriteFile(filepath.Clean(path), data, 0600)
}
func backoff(n int) time.Duration {
	if n > 6 {
		n = 6
	}
	return time.Duration(1<<n) * time.Second
}
func randomID() string {
	b := make([]byte, 16)
	_, _ = rand.Read(b)
	return base64.RawURLEncoding.EncodeToString(b)
}
