-- Staging-only control-plane hardening.
-- The bootstrap migration already creates storage_nodes,
-- storage_commands, and storage_audits. Add only replay-protection storage.

CREATE TABLE IF NOT EXISTS storage_node_nonces (
  node_id TEXT NOT NULL,
  nonce TEXT NOT NULL,
  seen_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  expires_at TEXT,
  PRIMARY KEY (node_id, nonce)
);

CREATE INDEX IF NOT EXISTS idx_storage_node_nonces_expiry
  ON storage_node_nonces(expires_at);

CREATE INDEX IF NOT EXISTS idx_storage_node_nonces_seen
  ON storage_node_nonces(seen_at);
