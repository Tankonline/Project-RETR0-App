# Production AI-Stack Split — Read-Only Audit and Gated Implementation Plan

**Date:** 2026-11-07
**Mode:** Audit and planning only. No production Cloudflare resources, secrets, migrations, uploads, flags, or deploys were created or mutated. No source/config outside this artifact was edited.

## 1. Executive decision

Do not execute the production split yet. Staging contains the active AI/RAG/research design; production is intentionally fail-closed and lacks the AI-specific bindings and auth/rate-limit configuration needed for a safe rollout. The implementation must proceed through the approval gates below, with production resource IDs recorded only after approved creation and read-back verification.

## 2. Audited surfaces

- Worker source: `cloudflare/save-files/src/save-files-worker.js` and related `src/` modules.
- Worker configs: `cloudflare/save-files/wrangler.jsonc` (production) and `wrangler.staging.jsonc` (staging).
- Production/staging migration trees and AI/RAG/research guard tests.
- Android endpoint/build integration (`app/build.gradle.kts`, Community Hub networking/auth/signing code).
- Existing audit/provenance/control-plane documentation and generated Worker bundle.

## 3. Exact staging vs production differences

| Area | Production | Staging | Consequence |
|---|---|---|---|
| Worker | `save-files` | `save-files-staging` | Separate deployment identities. |
| D1 | `save-files`, ID `0937c661-1914-486d-88b1-7a03332ff99a`; `migrations_dir: migrations` | `retr0-save-files-staging`, ID `80e10844-4552-4cf0-9219-d86caaa1884d`; `migrations_dir: migrations-staging` | Production migration ledger/path must be independently verified. |
| R2 | `save-files`, `project-retr0` | `retr0-community-staging`, `retr0-assets-staging` | No production AI knowledge bucket is declared. Existing production buckets must not be repurposed without approval. |
| KV | `ASSET_KV` `686d7a...300e`; `SOCIAL_KV` `eedfe8...0beb` | Separate IDs plus `STORAGE_NODE_RATE_LIMIT` `83431c...eac9` and `RR3_RAG_CACHE` `d2415d...3247` | Production lacks dedicated RAG cache and storage/auth rate-limit bindings. Never reuse `ASSET_KV`. |
| Vectorize | Not declared | `RR3_RAG_INDEX` → `rr3-knowledge` | Production index name/ID is unknown and must be approved/created. |
| AI Search | Not declared | `KB_SEARCH` → namespace `retr0-kb` | Production namespace binding is unresolved; staging comments say runtime still requires operator validation. |
| AI binding | `AI` declared | `AI` declared | Available for Workers AI model calls; does not prove model/provider readiness. |
| RAG flags | `RR3_RAG_ENABLED=false`; empty source policy; manual trigger false | RAG enabled; Fandom host/path allowlist; manual trigger true | Production is fail-closed by design. |
| Web research | Disabled; provider/model/budget empty/0 | Enabled; provider `ceramic`, gateway `default`, max 3 results/fetches, daily budget 50, robots required, Fandom allowlist | Provider verification and production budget/contract are unresolved. |
| Auth | No explicit Firebase production vars; no `AUTH_RATE_LIMIT` binding | Firebase staging vars and auth limits declared; `project-retr0` issuer/audience | Account routes cannot be assumed production-ready. Android has no Firebase bearer-token integration. |
| Observability | Logs persisted, query redaction false | Logs persisted, query redaction true | Review production redaction decision before rollout. |
| Cron | Same `*/15 * * * *` and `0 */6 * * *` | Same | Scheduled ingestion/reconciliation must remain disabled or harmless until gates pass. |

## 4. Current code, test, and generated-artifact status

