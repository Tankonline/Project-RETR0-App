-- Staging Community Hub music index and engagement schema.
-- Mirrors the production Worker contract and is safe for empty or partially
-- bootstrapped staging databases. All DDL is idempotent.
CREATE TABLE IF NOT EXISTS community_music_uploads (
  id TEXT PRIMARY KEY,
  owner_id TEXT NOT NULL,
  file_key TEXT NOT NULL UNIQUE,
  file_size INTEGER NOT NULL CHECK (file_size >= 0),
  file_sha256 TEXT NOT NULL,
  custom_name TEXT NOT NULL,
  uploader_name TEXT NOT NULL,
  target_track TEXT NOT NULL,
  cover_key TEXT,
  cover_sha256 TEXT,
  cover_content_type TEXT,
  deleted_at TEXT,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS music_likes (
  music_id TEXT NOT NULL REFERENCES community_music_uploads(id) ON DELETE CASCADE,
  user_id TEXT NOT NULL,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (music_id, user_id)
);

CREATE TABLE IF NOT EXISTS music_downloads (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  music_id TEXT NOT NULL REFERENCES community_music_uploads(id) ON DELETE CASCADE,
  user_id TEXT,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_community_music_uploads_created
  ON community_music_uploads(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_community_music_uploads_owner
  ON community_music_uploads(owner_id, created_at DESC);
CREATE INDEX IF NOT EXISTS idx_community_music_uploads_active
  ON community_music_uploads(deleted_at, created_at DESC);
CREATE INDEX IF NOT EXISTS idx_music_likes_music
  ON music_likes(music_id);
CREATE INDEX IF NOT EXISTS idx_music_likes_user
  ON music_likes(user_id, music_id);
CREATE INDEX IF NOT EXISTS idx_music_downloads_music
  ON music_downloads(music_id);
CREATE INDEX IF NOT EXISTS idx_music_downloads_user
  ON music_downloads(user_id, music_id);
