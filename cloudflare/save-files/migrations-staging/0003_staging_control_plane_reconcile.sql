-- Staging-only reconciliation. Never edit or re-run earlier migrations.
-- Idempotent for databases bootstrapped by either staging history.
CREATE TABLE IF NOT EXISTS storage_nodes (
  id TEXT PRIMARY KEY,
  display_name TEXT NOT NULL,
  endpoint TEXT NOT NULL DEFAULT '',
  public_key TEXT NOT NULL,
  fingerprint TEXT,
  protocol_version TEXT NOT NULL DEFAULT 'v1',
  status TEXT NOT NULL DEFAULT 'pending' CHECK (status IN ('pending','approved','draining','revoked','offline','error')),
  capacity_bytes INTEGER NOT NULL DEFAULT 0 CHECK (capacity_bytes >= 0),
  free_bytes INTEGER NOT NULL DEFAULT 0 CHECK (free_bytes >= 0),
  object_count INTEGER NOT NULL DEFAULT 0 CHECK (object_count >= 0),
  enrollment_code_hash TEXT,
  enrollment_expires_at TEXT,
  enrolled_at TEXT,
  approved_at TEXT,
  revoked_at TEXT,
  revoked_reason TEXT,
  last_heartbeat_nonce TEXT,
  last_heartbeat_at TEXT,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS storage_node_nonces (
  node_id TEXT NOT NULL,
  nonce TEXT NOT NULL,
  expires_at TEXT NOT NULL,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (node_id, nonce)
);
CREATE INDEX IF NOT EXISTS idx_storage_node_nonces_expiry ON storage_node_nonces(expires_at);
CREATE INDEX IF NOT EXISTS idx_storage_nodes_staging_health ON storage_nodes(status,last_heartbeat_at);
CREATE INDEX IF NOT EXISTS idx_storage_audits_node_created ON storage_audits(node_id,created_at);
