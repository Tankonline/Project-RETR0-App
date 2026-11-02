-- Staging-only PUT_OBJECT replication state and command delivery metadata.
-- Additive only; do not apply remotely from this repository.

CREATE TABLE IF NOT EXISTS storage_objects (
  hash TEXT PRIMARY KEY CHECK (length(hash) = 64),
  size_bytes INTEGER NOT NULL CHECK (size_bytes >= 0),
  content_type TEXT,
  r2_key TEXT NOT NULL,
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

CREATE TABLE IF NOT EXISTS storage_command_payloads (
  command_id TEXT PRIMARY KEY REFERENCES storage_commands(id) ON DELETE CASCADE,
  payload_json TEXT NOT NULL,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_storage_commands_delivery ON storage_commands(node_id, status, expires_at);
CREATE INDEX IF NOT EXISTS idx_storage_replicas_queue ON storage_replicas(node_id, state, updated_at);
