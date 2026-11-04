-- Staging authentication foundation. No credentials or tokens belong in migrations.
CREATE TABLE IF NOT EXISTS accounts (
  id TEXT PRIMARY KEY,
  issuer TEXT NOT NULL,
  subject TEXT NOT NULL,
  email TEXT,
  display_name TEXT,
  status TEXT NOT NULL DEFAULT 'active' CHECK (status IN ('active','disabled')),
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (issuer, subject)
);
CREATE TABLE IF NOT EXISTS account_installations (
  account_id TEXT NOT NULL REFERENCES accounts(id) ON DELETE CASCADE,
  installation_id TEXT NOT NULL REFERENCES anonymous_installations(id) ON DELETE CASCADE,
  linked_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  unlinked_at TEXT,
  PRIMARY KEY (account_id, installation_id)
);
CREATE INDEX IF NOT EXISTS idx_account_installations_installation ON account_installations(installation_id, unlinked_at);
CREATE TABLE IF NOT EXISTS auth_audit_events (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  account_id TEXT,
  event_type TEXT NOT NULL,
  installation_id TEXT,
  request_id TEXT,
  ip_hash TEXT,
  metadata_json TEXT,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS idx_auth_audit_events_created ON auth_audit_events(created_at);
CREATE INDEX IF NOT EXISTS idx_auth_audit_events_account ON auth_audit_events(account_id, created_at);
