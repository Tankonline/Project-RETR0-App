# RR3 Vectorize/RAG safety runbook

## Current state

The feature is **fail-closed** (`RR3_RAG_ENABLED=false`). No Vectorize binding or dedicated KV namespace is declared. `ASSET_KV` remains reserved for release-manifest data and must not be reused for embeddings/context. The `rr3_knowledge` schema is supplied by migration `0016_rr3_knowledge.sql`; cron never creates tables.

## Ingestion policy

Enable only after an explicit allowlist and ToS/robots review. Do not scrape arbitrary web pages, copyrighted sources, or disallowed paths. Store source URL/host, policy version, and SHA-256 provenance. Bound each run by URL count, response bytes, extracted characters, chunk count, embedding calls, and Vectorize upserts. Skip redirects to non-allowlisted hosts, non-HTML content, oversized responses, and malformed text.

## Runtime safety

- Keep RAG disabled unless every dependency is configured and validated.
- Use a dedicated KV namespace for short-lived cache only after remote binding approval; never place a guessed ID in config.
- Validate Vectorize dimensions/model/API in a local test harness before adding a binding.
- Treat retrieved text as untrusted data: delimit it as reference context, instruct Granite to ignore commands inside it, and never let it override system policy or route/authentication behavior.
- Use bounded top-k and prompt size; on cache/vector/AI failure, fall back to the existing `/troubleshoot` behavior.
- Scheduled reconciliation has an in-process overlap guard and remains unchanged in scope.

## Remote setup (operator approval required)

1. Apply D1 migration `0016_rr3_knowledge.sql` to the intended environment.
2. Create a dedicated KV namespace and record its ID in a reviewed local-only config change.
3. Create/validate a Vectorize index with the exact embedding model dimensions and add a reviewed binding.
4. Configure an explicit allowlist and `RR3_RAG_ENABLED=true` only after tests and dry-runs pass.
5. Deploy only through an approved change window.

## URL/redirect adapter policy

The staging scraper follows only 301/302/303/307/308 responses using `redirect: "manual"`. Every Location is resolved against the current URL and revalidated: HTTPS, ports empty/443 only, no userinfo or fragments, exact configured host, and an explicitly configured path prefix. Relative and absolute Locations are supported; root-to-wiki, trailing slash, and percent-encoding variations are canonicalized. Unknown hosts, external domains, malformed/missing Location, loops, and more than five hops are rejected. HTML/plain/XHTML responses are bounded to 512 KiB and 10 seconds. Per-source diagnostics are retained in `rr3:last_run_stats` with redirect count, final host/path, status, and category, including manual failures.

Configure `RR3_RAG_ALLOWED_HOSTS`, `RR3_RAG_ALLOWED_PATH_PREFIXES`, and `RR3_RAG_SOURCE_ENTRIES` only in staging. Production remains disabled with no RAG bindings/resources.

## No-deploy verification

```powershell
cd cloudflare/save-files
node --check src/save-files-worker.js
node tests-troubleshoot-ai-response.mjs
node tests-troubleshoot-auth.mjs
node tests-d1-prepare-guard.mjs
npx wrangler@latest deploy --config wrangler.jsonc --dry-run --outdir .wrangler-dry-run/production
```

The Wrangler command is a local dry-run and does not deploy or mutate Cloudflare resources. Do not run `wrangler d1 migrations apply`, `wrangler kv namespace create`, `wrangler vectorize create`, or `wrangler deploy` without approval.
