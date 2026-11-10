const EMBEDDING_DIMENSION = 768;
const FETCH_TIMEOUT_MS = 10_000;
const MAX_REDIRECTS = 5;
const MAX_RESPONSE_BYTES = 512 * 1024;
const MAX_TEXT_CHARS = 120_000;
const MAX_CHUNK_CHARS = 1_200;
const MAX_CHUNKS = 256;
const LOCK_TTL_SECONDS = 5 * 60;
const CONTEXT_TTL_SECONDS = 60 * 60;
const ROBOTS_TIMEOUT_MS = 5_000;
const POLICY_VERSION = "rr3-rag-v4-robots-enforced";
const DEFAULT_SOURCE_PATH = "/wiki/Real_Racing_3";
const DEFAULT_ALLOWED_PREFIXES = "/wiki/";
const REDIRECT_STATUSES = new Set([301, 302, 303, 307, 308]);
const SAFE_PORTS = new Set(["", "443"]);
const CHECK_MAX_URLS = 8;
const CHECK_MAX_BODY_BYTES = 8 * 1024;
const CHECK_COOLDOWN_MS = 30_000;
const CHECK_MAX_REDIRECTS = 3;
const PRIVATE_IPV4 = /^(?:0|10|127|169\.254|172\.(?:1[6-9]|2\d|3[01])|192\.168)(?:\.|$)/;
const LOCAL_HOSTS = new Set(["localhost", "localhost.localdomain", "ip6-localhost"]);
const CANDIDATE_SOURCES = Object.freeze([
  { id: "fandom-root", url: "https://realracing3.fandom.com/", policyStatus: "review_required" },
  { id: "fandom-wiki", url: "https://realracing3.fandom.com/wiki/Real_Racing_3", policyStatus: "review_required" },
  { id: "fandom-api", url: "https://realracing3.fandom.com/api.php", policyStatus: "review_required" },
  { id: "rr3-fandom", url: "https://rr3.fandom.com/", policyStatus: "review_required" },
  { id: "github-raw", url: "https://raw.githubusercontent.com/", policyStatus: "review_required" },
  { id: "github", url: "https://github.com/", policyStatus: "review_required" },
  { id: "reddit-api", url: "https://www.reddit.com/dev/api/", policyStatus: "review_required" },
  { id: "project-r2", url: "https://pub-f87b4daf1b344ca39c9ba6de6812a777.r2.dev/", policyStatus: "project_owned_review_required" }
]);

