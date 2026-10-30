# Contributing

Thanks for helping with Project RETR0 Extension Storage.

## Before submitting changes

1. Read [architecture.md](docs/architecture.md).
2. Read [threat-model.md](docs/threat-model.md).
3. Read [provider-gui.md](docs/provider-gui.md) for desktop security rules.
4. Keep Cloudflare as the authority for ownership, publication, and deletion.
5. Never add credentials, node private keys, or production endpoints to Git.
6. Run the applicable tests locally.

## Development checks

### Storage node

From `storage-node/`:

```powershell
go test ./...
go vet ./...
gofmt -d .
```

### Worker/control plane

Run the package scripts from `control-plane/` when the Worker implementation
is present:

```powershell
npm ci
npm test
npm run typecheck
```

Use Wrangler dry-run before any deployment. Never deploy a local test node or
experimental migration to production bindings.

### Provider GUI

The provider GUI must use the Tauri capability allowlist. Do not add arbitrary
shell execution, unrestricted filesystem access, remote privileged pages, or
admin credentials to the renderer.

## Pull requests

- Explain the security boundary affected by the change.
- Include tests for protocol, authorization, and failure paths.
- Keep protocol changes versioned and backward-compatible where possible.
- Include migration and rollback notes for D1 changes.
- Do not mix production deployment changes with unrelated UI changes.

## Commit guidance

Use concise imperative commit messages, for example:

```text
Add replay protection to storage commands
Improve node capacity reporting
Document provider enrollment flow
```
