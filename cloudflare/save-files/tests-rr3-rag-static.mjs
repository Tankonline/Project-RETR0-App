import assert from "node:assert/strict";
import { readFileSync } from "node:fs";

const source = readFileSync(new URL("src/save-files-worker.js", import.meta.url), "utf8");
const production = readFileSync(new URL("wrangler.jsonc", import.meta.url), "utf8");
const staging = readFileSync(new URL("wrangler.staging.jsonc", import.meta.url), "utf8");
const migration = readFileSync(new URL("migrations/0016_rr3_knowledge.sql", import.meta.url), "utf8");

assert.match(production, /"RR3_RAG_ENABLED"\s*:\s*"true"/);
assert.match(staging, /"RR3_RAG_ENABLED"\s*:\s*"true"/);
assert.match(staging, /"RR3_RAG_ALLOWED_HOSTS"\s*:\s*"realracing3\.fandom\.com"/);
assert.ok(staging.includes('"RR3_RAG_SOURCE_PATH": "/wiki/Real_Racing_3"'));
assert.doesNotMatch(production, /(^|\n)\s*"binding"\s*:\s*"[^"]*VECTORIZE/i);
assert.doesNotMatch(production, /"binding"\s*:\s*"(?:[^"\\n]*(?:EMBED|VECTOR)[^"\\n]*)"/i);
assert.match(migration, /CREATE TABLE IF NOT EXISTS rr3_knowledge/);
assert.match(migration, /content_sha256 TEXT NOT NULL UNIQUE/);
assert.match(source, /reconciliationInFlight/);
assert.match(source, /reason: "already_running"/);
assert.match(source, /installation-auth-v2/);
assert.match(source, /@cf\/ibm-granite\/granite-4\.0-h-micro/);
assert.doesNotMatch(source, /fetch\([^)]*http/i);
assert.match(source, /research_used: false/);
assert.match(source, /TROUBLESHOOT_AI_SEARCH_UNVERIFIED/);
assert.match(staging, /"binding": "AI"/);
console.log("RR3 RAG fail-closed, migration, route, model, and cron guards passed");
