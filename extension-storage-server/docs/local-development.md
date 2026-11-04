# Local development

## Storage node

From `extension-storage-server/storage-node`:

```bash
go test ./...
go vet ./...
go run ./cmd/node -storage-root ./dev-data -listen 127.0.0.1:8788
```

The initial local API exposes only health, capacity, and read-only local object
inspection. Mutating and control-plane-authenticated routes are intentionally
not enabled until command-signing and enrollment are implemented.

## Safety

- Use a temporary storage directory for development.
- Do not place production credentials in the repository.
- Do not point the local node at the production Community Hub.
- Do not expose the development listener to the public network.
