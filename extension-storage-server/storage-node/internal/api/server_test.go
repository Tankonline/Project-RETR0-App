package api

import (
	"net/http/httptest"
	"testing"

	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/store"
)

func TestHealth(t *testing.T) {
	handler := Server{Store: store.FileSystemStore{Root: t.TempDir()}}.Handler()
	request := httptest.NewRequest("GET", "/healthz", nil)
	recorder := httptest.NewRecorder()
	handler.ServeHTTP(recorder, request)
	if recorder.Code != 200 {
		t.Fatalf("status = %d", recorder.Code)
	}
}
