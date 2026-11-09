# Skill Observation Log

Observations captured during task-oriented work.

**Status key:** OPEN = not yet actioned | ACTIONED (YYYY-MM-DD) = skill updated/created | DECLINED (YYYY-MM-DD) = user decided not to pursue

---

## 2026-11-06

### Observation 1: Preserve Cloudflare multipart Worker package boundaries

**Status:** OPEN
**Date:** 2026-09-19
**Session context:** Adding profile-picture endpoints to an exported Cloudflare Worker package.
**Skill:** workers-best-practices
**Type:** internal
**Phase/Area:** Source acquisition and deployment packaging

**Issue:** Cloudflare Worker export files can be multipart form-data packages rather than raw JavaScript. Treating the package as a plain `.js` module causes syntax validation to fail on the wrapper, while removing the wrapper would break a user workflow that imports the complete package.

**Suggested improvement:** Add a packaging-validation step that distinguishes raw Worker modules from multipart exports before syntax checks or edits.

**Principle:** Validate the artifact format before applying source-level tooling.

### Observation 2: Verify audit schema before changing Worker writes

**Status:** OPEN
**Date:** 2026-11-01
**Session context:** Fixing staging storage-node approval audit internal errors.
**Skill:** workers-best-practices
**Type:** open-source
**Phase/Area:** D1 schema compatibility and audit logging

**Issue:** The Worker audit writer must be checked against every active migration lineage before changing insert statements; staging and production can intentionally have different historical schemas.

**Suggested improvement:** Add a schema-first checklist to Worker database changes: inspect active migration directories, compare canonical columns, preserve prepared statements, and validate with a dry-run before considering migration changes.

**Principle:** Database writes should target the narrowest canonical schema shared by all deployed migration histories, while optional context belongs in a structured details field.

### Observation 3: Treat unavailable remote object listing as an explicit test blocker

**Status:** OPEN
**Date:** 2026-11-01
**Session context:** Preparing a staging-only PUT_OBJECT verification without remote mutation.
**Skill:** workers-best-practices
**Type: open-source
**Phase/Area:** Safe staging verification and operator handoff

**Issue:** The installed Wrangler version exposed bucket metadata but no R2 object-list command, so selecting an object key would require inventing metadata or using a mutating/application route.

**Suggested improvement:** Document a deterministic fallback: report bucket emptiness when metadata proves zero objects; otherwise require an operator-confirmed existing key and keep seed/queue commands parameterized.

**Principle:** When read-only discovery cannot establish an input, stop before mutation and hand off an explicit, bounded operator prerequisite.

### Observation 4: Release audits must separate pre-existing blockers from release metadata changes

**Status:** OPEN
**Date:** 2026-11-03
**Session context:** Auditing and preparing Android release v1.1.4 without touching unrelated extension-server or Cloudflare code.
**Skill:** New skill candidate: Android release audit
**Type:** open-source
**Phase/Area:** Verification and release readiness

**Issue:** A release-preparation change can be correct while CI remains blocked by unrelated existing lint and test failures, such as a dependency compatibility lint error, deprecated test APIs promoted to compilation errors, or missing local signing credentials.

**Suggested improvement:** Require separate verification results for metadata/docs edits, debug assembly, unit tests, lint, and signed release assembly; report each blocker with its exact task and first actionable error rather than treating the overall build as a single pass/fail.

**Principle:** Release readiness is multidimensional: version metadata, documentation, compilation, tests, lint, signing, and functional verification must be reported independently.

### Observation 5: Verify annotated tag targets and peeled commits separately

**Status:** OPEN
**Date:** 2026-11-04
**Session context:** Auditing a stale Android release tag against local and remote repository history.
**Skill:** New skill candidate: Android release audit
**Type:** open-source
**Phase/Area:** Git tag/ref verification and safe release handoff

**Issue:** A release tag can be an annotated tag whose object ID differs from its peeled commit. Checking only the tag ref obscures whether the tag points at the release metadata commit; comparing both `refs/tags/<tag>` and `refs/tags/<tag>^{}` against `versionName` history is required before proposing repair commands.

**Suggested improvement:** Add an explicit annotated-tag audit step that records tag object, peeled commit, tagger/message, and matching release metadata before any tag repair or push.

**Principle:** Git release references have multiple layers; validate the tag object and peeled commit independently.

### Observation 6: Wrangler version inspection may expose metadata without source retrieval

**Status:** OPEN
**Date:** 2026-11-05
**Session context:** Auditing local save-files Worker source with no deployment.
**Skill:** workers-best-practices
**Type:** open-source
**Phase/Area:** Remote source synchronization and verification

**Issue:** Deployment metadata does not establish source equivalence when source retrieval is unavailable.

**Suggested improvement:** Record exact metadata and compare only locally generated dry-run bundles; never infer source equality from bindings or timestamps.

**Principle:** Remote metadata verifies deployment identity and configuration shape, not source equivalence.

### Observation 7: Generated-bundle guards must be regenerated after source edits

