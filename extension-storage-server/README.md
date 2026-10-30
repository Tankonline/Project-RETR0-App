# Project RETR0 Extension Storage Server

A separately deployable storage-node platform for published Project RETR0 Community Hub assets.

## Design boundary

- Cloudflare remains authoritative for authentication, ownership, metadata,
  moderation, manifests, hashes, routing, and deletion authorization.
- This extension server stores blobs and reports health/capacity.
- Android clients never connect directly to extension servers.
- Extension servers must not receive Cloudflare API tokens, D1 credentials,
  R2 credentials, installation private keys, or administrator secrets.
- A machine owner with root/administrator access can still stop or erase the
  local service. Cloudflare/R2 remains the canonical fallback.

## Repository layout

```text
extension-storage-server/
├── control-plane/     # Cloudflare Worker and D1 coordination
├── storage-node/      # Go storage daemon (planned)
├── admin/              # Private administration UI (planned)
├── protocol/           # API schemas and signed-command contract
└── docs/               # Architecture and operations documentation
```

## Status

Initial architecture scaffold. The first implementation targets published,
public content only. Draft/private content and encryption-key workflows are
intentionally out of scope until the threat model and protocol are reviewed.

## Planned safeguards

- Worker-only node access.
- Signed, short-lived, replay-protected commands.
- Content-addressed objects identified by SHA-256.
- No public delete endpoint on storage nodes.
- Size, MIME type, and checksum verification.
- Heartbeats and capacity reporting.
- Replica health tracking and automatic re-replication.
- Append-only audit events and node revocation.
