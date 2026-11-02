-- Staging-only control plane hardening. Apply only to a staging D1 database.
-- No secrets, production credentials, replication, directory, or Android exposure.

ALTER TABLE storage_nodes ADD COLUMN IF NOT EXISTS enrollment_code_hash TEXT;
ALTER TABLE storage_nodes ADD COLUMN IF NOT EXISTS enrollment_expires_at TEXT;
ALTER TABLE storage_nodes ADD COLUMN IF NOT EXISTS enrolled_at TEXT;
ALTER TABLE storage_nodes ADD COLUMN IF NOT EXISTS last_heartbeat_nonce TEXT;
ALTER TABLE storage_nodes ADD COLUMN IF NOT EXISTS last_heartbeat_at TEXT;
ALTER TABLE storage_nodes ADD COLUMN IF NOT EXISTS revoked_at TEXT;
ALTER TABLE storage_nodes ADD COLUMN IF NOT EXISTS drain_reason TEXT;

CREATE TABLE IF NOT EXISTS storage_node_nonces (
  node_id TEXT NOT NULL REFERENCES storage_nodes(id) ON DELETE CASCADE,
  nonce TEXT NOT NULL,
  expires_at TEXT NOT NULL,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (node_id, nonce)
);
CREATE INDEX IF NOT EXISTS idx_storage_node_nonces_expiry ON storage_node_nonces(expires_at);
CREATE INDEX IF NOT EXISTS idx_storage_nodes_staging_health ON storage_nodes(status, last_heartbeat_at);
CREATE INDEX IF NOT EXISTS idx_storage_audits_node_created ON storage_audits(node_id, created_at);