**Status:** OPEN
**Date:** 2026-11-05
**Session context:** Updating Worker guards.
**Skill:** workers-best-practices
**Type:** open-source
**Phase/Area:** Generated artifact verification

**Issue:** Source-only changes can leave stale generated bundles.

**Suggested improvement:** Regenerate dry-run artifacts before source, bundle, and provenance guards.

**Principle:** Derived deployment artifacts must be regenerated deterministically before validation.

### Observation 8: Feature flags should fail closed when remote bindings are unapproved

**Status:** OPEN
**Date:** 2026-11-05
**Session context:** Assessing optional AI infrastructure.
**Skill:** workers-best-practices
**Type:** open-source
**Phase/Area:** Optional AI infrastructure and safe rollout

**Issue:** Unvalidated external bindings risk remote failures and unintended cost.

**Suggested improvement:** Keep optional retrieval features disabled by default and require explicit operator prerequisites.

**Principle:** Unvalidated dependencies are rollout prerequisites, not inferred configuration.

### Observation 9: Read-only Wrangler diagnostics need command-by-command capture

**Status:** OPEN
**Date:** 2026-11-06
**Session context:** Auditing staging RAG ingestion.
**Skill:** workers-best-practices
**Type: open-source
**Phase/Area:** Operational diagnostics and evidence capture

**Issue:** Chained diagnostics make error attribution ambiguous.

**Suggested improvement:** Capture each read-only operational probe separately.

**Principle:** Operational conclusions require evidence mapped to the individual probe.

### Observation 10: Secret-binding investigations need a read-only verification boundary

**Status:** OPEN
**Date:** 2026-11-06
**Session context:** Investigating auth configuration.
**Skill:** workers-best-practices
**Type: open-source
**Phase/Area:** Secret/config diagnostics

**Issue:** Config intent cannot verify secret values.

**Suggested improvement:** Record binding names and gates without retrieving or mutating secret values.

**Principle:** Separate configuration intent from secret-value verification.

### Observation 11: Verify AI product bindings before writing integration code

**Status:** OPEN
**Date:** 2026-11-06
**Session context:** Preparing AI Search integration.
**Skill:** workers-best-practices
**Type: open-source
**Phase/Area:** Optional AI service integration

**Issue:** A guessed binding/API can make dry runs appear configured without proving runtime support.

**Suggested improvement:** Require authoritative binding/API verification before enabling an external AI integration.

**Principle:** External service availability is not equivalent to verified runtime integration.

### Observation 12: Provider verification must precede Web Research fallback implementation

**Status:** OPEN
**Date:** 2026-11-06
**Session context:** Preparing web-research fallback.
**Skill:** workers-best-practices
**Type: open-source
**Phase/Area:** External search provider integration

**Issue:** Provider API and billing were not verified.

**Suggested improvement:** Keep research disabled and require authoritative provider evidence before activation.

**Principle:** An unverified capability must be treated as unavailable.

### Observation 13: Production resource identity must be verified per product before mutation

**Status:** OPEN
**Date:** 2026-11-07
**Session context:** Reviewing production AI resource configuration.
**Skill:** workers-best-practices
**Type:** open-source
**Phase/Area:** Production resource provisioning

**Issue:** Resource identity and supported inspection paths were ambiguous.

**Suggested improvement:** Require exact per-product identity and read-only inspection before any production mutation.

**Principle:** Production mutation requires independently verifiable names, IDs, and API capability.

### Observation 14: Production safety audit must treat enabled ancillary flags as behavior

**Status:** OPEN
**Date:** 2026-11-08
**Session context:** Auditing production web-research and AI ingestion safeguards.
**Skill:** workers-best-practices
**Type:** open-source
**Phase/Area:** Production fail-closed invariants

**Issue:** Production had web research and manual trigger disabled, but ancillary RAG/AI Search/vector bindings and flags remained enabled or declared, contradicting the requested production-disabled invariant and weakening audit confidence.

**Suggested improvement:** Add a single production invariant test covering every ingestion/retrieval flag and optional binding, and require production config to use false/empty values unless independently verified and explicitly approved.

**Principle:** A feature is not production-disabled if any activation path or optional binding remains enabled or ambiguously configured.

### Observation 15: Existing invariant tests can intentionally reject an approved canary state

**Status:** OPEN
**Date:** 2026-11-08
**Session context:** Enabling the explicitly approved production AI/RAG canary flags.
**Skill:** workers-best-practices
**Type:** open-source
**Phase/Area:** Release verification and feature-flag test maintenance

**Issue:** Relevant static tests hard-coded the previous production-disabled expectation for RR3_RAG_ENABLED, so they failed after the requested canary change even though syntax, Wrangler types, and dry-run binding checks passed.

**Suggested improvement:** Parameterize or split invariant tests into production-disabled and explicitly approved canary profiles, and ensure the selected test profile matches the release gate before deployment.

**Principle:** Verification assertions must encode the current approved rollout state, not only the historical default state.
