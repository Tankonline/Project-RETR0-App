package controlplane

import (
	"context"
	"net/http"
	"net/http/httptest"
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
func TestPostAdminSendsSecretHeader(t *testing.T) {
	server := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		if got := r.Header.Get("X-Storage-Admin"); got != "secret-value" {
			t.Fatalf("header = %q", got)
		}
		w.WriteHeader(http.StatusOK)
	}))
	defer server.Close()
	client := &Client{Config: Config{Environment: "staging", BaseURL: "https://save-files-staging.armaandeep08-ap.workers.dev"}, HTTP: server.Client()}
	client.Config.BaseURL = server.URL
	result, err := client.postAdmin(context.Background(), "", "secret-value", map[string]string{"nodeId": "node"})
	if err != nil || result.ErrorCode != "" {
		t.Fatalf("postAdmin failed: result=%+v err=%v", result, err)
	}
}

func TestBackoffBounded(t *testing.T) {
	if backoff(20) != 64*time.Second {
		t.Fatalf("backoff exceeded bound: %v", backoff(20))
	}
}
