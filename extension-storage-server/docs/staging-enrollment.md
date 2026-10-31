# Staging node enrollment

Enrollment and heartbeat are intentionally staging-only in this milestone. Do not use production endpoints, tokens, private keys, D1 IDs, or replication bindings.

## Node identity and state

The node creates a persistent Ed25519 identity at `node-identity.json` and a replay journal beside it. Keep both files private and back them up only through an approved secure process. Control-plane configuration and retry/heartbeat state should be stored with mode `0600`.

Example Windows setup:

```powershell
$env:CGO_ENABLED="1"
cd extension-storage-server\storage-node
go run -race .\cmd\node -storage-root .\dev-data -listen 127.0.0.1:8788 -identity-file .\dev-data\node-identity.json
```

Set `environment` to `staging` and use a staging Worker URL plus a one-time staging enrollment code. The node must refuse enrollment or heartbeat when the environment is not staging.

## Windows CGO and race checks

Install a supported C compiler (for example, the LLVM/MinGW toolchain supported by the installed Go distribution), ensure it is on `PATH`, and verify:

```powershell
go version
go env CGO_ENABLED CC
where.exe gcc
```

Run `go test ./...`, `go vet ./...`, and `go test -race ./...`. If a compiler is unavailable, run the non-race checks and record race verification as deferred; do not disable security checks or commit credentials.

## Rollout safety

Apply migration `0009_storage_node_enrollment.sql` only to a staging D1 database. Use Wrangler dry-run before any deployment. Cloudflare remains authoritative; production replication and Android node connectivity are deferred.
