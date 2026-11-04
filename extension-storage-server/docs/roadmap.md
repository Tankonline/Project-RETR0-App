# Extension Storage Roadmap

## Milestone 0 — Contract and local node

- Protocol version 1 schemas.
- Content-addressed object layout.
- Local health/capacity API.
- Atomic hash-verified writes.
- Go tests and static analysis.

## Milestone 1 — Secure node management

- Node identity keypair generation.
- Enrollment code and fingerprint.
- Signed command envelopes.
- Expiry and replay protection.
- Local management API bound to loopback.
- Provider GUI first-run wizard.

## Milestone 2 — Cloudflare control plane

- D1 node/object/replica tables.
- Node approval and revocation.
- Heartbeat ingestion.
- Signed command issuance.
- Audit events.
- Queue-backed replication jobs.

## Milestone 3 — Community Hub integration

- Published-only replication.
- Verified replica reads.
- R2 fallback.
- Repair and drain flows.
- Server directory endpoint.
- Android server selector.

## Milestone 4 — Operations and scale

- Admin dashboard.
- Regional routing.
- Capacity policies.
- Multi-node repair scheduling.
- Signed Windows/Linux packages.
- Metrics, alerting, and incident runbooks.

## Milestone 5 — Optional private content

Only after a separate privacy and encryption review:

- client or control-plane encryption;
- draft/private replication;
- key rotation;
- retention and erasure guarantees;
- privacy-preserving node diagnostics.
