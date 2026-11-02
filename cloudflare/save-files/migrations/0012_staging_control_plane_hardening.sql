-- Staging-only enrollment lifecycle and replay/rate-limit metadata.
ALTER TABLE storage_nodes ADD COLUMN enrollment_code_hash TEXT;
ALTER TABLE storage_nodes ADD COLUMN enrollment_expires_at TEXT;
ALTER TABLE storage_nodes ADD COLUMN revoked_at TEXT;
ALTER TABLE storage_nodes ADD COLUMN drain_reason TEXT;
CREATE TABLE IF NOT EXISTS storage_node_nonces (
  node_id TEXT NOT NULL REFERENCES storage_nodes(id) ON DELETE CASCADE,
  nonce TEXT NOT NULL,
  expires_at TEXT NOT NULL,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (node_id, nonce)
);
CREATE INDEX IF NOT EXISTS idx_storage_node_nonces_expiry ON storage_node_nonces(expires_at);
CREATE INDEX IF NOT EXISTS idx_storage_nodes_status_heartbeat ON storage_nodes(status, last_heartbeat_at);
