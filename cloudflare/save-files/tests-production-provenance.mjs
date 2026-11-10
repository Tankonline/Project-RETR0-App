import assert from "node:assert/strict";
import { readFileSync } from "node:fs";

const config = readFileSync(new URL("wrangler.jsonc", import.meta.url), "utf8");
assert.match(config, /"main"\s*:\s*"src\/save-files-worker\.js"/, "production config must use canonical source entrypoint");
assert.ok(!config.includes(".wrangler-dry-run"), "production config must not point at generated output");
const provenance = readFileSync(new URL("docs/worker-source-provenance.md", import.meta.url), "utf8");
assert.match(provenance, /canonical production Worker source/i);
assert.match(provenance, /Never edit or deploy the generated bundle/i);
console.log("production provenance and no-deploy guard passed");
