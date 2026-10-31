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
	ErrStagingOnly = errors.New("control-plane operation is staging-only")
	ErrRevoked     = errors.New("node is revoked")
)

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
type Heartbeat struct {
	NodeID        string    `json:"nodeId"`
	Fingerprint   string    `json:"fingerprint"`
	CapacityBytes int64     `json:"capacityBytes"`
	FreeBytes     int64     `json:"freeBytes"`
	ObjectCount   int64     `json:"objectCount"`
	SentAt        time.Time `json:"sentAt"`
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
	if strings.TrimRight(u.Path, "/") == "" {
		return fmt.Errorf("staging control-plane URL must include an explicit staging path")
	}
	if strings.Contains(strings.ToLower(u.Hostname()), "localhost") || strings.HasPrefix(u.Hostname(), "127.") || strings.HasPrefix(u.Hostname(), "10.") || strings.HasPrefix(u.Hostname(), "192.168.") {
		return fmt.Errorf("staging control-plane URL must not target a local or private host")
	}
	return nil
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
	value := map[string]any{"nodeId": c.Identity.NodeID, "publicKey": base64.RawURLEncoding.EncodeToString(pub), "fingerprint": c.Identity.Fingerprint(), "enrollmentCode": c.Config.EnrollmentCode, "nonce": randomID(), "issuedAt": now.Format(time.RFC3339Nano), "expiresAt": now.Add(5 * time.Minute).Format(time.RFC3339Nano)}
	var out struct {
		Status string `json:"status"`
	}
	if err = c.post(ctx, "/v1/storage/nodes/enroll", value, &out); err != nil {
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
	payload, err := json.Marshal(Heartbeat{c.Identity.NodeID, c.Identity.Fingerprint(), capacity, free, count, now})
	if err != nil {
		return err
	}
	cmd := protocol.Command{Version: "v1", CommandID: randomID(), NodeID: c.Identity.NodeID, Type: protocol.Heartbeat, IssuedAt: now, ExpiresAt: now.Add(5 * time.Minute), Nonce: randomID(), Payload: payload}
	priv, err := c.Identity.Private()
	if err != nil {
		return err
	}
	cmd.Signature = ed25519.Sign(priv, cmd.CanonicalBytes())
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
		if err := c.Heartbeat(ctx, a, f, n); err != nil {
			if errors.Is(err, ErrRevoked) {
				return err
			}
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
		b, _ := io.ReadAll(io.LimitReader(resp.Body, 4096))
		if strings.Contains(string(b), "revoked") {
			return ErrRevoked
		}
		return fmt.Errorf("control-plane returned %s: %s", resp.Status, string(b))
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
