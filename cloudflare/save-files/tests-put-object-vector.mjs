import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
const control = readFileSync(new URL("src/storage-node-control-plane.js", import.meta.url), "utf8");
for (const token of ["storage_command_payloads", "storage_objects", "storage/transfer", "X-Storage-Target-Node", "storage_replicas SET state", "expires_at>CURRENT_TIMESTAMP", "status IN ('issued','accepted')", "storage_audits"]) assert.ok(control.includes(token), `missing ${token}`);
assert.ok(!control.includes("R2_ACCESS_KEY") && !control.includes("AWS_SECRET"));
for (const token of ["storage-objects\\/seed", "STAGING_BUCKETS", "SAFE_R2_KEY", "MAX_SEED_OBJECT_BYTES", "STORAGE_SEED_ENABLED", "ON CONFLICT(hash)", "arrayBuffer()", "size_bytes,content_type,r2_key,created_at"]) assert.ok(control.includes(token), `missing seed guard ${token}`);
assert.ok(!control.includes("seed.hash") && !control.includes("seed.size") && !control.includes("seed.contentType"));
console.log("PUT_OBJECT staging route guards passed");
