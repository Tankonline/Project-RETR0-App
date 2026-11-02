package api

import (
	"crypto/subtle"
	"encoding/json"
	"net/http"
	"os"
)

func (s Server) enrollRequest(w http.ResponseWriter, r *http.Request) {
	var body struct {
		NodeID string `json:"nodeId"`
	}
	if json.NewDecoder(http.MaxBytesReader(w, r.Body, 64*1024)).Decode(&body) != nil || body.NodeID != r.PathValue("nodeId") {
		writeJSON(w, http.StatusBadRequest, map[string]any{"error": map[string]string{"code": "node_id_mismatch", "message": "nodeId does not match the route"}, "requestId": r.Header.Get("X-Request-ID")})
		return
	}
	writeJSON(w, http.StatusCreated, map[string]any{"nodeId": body.NodeID, "status": "pending", "requestId": r.Header.Get("X-Request-ID")})
}

func (s Server) enrollApprove(w http.ResponseWriter, r *http.Request) {
	var body struct {
		NodeID string `json:"nodeId"`
	}
	if json.NewDecoder(http.MaxBytesReader(w, r.Body, 64*1024)).Decode(&body) != nil || body.NodeID != r.PathValue("nodeId") {
		writeJSON(w, http.StatusBadRequest, map[string]any{"error": map[string]string{"code": "node_id_mismatch", "message": "nodeId does not match the route"}, "requestId": r.Header.Get("X-Request-ID")})
		return
	}
	expected := os.Getenv("STORAGE_STAGING_ADMIN_TOKEN")
	provided := r.Header.Get("X-Storage-Admin")
	if expected == "" || provided == "" || provided == "staging" || subtle.ConstantTimeCompare([]byte(provided), []byte(expected)) != 1 {
		writeJSON(w, http.StatusForbidden, map[string]any{"error": map[string]string{"code": "admin_required", "message": "Admin authorization required"}, "requestId": r.Header.Get("X-Request-ID")})
		return
	}
	writeJSON(w, http.StatusOK, map[string]any{"nodeId": body.NodeID, "status": "approved", "requestId": r.Header.Get("X-Request-ID")})
}
