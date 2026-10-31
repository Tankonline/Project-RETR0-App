# Staging control plane

Enabled only when `STORAGE_CONTROL_PLANE_ENABLED=true` and `STORAGE_ENVIRONMENT=staging`. Production remains disabled; no replication, account auth, public node directory, or Android selector is enabled.

Routes use `/v1/storage/nodes/{nodeId}/...` consistently. Lifecycle mutations (`approve`, `cancel`, `drain`, `undrain`, `revoke`) require the staging-only `X-Storage-Admin: staging` authorization marker and are audited. Enrollment is idempotent for the same node/public key, validates fingerprint/public-key encoding, bounded body and code, timestamp/TTL, state, and capacity. Replay insertion is atomic via `INSERT OR IGNORE`; expired nonce rows are scheduled for bounded cleanup. Rate limits return normalized JSON `429` responses with request IDs.

The Ed25519 format is documented in `protocol/README.md` and `protocol/cross-language-vector.json`. Existing migrations are immutable; `0013_staging_control_plane_reconcile.sql` reconciles empty or staging schemas without changing production history.
