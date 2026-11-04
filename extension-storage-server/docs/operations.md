# Operations Runbook

## Node lifecycle

### Enroll

1. Install the signed provider package.
2. Choose a dedicated storage directory.
3. Confirm quota and free space.
4. Copy the displayed node fingerprint and enrollment code.
5. Approve the node in the private control-plane dashboard.
6. Confirm the node reports `approved` and sends heartbeats.

### Drain

Drain a node before maintenance. A drained node:

- remains visible to the operator;
- stops receiving new replicas;
- finishes or cancels active transfers safely;
- remains out of user routing;
- can be undrained after health checks pass.

### Revoke

Revoke a node when its key is compromised, software integrity is uncertain, or
its operator no longer participates. Revocation must remove it from routing and
reject future commands.

## Incident response

### Missing replica

1. Mark the replica unhealthy.
2. Route reads to another replica or R2.
3. Queue repair.
4. Verify repaired size and SHA-256.
5. Record the result in the audit log.

### Corrupt replica

1. Stop routing immediately.
2. Preserve request IDs and object hash in the incident record.
3. Delete or quarantine the corrupt local copy through an authorized command.
4. Re-replicate from canonical R2.
5. Investigate node software, disk, and operator access.

### Node unavailable

Do not delete the node record immediately. Mark it offline, preserve its last
heartbeat and replica inventory, and allow the recovery window to expire before
rebalancing.

## Data safety

- R2 remains canonical for published objects.
- D1 remains authoritative for metadata and ownership.
- Nodes are never the only copy.
- Never place secrets in logs or diagnostic bundles.
- Never use a node filesystem path as an object identity.
