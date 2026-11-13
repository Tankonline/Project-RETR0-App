# AI Search staging readiness

## Verified implementation status

Wrangler 4.158.0 accepts the direct `ai_search` instance binding and the separate namespace binding schema. Because the dashboard has both namespace `retr0-kb` and instance `retr0-kb`, this worker uses the namespace binding so the instance is selected explicitly at runtime:

```jsonc
"ai_search_namespaces": [
  {
    "binding": "KB_SEARCH",
    "namespace": "retr0-kb"
  }
]
```

The generated type is `env.KB_SEARCH: AiSearchNamespace`; the runtime must call `env.KB_SEARCH.get("retr0-kb")`, then call `.search(...)` on that instance. This avoids the deployment failure caused by resolving `KB_SEARCH` to an unintended default/instance binding. Wrangler 4.158.0 `types --check` and staging dry-run are the authority for the installed schema/API; no unsupported or guessed field is used.

The staging config uses `compatibility_date: "2026-08-22"`, later than the required `2026-03-27`. The runtime adapter calls only the documented `KB_SEARCH.search({ query, ai_search_options })` method and is guarded by `AI_SEARCH_ENABLED`. It remains `false`, so the feature is fail-closed.

## Validation

- `npx -y wrangler@4.158.0 types --config wrangler.staging.jsonc --check` passes.
- `npx -y wrangler@4.158.0 deploy --config wrangler.staging.jsonc --dry-run --outdir .wrangler-dry-run/ai-search-namespace-check-2` passes and reports the AI Search Instance binding.
- Node syntax, RAG static, staging admin/auth, AI response, quality, and provenance tests pass.
- Existing bundle-oriented tests that inspect historical production/generated artifacts remain unrelated failures because those artifacts are stale or predate the current source/config; no production artifact was regenerated or changed.

## Safety behavior

- Existing installation authentication and Granite generation remain unchanged.
- AI Search is retrieval-only; retrieved text is delimited as untrusted reference data.
- The existing Vectorize retrieval remains the fallback when AI Search is disabled or unavailable.
- Search failures and empty results fail closed and do not trigger production behavior.
- No token, provider key, billing change, AI Gateway, or production binding was created or changed.
- Production configuration was not modified.

## Required dashboard/account actions before enabling or deploying

1. Verify that the Worker binding points to the intended staging instance/namespace and that the R2 source has the expected 76 indexed items.
2. Confirm the instance is healthy.
3. If the dashboard reports `paused_invalid_api_token`, use the approved account workflow to replace/resume the staging token. Never guess, commit, log, or rotate a token from code.
4. Review any AI Search account/billing requirements with the account owner. Do not add credits or enable production access as part of this change.
5. After those checks, a separate approved change may set `AI_SEARCH_ENABLED=true` in staging and run a staging-only validation. Do not enable it in production.

No deployment was performed.
