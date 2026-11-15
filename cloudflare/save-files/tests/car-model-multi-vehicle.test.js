import test from "node:test";
import assert from "node:assert/strict";
import { readFileSync, writeFileSync, mkdtempSync } from "node:fs";
import { tmpdir } from "node:os";
import { join } from "node:path";
import { pathToFileURL } from "node:url";

// Multi-car ADDON packages (e.g. "[S2RR3] v0.4 early access") must pass the same PUT /manifest validation as a
// single-car model. The wire shape mirrors CarModelUploadManager: each package car keeps its own folder.
const ID = "11111111-2222-4333-8444-555555555555";
const sha = (c) => c.repeat(64);

function manifest(files, vehicleDirectories) {
  return {
    schemaVersion: 1, manifestVersion: 1, packageType: "rr3-car-model", metadata: { name: "S2RR3 pack" },
    packageId: "com.ea.games.r3_row", supportedGamePackages: ["com.ea.games.r3_row"], installMode: "ADDON", installationPolicy: "ADDON",
    target: { vehicleDirectory: "1990_bmw_m3_e36", relativeRoot: "1990_bmw_m3_e36" }, vehicleRoot: "1990_bmw_m3_e36",
    ...(vehicleDirectories ? { vehicleDirectories } : {}),
    files: files.map(([path, destinationPath, destinationRoot], i) => ({ path, sourcePath: path, destinationPath, destinationRoot, sizeBytes: 10 + i, sha256: sha(String(i % 10)) })),
  };
}

// The validator is module-internal and this test must not change the deployed Worker, so load a copy of the
// source with the validator exposed (relative imports rewritten to absolute URLs).
const srcUrl = new URL("../src/save-files-worker.js", import.meta.url);
const source = readFileSync(srcUrl, "utf8")
  .replace(/from "\.\/([^"]+)"/g, (_, file) => `from "${new URL(`../src/${file}`, import.meta.url).href}"`)
  .replace(/export \{\s*index_default as default\s*\};/, "export { validateCarModelManifest };");
const dir = mkdtempSync(join(tmpdir(), "retr0-worker-"));
const copy = join(dir, "worker-copy.mjs");
writeFileSync(copy, source);
const { validateCarModelManifest: validate } = await import(pathToFileURL(copy).href);

test("multi-car package with each car in its own folder is a valid manifest", () => {
  const result = validate(manifest([
    ["vehicles/1990_bmw_m3_e36/1990_bmw_m3_e36.points", "1990_bmw_m3_e36/1990_bmw_m3_e36.points", "VEHICLE"],
    ["vehicles/1998_toyota_supra_mk4/1998_toyota_supra_mk4.points", "1998_toyota_supra_mk4/1998_toyota_supra_mk4.points", "VEHICLE"],
    ["vehicles/data/car.brakes.nct", "vehicles/data/car.brakes.nct", "VEHICLES_DATA"],
    ["gametext.txt", "gametext.txt", "DEPOT_ROOT"],
  ], ["1990_bmw_m3_e36", "1998_toyota_supra_mk4"]), ID);
  assert.equal(result.ok, true, JSON.stringify(result));
});

test("single-car manifest still validates", () => {
  const result = validate(manifest([["vehicles/1990_bmw_m3_e36/body.m3g", "1990_bmw_m3_e36/body.m3g", "VEHICLE"]]), ID);
  assert.equal(result.ok, true, JSON.stringify(result));
});

test("traversal in a package car path is still refused", () => {
  const result = validate(manifest([["vehicles/../x", "../x", "VEHICLE"]], ["1990_bmw_m3_e36", "x"]), ID);
  assert.equal(result.ok, false);
});
