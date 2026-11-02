import assert from "node:assert/strict";
import { readFileSync } from "node:fs";

const worker = readFileSync(new URL("src/save-files-worker.js", import.meta.url), "utf8");
const control = readFileSync(new URL("src/storage-node-control-plane.js", import.meta.url), "utf8");

const adminRoute = "^\\/v1\\/storage\\/admin\\/(?:storage-objects\\/seed|nodes\\/[^/]+\\/commands)$";
assert.ok(worker.includes("const stagingAdminRoute = /^\\/v1\\/storage\\/admin\\/(?:storage-objects\\/seed|nodes\\/[^/]+\\/commands)$/.test(url.pathname);"));
assert.ok(worker.includes("v1Response = await handleStorageNodeRoute(request, env, url, requestId)"));
assert.ok(worker.indexOf("const stagingAdminRoute") < worker.indexOf('if (url.pathname === "/v1/auth/me"'));
assert.ok(worker.includes("await handleStorageNodeRoute(request, env, url, requestId)"));

for (const guard of [
  'env.STORAGE_ENVIRONMENT !== "staging"',
  'env.STORAGE_CONTROL_PLANE_ENABLED !== "true"',
  'request.headers.get("X-Storage-Admin")',
  'STORAGE_STAGING_ADMIN_TOKEN',
]) assert.ok(control.includes(guard), `missing admin guard ${guard}`);
assert.ok(!control.includes("X-Installation-ID"), "staging admin control plane must not use installation IDs");
assert.ok(worker.includes('"/v1/music"'), "community route must remain present");
assert.ok(worker.includes("authenticate(request, env.SKINS_DB"), "community installation authentication must remain present");

console.log("staging admin dispatch/auth guards passed");
