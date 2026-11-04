# Project RETR0 Extension Storage Server

A separately deployable storage-node platform for published Project RETR0 Community Hub assets.

## What this project does

Approved computers and VPS servers can provide replaceable storage capacity for
published Community Hub content. Cloudflare remains authoritative for:

- authentication and installation identity;
- ownership and publication state;
- metadata, manifests, and hashes;
- moderation, reports, likes, and deletion authorization;
- routing, audit history, and canonical fallback storage.

Android clients never connect directly to extension nodes. They communicate with
Cloudflare, which chooses and verifies healthy replicas.

> [!WARNING]
> A machine owner with root or administrator access can stop the service or
> delete local files. Nodes are replaceable replicas, never the only copy.
> Cloudflare/R2 remains canonical.

## Current status

This repository is in the local storage-node foundation milestone.

### Implemented

- Go content-addressed filesystem store.
- Atomic writes with exact size and SHA-256 verification.
- Local health and capacity endpoints.
- Windows-compatible Go tests and `go vet` checks.
- Versioned protocol schemas and OpenAPI documentation.
- Docker development image.
- Installation, contribution, security, operations, and provider-GUI guides.

### Not enabled yet

- Production node enrollment.
- Signed command authentication on object routes.
- Cloudflare replication and R2 fallback routing.
- Live Community Hub server selection.
- Tauri provider GUI binary.
- Production deletion, repair, and admin workflows.

## Installation

Read the full [installation guide](INSTALLATION.md) before running a node.

### Windows/Linux local development

Requirements:

- Go 1.23 or newer;
- Windows 10/11 or supported Linux;
- a dedicated development storage directory;
- Docker only if you prefer container execution.

From the repository root:

```powershell
cd storage-node
go test ./...
go vet ./...
go run .\cmd\node -storage-root .\dev-data -listen 127.0.0.1:8788
```

From another terminal:

```powershell
Invoke-WebRequest http://127.0.0.1:8788/healthz
Invoke-WebRequest http://127.0.0.1:8788/v1/local/capacity
```

Linux equivalent:

```bash
cd storage-node
go test ./...
go vet ./...
go run ./cmd/node --storage-root ./dev-data --listen 127.0.0.1:8788
```

The current node listens on loopback and is for local development only. Do not
expose it to the public Internet or use production credentials.

### Docker development

```bash
docker build -t project-retr0-extension-storage ./storage-node
docker run --rm \
  -p 127.0.0.1:8788:8788 \
  -v "$PWD/dev-data:/var/lib/project-retr0-storage" \
  project-retr0-extension-storage
```

## Architecture

```text
Android Community Hub
        |
        v
Cloudflare Worker / control plane
   |       |        |
  D1      R2      Queues
        |
        v
Managed storage nodes
```

### Trust boundaries

- **Cloudflare:** authority, ownership, metadata, routing, security, and audit.
- **R2:** canonical published-object storage and fallback.
- **Storage nodes:** opaque blob storage and health reporting only.
- **Provider GUI:** local setup and monitoring only.
- **Android app:** Community Hub client; no node credentials or raw node URLs.

## Repository layout

```text
extension-storage-server/
├── control-plane/     # Cloudflare Worker and D1 coordination
├── storage-node/      # Go storage daemon
├── provider-gui/      # Planned Windows/Linux Tauri GUI
├── admin/             # Private administration UI
├── protocol/          # Schemas and OpenAPI contract
└── docs/              # Architecture, operations, security, and roadmap
```

## Provider GUI

The planned Windows/Linux provider GUI will simplify node operation without
requiring terminal commands. It will provide:

- storage directory and quota setup;
- node enrollment and approval state;
- start, stop, restart, and drain controls;
- capacity, heartbeat, and replication status;
- connectivity and disk diagnostics;
- safe redacted log export.

It will not contain Cloudflare master credentials, ownership controls,
moderation controls, arbitrary delete controls, or unrestricted shell execution.
See the [provider GUI specification](docs/provider-gui.md).

## Security model

Planned storage commands are versioned, short-lived, node-bound, signed, and
replay-protected. Objects are addressed by SHA-256:

```text
objects/sha256/ab/cd/<full-sha256>
```

The first live replication milestone is limited to published public content.
Drafts, private uploads, installation private keys, D1 credentials, R2
credentials, and encryption master keys remain outside the node.

Read the [threat model](docs/threat-model.md) and [security policy](SECURITY.md)
before contributing security-sensitive changes.

## Roadmap

1. Protocol and local node foundation.
2. Secure node enrollment and signed commands.
3. Cloudflare D1 control plane and replica tracking.
4. Provider GUI for Windows/Linux.
5. Community Hub server directory and Android selector.
6. Published-content replication and R2 fallback.
7. Repair, drain, deletion, and private admin dashboard.
8. Optional private-content encryption after a separate review.

See the full [roadmap](docs/roadmap.md).

## Contributing

Read [CONTRIBUTING.md](CONTRIBUTING.md) before opening a pull request.

Storage-node checks:

```powershell
go test ./...
go vet ./...
gofmt -d .
```

Protocol or control-plane changes must include authorization and failure-path
tests. Never commit tokens, private keys, production endpoints, or local node
data.

## Documentation index

- [Installation](INSTALLATION.md)
- [Contributing](CONTRIBUTING.md)
- [Security policy](SECURITY.md)
- [Code of Conduct](CODE_OF_CONDUCT.md)
- [Changelog](CHANGELOG.md)
- [Architecture](docs/architecture.md)
- [Threat model](docs/threat-model.md)
- [Provider GUI specification](docs/provider-gui.md)
- [Operations runbook](docs/operations.md)
- [Roadmap](docs/roadmap.md)
- [Local development](docs/local-development.md)
- [Protocol contract](protocol/README.md)

## License status

See [LICENSE](LICENSE). A finalized open-source license must be selected before
accepting external contributions or distributing signed provider binaries.
