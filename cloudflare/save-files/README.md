# save-files profile-picture update

## Source package

`C:\Users\armaa\Desktop\save-files-worker-current.js` is preserved in the Cloudflare multipart export/import format supplied by the user. Its outer multipart boundary and `Content-Disposition` line were not changed. The avatar feature was added only to the enclosed JavaScript payload.

## D1 migration safety and production reconciliation

The files in `migrations/` are an ordered Wrangler migration history, not a set of free-standing idempotent SQL scripts. SQLite/D1 supports `CREATE TABLE IF NOT EXISTS` and `CREATE INDEX IF NOT EXISTS`, but it does **not** support `ALTER TABLE ... ADD COLUMN IF NOT EXISTS`. Consequently, the historical `0001`, `0002`, `0003`, and `0005` files must not be edited after they may have been applied anywhere: changing them changes the checksum/history contract and does not repair a production database whose migration ledger is missing or out of sync.

`0001_add_installation_avatars.sql` and `0005_add_car_model_invalidation.sql` are therefore intentionally additive, but not re-runnable against a schema that already contains their columns. Do not “fix” this by dropping columns/tables or by rebuilding tables; that risks data loss. `0004` is safe for its tables/indexes because it uses `IF NOT EXISTS`, but it still depends on the existing table definitions being compatible.

### First determine whether production history is missing or merely behind

Run these **read-only** commands from this directory. They target the configured database only when `--remote` is supplied; do not use `--remote` until you have verified the account, database name, and ID in `wrangler.toml`.

```powershell
npx wrangler d1 execute save-files --remote --command "SELECT name, sql FROM sqlite_master WHERE type IN ('table','index') AND (name = 'd1_migrations' OR name IN ('anonymous_installations','skins','car_models')) ORDER BY type, name;"
npx wrangler d1 execute save-files --remote --command "PRAGMA table_info(anonymous_installations);"
npx wrangler d1 execute save-files --remote --command "PRAGMA table_info(skins);"
npx wrangler d1 execute save-files --remote --command "PRAGMA table_info(car_models);"
npx wrangler d1 execute save-files --remote --command "SELECT * FROM d1_migrations ORDER BY id;"
```

If the final query reports that `d1_migrations` does not exist, or it contains no rows/does not include the migrations already represented by the columns/tables, the database has a **schema/history split**. The failed `0001` is expected in that state: Wrangler is replaying an old migration against a newer schema.

### Safest reconciliation procedure (operator-run; this repository does not run it)

1. Export/backup the production D1 database using the Cloudflare dashboard/API procedure appropriate to the account, and save the read-only query output above.
2. Compare `PRAGMA table_info` with every historical migration. Confirm that each object is complete, including `avatar_key`, all other avatar columns, `revoked_at`, the four skin manifest columns, all `0004` tables/indexes, and both `0005` invalidation columns plus its index. Confirm column types/defaults where applicable.
3. If the schema is complete, **do not apply `0001`–`0005`**. Reconcile the Wrangler ledger using the documented D1 migration-history repair workflow for the installed Wrangler version, or have Cloudflare support/account tooling mark the already-realized migrations as applied. Do not invent ledger rows or manually update internal tables without first confirming the current Wrangler/D1 schema and supported procedure.
4. If the schema is only partially complete, apply only the missing additive statements manually, one reviewed statement at a time, using `CREATE ... IF NOT EXISTS` where available. For missing columns, first verify with `PRAGMA table_info`; then run a plain `ALTER TABLE ... ADD COLUMN ...` exactly once. Do not rerun a migration file wholesale.
5. After reconciliation, use `npx wrangler d1 migrations list save-files --remote` (or the equivalent command shown by the installed Wrangler) and apply only genuinely pending migrations. Take a fresh backup first.

There is no portable SQL command that both conditionally adds a SQLite column and remains accepted by D1. A `SELECT`/`CASE` cannot conditionally execute DDL. Any proposed `ADD COLUMN IF NOT EXISTS` fix is invalid SQLite. Future schema changes should use a new migration and an explicit preflight/reconciliation step when production may have been changed outside Wrangler.

### Migration order for a clean database

For a new/local database, apply the files in numeric order:

1. `0001_add_installation_avatars.sql`
2. `0002_add_installation_revoked_at.sql`
3. `0003_add_skin_manifests.sql`
4. `0004_add_car_models.sql`
5. `0005_add_car_model_invalidation.sql`

Never use a clean local migration run as proof that production history is synchronized.

## Avatar endpoints

- `PUT /v1/installations/avatar` — signed upload, 200 KiB maximum, PNG/JPEG/WebP magic/MIME validation.
- `DELETE /v1/installations/avatar` — signed, idempotent profile-picture removal.
- `GET|HEAD /v1/installations/{installationId}/avatar?v={sha256}` — public immutable avatar delivery.

Objects are stored through the existing `COMMUNITY_BUCKET` binding using:

```text
avatars/{installationId}/{sha256}.{ext}
```

## Skin manifest support

Migration `migrations/0003_add_skin_manifests.sql` adds nullable manifest metadata to `skins`:

- `manifest_key`
- `manifest_size`
- `manifest_sha256`
- `manifest_content_type`

Endpoints:

- `PUT /v1/skins/{id}/manifest` — signed owner upload, JSON validation, R2 storage, D1 metadata persistence.
- `GET|HEAD /v1/skins/{id}/manifest` — public manifest delivery for approved skins.

New uploads require a verified manifest before publish. Existing legacy skins with null manifest metadata remain readable, but cannot be newly published through the manifest-required publish path.

## Manual deployment checks

1. Keep Worker versions 83 and 81 available as rollback references.
2. Import/upload the unchanged multipart package format from the Desktop file.
3. Confirm all pre-existing bindings remain unchanged.
4. Test a signed avatar upload, list responses for save/music/skin avatar URLs, avatar GET/HEAD, replacement, deletion, and an existing save/music/skin request.
5. Roll back Worker traffic first if needed; do not drop the nullable avatar columns during an emergency code rollback.
