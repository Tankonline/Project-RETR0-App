-- Extension storage foundation (additive; does not alter existing Community Hub tables).
-- No secrets, tokens, or production node credentials belong in D1.

CREATE TABLE IF NOT EXISTS storage_nodes (
  id TEXT PRIMARY KEY,
  display_name TEXT NOT NULL,
  endpoint TEXT NOT NULL,
  public_key TEXT NOT NULL,
  status TEXT NOT NULL DEFAULT 'pending' CHECK (status IN ('pending','approved','draining','revoked','offline')),
  capacity_bytes INTEGER NOT NULL DEFAULT 0 CHECK (capacity_bytes >= 0),
  free_bytes INTEGER NOT NULL DEFAULT 0 CHECK (free_bytes >= 0),
  last_heartbeat_at TEXT,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS storage_objects (
  hash TEXT PRIMARY KEY CHECK (length(hash) = 64),
  size_bytes INTEGER NOT NULL CHECK (size_bytes >= 0),
  content_type TEXT,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS storage_replicas (
  object_hash TEXT NOT NULL REFERENCES storage_objects(hash) ON DELETE CASCADE,
  node_id TEXT NOT NULL REFERENCES storage_nodes(id) ON DELETE CASCADE,
  state TEXT NOT NULL DEFAULT 'queued' CHECK (state IN ('queued','copying','healthy','stale','missing','failed','deleted')),
  verified_at TEXT,
  last_error TEXT,
  updated_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (object_hash, node_id)
);

CREATE TABLE IF NOT EXISTS storage_commands (
  id TEXT PRIMARY KEY,
  node_id TEXT NOT NULL REFERENCES storage_nodes(id) ON DELETE CASCADE,
  command_type TEXT NOT NULL,
  payload_json TEXT NOT NULL,
  issued_at TEXT NOT NULL,
  expires_at TEXT NOT NULL,
  nonce TEXT NOT NULL UNIQUE,
  signature TEXT NOT NULL,
  status TEXT NOT NULL DEFAULT 'issued' CHECK (status IN ('issued','accepted','completed','failed','expired','rejected')),
  completed_at TEXT,
  result_json TEXT
);

CREATE TABLE IF NOT EXISTS storage_audits (
  id TEXT PRIMARY KEY,
  actor_type TEXT NOT NULL,
  actor_id TEXT,
  action TEXT NOT NULL,
  node_id TEXT,
  object_hash TEXT,
  command_id TEXT,
  details_json TEXT NOT NULL DEFAULT '{}',
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS storage_server_directory (
  id TEXT PRIMARY KEY,
  node_id TEXT NOT NULL REFERENCES storage_nodes(id) ON DELETE CASCADE,
  base_url TEXT NOT NULL,
  region TEXT,
  priority INTEGER NOT NULL DEFAULT 100,
  enabled INTEGER NOT NULL DEFAULT 1 CHECK (enabled IN (0,1)),
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_storage_nodes_status ON storage_nodes(status, last_heartbeat_at);
CREATE INDEX IF NOT EXISTS idx_storage_replicas_node_state ON storage_replicas(node_id, state);
CREATE INDEX IF NOT EXISTS idx_storage_replicas_object_state ON storage_replicas(object_hash, state);
CREATE INDEX IF NOT EXISTS idx_storage_commands_node_status ON storage_commands(node_id, status, expires_at);
CREATE INDEX IF NOT EXISTS idx_storage_audits_created ON storage_audits(created_at);
CREATE INDEX IF NOT EXISTS idx_storage_directory_enabled ON storage_server_directory(enabled, priority);
