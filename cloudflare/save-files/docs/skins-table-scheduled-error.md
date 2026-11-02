# Scheduled `skins` table error

The scheduled worker references the Community Hub `skins` table (and related
`skin_*` tables), but the repository's production and staging migration sets do
not contain an authoritative `CREATE TABLE skins` definition. The Worker
references alone do not establish column types, constraints, indexes, or foreign
keys safely enough to infer a migration.

No migration was added. This is intentional: guessing the schema could damage
an existing D1 database. Derive the schema from the canonical production schema
or an exported database snapshot first, then add a separate additive migration
for each explicitly supported environment. Do not apply remote migrations as
part of this fix.
