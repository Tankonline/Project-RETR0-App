package controlplane

import (
	"testing"
	"time"
)

func TestEnsureStagingRejectsNonHTTPSAndProduction(t *testing.T) {
	cases := []Config{{Environment: "production", BaseURL: "https://staging.example.test/api"}, {Environment: "staging", BaseURL: "http://staging.example.test/api"}, {Environment: "staging", BaseURL: "https://127.0.0.1/api"}, {Environment: "staging", BaseURL: "https://staging.example.test"}}
	for _, cfg := range cases {
		if err := (&Client{Config: cfg}).ensureStaging(); err == nil {
			t.Fatalf("expected rejection for %+v", cfg)
		}
	}
}
func TestBackoffBounded(t *testing.T) {
	if backoff(20) != 64*time.Second {
		t.Fatalf("backoff exceeded bound: %v", backoff(20))
	}
}
