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
**Date:** 2026-09-19
**Session context:** Persisting an Android media file with metadata and artwork through a privileged filesystem service.
**Skill:** Android development workflow
**Type:** open-source
**Phase/Area:** Multi-file persistence and rollback

**Issue:** A media record spans several files but the service only exposes single-file imports, writes, moves, and deletes, so publishing the primary file before its sidecars leaves partially visible records when later operations fail.

**Suggested improvement:** Stage every artifact under a transaction-specific directory, verify all contents, then publish atomically with rollback for failures.

**Principle:** Multi-artifact records should become visible only after every required artifact passes verification.

### Observation 6: Separate privileged discovery from client-side acceptance

**Status:** OPEN
**Date:** 2026-09-20
**Session context:** Deep Smart Match investigation in an Android Shizuku/AIDL filesystem browser.
**Skill:** Android development workflow
**Type:** open-source
**Phase/Area:** Cross-process discovery, ranking, and final UI validation

**Issue:** The privileged recursive scan filtered only exact imported filenames before transport, preventing related candidates from reaching client-side ranking; the client also validated bulk results before ranking, collapsing discovery into final acceptance.

**Suggested improvement:** Keep privileged traversal bounded and safety-scoped, transport all safe `.dds.z` candidates in the canonical path encoding, rank related candidates client-side, and reserve strict filename/path validation for confirmation.

**Principle:** Discovery and acceptance are different contracts; preserve candidate information across process boundaries and validate only at the final commit boundary.
