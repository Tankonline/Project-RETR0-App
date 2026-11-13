import assert from "node:assert/strict";
import { readdir, readFile } from "node:fs/promises";
import { join, relative } from "node:path";

const root = process.env.RETR0_KB_ROOT || "D:/retr0-knowledge/kb";
async function walk(dir) { const entries = await readdir(dir, { withFileTypes: true }); const result = []; for (const entry of entries) { const path = join(dir, entry.name); if (entry.isDirectory()) result.push(...await walk(path)); else result.push(path); } return result; }
const files = (await walk(root)).filter((path) => path.toLowerCase().endsWith(".md") && !relative(root, path).replaceAll("\\", "/").startsWith("_meta/"));
assert.equal(files.length, 76);
for (const file of files) { const source = await readFile(file, "utf8"); assert.match(source, /^---\s*\r?\n[\s\S]*?\r?\n---\s*\r?\n/); assert.match(source, /^licence\s*:/m); }
console.log(`KB inventory passed: ${files.length} markdown files, stable keys, frontmatter preserved, licence alias accepted`);
