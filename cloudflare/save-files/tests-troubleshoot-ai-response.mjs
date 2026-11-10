import assert from "node:assert/strict";
import { readFileSync } from "node:fs";

const source = readFileSync(new URL("src/save-files-worker.js", import.meta.url), "utf8");
const graniteModel = "@cf/ibm-granite/granite-4.0-h-micro";
assert.equal((source.match(new RegExp(graniteModel.replace(/[.*+?^${}()|[\\]\\\\]/g, "\\\\$&"), "g")) || []).length, 1, "source must use Granite exactly once");
assert.ok(!source.includes("@cf/meta/llama-3.2-3b-instruct"), "legacy Llama model must not remain");
const start = source.indexOf("function stripTroubleshootPresentation");
const end = source.indexOf("function safeCarModelPath", start);
assert.ok(start >= 0 && end > start, "normalizer must exist");
const normalize = new Function(`${source.slice(start, end)}; return normalizeTroubleshootAiResponse;`)();

assert.equal(normalize("plain response"), "plain response");
assert.equal(normalize('{"response":"Natural answer"}'), "Natural answer");
assert.equal(normalize("Natural answer\n\nSources:\n- kb:rr3/internal/file"), "Natural answer");
assert.equal(normalize("Useful answer\n\nDocumented Facts:\n- internal path"), "Useful answer");
assert.equal(normalize({ response: "response field" }), "response field");
assert.equal(normalize({ text: "text response" }), "text response");
assert.equal(normalize({ choices: [{ message: { content: "Granite chat content" } }] }), "Granite chat content");
assert.equal(normalize({ choices: [{ text: "Granite completion text" }] }), "Granite completion text");
assert.equal(normalize({ choices: [{ message: { content: "   " } }, { text: "second choice" }] }), "second choice");
assert.equal(normalize({ output: { content: { message: "nested output message" } } }), "nested output message");
assert.equal(normalize({ content: { message: { text: "deep text" } } }), "deep text");
assert.equal(normalize([{ output: "array output" }]), "array output");
assert.equal(normalize(["array string"]), "array string");
assert.equal(normalize({ response: "   " }), null);
assert.equal(normalize({ choices: [], unexpected: { value: 1 } }), null);
assert.equal(normalize({ unexpected: { value: 1 } }), null);
assert.equal(normalize(null), null);
assert.equal(normalize(42), null);

for (const configPath of ["wrangler.jsonc", "wrangler.staging.jsonc"]) {
  const config = readFileSync(new URL(configPath, import.meta.url), "utf8");
  assert.ok(config.includes('"binding": "AI"'), `${configPath}: AI binding must remain declared`);
}
console.log("troubleshoot Granite model and AI response normalization tests passed");
