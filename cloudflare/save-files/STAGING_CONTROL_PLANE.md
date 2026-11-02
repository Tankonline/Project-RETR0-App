# Staging storage-node control plane

This control plane is staging-only: `STORAGE_ENVIRONMENT=staging` and `STORAGE_CONTROL_PLANE_ENABLED=true` are both required. Do not deploy these routes to production or apply the staging migration remotely from this repository.

## Current safe readiness

- `wrangler.staging.jsonc` has `STORAGE_SEED_ENABLED=true`.
- Read-only checks found both configured staging R2 buckets empty (`object_count: 0`, `bucket_size: 0 B`).
- Read-only staging D1 inspection found `storage_objects` empty and one approved node: `024468bb6b3bcf198aa3fca5f0b2676f`; four other nodes are pending.
- The installed Wrangler supports bucket listing/info, but not an R2 object-list command. No object key is therefore invented or selected.
- No object contents, credentials, or secrets were retrieved.

## PUT_OBJECT flow

An authorized staging operator first seeds an already-existing small object through `POST /v1/storage/admin/storage-objects/seed`, then creates a command with `POST /v1/storage/admin/nodes/{nodeId}/commands` using the returned hash. The object must already exist in a configured staging bucket and use an allowed key prefix. The Worker computes hash, size, and content type itself; callers cannot provide metadata. The node polls `GET /v1/storage/nodes/{nodeId}/commands`, downloads `GET /v1/storage/transfer/{commandId}`, verifies exact size/SHA-256, atomically stores the object, and acknowledges.

## Local deterministic checks

```powershell
$env:STORAGE_ENVIRONMENT = "staging"
$env:STORAGE_CONTROL_PLANE_ENABLED = "true"

# Local only; no --remote and no production bindings.
npx wrangler@latest d1 migrations apply retr0-save-files-staging --local --config wrangler.staging.jsonc
node .\tests-d1-prepare-guard.mjs
node .\tests-put-object-vector.mjs
npx wrangler@latest deploy --config wrangler.staging.jsonc --dry-run
```

## Exact operator commands after a suitable object exists

Set secrets only in the operator environment; do not put them in source, command history, or documentation:

```powershell
$env:STORAGE_ENVIRONMENT = "staging"
$env:STORAGE_CONTROL_PLANE_ENABLED = "true"
$env:STORAGE_STAGING_ADMIN_TOKEN = "<retrieve-from-approved-secret-store>"
$base = "https://<staging-worker-host>"
$headers = @{ "X-Storage-Admin" = $env:STORAGE_STAGING_ADMIN_TOKEN; "Content-Type" = "application/json" }

# Replace only with a key confirmed by the operator from the staging bucket.
$seed = Invoke-RestMethod -Method Post -Uri "$base/v1/storage/admin/storage-objects/seed" -Headers $headers -Body (@{ bucket = "community"; r2Key = "saves/<confirmed-existing-small-key>" } | ConvertTo-Json -Compress)
$seed | Select-Object hash,sizeBytes,contentType,r2Key | ConvertTo-Json

$command = Invoke-RestMethod -Method Post -Uri "$base/v1/storage/admin/nodes/024468bb6b3bcf198aa3fca5f0b2676f/commands" -Headers $headers -Body (@{ type = "PUT_OBJECT"; objectId = $seed.hash } | ConvertTo-Json -Compress)
$command | Select-Object commandId,nodeId,status | ConvertTo-Json
```

The node operator then polls the command endpoint, downloads the fixed HTTPS transfer URL with the node target header, verifies the hash and byte length, and acknowledges success. The control-plane route and node verifier remain authoritative for signature, target, expiry, and replay. Do not use arbitrary URLs, query strings, fragments, R2 credentials, or production endpoints.

## Isolation rules

Do not deploy from this session, do not use `--remote` for migrations, and do not mutate remote D1/R2 from this repository. The only remote commands already performed were read-only bucket metadata and D1 `SELECT` queries. Do not apply staging migrations remotely until the staging schema owner explicitly approves it. Never use production Wrangler config, production bucket names, or production D1 IDs for this test.
