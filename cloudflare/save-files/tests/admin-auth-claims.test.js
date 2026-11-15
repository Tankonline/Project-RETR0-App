import test from "node:test";
import assert from "node:assert/strict";
import { authenticatedAdmin } from "../src/auth.js";

test("admin authorization does not trust client claim headers", async () => {
  const original = globalThis.fetch;
  globalThis.fetch = async () => new Response(JSON.stringify({}), { status: 200 });
  try {
    const request = new Request("https://example.test/v1/admin/rr3-ingestion", {
      headers: { Authorization: "Bearer malformed", "X-Firebase-Admin-Claim": "true" }
    });
    const result = await authenticatedAdmin(request, { FIREBASE_PROJECT_ID: "project", SKINS_DB: {} }, "req");
    assert.notEqual(result.code, "admin_claim_required");
    assert.equal(result.ok, false);
    assert.equal(result.code, "invalid_token");
  } finally {
    globalThis.fetch = original;
  }
});
