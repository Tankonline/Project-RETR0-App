package controlplane

import (
	"testing"
	"time"
)

func TestEnsureStagingAcceptsDeployedStagingURL(t *testing.T) {
	cfg := Config{Environment: "staging", BaseURL: "https://save-files-staging.armaandeep08-ap.workers.dev"}
	if err := (&Client{Config: cfg}).ensureStaging(); err != nil {
		t.Fatalf("expected deployed staging URL to be accepted: %v", err)
	}
}

func TestEnsureStagingRejectsInvalidURLs(t *testing.T) {
	cases := []Config{
		{Environment: "production", BaseURL: "https://save-files-staging.armaandeep08-ap.workers.dev"},
		{Environment: "staging", BaseURL: "http://save-files-staging.armaandeep08-ap.workers.dev"},
		{Environment: "staging", BaseURL: "https://save-files.armaandeep08-ap.workers.dev"},
		{Environment: "staging", BaseURL: "https://save-files-staging.armaandeep08-ap.workers.dev.evil.test"},
		{Environment: "staging", BaseURL: "https://user:pass@save-files-staging.armaandeep08-ap.workers.dev"},
		{Environment: "staging", BaseURL: "https://save-files-staging.armaandeep08-ap.workers.dev?debug=1"},
		{Environment: "staging", BaseURL: "https://save-files-staging.armaandeep08-ap.workers.dev#fragment"},
		{Environment: "staging", BaseURL: "https://127.0.0.1/staging"},
		{Environment: "staging", BaseURL: "https://10.0.0.1/staging"},
		{Environment: "staging", BaseURL: "https://192.168.1.10/staging"},
		{Environment: "staging", BaseURL: "https://localhost/staging"},
	}
	for _, cfg := range cases {
		if err := (&Client{Config: cfg}).ensureStaging(); err == nil {
			t.Errorf("expected rejection for %+v", cfg)
		}
	}
}
func TestBackoffBounded(t *testing.T) {
	if backoff(20) != 64*time.Second {
		t.Fatalf("backoff exceeded bound: %v", backoff(20))
	}
}
