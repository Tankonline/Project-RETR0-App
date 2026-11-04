# Extension Storage Threat Model

## Assets

- published Community Hub blobs;
- object hashes and sizes;
- node enrollment identity;
- replica health and placement metadata;
- deletion and audit records.

## Out of scope for nodes

- installation private keys;
- Cloudflare API tokens;
- D1 credentials;
- R2 credentials;
- ownership database;
- moderation data;
- draft/private content;
- encryption master keys.

## Threats and controls

| Threat | Control |
| --- | --- |
| Direct client deletion | No client-to-node network path |
| Forged node command | Control-plane signatures and node public-key verification |
| Replay | Command ID, expiry, nonce, and node-side replay cache |
| Corrupt object | SHA-256 and exact byte-length verification |
| Node unavailable | Health checks, routing exclusion, R2 fallback |
| Operator erases local data | Periodic audits and automatic re-replication |
| Stolen node credentials | Revoke node and rotate command credentials |
| Draft exposure | Keep drafts in R2; do not replicate in milestone one |

## Non-negotiable limitation

A person with operating-system administrator access can stop or erase a local
node. The platform must therefore treat nodes as replaceable replicas and never
rely on them as the only copy.
