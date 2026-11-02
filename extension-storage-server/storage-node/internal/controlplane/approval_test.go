package controlplane

import (
	"encoding/json"
	"strings"
	"testing"
	"time"

	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/auth"
)

func TestBuildApprovalEnvelope(t *testing.T) {
	id := auth.Identity{NodeID: "node-1", PublicKey: "AAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8"}
	now := time.Date(2026, 1, 2, 3, 4, 5, 0, time.UTC)
	body, err := BuildApprovalEnvelope(id, "enrollment-code", now)
	if err != nil {
		t.Fatal(err)
	}
	data, err := json.Marshal(body)
	if err != nil {
		t.Fatal(err)
	}
	text := string(data)
	for _, field := range []string{"\"nodeId\":\"node-1\"", "\"publicKey\"", "\"enrollmentCode\":\"enrollment-code\"", "\"nonce\"", "\"issuedAt\":\"2026-01-02T03:04:05Z\"", "\"expiresAt\":\"2026-01-02T03:19:05Z\"", "\"signature\":\"staging-admin\""} {
		if !strings.Contains(text, field) {
			t.Fatalf("missing %s in %s", field, text)
		}
	}
}
