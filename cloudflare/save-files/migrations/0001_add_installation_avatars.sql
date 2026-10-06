ALTER TABLE anonymous_installations ADD COLUMN avatar_key TEXT;
ALTER TABLE anonymous_installations ADD COLUMN avatar_sha256 TEXT;
ALTER TABLE anonymous_installations ADD COLUMN avatar_content_type TEXT;
ALTER TABLE anonymous_installations ADD COLUMN avatar_size INTEGER;
ALTER TABLE anonymous_installations ADD COLUMN avatar_updated_at TEXT;

CREATE INDEX IF NOT EXISTS idx_anonymous_installations_avatar_key
ON anonymous_installations(avatar_key)
WHERE avatar_key IS NOT NULL;
