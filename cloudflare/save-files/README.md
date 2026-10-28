# save-files profile-picture update

## Source package

`C:\Users\armaa\Desktop\save-files-worker-current.js` is preserved in the Cloudflare multipart export/import format supplied by the user. Its outer multipart boundary and `Content-Disposition` line were not changed. The avatar feature was added only to the enclosed JavaScript payload.

## D1 migration safety and production reconciliation

The files in `migrations/` are an ordered Wrangler migration history, not a set of free-standing idempotent SQL scripts. SQLite/D1 supports `CREATE TABLE IF NOT EXISTS` and `CREATE INDEX IF NOT EXISTS`, but it does **not** support `ALTER TABLE ... ADD COLUMN IF NOT EXISTS`. Consequently, the historical `0001`, `0002`, `0003`, and `0005` files must not be edited after they may have been applied anywhere: changing them changes the checksum/history contract and does not repair a production database whose migration ledger is missing or out of sync.

`0001_add_installation_avatars.sql` and `0005_add_car_model_invalidation.sql` are therefore intentionally additive, but not re-runnable against a schema that already contains their columns. Do not “fix” this by dropping columns/tables or by rebuilding tables; that risks data loss. `0004` is safe for its tables/indexes because it uses `IF NOT EXISTS`, but it still depends on the existing table definitions being compatible.

Migration `0006_add_published_car_model_status.sql` is a forward-only SQLite table reconstruction. It widens only the `car_models.status` CHECK constraint and preserves the repository-known columns, defaults, unique constraint, indexes, rows, and the `0005` invalidation columns. Because the production schema output was truncated, it is deliberately **not** safe to apply until the preflight below proves production has exactly the represented `car_models` columns/objects. If any additional column, constraint, trigger, or index is found, stop and generate a reviewed migration variant containing it; never guess.

Migration `0007_rebuild_car_model_child_foreign_keys.sql` repairs the four child tables after the production `0006` reconstruction left their foreign keys targeting the dropped `car_models__status_migration_backup`. It copies rows into uniquely named temporary tables, drops/recreates each child table with `REFERENCES car_models(id) ON DELETE CASCADE`, and recreates every uniqueness constraint and index from `0004`. It intentionally has no `BEGIN`/`COMMIT` because remote D1 `--file` execution can reject explicit transaction statements, and it does not disable `PRAGMA foreign_keys`.

The migration uses `PRAGMA foreign_keys = OFF` only around the reconstruction and restores it afterward. Run it through Wrangler's migration mechanism, not by splitting statements or running individual statements. Take an export/backup first; table reconstruction is transactional for the DDL/data sequence, but the backup is the recovery boundary.

### car_models status-constraint migration preflight and verification

```powershell
npx wrangler d1 execute save-files --remote --command "SELECT type, name, tbl_name, sql FROM sqlite_master WHERE tbl_name LIKE 'car_model%' OR name LIKE 'idx_car_models%' ORDER BY type, name;"
npx wrangler d1 execute save-files --remote --command "PRAGMA table_info(car_models);"
npx wrangler d1 execute save-files --remote --command "PRAGMA index_list(car_models);"
npx wrangler d1 execute save-files --remote --command "PRAGMA foreign_key_list(car_models);"
npx wrangler d1 execute save-files --remote --command "SELECT status, COUNT(*) AS count FROM car_models GROUP BY status ORDER BY status;"
npx wrangler d1 execute save-files --remote --command "SELECT type, name, tbl_name, sql FROM sqlite_master WHERE tbl_name IN ('car_model_files','car_model_screenshots','car_model_likes','car_model_reports') ORDER BY tbl_name, type, name;"
npx wrangler d1 execute save-files --remote --command "PRAGMA table_info(car_model_files); PRAGMA index_list(car_model_files); PRAGMA foreign_key_list(car_model_files); PRAGMA table_info(car_model_screenshots); PRAGMA index_list(car_model_screenshots); PRAGMA foreign_key_list(car_model_screenshots); PRAGMA table_info(car_model_likes); PRAGMA index_list(car_model_likes); PRAGMA foreign_key_list(car_model_likes); PRAGMA table_info(car_model_reports); PRAGMA index_list(car_model_reports); PRAGMA foreign_key_list(car_model_reports);"
npx wrangler d1 migrations list save-files --remote
```

