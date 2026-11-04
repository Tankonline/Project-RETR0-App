# Storage Node

Local/staging-ready Go storage daemon. Existing local flags remain compatible:

```text
-storage-root -listen -identity-file -replay-file -quota-bytes
```

Optional staging enrollment flags are additive and never enable production:

```text
-control-plane-environment staging
-control-plane-url https://<staging-worker-host>/<staging-path>
-enrollment-code <one-time-code>
-control-plane-state-file <0600-state-file>
-heartbeat-interval-seconds 60
```

The control-plane URL must be HTTPS, explicit, non-private, and used only with `staging`. Enrollment state is persisted atomically with mode `0600`; the Ed25519 identity and replay journal remain under the storage root. Heartbeats use the canonical signed v1 protocol, with bounded retry/backoff, status/revocation handling, and graceful shutdown.

No production URL, admin token, production secret, live replication, or Android integration is included.

## Local staging approval (admin-only)

The dedicated `staging-approve` command reads only the node's public identity and
pending enrollment data from local state. It prompts for the secret value sent in
`X-Storage-Admin` (configured as `STORAGE_STAGING_ADMIN_TOKEN`) without echoing the
token, never logs or persists it, validates a staging-only HTTPS URL,
and prints only a safe status, request ID, or error code.

PowerShell:

```powershell
cd extension-storage-server\storage-node
go run .\cmd\staging-approve `
  -storage-root .\dev-data `
  -control-plane-url https://save-files-staging.armaandeep08-ap.workers.dev `
  -enrollment-code $env:RETR0_STAGING_ENROLLMENT_CODE
```

The command rejects production, private, loopback, credential-bearing, query, or
fragment URLs. Do not pass the admin token as a command-line argument or environment
variable. The existing `cmd/node` flags and behavior are unchanged.

Validation commands:

```powershell
gofmt -w .
go test ./...
go vet ./...
go test -race ./...
go build ./...
```
