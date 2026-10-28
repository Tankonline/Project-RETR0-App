# Skill Observation Log

Observations captured during task-oriented work.

**Status key:** OPEN = not yet actioned | ACTIONED (YYYY-MM-DD) = skill updated/created | DECLINED (YYYY-MM-DD) = user decided not to pursue

---

## 2026-09-20

### Observation 1: Preserve Cloudflare multipart Worker package boundaries

**Status:** OPEN
**Date:** 2026-09-19
**Session context:** Adding profile-picture endpoints to an exported Cloudflare Worker package.
**Skill:** workers-best-practices
**Type:** internal
**Phase/Area:** Source acquisition and deployment packaging

**Issue:** Cloudflare Worker export files can be multipart form-data packages rather than raw JavaScript. Treating the package as a plain `.js` module causes syntax validation to fail on the wrapper, while removing the wrapper would break a user workflow that imports the complete package.

**Suggested improvement:** Detect and preserve multipart package boundaries before validating or deploying Worker exports.

**Principle:** Validate an artifact according to its actual packaging format before transforming it.

### Observation 2: Security-sensitive fallback code benefits from isolated deterministic policy components

**Status:** OPEN
**Date:** 2026-09-19
**Session context:** Implementing a local-only Android media authentication fallback with encrypted device storage and temporary credential material.
**Skill:** Android development workflow
**Type:** open-source
**Phase/Area:** Security-sensitive integration and testing

**Issue:** A large existing orchestration method mixed provider execution, retry decisions, temporary-file lifecycle, and UI concerns, making it difficult to prove exactly-once retry and cleanup behavior.

**Suggested improvement:** Extract classifier/state-machine and encrypted-storage boundaries into small pure or narrowly scoped components with deterministic unit tests, then keep the orchestration layer responsible only for lifecycle wiring.

**Principle:** Isolate security policy and retry state from I/O orchestration so invariants can be tested independently.

### Observation 3: Keep derived upload assets explicit across orchestration boundaries

**Status:** OPEN
**Date:** 2026-09-19
**Session context:** Fixing an Android media upload flow where preview metadata artwork was not forwarded to a secondary cover upload.
**Skill:** Android development workflow
**Type:** open-source
**Phase/Area:** Multi-stage upload orchestration

**Issue:** A workflow carried metadata for local application but only carried a local picker URI for the related upload, causing a remote fallback asset shown in the UI to disappear at a service boundary.

**Suggested improvement:** Model local and remote fallback asset sources explicitly through every orchestration layer, define precedence once, and treat expected secondary-upload failure as overall failure.

**Principle:** When a workflow previews a derived asset, preserve all candidate sources and failure semantics across every asynchronous boundary.

### Observation 4: Bounded media transforms need separate input and output limits

**Status:** OPEN
**Date:** 2026-09-19
**Session context:** Adding automatic Android artwork compression before a size-limited upload.
**Skill:** Android development workflow
**Type:** open-source
**Phase/Area:** Media decoding, compression, and upload orchestration

**Issue:** Applying the final encoded-byte limit to source downloads prevents oversized-but-valid images from reaching a safe sample/decode/compress pipeline, while removing all source limits risks unbounded temporary storage.

**Suggested improvement:** Distinguish a conservative source acquisition cap from the authoritative encoded output limit, then enforce bounds decoding, sample sizing, deterministic retry termination, and the final client guard.

**Principle:** Media pipelines should bound both acquisition and decoded work independently while treating the server-compatible encoded size as the final invariant.

### Observation 5: Privileged multi-file persistence needs staged publication

**Status:** OPEN
**Date: 2026-09-19
**Session context:** Persisting an Android media file with metadata and artwork through a privileged filesystem service.
**Skill:** Android development workflow
**Type:** open-source
**Phase/Area:** Multi-file persistence and rollback

**Issue:** A media record spans several files but the service only exposes single-file imports, writes, moves, and deletes, so publishing the primary file before its sidecars leaves partially visible records when later operations fail.

**Suggested improvement:** Stage every artifact under a transaction-specific directory, verify all contents, then publish atomically with rollback for failures.

**Principle:** Multi-artifact records should become visible only after every required artifact passes verification.

### Observation 6: Separate privileged discovery from client-side acceptance

**Status:** OPEN
**Date: 2026-09-20
**Session context:** Deep Smart Match investigation in an Android Shizuku/AIDL filesystem browser.
**Skill:** Android development workflow
**Type:** open-source
**Phase/Area:** Cross-process discovery, ranking, and final UI validation

