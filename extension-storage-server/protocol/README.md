# Extension Storage Protocol

Versioned contract between the Cloudflare control plane and managed storage nodes.

## Signed request format

The Ed25519 signature is URL-safe unpadded base64 over the exact UTF-8 canonical JSON bytes, with fields in this order and no whitespace:

`{"version":"v1","commandId":"...","nodeId":"...","type":"...","issuedAt":"RFC3339Nano","expiresAt":"RFC3339Nano","nonce":"...","payloadHash":"sha256 hex"}`

`payloadHash` is SHA-256 of the exact compact UTF-8 JSON serialization of `payload`; Go emits this with `encoding/json` and the Worker reproduces it with `JSON.stringify`. An omitted payload is encoded as `null`. `signature` is excluded from the signed bytes. Go and Worker must use this exact format. See `cross-language-vector.json`. The safe comparison diagnostic may report canonical/payload byte lengths and SHA-256 hashes only; it must never log payloads, signatures, private keys, or tokens.

Nodes reject unknown versions, timestamps outside the five-minute clock window, TTL over fifteen minutes, replayed nonces, mismatched node IDs, invalid base64, and invalid signatures.

Production enrollment, replication, public node directory, account authentication, and Android server selection remain disabled.
