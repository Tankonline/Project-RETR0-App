# Installation

This project is currently in local-node development. Production enrollment and
Cloudflare replication are not enabled yet.

## Requirements

- Windows 10/11 or a supported Linux distribution.
- Go 1.23 or newer for the storage node.
- Docker (optional).
- A dedicated storage directory.
- Network access to the future Cloudflare control plane.

## Windows: run from source

Install Go, then verify:

```powershell
go version
```

Start the local node:

```powershell
cd storage-node
go run .\cmd\node -storage-root .\dev-data -listen 127.0.0.1:8788
```

Test it from another PowerShell window:

```powershell
Invoke-WebRequest http://127.0.0.1:8788/healthz
Invoke-WebRequest http://127.0.0.1:8788/v1/local/capacity
```

The default listener is loopback-only. Do not expose it to the public Internet.

## Linux: run from source

```bash
cd storage-node
go test ./...
go run ./cmd/node --storage-root ./dev-data --listen 127.0.0.1:8788
```

## Docker

Build and run the development node:

```bash
docker build -t project-retr0-extension-storage ./storage-node
docker run --rm \
  -p 127.0.0.1:8788:8788 \
  -v "$PWD/dev-data:/var/lib/project-retr0-storage" \
  project-retr0-extension-storage
```

On PowerShell, use an absolute volume path appropriate to your machine.

## Provider GUI

The Windows/Linux provider GUI is planned and must be built from the provider
GUI package once its Tauri scaffold is added. It will eventually install and
manage the node service, storage quota, enrollment, drain state, and diagnostics.

Do not manually enroll a machine with production credentials. The current
release has no production enrollment flow.

## Uninstalling the development node

Stop the process or container, then remove only the development directory:

```powershell
Remove-Item -Recurse -Force .\storage-node\dev-data
```

Never run a recursive delete against a production storage directory without an
approved Cloudflare deletion command and an audit record.
