# Extension Storage Protocol

This directory defines the contract between the Cloudflare control plane and
managed extension storage nodes.

## Principles

1. The control plane is authoritative.
2. Nodes store opaque content-addressed blobs.
3. Android clients communicate only with the control plane.
4. Every mutating command is signed, short-lived, and replay-protected.
5. Nodes verify object hash and byte length before acknowledging storage.
6. Node-local deletion is not exposed as a user operation.
7. Missing or corrupt replicas are replaceable; they are never authoritative.

## Initial object identity

```text
sha256/<first-two-hex>/<next-two-hex>/<full-sha256>
```

## Planned commands

- `PUT_OBJECT`
- `HEAD_OBJECT`
- `GET_OBJECT`
- `DELETE_OBJECT`
- `HEARTBEAT`
- `CAPACITY_REPORT`
- `DRAIN_NODE`

`DELETE_OBJECT` will require an administrator-authorized control-plane command.
The Android application will never issue it directly.
