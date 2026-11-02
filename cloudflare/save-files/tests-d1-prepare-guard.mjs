import fs from "node:fs";

const files = [
  "src/storage-node-control-plane.js",
  "src/auth.js",
  "src/save-files-worker.js",
];

for (const file of files) {
  const source = fs.readFileSync(new URL(file, import.meta.url), "utf8");
  if (/\.prepare\(\s*\{/.test(source)) {
    throw new Error(`${file}: D1 prepare() must receive SQL text, not an object`);
  }

  // D1Database.batch() accepts only D1PreparedStatement instances. Reject
  // raw objects, promises, SQL strings, or helper calls that are not prepared.
  for (const match of source.matchAll(/\.batch\(\s*\[([\s\S]*?)\]\s*\)/g)) {
    const batchBody = match[1];
    if (/\[\s*\{/.test(batchBody) || /\[\s*['`\"]/.test(batchBody)) {
      throw new Error(`${file}: D1 batch contains a raw object or SQL string`);
    }
    if (/env\.SKINS_DB\.prepare\(\s*\{/.test(batchBody) || /db\.prepare\(\s*\{/.test(batchBody)) {
      throw new Error(`${file}: D1 batch prepare() received an object`);
    }
    for (const statement of batchBody.matchAll(/(?:env\.SKINS_DB|db)\.prepare\(\s*[`\"'][\s\S]*?\)\.bind\(/g)) {
      if (!statement[0]) throw new Error(`${file}: invalid prepared D1 batch statement`);
    }
  }
}

const controlPlane = fs.readFileSync(new URL("src/storage-node-control-plane.js", import.meta.url), "utf8");
if (!/INSERT INTO storage_audits \(id, actor_type, actor_id, action, node_id, details_json\)/.test(controlPlane)) {
  throw new Error("storage audit insertion does not use the canonical storage_audits schema");
}
for (const column of ["enrolled_at", "approved_at", "updated_at"]) {
  if (!new RegExp(`\\b${column}\\b`).test(controlPlane)) {
    throw new Error(`approval handler does not account for storage_nodes.${column}`);
  }
}

console.log("D1 prepare/batch/schema guard passed");
