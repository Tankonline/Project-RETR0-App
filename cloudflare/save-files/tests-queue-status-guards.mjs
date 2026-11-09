import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
const source = readFileSync(new URL("src/save-files-worker.js", import.meta.url), "utf8");
const migration = readFileSync(new URL("migrations-staging/0009_rr3_research_queue.sql", import.meta.url), "utf8");
assert.match(source, /pending_review/);
assert.match(source, /queueInsertionStatus/);
assert.match(source, /budget_exhausted/);
assert.match(migration, /CHECK \(status IN \('pending_review','queued','running','completed','failed','rejected'\)\)/);
console.log("queue/status guards passed");
