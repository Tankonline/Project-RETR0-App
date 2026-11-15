import test from "node:test";
import assert from "node:assert/strict";
import { readFileSync } from "node:fs";

const source = readFileSync(new URL("../src/save-files-worker.js", import.meta.url), "utf8");
test("troubleshoot prompt requires concise answer and one focused follow-up", () => {
  const start = source.indexOf("function troubleshootSystemPrompt");
  assert.ok(start >= 0);
  const excerpt = source.slice(start, start + 3000);
  assert.match(excerpt, /concise|practical|natural/i);
  assert.match(excerpt, /follow.?up/i);
  assert.match(excerpt, /source|citation/i);
});
