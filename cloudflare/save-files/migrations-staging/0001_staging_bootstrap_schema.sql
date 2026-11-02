-- Staging-only bootstrap schema for the isolated extension-storage control plane.
-- Production migration history and Community Hub tables are intentionally not used.

CREATE TABLE IF NOT EXISTS storage_nodes (
  id TEXT PRIMARY KEY,
  display_name TEXT NOT NULL,
  endpoint TEXT NOT NULL DEFAULT '',
  public_key TEXT NOT NULL,
  fingerprint TEXT,
  protocol_version TEXT NOT NULL DEFAULT 'v1',
  status TEXT NOT NULL DEFAULT 'pending' CHECK (status IN ('pending','approved','draining','revoked','offline','error')),
  software_version TEXT,
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

CREATE TABLE IF NOT EXISTS storage_commands (
  id TEXT PRIMARY KEY,
  node_id TEXT NOT NULL,
  command_type TEXT NOT NULL,
  nonce TEXT NOT NULL UNIQUE,
  issued_at TEXT NOT NULL,
  expires_at TEXT NOT NULL,
  signature TEXT,
  status TEXT NOT NULL DEFAULT 'accepted',
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS storage_audits (
  id TEXT PRIMARY KEY,
  actor_type TEXT NOT NULL,
  actor_id TEXT,
  action TEXT NOT NULL,
  node_id TEXT,
  details_json TEXT NOT NULL DEFAULT '{}',
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_storage_nodes_status_heartbeat ON storage_nodes(status, last_heartbeat_at);
CREATE INDEX IF NOT EXISTS idx_storage_commands_node_expiry ON storage_commands(node_id, expires_at);
CREATE INDEX IF NOT EXISTS idx_storage_audits_node_created ON storage_audits(node_id, created_at);