Before applying, compare the complete `sqlite_master` and PRAGMA output with `0004`, `0005`, and `0006`. Back up/export the database using the account's approved D1 export procedure. Then, from this directory, apply pending migrations only after confirming the configured account/database ID:

```powershell
npx wrangler d1 migrations apply save-files --remote
```

Afterward verify:

```powershell
npx wrangler d1 execute save-files --remote --command "SELECT sql FROM sqlite_master WHERE type='table' AND name='car_models';"
npx wrangler d1 execute save-files --remote --command "SELECT status, COUNT(*) AS count FROM car_models GROUP BY status ORDER BY status;"
npx wrangler d1 execute save-files --remote --command "PRAGMA integrity_check;"
npx wrangler d1 execute save-files --remote --command "PRAGMA foreign_key_check;"
npx wrangler d1 execute save-files --remote --command "SELECT 'car_model_files' AS child, ""table"", ""from"", ""to"", on_delete FROM pragma_foreign_key_list('car_model_files') UNION ALL SELECT 'car_model_screenshots', ""table"", ""from"", ""to"", on_delete FROM pragma_foreign_key_list('car_model_screenshots') UNION ALL SELECT 'car_model_likes', ""table"", ""from"", ""to"", on_delete FROM pragma_foreign_key_list('car_model_likes') UNION ALL SELECT 'car_model_reports', ""table"", ""from"", ""to"", on_delete FROM pragma_foreign_key_list('car_model_reports');"
npx wrangler d1 migrations list save-files --remote
```

### 0007 child foreign-key repair: exact operator procedure

Run from `C:\Users\armaa\AndroidStudioProjects\ProjectRETR0installer\cloudflare\save-files`, after reviewing and saving the complete preflight output. These commands are read-only until the migration apply command. Do not use `--command` with guessed SQL or manually split the file.

```powershell
npx wrangler d1 execute save-files --remote --command "SELECT type, name, tbl_name, sql FROM sqlite_master WHERE tbl_name IN ('car_models','car_model_files','car_model_screenshots','car_model_likes','car_model_reports') ORDER BY tbl_name, type, name;"
npx wrangler d1 execute save-files --remote --command "PRAGMA table_info(car_model_files); PRAGMA index_list(car_model_files); PRAGMA foreign_key_list(car_model_files); PRAGMA table_info(car_model_screenshots); PRAGMA index_list(car_model_screenshots); PRAGMA foreign_key_list(car_model_screenshots); PRAGMA table_info(car_model_likes); PRAGMA index_list(car_model_likes); PRAGMA foreign_key_list(car_model_likes); PRAGMA table_info(car_model_reports); PRAGMA index_list(car_model_reports); PRAGMA foreign_key_list(car_model_reports);"
npx wrangler d1 migrations apply save-files --remote
npx wrangler d1 execute save-files --remote --command "SELECT 'car_model_files' AS child, ""table"", ""from"", ""to"", on_delete FROM pragma_foreign_key_list('car_model_files') UNION ALL SELECT 'car_model_screenshots', ""table"", ""from"", ""to"", on_delete FROM pragma_foreign_key_list('car_model_screenshots') UNION ALL SELECT 'car_model_likes', ""table"", ""from"", ""to"", on_delete FROM pragma_foreign_key_list('car_model_likes') UNION ALL SELECT 'car_model_reports', ""table"", ""from"", ""to"", on_delete FROM pragma_foreign_key_list('car_model_reports');"
npx wrangler d1 execute save-files --remote --command "PRAGMA integrity_check; PRAGMA foreign_key_check;"
npx wrangler d1 migrations list save-files --remote
```

The verification query must return exactly four rows, with `table = car_models` and `on_delete = CASCADE` for every child. Stop if any migration statement fails; inspect the error and restore from the approved D1 backup/export rather than retrying blindly. Do not apply remotely or deploy automatically from repository changes.
```

There is no automatic rollback migration: reverting the CHECK requires another reviewed table reconstruction, and rolling back the Worker does not undo the schema. If the migration fails, do not retry blindly; inspect the error and restore from the approved D1 backup/export if required. Do not apply remotely or deploy automatically from repository changes.


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
