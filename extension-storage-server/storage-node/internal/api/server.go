package api

import (
	"encoding/json"
	"fmt"
	"net/http"
	"path/filepath"

	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/health"
	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/store"
)

type Server struct {
	Store store.FileSystemStore
}

func (s Server) Handler() http.Handler {
	mux := http.NewServeMux()
	mux.HandleFunc("GET /healthz", s.health)
	mux.HandleFunc("GET /v1/local/capacity", s.capacity)
	mux.HandleFunc("HEAD /v1/local/objects/{hash}", s.headObject)
	mux.HandleFunc("GET /v1/local/objects/{hash}", s.getObject)
	return mux
}

func (s Server) health(w http.ResponseWriter, _ *http.Request) {
	writeJSON(w, http.StatusOK, map[string]string{"status": "ok"})
}

func (s Server) capacity(w http.ResponseWriter, _ *http.Request) {
	snapshot, err := health.Read(s.Store.Root)
	if err != nil {
		writeJSON(w, http.StatusInternalServerError, map[string]string{"error": "capacity_unavailable"})
		return
	}
	writeJSON(w, http.StatusOK, snapshot)
}

func (s Server) headObject(w http.ResponseWriter, r *http.Request) {
	_, info, err := s.Store.Open(r.PathValue("hash"))
	if err != nil {
		http.NotFound(w, r)
		return
	}
	w.Header().Set("Content-Length", fmt.Sprintf("%d", info.Size()))
	w.WriteHeader(http.StatusOK)
}

func (s Server) getObject(w http.ResponseWriter, r *http.Request) {
	file, info, err := s.Store.Open(r.PathValue("hash"))
	if err != nil {
		http.NotFound(w, r)
		return
	}
	defer file.Close()
	w.Header().Set("Content-Length", fmt.Sprintf("%d", info.Size()))
	http.ServeContent(w, r, filepath.Base(file.Name()), info.ModTime(), file)
}

func writeJSON(w http.ResponseWriter, status int, value any) {
	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(status)
	_ = json.NewEncoder(w).Encode(value)
}

