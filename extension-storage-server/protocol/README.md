# Extension Storage Protocol

Versioned contract between the Cloudflare control plane and managed storage nodes.

## Signed request format

The Ed25519 signature is URL-safe unpadded base64 over the exact UTF-8 canonical JSON bytes, with fields in this order and no whitespace:

`{"version":"v1","commandId":"...","nodeId":"...","type":"...","issuedAt":"RFC3339Nano","expiresAt":"RFC3339Nano","nonce":"...","payloadHash":"sha256 hex"}`

`payloadHash` is SHA-256 of canonical JSON serialization of `payload` (or `{}` when absent). `signature` is excluded from the signed bytes. Go and Worker must use this exact format. See `cross-language-vector.json`.

Nodes reject unknown versions, timestamps outside the five-minute clock window, TTL over fifteen minutes, replayed nonces, mismatched node IDs, invalid base64, and invalid signatures.

Production enrollment, replication, public node directory, account authentication, and Android server selection remain disabled.
