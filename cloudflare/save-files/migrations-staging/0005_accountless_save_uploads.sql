-- Staging Community Hub save index schema.
-- Non-destructive and idempotent: preserves any existing rows and indexes.
CREATE TABLE IF NOT EXISTS accountless_save_uploads (
  id TEXT PRIMARY KEY,
  owner_id TEXT NOT NULL,
  file_key TEXT NOT NULL UNIQUE,
  file_size INTEGER NOT NULL CHECK (file_size >= 0),
  file_sha256 TEXT NOT NULL,
  custom_name TEXT NOT NULL,
  uploader_name TEXT NOT NULL,
  player_level INTEGER NOT NULL DEFAULT 0 CHECK (player_level >= 0),
  player_gold INTEGER NOT NULL DEFAULT 0 CHECK (player_gold >= 0),
  player_rs INTEGER NOT NULL DEFAULT 0 CHECK (player_rs >= 0),
  player_ms INTEGER NOT NULL DEFAULT 0 CHECK (player_ms >= 0),
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_accountless_save_uploads_created
  ON accountless_save_uploads(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_accountless_save_uploads_owner
  ON accountless_save_uploads(owner_id, created_at DESC);
