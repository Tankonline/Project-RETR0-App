import assert from "node:assert/strict";
import { readFileSync } from "node:fs";

const worker = readFileSync(new URL("src/save-files-worker.js", import.meta.url), "utf8");
const scraper = readFileSync(new URL("src/rr3-knowledge-scraper.js", import.meta.url), "utf8");

assert.match(worker, /await scheduledIngest\(env, ctx\)/);
assert.doesNotMatch(worker, /ctx\.waitUntil\(scheduledIngest/);
assert.match(worker, /await reconcileCommunityAssets\(env\)/);
assert.match(worker, /community_asset_reconciliation_failed/);
assert.match(scraper, /finally \{/);
assert.match(scraper, /rr3:last_run_stats/);
assert.match(scraper, /delete\(lockKey\)/);
assert.match(scraper, /Math\.min\(Date\.now\(\) - startedAt, 900000\)/);
console.log("scheduled isolation, await semantics, bounded stats, and lock cleanup guards passed");
