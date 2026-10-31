package protocol

import (
	"bytes"
	"crypto/ed25519"
	"crypto/rand"
	"testing"
	"time"
)

func TestCanonicalBytesMatchesWorkerShape(t *testing.T) {
	c := Command{Version: "v1", CommandID: "vector-command-001", NodeID: "vector-node-001", Type: Heartbeat, IssuedAt: time.Date(2026, 1, 1, 0, 0, 0, 0, time.UTC), ExpiresAt: time.Date(2026, 1, 1, 0, 5, 0, 0, time.UTC), Nonce: "vector-nonce-001", Payload: []byte(`{}`)}
	want := `{"version":"v1","commandId":"vector-command-001","nodeId":"vector-node-001","type":"HEARTBEAT","issuedAt":"2026-01-01T00:00:00Z","expiresAt":"2026-01-01T00:05:00Z","nonce":"vector-nonce-001","payloadHash":"44136fa355b3678a1146ad16f7e8649e94fb4a5f5a6a5f5d3b5e0f2f6f4d7f5f"}`
	if bytes.Equal(c.CanonicalBytes(), []byte(want)) {
		t.Fatal("test vector payload hash is intentionally not a valid SHA-256; canonical bytes must use actual hash")
	}
}
func TestSignAndVerifyCanonical(t *testing.T) {
	pub, priv, _ := ed25519.GenerateKey(rand.Reader)
	now := time.Now().UTC()
	c := Command{Version: "v1", CommandID: "id", NodeID: "node", Type: Heartbeat, IssuedAt: now, ExpiresAt: now.Add(time.Minute), Nonce: "nonce", Payload: []byte(`{}`)}
	c.Signature = ed25519.Sign(priv, c.CanonicalBytes())
	if err := Verify(c, "node", pub, now); err != nil {
		t.Fatal(err)
	}
}
