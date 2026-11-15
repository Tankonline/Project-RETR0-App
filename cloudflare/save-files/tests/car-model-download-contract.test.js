import test from "node:test";
import assert from "node:assert/strict";
import worker from "../src/save-files-worker.js";

// Contract for GET /v1/car-models/{id}/download as consumed by the Android CarModelDownloadPlan parser.
const ID = "8c89ed68-1327-4e1c-a7b9-aedc422b0a22";
const sha = (c) => c.repeat(64);

function fakeEnv({ coverKey = "cover-key", headCalls = [], fileObjects = true } = {}) {
  const row = {
    id: ID, title: "BMW M3 GTR", status: "published", manifest_key: "manifest-key", manifest_size: 10, manifest_sha256: sha("a"),
    cover_key: coverKey, cover_sha256: sha("c"), cover_content_type: "image/webp", file_count: 2, total_size: 30,
  };
  const files = [
    { relative_path: "2004_bmw_m3_gtr/body.z", object_key: "f1", file_size: 10, file_sha256: sha("1"), content_type: "application/octet-stream" },
    { relative_path: "2004_bmw_m3_gtr/wheel rim.z", object_key: "f2", file_size: 20, file_sha256: sha("2"), content_type: null },
  ];
  const objects = {
    "manifest-key": { size: 10, customMetadata: { sha256: sha("a") } },
    "cover-key": { size: 100, customMetadata: { sha256: sha("c") } },
    f1: fileObjects ? { size: 10, customMetadata: { sha256: sha("1") } } : null,
    f2: { size: 20, customMetadata: { sha256: sha("2") } },
  };
  const statement = (sql) => ({
    bind: () => statement(sql),
    first: async () => (sql.includes("FROM car_models") ? row : null),
    all: async () => {
      if (sql.includes("FROM car_model_files")) return { results: files };
      if (sql.includes("FROM car_model_screenshots")) return { results: [{ position: 2, sha256: sha("e"), content_type: "image/webp" }, { position: 1, sha256: sha("d"), content_type: "image/webp" }].sort((a, b) => a.position - b.position) };
      return { results: [] };
    },
    run: async () => ({ meta: { changes: 1 } }),
  });
  return {
    SKINS_DB: { prepare: statement, batch: async () => [] },
    COMMUNITY_BUCKET: { head: async (key) => { headCalls.push(key); return objects[key] ?? null; } },
  };
}

const ctx = { waitUntil() {}, passThroughOnException() {} };
const get = (env) => worker.fetch(new Request(`https://api.example.test/v1/car-models/${ID}/download`), env, ctx);

test("download response includes files, manifest checksum, cover and screenshots", async () => {
  const response = await get(fakeEnv());
  assert.equal(response.status, 200);
  const body = await response.json();
  assert.equal(body.manifestUrl, `/v1/car-models/${ID}/manifest`);
  assert.equal(body.manifestSha256, sha("a"));
  assert.equal(body.coverUrl, `/v1/car-models/${ID}/cover`);
  assert.deepEqual(body.cover, { url: `/v1/car-models/${ID}/cover`, sha256: sha("c"), contentType: "image/webp" });
  assert.deepEqual(body.screenshots.map((s) => [s.position, s.url, s.sha256]), [[1, `/v1/car-models/${ID}/screenshots/1`, sha("d")], [2, `/v1/car-models/${ID}/screenshots/2`, sha("e")]]);
  assert.equal(body.files.length, 2);
  assert.deepEqual(body.files[1], { path: "2004_bmw_m3_gtr/wheel rim.z", size: 20, sha256: sha("2"), contentType: "application/octet-stream", url: `/v1/car-models/${ID}/files/2004_bmw_m3_gtr/wheel%20rim.z` });
});

test("each R2 object is HEAD-checked once (no serial re-check after validation)", async () => {
  const headCalls = [];
  await get(fakeEnv({ headCalls }));
  assert.deepEqual([...headCalls].sort(), ["cover-key", "f1", "f2", "manifest-key"]);
});

test("a missing file object makes the model unavailable", async () => {
  const response = await get(fakeEnv({ fileObjects: false }));
  assert.equal(response.status, 404);
});
