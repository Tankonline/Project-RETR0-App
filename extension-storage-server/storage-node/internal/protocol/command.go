package protocol

import (
	"crypto/ed25519"
	"crypto/sha256"
	"encoding/base64"
	"errors"
	"fmt"
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
	Version     string      `json:"version"`
	CommandID   string      `json:"commandId"`
	NodeID      string      `json:"nodeId"`
	Type        CommandType `json:"type"`
	IssuedAt    time.Time   `json:"issuedAt"`
	ExpiresAt   time.Time   `json:"expiresAt"`
	Nonce       string      `json:"nonce"`
	ObjectHash  string      `json:"objectHash,omitempty"`
	ObjectSize  int64       `json:"objectSize,omitempty"`
	ContentType string      `json:"contentType,omitempty"`
	Payload     []byte      `json:"payload,omitempty"`
	Signature   []byte      `json:"signature"`
}

// CanonicalBytes is the one wire format shared with the Worker: canonical JSON
// with the fixed field order and payloadHash, excluding the signature.
func (c Command) CanonicalBytes() []byte {
	payloadHash := sha256.Sum256(c.Payload)
	return []byte(fmt.Sprintf(`{"version":%q,"commandId":%q,"nodeId":%q,"type":%q,"issuedAt":%q,"expiresAt":%q,"nonce":%q,"payloadHash":%q}`,
		c.Version, c.CommandID, c.NodeID, c.Type,
		c.IssuedAt.UTC().Format(time.RFC3339Nano), c.ExpiresAt.UTC().Format(time.RFC3339Nano), c.Nonce,
		fmt.Sprintf("%x", payloadHash[:])))
}

func (c Command) BodyHash() string         { d := sha256.Sum256(c.Payload); return fmt.Sprintf("%x", d[:]) }
func (c Command) EncodedSignature() string { return base64.RawURLEncoding.EncodeToString(c.Signature) }

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
	if len(c.Signature) != ed25519.SignatureSize {
		return ErrInvalidSignature
	}
	if c.IssuedAt.After(now.Add(5*time.Minute)) || c.IssuedAt.Before(now.Add(-5*time.Minute)) || !now.Before(c.ExpiresAt) || c.ExpiresAt.Sub(c.IssuedAt) > MaxCommandTTL {
		return ErrExpiredCommand
	}
	if c.ObjectHash != "" && !hashPattern.MatchString(c.ObjectHash) {
		return ErrInvalidSignature
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
	if len(key) != ed25519.PublicKeySize || !ed25519.Verify(key, c.CanonicalBytes(), c.Signature) {
		return ErrInvalidSignature
	}
	return nil
}
