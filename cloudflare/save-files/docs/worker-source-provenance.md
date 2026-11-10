# Production Worker source and provenance

`src/save-files-worker.js` is the canonical production Worker source. The checked-in production Wrangler configuration, `wrangler.jsonc`, points directly to that file via `main`.

Generate the production verification bundle without deploying:

```powershell
npx wrangler@latest deploy --config wrangler.jsonc --dry-run --outdir .wrangler-dry-run/production
```

Run the synchronization guard after generation:

```powershell
node tests-troubleshoot-auth.mjs
node tests-troubleshoot-ai-response.mjs
```

Never edit or deploy the generated bundle. It is a derived artifact used only to prove that the configured production entrypoint contains the checked-in source changes.

## Synchronization provenance

**Verified local canonical source:** `src/save-files-worker.js`, `wrangler.jsonc`, `wrangler.staging.jsonc`, and the generated production/staging dry-run bundles checked by the local guards. These now consistently use `env.AI` with `@cf/ibm-granite/granite-4.0-h-micro`; the `installation-auth-v2` routing marker and AI response normalization are preserved.

**Authoritative user-provided Cloudflare context:** production Worker `save-files`, AI binding `env.AI`, Granite model, `/troubleshoot` behavior, response fields, and route marker. This context was used as the synchronization target.

**Remotely unverifiable historical source:** no remote Worker source artifact was available for a line-level comparison during this local-only sync. Deployment/version metadata or matching bindings do not prove source equality. Therefore no remote source was downloaded, no local unsaved work was overwritten, and no Cloudflare deployment or D1/R2/KV/secret mutation was performed.

Remote deployment metadata is not a source of truth and must not be used to overwrite local files.
