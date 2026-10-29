-- Repair the four car-model child tables rebuilt by migration 0006.
--
-- Required preflight: capture and compare the production sqlite_master and
-- PRAGMA output for all four tables, indexes, and foreign keys with migrations
-- 0004/0005. STOP if production contains any column/constraint/index not
-- represented below; this migration intentionally preserves the repository-
-- known schema only.
--
-- D1/Wrangler note: do not add BEGIN/COMMIT. D1 remote --file execution can
-- reject explicit transaction statements. Each table is copied before its
-- old definition is dropped; a failed statement stops the file and never
-- reaches later table rebuilds. Foreign-key enforcement is not disabled.
-- The temporary names are unique to 0007 and cannot collide with the dropped
-- car_models__status_migration_backup from 0006.

CREATE TABLE car_model_files__fk_repair_0007 (
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
INSERT INTO car_model_files__fk_repair_0007
SELECT id, model_id, relative_path, destination_relative_path, role,
       detected_format, variant, required, object_key, file_size,
       file_sha256, content_type, uploaded_at, created_at
FROM car_model_files;
DROP TABLE car_model_files;
ALTER TABLE car_model_files__fk_repair_0007 RENAME TO car_model_files;
CREATE INDEX idx_car_model_files_model ON car_model_files(model_id, relative_path);
CREATE INDEX idx_car_model_files_format ON car_model_files(detected_format, variant);

CREATE TABLE car_model_screenshots__fk_repair_0007 (
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
INSERT INTO car_model_screenshots__fk_repair_0007
SELECT id, model_id, position, object_key, sha256, content_type, caption,
       created_at
FROM car_model_screenshots;
DROP TABLE car_model_screenshots;
ALTER TABLE car_model_screenshots__fk_repair_0007 RENAME TO car_model_screenshots;
CREATE INDEX idx_car_model_screenshots_model
  ON car_model_screenshots(model_id, position);

CREATE TABLE car_model_likes__fk_repair_0007 (
  model_id TEXT NOT NULL REFERENCES car_models(id) ON DELETE CASCADE,
  user_id TEXT NOT NULL,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(model_id, user_id)
);
INSERT INTO car_model_likes__fk_repair_0007
SELECT model_id, user_id, created_at
FROM car_model_likes;
DROP TABLE car_model_likes;
ALTER TABLE car_model_likes__fk_repair_0007 RENAME TO car_model_likes;
CREATE INDEX idx_car_model_likes_model ON car_model_likes(model_id);

CREATE TABLE car_model_reports__fk_repair_0007 (
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
INSERT INTO car_model_reports__fk_repair_0007
SELECT id, model_id, reporter_id, reason, details, status, created_at,
       updated_at
FROM car_model_reports;
DROP TABLE car_model_reports;
ALTER TABLE car_model_reports__fk_repair_0007 RENAME TO car_model_reports;
CREATE INDEX idx_car_model_reports_status
  ON car_model_reports(status, created_at);
