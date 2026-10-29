-- Rebuild car_models solely to widen its status CHECK constraint.
-- Preflight is mandatory: this migration must only run after the production
-- car_models schema has been captured and compared with the repository schema.
-- SQLite has no ALTER CHECK; preserve the complete known table definition,
-- including migration 0005 columns, constraints, and defaults.
--
-- IMPORTANT: If production has columns/constraints not represented below,
-- STOP. Export sqlite_master/PRAGMA metadata and generate a reviewed variant
-- that includes them before applying. Do not guess omitted production schema.

PRAGMA foreign_keys = OFF;


-- Wrangler applies this file only after the existing car_models table exists.
-- The standalone sqlite3 :memory: check therefore requires a schema fixture.
ALTER TABLE car_models RENAME TO car_models__status_migration_backup;

CREATE TABLE car_models (
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
  status TEXT NOT NULL DEFAULT 'draft' CHECK (status IN ('draft','uploading','pending','approved','rejected','hidden','published')),
  idempotency_key TEXT NOT NULL,
  moderation_note TEXT,
  replacement_mode TEXT NOT NULL DEFAULT 'full_directory',
  backup_metadata_json TEXT,
  revert_metadata_json TEXT,
  deleted_at TEXT,
  approved_at TEXT,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  invalidated_at TEXT,
  invalid_reason TEXT,
  UNIQUE(owner_id, idempotency_key)
);

INSERT INTO car_models (
  id, owner_id, title, creator_name, description, target_vehicle_directory,
  package_id, supported_versions, manifest_version, package_name, manifest_key,
  manifest_size, manifest_sha256, file_count, total_size, cover_key,
  cover_sha256, cover_content_type, status, idempotency_key, moderation_note,
  replacement_mode, backup_metadata_json, revert_metadata_json, deleted_at,
  approved_at, created_at, updated_at, invalidated_at, invalid_reason
)
SELECT
  id, owner_id, title, creator_name, description, target_vehicle_directory,
  package_id, supported_versions, manifest_version, package_name, manifest_key,
  manifest_size, manifest_sha256, file_count, total_size, cover_key,
  cover_sha256, cover_content_type, status, idempotency_key, moderation_note,
  replacement_mode, backup_metadata_json, revert_metadata_json, deleted_at,
  approved_at, created_at, updated_at, invalidated_at, invalid_reason
FROM car_models__status_migration_backup;

DROP TABLE car_models__status_migration_backup;

CREATE INDEX idx_car_models_public
ON car_models(status, deleted_at, created_at DESC);
CREATE INDEX idx_car_models_owner
ON car_models(owner_id, status, updated_at DESC);
CREATE INDEX idx_car_models_target
ON car_models(target_vehicle_directory, package_id, status);
CREATE INDEX idx_car_models_invalidated ON car_models(invalidated_at);

PRAGMA foreign_keys = ON;
