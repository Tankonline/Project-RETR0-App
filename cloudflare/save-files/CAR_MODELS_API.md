# Local car-model API contract

This local implementation adds `/v1/car-models` without changing existing save, music, or skin routes. It uses the existing signed installation identity, `SKINS_DB`, and `COMMUNITY_BUCKET` bindings. Original user files and backups remain on-device and are never uploaded.

## Lifecycle and routes

`draft -> uploading -> published` (or `hidden`). Car models have no moderation or automatic moderation: publish is an integrity gate and transitions to `published` immediately after validation. Create with `POST /v1/car-models` and an `Idempotency-Key`; upload the manifest with `PUT /v1/car-models/{id}/manifest`, then each declared file with `PUT /v1/car-models/{id}/files/{relativePath}`. The server derives the R2 key; clients cannot provide one. `PUT /cover`, `PUT /screenshots/{1..8}`, `POST /publish`, `GET|HEAD /{id}`, `GET|HEAD /files/{relativePath}`, `GET|HEAD /cover`, `GET /`, `POST|DELETE /like`, `POST /report`, and owner `DELETE /{id}` are supported.

Draft/uploading details and assets are owner-restricted. Published details and assets are public. Publishing requires a validated manifest, cover, every declared file, and applicable screenshots to be present and integrity-verified. Published catalog/detail reads also verify the required manifest and every declared package file in R2; an indexed row with missing required assets is excluded immediately and asynchronously hidden, with its bounded model prefix cleanup scheduled. Cover and screenshots remain optional for catalog validity and are not grounds for hiding a model.

Owner deletion: signed `DELETE /v1/car-models/{id}` requires the authenticated installation to be the owner. The Worker immediately sets the row to `hidden`, records `deleted_at`, returns HTTP `202` with `{success:true, deleted:true, requestId}`, and schedules asynchronous deletion of the entire `car-models/{id}/` R2 prefix. Repeated deletion is not successful after soft deletion and returns `404 model_not_found`; no local device files are affected.

## Manifest shape

```json
{
  "targetVehicleDirectory": "2001_bmw_m3_gtr_street",
  "packageId": "com.ea.games.r3_row",
  "supportedVersions": ["1.0.0"],
  "files": [
    {"path":"textures/body.atc","role":"texture","detectedFormat":"atc","variant":"nfs/silver","required":true,"size":123,"sha256":"<64 hex chars>"}
  ]
}
```

Manifests require `manifestVersion: 1` (Android also emits the compatibility `schemaVersion: 1`), `packageType: "rr3-car-model"`, package metadata (`metadata.name`, version, description, creator), target metadata, supported game packages/versions, installation policy, backup/revert flags, warnings, and each file must declare both `sourcePath`/`path` and `destinationRelativePath`/`destinationPath`. Optional cover/screenshots are uploaded separately with detected image content types. The Android uploader resumes an owner draft by querying details and only sends missing or hash/size-mismatched assets; it never sends `POST /publish` automatically. For `VEHICLE` files, `destinationPath` must equal `{targetVehicleDirectory}/{sourcePath}`. `VEHICLES_DATA` maps to `vehicles/data/{sourcePath}`, `DEPOT_DATA` maps to `data/{sourcePath}`, and `DEPOT_ROOT` accepts only `gametext.txt`, `gametext/text_*.txt`, `gametext/asset_list_base.txtCache.txt`, `text_*.txt`, or `asset_list_base.txtCache.txt`. Non-vehicle roots are valid only for `ADDON`; `REPLACE` remains vehicle-only for compatibility. Android emits both canonical fields (`path`, `size`, `root`, `destinationRelativePath`) and compatibility aliases (`sourcePath`, `sizeBytes`, `destinationRoot`, `destinationPath`). Paths are strict relative paths. `..`, absolute paths (including drive-letter paths), backslashes, empty segments, control characters, duplicates, and destination collisions are rejected. ATC/ETC names are retained exactly. Unknown formats remain in the manifest and are not silently dropped; integrity validation does not reinterpret or discard them.

## Limits and storage

Up to 256 files; each file must be non-empty and is limited to 20 MiB; total declared size is limited to 520 MiB; manifest up to 256 KiB. Cover (500 KiB) and up to 8 screenshots (1 MiB each) retain their existing limits. R2 layout is `car-models/{modelUuid}/manifest.json`, `car-models/{modelUuid}/files/{validatedRelativePath}`, `car-models/{modelUuid}/cover.{ext}`, and `car-models/{modelUuid}/screenshots/{position}.{ext}`.

## Rollout gates

Before production: apply migration 0004 additively after reviewing the local D1 schema, run signed integration tests against a staging Worker, verify R2 put/head/get/delete behavior and orphan cleanup, validate handling for unknown formats, and explicitly review limits and package allow-list. Do not deploy, upload fixtures, alter bindings/settings, or upload original device backups as part of local development.
