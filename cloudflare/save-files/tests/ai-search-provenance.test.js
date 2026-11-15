import test from "node:test";
import assert from "node:assert/strict";

function sanitize(sources) {
  return [...new Set(sources.map((value) => {
    if (typeof value !== "string" || !value.trim()) return null;
    const source = value.trim();
    try {
      const url = new URL(source);
      return url.protocol === "https:" ? `${url.origin}${url.pathname || "/"}${url.search}` : null;
    } catch {
      return /^(?:kb|r2|source|key):[A-Za-z0-9._:/-]+$/.test(source) || /^[A-Za-z0-9._-]+(?:\/[A-Za-z0-9._-]+)+$/.test(source) ? source : null;
    }
  }).filter(Boolean))];
}

function normalizeChunks(chunks) {
  const sources = sanitize(chunks.flatMap((chunk) => [chunk?.item?.key, chunk?.item?.url]));
  const aiSearchUsed = chunks.length > 0 && chunks.some((chunk) => Boolean(chunk?.text || chunk?.content || chunk?.item?.text));
  const vectorizeFallbackUsed = false;
  return { sources, sourceCount: sources.length, rag_used: aiSearchUsed || vectorizeFallbackUsed };
}

test("R2 key-only chunk preserves item key with empty URL semantics", () => {
  assert.deepEqual(normalizeChunks([{ item: { key: "kb/file.md" }, text: "context" }]), { sources: ["kb/file.md"], sourceCount: 1, rag_used: true });
});

test("URL chunk preserves HTTPS URL", () => {
  assert.deepEqual(normalizeChunks([{ item: { key: "kb/file.md", url: "https://docs.example/file" }, text: "context" }]), { sources: ["kb/file.md", "https://docs.example/file"], sourceCount: 2, rag_used: true });
});

test("zero chunks produce no provenance and no retrieval usage", () => {
  assert.deepEqual(normalizeChunks([]), { sources: [], sourceCount: 0, rag_used: false });
});

test("diagnostics source count equals final sanitized provenance", () => {
  const response = normalizeChunks([{ item: { key: "kb/file.md" }, text: "context" }]);
  const diagnostics = { sourceCount: response.sourceCount };
  assert.equal(diagnostics.sourceCount, response.sources.length);
});
