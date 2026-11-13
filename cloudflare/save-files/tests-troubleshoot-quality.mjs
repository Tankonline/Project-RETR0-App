import assert from "node:assert/strict";
import { readFileSync } from "node:fs";

const source = readFileSync(new URL("src/save-files-worker.js", import.meta.url), "utf8");
const start = source.indexOf("function stripTroubleshootPresentation");
const end = source.indexOf("function safeCarModelPath", start);
assert.ok(start >= 0 && end > start);
const helpers = new Function(`${source.slice(start, end)}; return { sanitizeTroubleshootSources, normalizeTroubleshootAiAnswer, troubleshootSystemPrompt };`)();

const prompt = helpers.troubleshootSystemPrompt("troubleshoot");
assert.match(prompt, /natural, practical, and concise/i);
assert.match(prompt, /retrieved context silently/i);
assert.match(prompt, /no Markdown source\/citation block/i);
assert.match(prompt, /exactly one focused follow-up/i);
assert.doesNotMatch(prompt, /no matching documents/i);

assert.deepEqual(helpers.sanitizeTroubleshootSources([
  "https://realracing3.fandom.com/wiki/Real_Racing_3#bad",
  "http://example.com/no",
  "https://user:pass@example.com/private",
  "https://realracing3.fandom.com/wiki/Real_Racing_3",
  "https://realracing3.fandom.com/wiki/Real_Racing_3?source_key=source-1"
]), ["https://realracing3.fandom.com/wiki/Real_Racing_3", "https://realracing3.fandom.com/wiki/Real_Racing_3?source_key=source-1"]);

const normalized = helpers.normalizeTroubleshootAiAnswer({ response: "Try the documented check.", confidence: "high", sources: ["https://realracing3.fandom.com/wiki/Real_Racing_3"], follow_up_question: "Which Android version are you using?" });
assert.deepEqual(normalized, {
  response: "Try the documented check.", rag_used: true, research_used: false, confidence: "high",
  sources: ["https://realracing3.fandom.com/wiki/Real_Racing_3"], follow_up_question: "Which Android version are you using?"
});
const fallback = helpers.normalizeTroubleshootAiAnswer({ response: "", confidence: "unknown", sources: ["not-a-url"] });
assert.equal(fallback.confidence, "low");
assert.equal(fallback.rag_used, false);
assert.equal(fallback.research_used, false);
assert.deepEqual(fallback.sources, []);
assert.match(fallback.response, /can’t confirm/i);

assert.match(source, /authenticate\(request, env\.SKINS_DB, url, bodyBytes\)/);
assert.match(source, /TROUBLESHOOT_AI_SEARCH_UNVERIFIED/);
assert.match(source, /research_used: false/);
console.log("troubleshoot prompt, schema, source sanitization, low-confidence fallback, and auth guards passed");
