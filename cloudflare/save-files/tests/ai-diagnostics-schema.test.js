import test from "node:test";
import assert from "node:assert/strict";

const forbidden = ["question", "prompt", "body", "token", "ip", "nonce", "signature", "installationId"];

test("diagnostics allowlist is metadata-only", () => {
  const diagnostics = {
    requestId: "req-1",
    route: "/troubleshoot",
    featureFlags: { aiSearchEnabled: false, ragEnabled: false, webResearchEnabled: false },
    aiSearchAttempted: true,
    aiSearchEnabled: false,
    aiSearchUsed: false,
    bindingReady: false,
    instanceReady: false,
    chunkCount: 0,
    highestScore: null,
    sourceCount: 0,
    triggerReason: "troubleshoot",
    vectorizeFallbackUsed: false,
    webResearchAttempted: false,
    webResearchUsed: false,
    queueInsertionStatus: "not_attempted",
    safeErrorCategory: null,
    durationMs: 4,
    responseSchemaPresent: true
  };
  const serialized = JSON.stringify(diagnostics).toLowerCase();
  for (const field of forbidden) assert.equal(serialized.includes(field.toLowerCase()), false, field);
  assert.equal(typeof diagnostics.requestId, "string");
  assert.equal(typeof diagnostics.durationMs, "number");
  assert.equal(typeof diagnostics.featureFlags.aiSearchEnabled, "boolean");
});
