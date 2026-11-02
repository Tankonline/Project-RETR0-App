package protocol

import (
	"crypto/ed25519"
	"crypto/sha256"
	"encoding/base64"
	"encoding/hex"
	"encoding/json"
	"errors"
	"regexp"
	"time"
)

var (
	ErrUnsupportedVersion = errors.New("unsupported protocol version")
	ErrExpiredCommand     = errors.New("expired command")
	ErrWrongNode          = errors.New("command addressed to another node")
	ErrInvalidSignature   = errors.New("invalid command signature")
)

const (
	MaxEnvelopeBytes = 256 * 1024
	MaxCommandTTL    = 15 * time.Minute
)

var idPattern = regexp.MustCompile(`^[A-Za-z0-9._:-]{1,128}$`)
var hashPattern = regexp.MustCompile(`^[a-f0-9]{64}$`)

type CommandType string

const (
	PutObject      CommandType = "PUT_OBJECT"
	HeadObject     CommandType = "HEAD_OBJECT"
	GetObject      CommandType = "GET_OBJECT"
	DeleteObject   CommandType = "DELETE_OBJECT"
	Heartbeat      CommandType = "HEARTBEAT"
	CapacityReport CommandType = "CAPACITY_REPORT"
	DrainNode      CommandType = "DRAIN_NODE"
)

type Command struct {
	Version     string          `json:"version"`
	CommandID   string          `json:"commandId"`
	NodeID      string          `json:"nodeId"`
	Type        CommandType     `json:"type"`
	IssuedAt    time.Time       `json:"issuedAt"`
	ExpiresAt   time.Time       `json:"expiresAt"`
	Nonce       string          `json:"nonce"`
	ObjectHash  string          `json:"objectHash,omitempty"`
	ObjectSize  int64           `json:"objectSize,omitempty"`
	ContentType string          `json:"contentType,omitempty"`
	Payload     json.RawMessage `json:"payload,omitempty"`
	Signature   string          `json:"signature"`
}

// CanonicalBytes signs a deterministic compact UTF-8 JSON object. The payload
// hash is SHA-256 of the payload's exact wire JSON bytes; both sides preserve
// field order, timestamp strings, JSON escaping, lowercase hex, and raw base64url.
func (c Command) CanonicalBytes() []byte {
	payloadHash := sha256.Sum256(c.Payload)
	canonical := struct {
		Version     string      `json:"version"`
		CommandID   string      `json:"commandId"`
		NodeID      string      `json:"nodeId"`
		Type        CommandType `json:"type"`
		IssuedAt    string      `json:"issuedAt"`
		ExpiresAt   string      `json:"expiresAt"`
		Nonce       string      `json:"nonce"`
		PayloadHash string      `json:"payloadHash"`
	}{c.Version, c.CommandID, c.NodeID, c.Type, c.IssuedAt.UTC().Format(time.RFC3339Nano), c.ExpiresAt.UTC().Format(time.RFC3339Nano), c.Nonce, hex.EncodeToString(payloadHash[:])}
	bytes, _ := json.Marshal(canonical)
	return bytes
}

func (c Command) BodyHash() string         { d := sha256.Sum256(c.Payload); return hex.EncodeToString(d[:]) }
func (c Command) EncodedSignature() string { return c.Signature }

func Validate(c Command, nodeID string, now time.Time) error {
	if c.Version != "v1" {
		return ErrUnsupportedVersion
	}
	if c.NodeID != nodeID {
		return ErrWrongNode
	}
	if !idPattern.MatchString(c.CommandID) || !idPattern.MatchString(c.NodeID) || !idPattern.MatchString(c.Nonce) {
		return ErrInvalidSignature
	}
	if len(c.Signature) == 0 {
		return ErrInvalidSignature
	}
	if c.IssuedAt.After(now.Add(5*time.Minute)) || c.IssuedAt.Before(now.Add(-5*time.Minute)) || !now.Before(c.ExpiresAt) || c.ExpiresAt.Sub(c.IssuedAt) > MaxCommandTTL {
		return ErrExpiredCommand
	}
	if c.ObjectHash != "" && !hashPattern.MatchString(c.ObjectHash) {
		return ErrInvalidSignature
	}
	if c.Type == Heartbeat && len(c.Payload) > 0 {
		var payload struct {
			CapacityBytes int64 `json:"capacityBytes"`
			FreeBytes     int64 `json:"freeBytes"`
			ObjectCount   int64 `json:"objectCount"`
		}
		if json.Unmarshal(c.Payload, &payload) != nil || payload.CapacityBytes < 0 || payload.FreeBytes < 0 || payload.FreeBytes > payload.CapacityBytes || payload.ObjectCount < 0 {
			return ErrInvalidSignature
		}
	}
	if c.ObjectSize < 0 || len(c.ContentType) > 128 {
		return ErrInvalidSignature
	}
	return nil
}

func Verify(c Command, nodeID string, key ed25519.PublicKey, now time.Time) error {
	if err := Validate(c, nodeID, now); err != nil {
		return err
	}
	signature, err := base64.RawURLEncoding.DecodeString(c.Signature)
	if err != nil || len(signature) != ed25519.SignatureSize {
		return ErrInvalidSignature
	}
	if len(key) != ed25519.PublicKeySize || !ed25519.Verify(key, c.CanonicalBytes(), signature) {
		return ErrInvalidSignature
	}
	return nil
}
