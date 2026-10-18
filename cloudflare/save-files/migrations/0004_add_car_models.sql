CREATE TABLE IF NOT EXISTS car_models (
  id TEXT PRIMARY KEY,
  owner_id TEXT NOT NULL,
  title TEXT NOT NULL,
  creator_name TEXT NOT NULL,
  description TEXT NOT NULL DEFAULT '',
  target_vehicle_directory TEXT NOT NULL,
  package_id TEXT NOT NULL,
  supported_versions TEXT NOT NULL DEFAULT '',
  manifest_version INTEGER NOT NULL DEFAULT 1,
  package_name TEXT NOT NULL DEFAULT '',
  manifest_key TEXT,
  manifest_size INTEGER,
  manifest_sha256 TEXT,
  file_count INTEGER NOT NULL DEFAULT 0,
  total_size INTEGER NOT NULL DEFAULT 0,
  cover_key TEXT,
  cover_sha256 TEXT,
  cover_content_type TEXT,
  -- Existing deployments constrain status; invalid_asset is represented by invalidated_at/invalid_reason in migration 0005.
  status TEXT NOT NULL DEFAULT 'draft' CHECK (status IN ('draft','uploading','published','pending','approved','rejected','hidden')),
  idempotency_key TEXT NOT NULL,
  moderation_note TEXT,
  replacement_mode TEXT NOT NULL DEFAULT 'full_directory',
  backup_metadata_json TEXT,
  revert_metadata_json TEXT,
  deleted_at TEXT,
  approved_at TEXT,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE(owner_id, idempotency_key)
);

CREATE INDEX IF NOT EXISTS idx_car_models_public
ON car_models(status, deleted_at, created_at DESC);
CREATE INDEX IF NOT EXISTS idx_car_models_owner
ON car_models(owner_id, status, updated_at DESC);
CREATE INDEX IF NOT EXISTS idx_car_models_target
ON car_models(target_vehicle_directory, package_id, status);

-- Published rows are only catalog candidates; Worker validation remains authoritative for R2 integrity.
-- Keep these nullable/derived fields compatible with older drafts and repair hidden orphan rows at read time.

CREATE TABLE IF NOT EXISTS car_model_files (
  id TEXT PRIMARY KEY,
  model_id TEXT NOT NULL REFERENCES car_models(id) ON DELETE CASCADE,
  relative_path TEXT NOT NULL,
  destination_relative_path TEXT NOT NULL,
  role TEXT NOT NULL DEFAULT 'asset',
  detected_format TEXT,
  variant TEXT,
  required INTEGER NOT NULL DEFAULT 0 CHECK (required IN (0,1)),
  object_key TEXT NOT NULL,
  file_size INTEGER NOT NULL,
  file_sha256 TEXT NOT NULL,
  content_type TEXT,
  uploaded_at TEXT,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE(model_id, relative_path),
  UNIQUE(model_id, object_key)
);
CREATE INDEX IF NOT EXISTS idx_car_model_files_model ON car_model_files(model_id, relative_path);
CREATE INDEX IF NOT EXISTS idx_car_model_files_format ON car_model_files(detected_format, variant);

CREATE TABLE IF NOT EXISTS car_model_screenshots (
  id TEXT PRIMARY KEY,
  model_id TEXT NOT NULL REFERENCES car_models(id) ON DELETE CASCADE,
  position INTEGER NOT NULL,
  object_key TEXT NOT NULL,
  sha256 TEXT NOT NULL,
  content_type TEXT NOT NULL,
  caption TEXT NOT NULL DEFAULT '',
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE(model_id, position)
);
CREATE INDEX IF NOT EXISTS idx_car_model_screenshots_model ON car_model_screenshots(model_id, position);

CREATE TABLE IF NOT EXISTS car_model_likes (
  model_id TEXT NOT NULL REFERENCES car_models(id) ON DELETE CASCADE,
  user_id TEXT NOT NULL,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(model_id, user_id)
);
CREATE INDEX IF NOT EXISTS idx_car_model_likes_model ON car_model_likes(model_id);

CREATE TABLE IF NOT EXISTS car_model_reports (
  id TEXT PRIMARY KEY,
  model_id TEXT NOT NULL REFERENCES car_models(id) ON DELETE CASCADE,
  reporter_id TEXT NOT NULL,
  reason TEXT NOT NULL,
  details TEXT NOT NULL DEFAULT '',
  status TEXT NOT NULL DEFAULT 'open' CHECK (status IN ('open','reviewed','dismissed','actioned')),
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE(model_id, reporter_id)
);
CREATE INDEX IF NOT EXISTS idx_car_model_reports_status ON car_model_reports(status, created_at);