**Issue:** The privileged recursive scan filtered only exact imported filenames before transport, preventing related candidates from reaching client-side ranking; the client also validated bulk results before ranking, collapsing discovery into final acceptance.

**Suggested improvement:** Keep privileged traversal bounded and safety-scoped, transport all safe `.dds.z` candidates in the canonical path encoding, rank related candidates client-side, and reserve strict filename/path validation for confirmation.

**Principle:** Discovery and acceptance are different contracts; preserve candidate information across process boundaries and validate only at the final commit boundary.

### Observation 7: Keep explicit remote lifecycle actions separate from local install

**Status:** OPEN
**Date: 2026-09-20
**Session context:** Completing an Android car-model UI integration slice with local import, draft upload, publish, and install flows.
**Skill:** Android development workflow
**Type:** open-source
**Phase/Area:** UI state and action orchestration

**Issue:** Local library state already carried draft/public remote identifiers, but the UI did not expose distinct upload and publish actions. Adding explicit confirmation boundaries made it possible to preserve the invariant that uploading never publishes and remote actions never install locally.

**Suggested improvement:** When a domain model has local, draft, published, and installed lifecycles, represent each transition as a separate UI action and confirmation path rather than inferring transitions from a single primary button.

**Principle:** Make cross-system lifecycle transitions explicit so asynchronous remote mutations cannot silently trigger local side effects.

### Observation 8: Keep active local state independent from remote draft actions

**Status:** OPEN
**Date: 2026-09-20
**Session context:** Fixing an Android car-model screen where active local models could not upload drafts.
**Skill:** Android development workflow
**Type:** open-source
**Phase/Area:** UI state and action availability

**Issue:** A local installation state was incorrectly reused as a gate for remote upload and publish actions, hiding valid remote lifecycle transitions.

**Suggested improvement:** Derive action availability from the remote lifecycle and operation state, while showing local install/revert state as independent messaging and controls.

**Principle:** Independent state machines should not share disabling conditions merely because they are rendered on the same details screen.

### Observation 9: Treat graphics configuration swaps as transactional and lifecycle-aware

**Status:** OPEN
**Date: 2026-10-25
**Session context:** Investigating an RR3 black screen after applying and reverting a graphics preset.
**Skill:** Android development workflow
**Type: open-source
**Phase/Area:** Privileged game-file mutation and recovery

**Issue:** The graphics workflow copied files directly into active game configuration directories without stopping RR3, did not verify every copy, and treated directory deletion/copy results as successful enough to report completion. A failed or partially applied configuration could therefore remain active after a nominal revert.

**Suggested improvement:** Stop the target process before mutation, resolve the package/path pair once, verify all expected file operations, and fail visibly on partial restore rather than reporting success.

**Principle:** Destructive multi-file updates should be lifecycle-aware, all-or-nothing from the caller's perspective, and verified before reporting success.

### Observation 10: Asset repair must use the same lifecycle and verification contract as graphics restore

**Status:** OPEN
**Date: 2026-10-25
**Session context:** Auditing the complete Graphics Hub and adjacent RR3 asset-repair path for black-screen causes.
**Skill:** Android development workflow
**Type:** open-source
**Phase/Area:** Game asset repair and recovery

**Issue:** The asset-repair path had a separate implementation from graphics restore and previously ignored delete and copy return values. That allowed a partial `eds` or `feats` repair to be reported as successful, leaving RR3 with an incomplete configuration.

**Suggested improvement:** Share the same package resolution, process-stop precondition, directory preparation, checked copy steps, and failure reporting across all game configuration repair paths.

**Principle:** Recovery paths should enforce the same safety and verification invariants as the mutation path they repair.

### Observation 11: SQLite child-table repairs must preserve schema metadata explicitly

**Status:** OPEN
**Date: 2026-10-28
**Session context:** Repairing production D1 foreign keys after a parent-table reconstruction.
**Skill:** New skill candidate: D1 forward migration repair
**Type:** open-source
**Phase/Area:** SQLite/D1 migration design and verification

**Issue:** Rebuilding a parent table can leave child foreign-key metadata pointing at a temporary parent name. A safe forward repair must reconstruct each child from the exact historical schema, copy rows, recreate indexes, avoid unsupported explicit transaction wrappers, and verify foreign-key targets and delete actions.

**Suggested improvement:** Establish a reusable D1 repair checklist: capture sqlite_master/PRAGMA metadata, stop on schema drift, use collision-resistant temporary names, avoid BEGIN/COMMIT in remote --file migrations, and perform post-apply foreign_key_check plus per-table foreign-key assertions.

**Principle:** Schema repairs should be metadata-preserving, forward-only, and independently verifiable at the database boundary.
