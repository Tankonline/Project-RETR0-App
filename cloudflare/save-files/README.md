# save-files profile-picture update

## Source package

`C:\Users\armaa\Desktop\save-files-worker-current.js` is preserved in the Cloudflare multipart export/import format supplied by the user. Its outer multipart boundary and `Content-Disposition` line were not changed. The avatar feature was added only to the enclosed JavaScript payload.

## Required production migration order

Apply these D1 migrations before importing the updated Worker package:

1. `migrations/0001_add_installation_avatars.sql`
2. `migrations/0002_add_installation_revoked_at.sql` only when the current `anonymous_installations` table does not already include `revoked_at`.

Both migrations are additive. Do not run the second migration if its column already exists.

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
