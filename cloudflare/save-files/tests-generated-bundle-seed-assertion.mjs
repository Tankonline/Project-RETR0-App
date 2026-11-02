import assert from "node:assert/strict";
import fs from "node:fs";

const bundle = fs.readFileSync(new URL(".wrangler-dry-run/save-files-worker.js", import.meta.url), "utf8");
const start = bundle.indexOf('const seedMatch = /^\\/v1\\/storage\\/admin\\/storage-objects\\/seed');
const end = bundle.indexOf('const commandCreateMatch', start);
assert.ok(start >= 0 && end > start, "generated bundle must contain the seed route");
const seed = bundle.slice(start, end);
assert.equal((seed.match(/request\.arrayBuffer\(\)/g) || []).length, 1);
assert.equal((seed.match(/object\.arrayBuffer\(\)/g) || []).length, 1);
assert.equal((seed.match(/object\.body/g) || []).length, 0);
assert.match(seed, /const bytes = new Uint8Array\(await object\.arrayBuffer\(\)\)/);
console.log("generated bundle seed single-read assertion passed");
