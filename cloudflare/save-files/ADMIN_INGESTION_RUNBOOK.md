# Production admin ingestion scaffolding (not enabled)

Implemented locally only. No migration, secret, resource, staging, Android, or extension-server changes were made.

## Routes

- `POST /v1/admin/rr3-ingestion` — bounded JSON source submission; creates `pending_review` only.
- `POST /v1/admin/rr3-ingestion/:id` — `{ "status": "queued" | "rejected" }`; guarded transition and idempotent repeat handling.

Both routes require Firebase/OIDC bearer validation plus the separate admin claim guard. They return 404 while `ADMIN_INGESTION_ENABLED=false`, before any installation-auth flow. `/troubleshoot` and normal Community Hub routes remain on their existing installation/account authentication paths.

## Safety scaffolding

- SHA-256 content hashing and duplicate-safe queue insertion.
- Audit metadata includes submitter/reviewer and timestamps in queue metadata.
- R2 promotion helper requires bounded source size, safe relative paths, expected size/hash, and post-write metadata verification.
- AI Search reindexing is an explicit abstraction and returns disabled/unbound until configured.
- No automatic approval, R2 promotion, or reindex occurs from submission/transition routes.

## Flags

`RR3_RAG_MANUAL_TRIGGER_ENABLED=false`, `WEB_RESEARCH_ENABLED=false`, `ADMIN_INGESTION_ENABLED=false`, and `AI_SEARCH_ENABLED=false` remain fail-closed. The existing production queue schema in `migrations-production/0001_production_schema_reconciliation.sql` was reused conceptually; no migration was applied or changed.

## Operator setup still required

1. Export and review live production D1 schema/migration ledger; confirm queue columns and audit retention before enabling.
2. Configure production Firebase issuer, audience/project, JWKS, and custom admin claim policy; do not trust a client-supplied claim header in the final implementation—derive the claim from verified JWT payload claims.
3. Decide and provision the approved production KB R2 source/quarantine bindings and exact safe key convention.
4. Confirm AI Search namespace/API contract and bind the production namespace; implement the provider-specific reindex call behind `createSearchReindexer`.
5. Add/approve an operator UI or signed operational client, rate limits, monitoring, rollback, and incident/audit retention policy.
6. Review and approve a production migration only if live schema proves the existing queue is incompatible.
7. Only after all gates: enable flags in an approved environment and run a separately approved deployment.