- Worker source is a generated/deployed bundle beginning with `save-files-worker.js`; it imports the source modules and contains the production route implementation. Treat generated output as non-authoritative until a reproducible build command and source-to-bundle hash are recorded.
- Existing static guards verify production RAG/research fail-closed behavior, staging enablement, migration presence, Granite model usage, provenance fields, bounded redirects, content limits, review states, and AI Search binding expectations.
- `0016_rr3_knowledge.sql` exists in the production migration tree and creates `rr3_knowledge` with source URL/host, title, content hash, chunks, policy version, timestamps, and uniqueness on `content_sha256`. Its existence is not proof that it has been applied remotely.
- `0009_rr3_research_queue.sql` is staging-only and creates a human-review queue. There is no evidence in this audit that a production research queue migration has been approved or applied.
- `tests-kb-inventory.mjs` expects an external knowledge root (`D:/retr0-knowledge/kb`) with exactly 76 Markdown files and frontmatter/license metadata. This is an inventory assertion, not evidence that content is uploaded, reviewed, licensed, or production-ready.
- AI Search is explicitly unresolved: staging declares `KB_SEARCH`/`retr0-kb`, but comments require operator validation. Production has no binding.

## 5. Required production resources and IDs

Names below are proposals, not created resources. IDs must remain `TBD` until approved creation/read-only discovery is performed.

| Resource | Proposed production name/binding | Current ID/status |
|---|---|---|
| R2 knowledge bucket | `retr0-knowledge` (or approved organization-standard name), binding `RR3_KNOWLEDGE_BUCKET` | TBD; no production declaration |
| R2 research/raw quarantine bucket | `retr0-research-quarantine`, binding optional and fail-closed | TBD; do not combine with published assets |
| KV RAG cache | `retr0-rr3-rag-cache`, binding `RR3_RAG_CACHE` | TBD; staging ID must not be reused |
| KV auth/rate limit | `retr0-auth-rate-limit`, binding `AUTH_RATE_LIMIT` | TBD; production config currently absent |
| Vectorize index | `rr3-knowledge-prod`, binding `RR3_RAG_INDEX` | TBD; staging index `rr3-knowledge` is not production |
| AI Search namespace | `retr0-kb-prod`, binding `KB_SEARCH` | TBD; provider/account namespace semantics must be verified |
| D1 | existing `save-files` (`0937c...99a`) | Existing ID; remote migration ledger/read-only status TBD |
| D1 research queue | approved production migration/table only if required | No production queue status confirmed |
| Worker | existing `save-files` | Existing deployment; no rollout performed |

Before creation, confirm naming constraints, account, jurisdiction, retention, encryption/access model, expected cardinality, and whether AI Search is a supported production binding in the target account/API version.

## 6. Knowledge-bank provenance and review readiness

### Required evidence before production upload

1. Freeze a content manifest containing stable document IDs, source URL, source host, title, license, retrieved timestamp, policy version, content hash, chunk count, and reviewer status.
2. Reconcile the manifest against the expected 76-file inventory without silently accepting additions/deletions.
3. Verify every document has acceptable provenance/license metadata; `licence` and `license` aliases must normalize to one canonical field.
4. Mark all imported web/research content `pending_review` until a human reviewer approves it. No research result may be written directly into the production knowledge index.
5. Run malware/content-type/size checks, deduplication by SHA-256, prompt-injection/untrusted-content handling, and source allowlist checks.
6. Produce a signed or otherwise immutable manifest hash and retain the review decision log.
7. Upload only after the **Knowledge Upload Approval Gate**; verify object count, byte totals, hashes, metadata, and index document counts afterward.

Current readiness: **Not ready for production upload.** The external KB path and exact contents are not verified in this audit, and the staging test only asserts a local inventory shape.

## 7. Web-research policy and provider verification

Production policy must be deny-by-default:

- Disabled until explicitly enabled by an approved flag change.
- Provider, gateway, model, account/project, and endpoint must be verified against current provider documentation and an approved contract test. `ceramic`/`default` is staging configuration, not production approval.
- HTTPS only; host/path allowlists; no credentials in URLs; bounded redirects; SSRF protections; robots required; bounded result/fetch/query/response sizes.
- Results are untrusted evidence, stored with source URLs, hashes, timestamps, policy version, and `pending_review` status.
- Daily and per-request budgets, rate limits, timeout/concurrency ceilings, and an emergency kill switch are mandatory.
- Research must not automatically publish, overwrite canonical knowledge, or trigger production deployment.

