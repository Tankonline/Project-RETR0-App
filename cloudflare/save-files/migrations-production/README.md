# Production migration preparation

This directory is a separate, non-applied production migration path. Existing `migrations/` and `migrations-staging/` files are unchanged.

## Classification

- Production-safe candidates reviewed: `0008_extension_storage_foundation.sql`, `0014_accountless_save_uploads.sql`, `0015_community_music_uploads.sql`, and `0016_rr3_knowledge.sql` are additive/idempotent in isolation, but are **not copied into this path** because the current production migration ledger/schema was not available for reconciliation.
- Staging-only and excluded: `0010_staging_node_control_plane.sql`, `0011_staging_bootstrap_schema.sql`, `0012_staging_control_plane_hardening.sql`, `0013_staging_control_plane_reconcile.sql`, `0014_staging_put_object_replication.sql`, and `0015_staging_put_object_replication.sql`; all files under `migrations-staging/`; and `0009_storage_node_enrollment.sql` because it uses non-idempotent `ALTER TABLE` and is not safe to replay without live schema verification.
- Unsafe/rebuild and excluded: `0006_add_published_car_model_status.sql` and `0007_rebuild_car_model_child_foreign_keys.sql`; both contain table rebuild/drop operations and explicitly require production metadata preflight.

## Prepared migration

`0001_production_schema_reconciliation.sql` contains only `CREATE TABLE IF NOT EXISTS` and `CREATE INDEX IF NOT EXISTS`. It adds `rr3_knowledge` and `rr3_research_queue`, while deliberately avoiding any guessed reconciliation of existing production tables.

## Wrangler decision/blocker

`wrangler.jsonc` still points to `migrations`. It was **not changed**. Changing `migrations_dir` to this directory cannot be validated safely because the production D1 migration ledger and live schema export are absent; Wrangler history could be treated as a different migration lineage. Before changing it, obtain and review the production D1 migration table/ledger and `sqlite_master`/`PRAGMA` schema metadata, then run a non-applying dry-run against the exact production config.

## Safety state

- Backup verified locally: `save-files-backup-2026-11-07.sql`, non-empty, 93,354 bytes.
- No production migration applied.
- No production deployment performed.
- No secrets or resources changed.
- Production AI/RAG/web/manual flags remain false in `wrangler.jsonc`.
- Staging configuration and Android/extension-server code were not modified.
