-- Staging-only node enrollment and heartbeat metadata. Additive; no production replication.
ALTER TABLE storage_nodes ADD COLUMN enrollment_code_hash TEXT;
ALTER TABLE storage_nodes ADD COLUMN enrolled_at TEXT;
ALTER TABLE storage_nodes ADD COLUMN last_heartbeat_nonce TEXT;
ALTER TABLE storage_nodes ADD COLUMN last_heartbeat_at TEXT;
CREATE INDEX IF NOT EXISTS idx_storage_nodes_heartbeat ON storage_nodes(last_heartbeat_at);