**Blocker:** web-search provider verification is unresolved. Do not set a production provider, API secret, model, or gateway ID until verified and approved.

## 8. Billing and budget controls

Before any AI or research enablement:

- Establish separate production billing/account ownership and alerts.
- Set hard daily/monthly ceilings for Workers AI inference, embeddings, Vectorize queries/upserts, AI Search queries, R2 operations/storage/egress, KV operations, D1 reads/writes, and external web research.
- Configure application-level counters and fail-closed `budget_exhausted` behavior; do not rely solely on provider dashboards.
- Set rate limits for anonymous/authenticated callers and scheduled jobs.
- Define alert thresholds (for example 50/80/100%), owner/on-call routing, and an immediate disable procedure.
- Run a no-cost/dry-run budget test in staging and record expected per-request cost.

No production budget values are approved by this audit. Staging’s `WEB_RESEARCH_DAILY_BUDGET=50` must not be copied without an explicit unit/currency interpretation and approval.

## 9. Android integration findings

- Android production and debug builds point to `https://save-files.armaandeep08-ap.workers.dev`; no staging endpoint was found in the checked-in app build configuration.
- Existing installation-signed routes are compatible with the production Worker: installation ID, timestamp, nonce, body hash, and P-256 signature are used.
- Android has no Firebase bearer-token integration. `/v1/auth/me` and account installation link/unlink cannot be treated as supported Android production flows until a separate, versioned Android auth change is implemented and approved.
- Android client car-model limits are lower than or otherwise stricter than the Worker in relevant areas; this is safe but must remain documented and tested.
- `local.properties` contains `RETR0_API_KEY`; it is outside checked-in build configuration and must never enter production artifacts. Investigate history/secrets exposure and rotate if necessary.
- No Android source/config should be changed as part of this audit-only task.

## 10. Migration strategy and status

1. **Inventory:** read-only compare production/staging migration filenames, hashes, dependencies, and applied ledgers. Confirm whether production config’s `migrations_dir: migrations` is complete.
2. **Staging rehearsal:** apply only to local/staging under explicit staging credentials; run D1 guards and contract tests.
3. **Production migration design review:** decide whether `rr3_knowledge`, research queue, provenance fields, indexes, and budget/audit tables are needed. Prefer additive, backward-compatible migrations.
4. **Migration approval gate:** approve exact SQL, expected lock/duration, backup/restore procedure, and rollback limitations.
5. **Apply:** run the approved production migration with a captured command/output/audit record; never use an unreviewed generated migration.
6. **Verify:** read-only schema/table/index/ledger checks, smoke queries, and compatibility checks before enabling any flag.

Status: migration presence is confirmed; remote applied status is **unknown**. Applying production migrations is explicitly blocked.

## 11. Explicit approval gates

No later gate may be executed without the preceding signed/recorded approval.

- **Gate A — Audit acceptance:** approve this inventory, ownership, target account, data classification, and rollback owner.
- **Gate B — Resource creation:** explicit approval before creating production R2, KV, Vectorize, AI Search, or any new D1 resource/table/index. Record exact names/IDs and access policies.
- **Gate C — Migration:** explicit approval of SQL, backup/restore plan, impact, and command before applying any production migration.
- **Gate D — Knowledge upload:** explicit approval of the frozen manifest, provenance/license review, content hashes, destination bucket/index, and validation plan before uploading any knowledge.
- **Gate E — Secrets/config:** explicit approval before setting/rotating `AUTH_KEY`, Firebase production variables/secrets, provider credentials, gateway credentials, or any budget-control secret.
- **Gate F — Flags:** explicit approval before enabling RAG, AI Search, web research, scheduled ingestion, manual triggers, account auth, or control-plane flags. Start with internal allowlisted canary traffic.
- **Gate G — Deploy:** explicit approval before production dry-run acceptance and deploy. Require source/bundle hash, test/lint/build results, config diff, migration status, resource IDs, and rollback command.
- **Gate H — Post-deploy expansion:** explicit approval after canary metrics, cost/latency/error review, provenance checks, and Android contract smoke tests before broad traffic.

