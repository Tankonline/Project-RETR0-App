# Extension Storage Architecture

```text
Android app
    |
    | signed Community Hub API requests
    v
Cloudflare Worker / control plane
    |
    +--> D1: ownership, metadata, manifests, hashes, node health
    +--> R2: canonical object storage and fallback
    +--> Queues: replication and repair jobs
    |
    +--> managed extension storage nodes
             |
             +--> opaque blobs
             +--> heartbeat/capacity
             +--> no authority over metadata
```

## Trust boundaries

### Cloudflare control plane

Trusted for:

- installation authentication;
- ownership and publication state;
- moderation and reports;
- canonical metadata and hashes;
- node enrollment and revocation;
- replication routing;
- administrator-authorized deletion.

### Extension node

Untrusted for correctness. It may:

- store and stream blobs;
- report capacity and health;
- reject or lose requests;
- become unavailable.

Every read is verified against the control-plane hash and expected length.

### Android client

The client talks only to Cloudflare. It does not receive node credentials or
node-management commands.

## First milestone

Replicate published car-model covers, screenshots, manifests, and files. Keep
drafts and private content in canonical R2 until a separate encryption design is
approved.