function enabled(env) { return env.RR3_RAG_ENABLED === "true" && env.RR3_RAG_CACHE && env.RR3_RAG_INDEX && env.AI && env.SKINS_DB; }
function normalizeHost(value) {
  const candidate = String(value || "").trim().toLowerCase().replace(/\.$/, "");
  if (!candidate || candidate.includes("://") || candidate.includes("/") || candidate.includes("@") || candidate.includes("#") || candidate.includes("?")) return null;
  try {
    const parsed = new URL(`https://${candidate}`);
    if (parsed.protocol !== "https:" || parsed.username || parsed.password || !SAFE_PORTS.has(parsed.port) || parsed.pathname !== "/" || parsed.search || parsed.hash || parsed.hostname !== candidate) return null;
    return parsed.hostname;
  } catch { return null; }
}
function allowedHosts(env) { return [...new Set(String(env.RR3_RAG_ALLOWED_HOSTS || "").split(/[\s,]+/).map(normalizeHost).filter(Boolean))]; }
function rejectedHosts(env) { return [...new Set(String(env.RR3_RAG_REJECTED_HOSTS || "").split(/[\s,]+/).map(normalizeHost).filter(Boolean))]; }
function rejectedPrefixes(env) {
  return [...new Set(String(env.RR3_RAG_REJECTED_PATHS || "").split(/[\s,]+/).map((value) => {
    const path = String(value || "").trim();
    if (!path || !path.startsWith("/") || path.includes("..") || path.includes("#") || path.includes("?")) return null;
    return path.endsWith("/") ? path : `${path}/`;
  }).filter(Boolean))];
}
function configuredPrefixes(env) {
  return [...new Set(String(env.RR3_RAG_ALLOWED_PATH_PREFIXES || DEFAULT_ALLOWED_PREFIXES).split(/[\s,]+/).map((value) => {
    const path = String(value || "").trim();
    if (!path || !path.startsWith("/") || path.includes("..") || path.includes("#") || path.includes("?")) return null;
    return path.endsWith("/") ? path : `${path}/`;
  }).filter(Boolean))];
}
function sourceEntries(env) {
  const raw = String(env.RR3_RAG_SOURCE_ENTRIES || "").trim();
  if (!raw) return [{ host: null, path: String(env.RR3_RAG_SOURCE_PATH || DEFAULT_SOURCE_PATH).trim() || DEFAULT_SOURCE_PATH }];
  return raw.split(/[\s,]+/).map((entry) => {
    const parsed = entry.split("|", 2);
    const path = parsed.length === 2 ? parsed[1] : parsed[0];
    return { host: parsed.length === 2 ? normalizeHost(parsed[0]) : null, path: path.startsWith("/") ? path : `/${path}` };
  }).filter((entry) => entry.path && !entry.path.includes("..") && (!entry.host || allowedHosts(env).includes(entry.host)));
}
function canonicalizeUrl(raw, baseUrl = null, env) {
  try {
    const url = new URL(raw, baseUrl || undefined);
    if (url.protocol !== "https:" || url.username || url.password || !SAFE_PORTS.has(url.port) || url.hash) return null;
    url.hostname = url.hostname.toLowerCase().replace(/\.$/, "");
    url.pathname = encodeURI(decodeURI(url.pathname || "/")).replace(/\/+/g, "/") || "/";
    if (url.pathname.includes("..")) return null;
    const hosts = allowedHosts(env);
    const rejectedHost = rejectedHosts(env).includes(url.hostname);
    const rejectedPath = rejectedPrefixes(env).some((prefix) => url.pathname === prefix.slice(0, -1) || url.pathname.startsWith(prefix));
    if (rejectedHost || rejectedPath) return null;
    if (!hosts.includes(url.hostname)) return null;
    if (!configuredPrefixes(env).some((prefix) => url.pathname === prefix.slice(0, -1) || url.pathname.startsWith(prefix))) return null;
    url.search = url.search;
    return url;
  } catch { return null; }
}
function approvedUrl(raw, env) { return canonicalizeUrl(raw, null, env); }
function approvedRedirectUrl(raw, baseUrl, env) { return canonicalizeUrl(raw, baseUrl, env); }
function reviewDiagnostic(raw, env, reason, status = "rejected") {
  const metadata = safeUrlMetadata(raw);
  return { host: metadata.host, path: metadata.path, reason, status };
}
function safeUrlMetadata(raw, baseUrl = null) { try { const url = new URL(raw, baseUrl || undefined); return { host: url.hostname.toLowerCase(), path: url.pathname || "/" }; } catch { return { host: null, path: null }; } }
const DIAGNOSTIC_STAGES = ["allowlist", "fetch", "extraction", "embedding", "vectorize", "d1"];
function diagnosticError(stage, error, extra = {}) { return { stage, category: error?.name === "AbortError" ? "timeout" : (error?.diagnosticCategory || "runtime_error"), errorName: error?.name || null, errorCode: error?.code || error?.cause?.code || null, ...extra, durationMs: 0 }; }
function responseMetadata(response, extra = {}) { return { httpStatus: Number.isFinite(response?.status) ? response.status : null, contentType: response?.headers?.get("content-type")?.split(";", 1)[0]?.trim().toLowerCase() || null, ...extra }; }
function splitText(text) { const normalized = text.replace(/\s+/g, " ").trim().slice(0, MAX_TEXT_CHARS); const chunks = []; for (let i = 0; i < normalized.length && chunks.length < MAX_CHUNKS; i += MAX_CHUNK_CHARS) { const chunk = normalized.slice(i, i + MAX_CHUNK_CHARS).trim(); if (chunk) chunks.push(chunk); } return chunks; }
function normalizeResearchMetadata(metadata = {}) {
  const value = metadata && typeof metadata === "object" ? metadata : {};
  const licence = value.license ?? value.licence ?? null;
  return { ...value, ...(licence == null ? { review_status: value.review_status || "pending_review" } : { license: licence }), review_status: value.review_status || (licence == null ? "pending_review" : "reviewed") };
}
function researchSources(context = []) { return [...new Set((Array.isArray(context) ? context : []).map((item) => String(item?.source_url || "").trim()).filter(Boolean))]; }
async function sha256Hex(value) { const bytes = typeof value === "string" ? new TextEncoder().encode(value) : value; const digest = await crypto.subtle.digest("SHA-256", bytes); return [...new Uint8Array(digest)].map((byte) => byte.toString(16).padStart(2, "0")).join(""); }
function robotsPathFor(url) { return `${url.origin}/robots.txt`; }
function robotsRules(text) {
  const groups = []; let current = null;
  for (const rawLine of String(text || "").split(/\r?\n/)) {
    const line = rawLine.replace(/#.*/, "").trim();
    if (!line) continue;
    const separator = line.indexOf(":"); if (separator < 0) continue;
    const key = line.slice(0, separator).trim().toLowerCase(); const value = line.slice(separator + 1).trim();
    if (key === "user-agent") { if (!current || current.hasRules) { current = { agents: [], rules: [], hasRules: false }; groups.push(current); } current.agents.push(value.toLowerCase()); }
    else if (current && (key === "allow" || key === "disallow")) { current.rules.push({ type: key, path: value || "/" }); current.hasRules = true; }
  }
  return groups;
}
function robotsAllowed(text, path, userAgent = "projectretr0") {
  const groups = robotsRules(text); const matching = groups.filter((group) => group.agents.includes("*") || group.agents.includes(userAgent.toLowerCase()));
  const rules = matching.flatMap((group) => group.rules); if (!rules.length) return true;
  const applicable = rules.filter((rule) => path.startsWith(rule.path)).sort((a, b) => b.path.length - a.path.length);
  return applicable.length === 0 || applicable[0].type === "allow";
}
async function fetchRobots(url, env) {
  const controller = new AbortController(); const timer = setTimeout(() => controller.abort(), ROBOTS_TIMEOUT_MS);
  try {
    const robotsUrl = new URL(robotsPathFor(url)); const response = await fetch(new Request(robotsUrl.href, { method: "GET", redirect: "manual", signal: controller.signal, headers: { accept: "text/plain", "user-agent": "ProjectRETR0-RR3-Knowledge/1.0" } }));
    if (response.status < 200 || response.status >= 300) return { ok: false, category: "robots_unavailable", status: response.status };
    const length = Number(response.headers.get("content-length") || 0); if (Number.isFinite(length) && length > CHECK_MAX_BODY_BYTES) return { ok: false, category: "robots_too_large", status: response.status };
    const text = await response.text(); if (new TextEncoder().encode(text).byteLength > CHECK_MAX_BODY_BYTES) return { ok: false, category: "robots_too_large", status: response.status };
    return { ok: true, allowed: robotsAllowed(text, url.pathname), status: response.status };
  } catch (error) { return { ok: false, category: error?.name === "AbortError" ? "robots_timeout" : "robots_failure", status: null }; } finally { clearTimeout(timer); }
}
async function boundedFetch(url, env) {
  const robots = await fetchRobots(url, env); if (!robots.ok || !robots.allowed) return { text: null, category: robots.ok ? "robots_denied" : robots.category, robots: robots.ok ? "denied" : "unavailable", finalHost: url.hostname, finalPath: url.pathname, redirectCount: 0 };
  const controller = new AbortController(); const timer = setTimeout(() => controller.abort(), FETCH_TIMEOUT_MS); const visited = new Set(); let currentUrl = url; let redirectCount = 0;
  try { while (true) {
    const key = currentUrl.href; if (visited.has(key)) return { text: null, category: "redirect_loop", redirectCount, finalHost: currentUrl.hostname, finalPath: currentUrl.pathname };
    visited.add(key); const response = await fetch(new Request(key, { method: "GET", redirect: "manual", signal: controller.signal, headers: { accept: "text/html, text/plain;q=0.9", "user-agent": "ProjectRETR0-RR3-Knowledge/1.0" } })); const metadata = responseMetadata(response, { redirectCount, finalHost: currentUrl.hostname, finalPath: currentUrl.pathname });
    if (REDIRECT_STATUSES.has(response.status)) { const location = response.headers.get("location"); if (!location) return { text: null, ...metadata, category: "redirect_missing_location", redirectLocationHost: null, redirectLocationPath: null }; const locationMetadata = safeUrlMetadata(location, currentUrl); if (redirectCount >= MAX_REDIRECTS) return { text: null, ...metadata, ...locationMetadata, category: "redirect_limit_exceeded", redirectLocationHost: locationMetadata.host, redirectLocationPath: locationMetadata.path }; const nextUrl = approvedRedirectUrl(location, currentUrl, env); if (!nextUrl) return { text: null, ...metadata, category: "unapproved_redirect", redirectLocationHost: locationMetadata.host, redirectLocationPath: locationMetadata.path }; currentUrl = nextUrl; redirectCount++; continue; }
    if (!response.ok) return { text: null, ...metadata, category: "http_error" }; if (!/(text\/html|text\/plain|application\/xhtml\+xml)/i.test(metadata.contentType || "")) return { text: null, ...metadata, category: "unsupported_content_type" }; const length = Number(response.headers.get("content-length") || 0); if (Number.isFinite(length) && length > MAX_RESPONSE_BYTES) return { text: null, ...metadata, category: "response_too_large" }; if (!response.body) return { text: null, ...metadata, category: "missing_body" };
    const reader = response.body.getReader(); const chunks = []; let total = 0; try { while (true) { const part = await reader.read(); if (part.done) break; total += part.value.byteLength; if (total > MAX_RESPONSE_BYTES) { await reader.cancel("response_too_large"); return { text: null, ...metadata, category: "response_too_large" }; } chunks.push(part.value); } } finally { reader.releaseLock(); } const bytes = new Uint8Array(total); let offset = 0; for (const chunk of chunks) { bytes.set(chunk, offset); offset += chunk.byteLength; } return { text: new TextDecoder().decode(bytes), ...metadata };
  } } finally { clearTimeout(timer); }
}
function checkHostIsSafe(hostname) {
  const host = String(hostname || "").toLowerCase().replace(/\.$/, "");
  if (!host || LOCAL_HOSTS.has(host) || host.endsWith(".local") || host.includes(":") || PRIVATE_IPV4.test(host)) return false;
  return true;
}
function checkUrl(raw, env) {
  try {
    const url = new URL(String(raw || ""));
    if (url.protocol !== "https:" || url.username || url.password || !SAFE_PORTS.has(url.port) || url.hash || !checkHostIsSafe(url.hostname)) return null;
    const approved = approvedUrl(url.href, env);
    return approved || (CANDIDATE_SOURCES.some((candidate) => new URL(candidate.url).hostname === url.hostname) ? url : null);
  } catch { return null; }
}
function checkMetadata(url, response, extra = {}) {
  return { host: url.hostname, path: url.pathname || "/", status: response?.status ?? null, contentType: response?.headers?.get("content-type")?.split(";", 1)[0]?.trim().toLowerCase() || null, contentLength: response?.headers?.get("content-length") || null, contentLengthHeaderPresent: response?.headers?.has("content-length") === true, ...extra };
}
async function checkSource(env, raw, fetchImpl = fetch) {
  const startedAt = Date.now(); const original = safeUrlMetadata(raw); const url = checkUrl(raw, env);
  if (!url) return { originalHost: original.host, originalPath: original.path, finalHost: null, finalPath: null, status: null, redirectCount: 0, robots: "not_checked", elapsedMs: Date.now() - startedAt, category: "ssrf_or_policy_blocked", error: "URL is not an approved HTTPS candidate" };
  let current = url; let redirects = 0; let response = null; let method = "HEAD";
  try {
    while (true) {
      response = await fetchImpl(new Request(current.href, { method, redirect: "manual", headers: { accept: "text/html, text/plain, application/json;q=0.8", "user-agent": "ProjectRETR0-RR3-Source-Checker/1.0" } }));
      if (REDIRECT_STATUSES.has(response.status)) {
        const location = response.headers.get("location"); const next = location ? checkUrl(new URL(location, current).href, env) : null;
        if (!next || redirects >= CHECK_MAX_REDIRECTS) return { ...checkMetadata(current, response, { originalHost: original.host, originalPath: original.path, finalHost: current.hostname, finalPath: current.pathname, redirectCount: redirects, robots: "not_checked", elapsedMs: Date.now() - startedAt, category: next ? "redirect_limit" : "unapproved_redirect", error: next ? null : "Redirect target is not approved" }) };
        current = next; redirects++; continue;
      }
      if (method === "HEAD" && (response.status === 405 || response.status === 501 || response.status === 403)) { method = "GET"; continue; }
      break;
    }
    const challenge = response.status === 403 || response.status === 429 || /challenge|captcha|cloudflare/i.test(response.headers.get("server") || "") || (method === "GET" && /text\/html/i.test(response.headers.get("content-type") || "") && Number(response.headers.get("content-length") || 0) < 4096);
    return { ...checkMetadata(current, response, { originalHost: original.host, originalPath: original.path, finalHost: current.hostname, finalPath: current.pathname, redirectCount: redirects, robots: "not_checked", elapsedMs: Date.now() - startedAt, category: challenge ? "challenge_or_blocked" : (response.ok ? "reachable" : "http_error"), error: null }) };
  } catch (error) { return { originalHost: original.host, originalPath: original.path, finalHost: current.hostname, finalPath: current.pathname, status: null, redirectCount: redirects, robots: "not_checked", elapsedMs: Date.now() - startedAt, category: error?.name === "AbortError" ? "timeout" : "network_error", error: "Network request failed" }; }
}
async function checkSources(env, urls, fetchImpl = fetch) {
  const list = Array.isArray(urls) ? urls : CANDIDATE_SOURCES.map((candidate) => candidate.url);
  const results = []; for (const raw of list) results.push(await checkSource(env, raw, fetchImpl));
  return { count: results.length, truncated: Array.isArray(urls) && urls.length > CHECK_MAX_URLS, results };
}
async function embeddings(env, texts) { if (!texts.length) return []; const result = await env.AI.run("@cf/baai/bge-base-en-v1.5", { text: texts }); const vectors = result?.data || result?.embeddings || []; if (!Array.isArray(vectors) || vectors.length !== texts.length || vectors.some((v) => !Array.isArray(v) || v.length !== EMBEDDING_DIMENSION)) throw new Error("invalid_embedding_dimension"); return vectors; }
async function cacheGet(env, key) { try { return await env.RR3_RAG_CACHE.get(key, "json"); } catch { return null; } }
async function ingestSource(env, ctx, sourceUrl) {
  const startedAt = Date.now(); const diagnostics = []; const url = approvedUrl(sourceUrl, env); const baseDiagnostic = { redirectCount: 0, finalHost: url?.hostname || null, finalPath: url?.pathname || null, status: null };
  if (!url) { diagnostics.push({ stage: "allowlist", category: "not_allowed", review: reviewDiagnostic(sourceUrl, env, "rejected_or_not_allowlisted"), ...baseDiagnostic, durationMs: 0 }); return { ok: false, reason: "source_not_approved", diagnostics }; }
  let fetched; try { fetched = await boundedFetch(url, env); } catch (error) { diagnostics.push({ ...diagnosticError("fetch", error, baseDiagnostic), durationMs: Date.now() - startedAt }); return { ok: false, reason: "fetch_rejected", diagnostics }; }
  if (!fetched?.text) { diagnostics.push({ stage: "fetch", category: fetched?.category || "http_error", redirectCount: fetched?.redirectCount || 0, finalHost: fetched?.finalHost || null, finalPath: fetched?.finalPath || null, status: fetched?.httpStatus || null, contentType: fetched?.contentType || null, redirectLocationHost: fetched?.redirectLocationHost || null, redirectLocationPath: fetched?.redirectLocationPath || null, extractedTextLength: 0, chunksCreated: 0, durationMs: Date.now() - startedAt }); return { ok: false, reason: "fetch_rejected", diagnostics }; }
  const chunks = splitText(fetched.text); if (!chunks.length) { diagnostics.push({ stage: "extraction", category: "empty", redirectCount: fetched.redirectCount, finalHost: fetched.finalHost, finalPath: fetched.finalPath, status: fetched.httpStatus, extractedTextLength: fetched.text.length, chunksCreated: 0, durationMs: Date.now() - startedAt }); return { ok: false, reason: "empty_source", diagnostics }; }
  let vectors; try { vectors = await embeddings(env, chunks); } catch (error) { diagnostics.push({ ...diagnosticError("embedding", error, { redirectCount: fetched.redirectCount, finalHost: fetched.finalHost, finalPath: fetched.finalPath, status: fetched.httpStatus, extractedTextLength: fetched.text.length, chunksCreated: chunks.length }), durationMs: Date.now() - startedAt }); return { ok: false, reason: "embedding_failed", diagnostics }; }
  const ids = []; try { const writes = []; for (let index = 0; index < chunks.length; index++) { const hash = await sha256Hex(`${url.href}\n${chunks[index]}`); const id = `rr3:${hash}`; ids.push(id); writes.push(env.SKINS_DB.prepare("INSERT INTO rr3_knowledge (id, source_url, source_host, source_title, content_sha256, chunk_index, chunk_text, policy_version, fetched_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ON CONFLICT(content_sha256) DO UPDATE SET updated_at = CURRENT_TIMESTAMP, chunk_text = excluded.chunk_text").bind(id, url.href, fetched.finalHost, null, hash, index, chunks[index], POLICY_VERSION)); } await env.SKINS_DB.batch(writes); } catch (error) { diagnostics.push({ ...diagnosticError("d1", error, { redirectCount: fetched.redirectCount, finalHost: fetched.finalHost, finalPath: fetched.finalPath, status: fetched.httpStatus, extractedTextLength: fetched.text.length, chunksCreated: chunks.length }), durationMs: Date.now() - startedAt }); return { ok: false, reason: "d1_failed", diagnostics }; }
  try { await env.RR3_RAG_INDEX.upsert(chunks.map((_, index) => ({ id: ids[index], values: vectors[index], metadata: { source_host: fetched.finalHost, source_path: fetched.finalPath, chunk_index: index } }))); } catch (error) { diagnostics.push({ ...diagnosticError("vectorize", error, { redirectCount: fetched.redirectCount, finalHost: fetched.finalHost, finalPath: fetched.finalPath, status: fetched.httpStatus, extractedTextLength: fetched.text.length, chunksCreated: chunks.length }), durationMs: Date.now() - startedAt }); return { ok: false, reason: "vectorize_failed", diagnostics }; }
  diagnostics.push({ stage: "extraction", category: "ok", redirectCount: fetched.redirectCount, finalHost: fetched.finalHost, finalPath: fetched.finalPath, status: fetched.httpStatus, extractedTextLength: fetched.text.length, chunksCreated: chunks.length, durationMs: Date.now() - startedAt }); return { ok: true, chunks: chunks.length, diagnostics };
}
async function retrieve(env, ctx, question, limit = 5) { const key = `rr3:ctx:${await sha256Hex(question)}`; const cached = await cacheGet(env, key); if (cached?.dimension === EMBEDDING_DIMENSION && Array.isArray(cached.context)) return { context: cached.context, sources: researchSources(cached.context), cacheHit: true }; const [vector] = await embeddings(env, [question]); const matches = await env.RR3_RAG_INDEX.query(vector, { topK: Math.min(limit, 8), returnMetadata: "all" }); const ids = (matches?.matches || []).map((m) => m.id).filter(Boolean); if (!ids.length) return { context: [], sources: [], cacheHit: false }; const rows = await env.SKINS_DB.prepare(`SELECT source_url, chunk_text FROM rr3_knowledge WHERE id IN (${ids.map(() => "?").join(",")})`).bind(...ids).all(); const context = (rows.results || []).map((row) => ({ source_url: row.source_url, text: String(row.chunk_text).slice(0, MAX_CHUNK_CHARS) })); const sources = researchSources(context); await env.RR3_RAG_CACHE.put(key, JSON.stringify({ dimension: EMBEDDING_DIMENSION, context, sources }), { expirationTtl: CONTEXT_TTL_SECONDS }); return { context, sources, cacheHit: false }; }
async function scheduledIngest(env, ctx) { if (!enabled(env)) return { ok: false, reason: "disabled" }; const lockKey = "rr3:lock:ingest"; if (await env.RR3_RAG_CACHE.get(lockKey)) return { ok: false, reason: "already_running" }; await env.RR3_RAG_CACHE.put(lockKey, "1", { expirationTtl: LOCK_TTL_SECONDS }); const startedAt = Date.now(); const results = []; const entries = sourceEntries(env); const hosts = allowedHosts(env); const sources = entries.flatMap((entry) => (entry.host ? [entry] : hosts.map((host) => ({ host, path: entry.path })))).filter((entry) => entry.host && hosts.includes(entry.host)).map((entry) => `https://${entry.host}${entry.path}`).slice(0, 8); const stats = { ok: true, reason: null, sources: sources.length, succeeded: 0, failed: 0, chunks: 0, errors: [], durationMs: 0 }; try { for (const source of sources) { const result = await ingestSource(env, ctx, source); results.push(result); stats.errors.push(...(result.diagnostics || []).slice(0, 8)); if (result.ok) { stats.succeeded++; stats.chunks += Number(result.chunks) || 0; } else stats.failed++; } stats.ok = stats.failed === 0; stats.reason = stats.ok ? null : "source_failures"; return { ok: stats.ok, results }; } finally { stats.durationMs = Math.min(Date.now() - startedAt, 900000); try { await env.RR3_RAG_CACHE.put("rr3:last_run_stats", JSON.stringify({ ...stats, results: results.map((r) => ({ ok: r.ok, reason: r.reason, diagnostics: r.diagnostics })) }), { expirationTtl: 7 * 24 * 60 * 60 }); } catch {} try { await env.RR3_RAG_CACHE.delete(lockKey); } catch {} } }
export { scheduledIngest, retrieve, approvedUrl, approvedRedirectUrl, allowedHosts, rejectedHosts, rejectedPrefixes, configuredPrefixes, sourceEntries, reviewDiagnostic, normalizeResearchMetadata, researchSources, DIAGNOSTIC_STAGES, EMBEDDING_DIMENSION, CONTEXT_TTL_SECONDS, checkSource, checkSources, CANDIDATE_SOURCES, CHECK_MAX_URLS, CHECK_MAX_BODY_BYTES, robotsAllowed };