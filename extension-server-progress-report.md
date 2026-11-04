# Extension-server progress report

## Complete
- Audited Worker music read/list/upload/cover/delete/reconciliation and likes/downloads references.
- Added production migration `cloudflare/save-files/migrations/0015_community_music_uploads.sql` with music uploads, likes, downloads, foreign keys, cascade behavior, defaults, checks, uniqueness, and query-aligned indexes.
- Added matching staging migration `cloudflare/save-files/migrations-staging/0006_community_music_uploads.sql`.
- Fixed storage-node filesystem metrics with OS-specific capacity/free-space implementations; Windows uses `GetDiskFreeSpaceExW`. Object count remains based on the existing regular-file walk.
- Added schema/reference guard and health metrics tests.
- Ran Go formatting, tests, vet, race tests, and build successfully.
- Ran Node schema and D1 prepare/batch static guards successfully.
- Ran local Wrangler migration listing, local D1 command, and Wrangler deploy dry-run successfully. No remote migration or deployment was applied.

## Staging-complete
- Staging schema and storage-node heartbeat contract are ready for a reviewed staging migration/apply step by an operator.
- Empty-table reconciliation is safe: all tables are created idempotently and Worker queries naturally return zero rows.

## In progress
- None for the two requested fixes.

## Later work
- Apply migrations to the intended remote environment only after production/staging approval and backup/preflight policy.
- Add a full D1 fixture-based migration integration test that applies the complete ordered migration history rather than checking the new files independently.
- Add Windows CI coverage on a Windows runner for the native filesystem syscall path.
- Consider a dedicated persisted object-count index if directory walking becomes too expensive; current object_count behavior remains accurate via the existing walk.
