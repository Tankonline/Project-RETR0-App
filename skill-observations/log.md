# Skill Observation Log

Observations captured during task-oriented work.

**Status key:** OPEN = not yet actioned | ACTIONED (YYYY-MM-DD) = skill updated/created | DECLINED (YYYY-MM-DD) = user decided not to pursue

---

## 2026-11-05

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
**Type:** open-source
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

**Suggested improvement:** Add an explicit annotated-tag audit step that records tag object, peeled commit, tagger/message, and matching release metadata before any tag repair or push recommendation.

**Principle:** Git release references have multiple layers; validate the tag object and peeled commit independently.

### Observation 6: Wrangler version inspection may expose metadata without source retrieval

**Status:** OPEN
**Date:** 2026-11-05
**Session context:** Safely comparing local save-files Worker source with production and staging deployments without overwriting local changes or deploying.
**Skill:** workers-best-practices
**Type:** open-source
**Phase/Area:** Remote source synchronization and verification

**Issue:** The installed Wrangler exposed deployment/version metadata, handlers, bindings, variables, and secret names, but offered no `versions download` command; attempted retrieval therefore could not produce remote source for a cryptographic or line-level comparison.

**Suggested improvement:** Add a remote-source-availability decision point: record exact version metadata and retrieval-command limitations, compare only locally generated dry-run bundles, and never infer source equality from matching bindings or timestamps.

**Principle:** Remote metadata verifies deployment identity and configuration shape, not source equivalence; source synchronization requires an actual retrievable artifact.

### Observation 7: Generated-bundle guards must be regenerated after source edits

**Status:** OPEN
**Date:** 2026-11-05
**Session context:** Updating local troubleshoot authentication and deterministic route/deployment guards.
**Skill:** workers-best-practices
**Type:** open-source
**Phase/Area:** Generated artifact verification

**Issue:** A source-only fix correctly triggered the stale-bundle guard until the production dry-run artifact was regenerated locally.

**Suggested improvement:** Make the verification sequence explicit: edit canonical source, run Wrangler dry-run with the production config, then execute source, generated-bundle, and provenance guards; never treat a stale derived bundle as evidence of a source defect.

**Principle:** Derived deployment artifacts are test inputs and must be regenerated deterministically from the canonical source before validation.

### Observation 8: Feature flags should fail closed when remote bindings are unapproved

**Status:** OPEN
**Date:** 2026-11-05
**Session context:** Assessing a proposed Vectorize/RAG cache feature in a Cloudflare Worker without remote mutation.
**Skill:** workers-best-practices
**Type:** open-source
**Phase/Area:** Optional AI infrastructure and safe rollout

**Issue:** A proposal can mention valid product APIs while still lacking validated index dimensions, binding IDs, migration state, or source-ingestion policy. Adding guessed bindings or enabling scheduled ingestion would risk remote failures, unexpected cost, and policy violations.

**Suggested improvement:** Keep optional retrieval features disabled by default, add only a reviewed migration and static guards, and document explicit operator prerequisites before enabling bindings or ingestion.

**Principle:** Unvalidated external dependencies should be represented as explicit, fail-closed rollout prerequisites rather than inferred configuration.
