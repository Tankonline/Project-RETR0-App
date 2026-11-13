import assert from "node:assert/strict";
import { readFileSync } from "node:fs";

const source = readFileSync(new URL("src/save-files-worker.js", import.meta.url), "utf8");
const production = readFileSync(new URL("wrangler.jsonc", import.meta.url), "utf8");
const staging = readFileSync(new URL("wrangler.staging.jsonc", import.meta.url), "utf8");

assert.match(source, /\/admin\/rr3-rag\/run/);
assert.match(source, /RR3_RAG_MANUAL_TRIGGER_ENABLED !== "true"/);
assert.match(source, /RR3_RAG_ENABLED !== "true"/);
assert.match(source, /X-RETR0-KEY/);
assert.match(source, /scheduledIngest\(env, ctx\)/);
assert.match(readFileSync(new URL("src/rr3-knowledge-scraper.js", import.meta.url), "utf8"), /rr3:last_run_stats/);
assert.doesNotMatch(source, /url\.searchParams\.get\(["']source/);
assert.match(production, /"RR3_RAG_ENABLED"\s*:\s*"true"/);
assert.match(production, /"RR3_RAG_MANUAL_TRIGGER_ENABLED"\s*:\s*"false"/);
assert.match(staging, /"RR3_RAG_MANUAL_TRIGGER_ENABLED"\s*:\s*"true"/);
console.log("manual RAG trigger is protected, bounded by scraper policy, and production-disabled");
