package api

import (
	"crypto/ed25519"
	"encoding/json"
	"fmt"
	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/auth"
	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/health"
	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/protocol"
	"github.com/Tankonline/project-retr0-extension-storage/storage-node/internal/store"
	"io"
	"net/http"
	"path/filepath"
	"time"
)

type Server struct {
	Store      store.FileSystemStore
	NodeID     string
	ControlKey ed25519.PublicKey
	Replay     *auth.ReplayCache
	Now        func() time.Time
}

func (s Server) Handler() http.Handler {
	m := http.NewServeMux()
	m.HandleFunc("POST /v1/storage/nodes/heartbeat", s.heartbeat)
	m.HandleFunc("POST /v1/storage/nodes/{nodeId}/heartbeat", s.heartbeat)
	m.HandleFunc("GET /v1/storage/nodes/{nodeId}/status", s.status)
	m.HandleFunc("GET /healthz", s.health)
	m.HandleFunc("GET /v1/local/capacity", s.capacity)
	m.HandleFunc("HEAD /v1/local/objects/{hash}", s.headObject)
	m.HandleFunc("GET /v1/local/objects/{hash}", s.getObject)
	m.HandleFunc("PUT /v1/objects/{hash}", s.putObject)
	m.HandleFunc("GET /v1/objects/{hash}", s.authenticatedGetObject)
	m.HandleFunc("HEAD /v1/objects/{hash}", s.authenticatedHeadObject)
	m.HandleFunc("DELETE /v1/objects/{hash}", s.deleteObject)
	return m
}
func (s Server) health(w http.ResponseWriter, _ *http.Request) {
	writeJSON(w, 200, map[string]string{"status": "ok", "nodeId": s.NodeID})
}
func (s Server) heartbeat(w http.ResponseWriter, r *http.Request) {
	c, e := decodeCommand(w, r)
	if e != nil || c.Type != protocol.Heartbeat || c.NodeID != s.NodeID || s.validate(c) != nil {
		writeJSON(w, 401, map[string]string{"error": "unauthorized_heartbeat"})
		return
	}
	writeJSON(w, 200, map[string]string{"status": "accepted"})
}
func (s Server) status(w http.ResponseWriter, r *http.Request) {
	if r.PathValue("nodeId") != s.NodeID {
		writeJSON(w, 404, map[string]string{"error": "node_not_found"})
		return
	}
	writeJSON(w, 200, map[string]any{"nodeId": s.NodeID, "status": "approved"})
}
func (s Server) validate(c protocol.Command) error {
	now := time.Now
	if s.Now != nil {
		now = s.Now
	}
	if e := protocol.Verify(c, s.NodeID, s.ControlKey, now()); e != nil {
		return e
	}
	if s.Replay != nil && !s.Replay.Consume(c.CommandID, c.ExpiresAt, now()) {
		return auth.ErrReplay
	}
	return nil
}
func (s Server) capacity(w http.ResponseWriter, _ *http.Request) {
	v, e := health.Read(s.Store.Root)
	if e != nil {
		writeJSON(w, 500, map[string]string{"error": "capacity_unavailable"})
		return
	}
	writeJSON(w, 200, v)
}
func (s Server) headObject(w http.ResponseWriter, r *http.Request) {
	_, i, e := s.Store.Open(r.PathValue("hash"))
	if e != nil {
		http.NotFound(w, r)
		return
	}
	w.Header().Set("Content-Length", fmt.Sprintf("%d", i.Size()))
	w.WriteHeader(200)
}
func (s Server) getObject(w http.ResponseWriter, r *http.Request) {
	f, i, e := s.Store.Open(r.PathValue("hash"))
	if e != nil {
		http.NotFound(w, r)
		return
	}
	defer f.Close()
	w.Header().Set("Content-Length", fmt.Sprintf("%d", i.Size()))
	http.ServeContent(w, r, filepath.Base(f.Name()), i.ModTime(), f)
}
func decodeCommand(w http.ResponseWriter, r *http.Request) (protocol.Command, error) {
	var c protocol.Command
	e := json.NewDecoder(http.MaxBytesReader(w, r.Body, protocol.MaxEnvelopeBytes)).Decode(&c)
	return c, e
}
func (s Server) command(w http.ResponseWriter, r *http.Request, hash string, t protocol.CommandType) (protocol.Command, error) {
	c, e := decodeCommand(w, r)
	if e != nil || c.Type != t || c.ObjectHash != hash {
		return c, protocol.ErrInvalidSignature
	}
	return c, s.validate(c)
}
func (s Server) putObject(w http.ResponseWriter, r *http.Request) {
	c, e := s.command(w, r, r.PathValue("hash"), protocol.PutObject)
	if e != nil {
		writeJSON(w, 401, map[string]string{"error": "unauthorized_command"})
		return
	}
	n, e := s.Store.Put(c.ObjectHash, c.ObjectSize, r.Body, c.ContentType)
	if e != nil {
		writeJSON(w, 422, map[string]string{"error": "object_verification_failed"})
		return
	}
	writeJSON(w, 201, map[string]any{"hash": c.ObjectHash, "sizeBytes": n})
}
func (s Server) authenticatedHeadObject(w http.ResponseWriter, r *http.Request) {
	if _, e := s.command(w, r, r.PathValue("hash"), protocol.HeadObject); e != nil {
		writeJSON(w, 401, map[string]string{"error": "unauthorized_command"})
		return
	}
	s.headObject(w, r)
}
func (s Server) authenticatedGetObject(w http.ResponseWriter, r *http.Request) {
	if _, e := s.command(w, r, r.PathValue("hash"), protocol.GetObject); e != nil {
		writeJSON(w, 401, map[string]string{"error": "unauthorized_command"})
		return
	}
	s.getObject(w, r)
}
func (s Server) deleteObject(w http.ResponseWriter, r *http.Request) {
	if _, e := s.command(w, r, r.PathValue("hash"), protocol.DeleteObject); e != nil {
		writeJSON(w, 401, map[string]string{"error": "unauthorized_command"})
		return
	}
	if e := s.Store.Remove(r.PathValue("hash")); e != nil {
		if e == io.EOF {
			http.NotFound(w, r)
		} else {
			http.NotFound(w, r)
		}
		return
	}
	w.WriteHeader(204)
}
func writeJSON(w http.ResponseWriter, status int, v any) {
	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(status)
	_ = json.NewEncoder(w).Encode(v)
}