## 12. Rollback and incident plan

- **Feature rollback:** immediately set AI/RAG/research flags false and disable scheduled/manual triggers; this must be tested as a config-only emergency path.
- **Worker rollback:** redeploy the last known-good Worker artifact/version after recording current version, logs, and reason. Keep production bindings unchanged during application rollback.
- **Data rollback:** do not destructive-delete knowledge. Quarantine the affected batch/index namespace, mark records rejected, and restore from the immutable manifest/object version or approved backup. Vectorize/AI Search deletion semantics must be verified before use.
- **Migration rollback:** prefer forward-fix migrations. SQLite/D1 destructive rollback is not assumed safe; restore/rebuild only under an approved backup procedure.
- **Secret incident:** revoke/rotate affected credentials, disable the feature, audit access, and verify no secret entered Android APKs, logs, generated bundles, or repository history.
- **Budget incident:** disable provider/feature and scheduled jobs, preserve audit records, and investigate per-request counters and provider billing.
- **Success criteria:** no production data loss, flags off within the agreed SLO, last-known-good Worker serving, cost bounded, and a documented incident review.

## 13. Ordered implementation worklist (future execution)

1. Obtain Gate A approval and production account/ownership confirmation.
2. Complete read-only migration ledger/schema and resource discovery.
3. Verify source-to-generated-bundle build reproducibility and freeze artifact hashes.
4. Validate AI Search production capability/namespace semantics and web provider contract; resolve blockers.
5. Produce and approve the knowledge manifest/review package.
6. Obtain Gate B and create only approved isolated resources; record IDs.
7. Rehearse migrations, indexing, budgets, rollback, and canary tests in staging.
8. Obtain Gate C and apply approved production migrations.
9. Obtain Gate D and upload/index approved knowledge with post-upload verification.
10. Obtain Gate E and set only approved secrets/config; confirm secrets are absent from Android artifacts/logs.
11. Obtain Gate F and enable one feature at a time for an allowlisted canary.
12. Run Android signed-route smoke tests and any separately approved auth integration tests.
13. Obtain Gate G, run production dry-run, deploy, verify health/cost/latency/errors.
14. Obtain Gate H before expanding traffic; retain rollback readiness.

## 14. Blockers requiring resolution

1. Production AI Search namespace/binding is unresolved.
2. Production Vectorize index and dedicated RAG cache IDs do not exist in config.
3. Production R2 knowledge/quarantine destinations are undefined.
4. Production remote D1 migration status is unknown; production migration directory/ledger must be reconciled.
5. Production auth variables and dedicated auth rate-limit binding are absent; Android account auth is not implemented.
6. Knowledge-bank path/content/provenance/review evidence is incomplete for production upload.
7. Web-research provider/gateway/model verification is incomplete.
8. Budget units, ceilings, alerts, and ownership are not approved.
9. Generated Worker artifact reproducibility/source hash is not established.
10. `RETR0_API_KEY` exposure/rotation status is unresolved.

## 15. Verification checklist for the eventual change

- Static Worker guards and all relevant staging/production contract tests.
- Migration dry-run/rehearsal and read-only production ledger verification.
- Source/bundle reproducibility and hash comparison.
- Knowledge manifest, provenance, license, review, dedupe, upload, and index-count verification.
- SSRF/robots/allowlist/redirect/content-size/research review tests.
- Budget exhaustion and kill-switch tests.
- Android unit tests, lint, release build, signed installation smoke tests, and explicit auth capability tests if approved.
- Canary observability: error rate, latency, AI Search/Vectorize hit quality, D1/R2/KV usage, research queue state, and billing counters.

**Conclusion:** The project has a sound staging-only foundation and several useful fail-closed tests, but production AI-stack split work is not execution-ready. Resolve the blockers and obtain the gates in order; do not create resources, upload knowledge, apply migrations, set secrets, enable flags, or deploy based solely on this plan.
