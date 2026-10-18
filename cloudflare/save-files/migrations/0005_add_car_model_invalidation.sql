ALTER TABLE car_models ADD COLUMN invalidated_at TEXT;
ALTER TABLE car_models ADD COLUMN invalid_reason TEXT;
CREATE INDEX IF NOT EXISTS idx_car_models_invalidated ON car_models(invalidated_at);
