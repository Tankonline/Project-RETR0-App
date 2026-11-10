import assert from "node:assert/strict";
import { normalizeResearchMetadata, researchSources } from "./src/rr3-knowledge-scraper.js";
assert.equal(normalizeResearchMetadata({ licence: "CC BY" }).license, "CC BY");
assert.equal(normalizeResearchMetadata({}).review_status, "pending_review");
assert.equal(normalizeResearchMetadata({ title: "x" }).title, "x");
assert.deepEqual(researchSources([{ source_url: "https://example.test/a" }, { source_url: "https://example.test/a" }, { text: "no source" }]), ["https://example.test/a"]);
console.log("RR3 provenance and metadata alias guards passed");
