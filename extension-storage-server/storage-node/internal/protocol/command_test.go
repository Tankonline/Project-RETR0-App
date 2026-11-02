package protocol

import (
	"bytes"
	"crypto/ed25519"
	"crypto/rand"
	"encoding/base64"
	"testing"
	"time"
)

func TestCanonicalBytesMatchesVector(t *testing.T) {
	c := Command{Version: "v1", CommandID: "vector-command-001", NodeID: "vector-node-001", Type: Heartbeat, IssuedAt: time.Date(2026, 1, 1, 0, 0, 0, 0, time.UTC), ExpiresAt: time.Date(2026, 1, 1, 0, 5, 0, 0, time.UTC), Nonce: "vector-nonce-001", Payload: []byte(`{"capacityBytes":1000,"freeBytes":600,"objectCount":7}`)}
	want := `{"version":"v1","commandId":"vector-command-001","nodeId":"vector-node-001","type":"HEARTBEAT","issuedAt":"2026-01-01T00:00:00Z","expiresAt":"2026-01-01T00:05:00Z","nonce":"vector-nonce-001","payloadHash":"ceadcd9936aa0184a03b6855c79bcf8b31f960133043f0d7fa2b50f9d087e970"}`
	if !bytes.Equal(c.CanonicalBytes(), []byte(want)) {
		t.Fatalf("canonical bytes mismatch: %s", c.CanonicalBytes())
	}
}

func TestSignAndVerifyCanonical(t *testing.T) {
	pub, priv, _ := ed25519.GenerateKey(rand.Reader)
	now := time.Now().UTC()
	c := Command{Version: "v1", CommandID: "id", NodeID: "node", Type: Heartbeat, IssuedAt: now, ExpiresAt: now.Add(time.Minute), Nonce: "nonce", Payload: []byte(`{"capacityBytes":10,"freeBytes":5,"objectCount":1}`)}
	c.Signature = base64.RawURLEncoding.EncodeToString(ed25519.Sign(priv, c.CanonicalBytes()))
	if err := Verify(c, "node", pub, now); err != nil {
		t.Fatal(err)
	}
	for name, mutate := range map[string]func(*Command){"altered-body": func(v *Command) { v.Payload = []byte(`{"capacityBytes":10,"freeBytes":4,"objectCount":1}`) }, "altered-path": func(v *Command) { v.NodeID = "other" }, "expired": func(v *Command) { v.ExpiresAt = now.Add(-time.Second) }, "bad-signature": func(v *Command) { v.Signature = base64.RawURLEncoding.EncodeToString([]byte("bad")) }} {
		copy := c
		mutate(&copy)
		if err := Verify(copy, "node", pub, now); err == nil {
			t.Errorf("%s: expected rejection", name)
		}
	}
}
