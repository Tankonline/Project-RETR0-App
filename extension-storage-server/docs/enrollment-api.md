# Canonical staging enrollment API

The staging contract is shared by the Worker and Go storage node:

- `POST /v1/storage/nodes/{nodeId}/enroll/request` creates an idempotent pending request. Body: `nodeId`, `displayName`, `endpoint`, `publicKey` (URL-safe unpadded base64 Ed25519 public key), `fingerprint`, `enrollmentCode`, optional `expiresAt`.
- `POST /v1/storage/nodes/{nodeId}/enroll/approve` approves a pending request. It requires `X-Storage-Admin: staging`; the URL and body `nodeId` must match. It accepts `nodeId`, `publicKey`, `enrollmentCode`, `nonce`, `issuedAt`, `expiresAt`, and `signature`.
- `POST /v1/storage/nodes/{nodeId}/enroll/status` returns `{nodeId,status,lastHeartbeatAt,...}`.
- `POST /v1/storage/nodes/{nodeId}/heartbeat` accepts a signed v1 `HEARTBEAT` envelope.

Every response is JSON with a `requestId`, an `X-Request-ID` header, and errors shaped as `{error:{code,message,details?},requestId}`. CORS supports `OPTIONS` and the documented headers. Route node IDs are authoritative; a body node ID mismatch is rejected. Signed envelopes use exact UTF-8 canonical JSON ordered as `version`, `commandId`, `nodeId`, `type`, `issuedAt`, `expiresAt`, `nonce`, `payloadHash`, where `payloadHash` is lowercase SHA-256 of the compact JSON payload. Timestamps allow five minutes of clock skew; expiry is required and TTL is at most fifteen minutes. Nonces are single-use.

Legacy `/v1/storage/nodes/enroll`, `/{nodeId}/request`, and GET-only status routes are not part of the canonical contract and must not be used by new clients; unsupported combinations return structured 404/405 responses.
