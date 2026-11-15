import test from "node:test";
import assert from "node:assert/strict";

function decode(value) {
  let text = String(value || "").trim();
  for (let i = 0; i < 3; i += 1) {
    const next = text.replace(/\\u([0-9a-f]{4})/gi, (_, h) => String.fromCharCode(parseInt(h, 16)))
      .replace(/\\r\\n|\\n|\\r/g, "\n").replace(/\\"/g, '"').replace(/\\\\/g, "\\");
    if (next === text) break;
    text = next;
  }
  return text;
}
function normalize(value) {
  if (typeof value === "string") {
    const text = decode(value).replace(/^```(?:json)?\s*/i, "").replace(/\s*```$/, "").trim();
    if (text.startsWith("{") || text.startsWith("[")) {
      try { return normalize(JSON.parse(text)); } catch { /* plain text fallback */ }
    }
    return text.replace(/(^|\n)\s*(?:documented facts|sources|citations?|follow[- ]?up question)\s*:?\s*[\s\S]*$/gim, "$1")
      .replace(/\n\s*(?:[-*]\s*)?(?:source|citation|reference)\s*:\s*(?:https?:\/\/|kb\/|kb:)[^\n]*/gim, "")
      .replace(/\n{3,}/g, "\n\n").trim();
  }
  if (!value || typeof value !== "object") return null;
  for (const key of ["response", "answer", "result", "output", "content", "message"]) {
    if (value[key] != null) { const result = normalize(value[key]); if (result) return result; }
  }
  if (Array.isArray(value)) for (const item of value) { const result = normalize(item); if (result) return result; }
  return null;
}

test("recursively unwraps escaped nested response and strips presentation blocks", () => {
  const sample = { result: { output: "Restart the app.\n\nDocumented Facts:\n- verified\n\nSources:\n- kb/rr3/internal/path\n\nFollow-up question: Which device?" } };
  assert.equal(normalize(sample), "Restart the app.");
});

test("malformed JSON safely falls back to plain text", () => {
  assert.equal(normalize('{"output":"not closed'), '{"output":"not closed');
});

test("unicode and escaped quotes are decoded", () => {
  assert.equal(normalize('"A \\u2013 \\"safe\\" answer"'), '"A – "safe" answer"');
});
