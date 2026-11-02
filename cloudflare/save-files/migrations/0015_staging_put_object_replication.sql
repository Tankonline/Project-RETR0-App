-- Staging-only PUT_OBJECT compatibility migration. Additive; never apply remotely from this repository.
CREATE TABLE IF NOT EXISTS storage_objects (
  hash TEXT PRIMARY KEY CHECK (length(hash) = 64),
  size_bytes INTEGER NOT NULL CHECK (size_bytes >= 0),
  content_type TEXT,
  r2_key TEXT NOT NULL UNIQUE,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS storage_command_payloads (
  command_id TEXT PRIMARY KEY REFERENCES storage_commands(id) ON DELETE CASCADE,
  payload_json TEXT NOT NULL,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_storage_commands_delivery ON storage_commands(node_id,status,expires_at);
