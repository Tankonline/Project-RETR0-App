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
