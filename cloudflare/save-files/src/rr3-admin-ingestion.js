const HEX64 = /^[a-f0-9]{64}$/i;
const SAFE_PATH = /^[a-z0-9][a-z0-9._/-]{0,240}$/i;
const MAX_SOURCE_BYTES = 512 * 1024;
const ALLOWED_STATUS = new Set(["pending_review", "queued", "rejected"]);

export function sha256Hex(value) {
  return crypto.subtle.digest("SHA-256", typeof value === "string" ? new TextEncoder().encode(value) : value)
    .then((digest) => [...new Uint8Array(digest)].map((b) => b.toString(16).padStart(2, "0")).join(""));
}

export function safeKnowledgePath(value) {
  const path = String(value || "").trim().replace(/^\/+/, "");
  if (!SAFE_PATH.test(path) || path.includes("..") || path.includes("\\") || path.includes("//")) return null;
  return path;
}

export async function promoteKnowledgeObject({ sourceBucket, targetBucket, sourceKey, targetKey, expectedSha256, expectedSize }) {
  if (!sourceBucket || !targetBucket || !safeKnowledgePath(sourceKey) || !safeKnowledgePath(targetKey) || !HEX64.test(String(expectedSha256 || ""))) throw new Error("promotion_guard_failed");
  const source = await sourceBucket.head(sourceKey);
  if (!source || Number(source.size) !== Number(expectedSize) || Number(source.size) > MAX_SOURCE_BYTES) throw new Error("source_integrity_failed");
  const object = await sourceBucket.get(sourceKey);
  if (!object) throw new Error("source_missing");
  const bytes = new Uint8Array(await object.arrayBuffer());
  const actual = await sha256Hex(bytes);
  if (actual.toLowerCase() !== String(expectedSha256).toLowerCase()) throw new Error("source_hash_mismatch");
  await targetBucket.put(targetKey, bytes, { customMetadata: { sha256: actual, size: String(bytes.byteLength) } });
  const stored = await targetBucket.head(targetKey);
  if (!stored || Number(stored.size) !== bytes.byteLength || String(stored.customMetadata?.sha256 || "").toLowerCase() !== actual.toLowerCase()) throw new Error("promotion_verification_failed");
  return { key: targetKey, size: bytes.byteLength, sha256: actual };
}

export function createSearchReindexer(env) {
  return {
    async reindex(manifest) {
      if (env.AI_SEARCH_ENABLED !== "true" || !env.KB_SEARCH || typeof env.KB_SEARCH.upsert !== "function") return { ok: false, reason: "ai_search_disabled_or_unbound" };
      if (!manifest || !Array.isArray(manifest.documents)) throw new Error("invalid_manifest");
      await env.KB_SEARCH.upsert(manifest.documents);
      return { ok: true, count: manifest.documents.length };
    }
  };
}

export async function submitPendingReview(db, input, admin) {
  const id = String(input.id || crypto.randomUUID());
  const content = String(input.content || "").slice(0, MAX_SOURCE_BYTES);
  if (!content || content.length > MAX_SOURCE_BYTES) throw new Error("invalid_content");
  const hash = await sha256Hex(content);
  const metadata = JSON.stringify({ ...(input.metadata || {}), submittedBy: admin.account.id, submittedAt: new Date().toISOString() }).slice(0, 4000);
  await db.prepare(`INSERT INTO rr3_research_queue (id, query, status, requested_by, reason, sources_json, content_sha256, metadata_json) VALUES (?, ?, 'pending_review', ?, ?, ?, ?, ?) ON CONFLICT(id) DO NOTHING`).bind(id, String(input.query || "manual-ingestion").slice(0, 500), admin.account.id, String(input.reason || "manual ingestion").slice(0, 500), JSON.stringify(input.sources || []).slice(0, 4000), hash, metadata).run();
  return { id, status: "pending_review", contentSha256: hash };
}

export async function transitionReview(db, id, status, admin) {
  if (!ALLOWED_STATUS.has(status)) throw new Error("invalid_transition");
  const current = await db.prepare("SELECT id, status, metadata_json FROM rr3_research_queue WHERE id = ?").bind(id).first();
  if (!current) return { ok: false, reason: "not_found" };
  if (current.status === status) return { ok: true, id, status, idempotent: true };
  if (current.status !== "pending_review") return { ok: false, reason: "invalid_state", status: current.status };
  const metadata = JSON.stringify({ ...(JSON.parse(current.metadata_json || "{}")), reviewedBy: admin.account.id, reviewedAt: new Date().toISOString(), decision: status }).slice(0, 4000);
  await db.prepare("UPDATE rr3_research_queue SET status = ?, metadata_json = ?, updated_at = CURRENT_TIMESTAMP, completed_at = CASE WHEN ? = 'rejected' THEN CURRENT_TIMESTAMP ELSE completed_at END WHERE id = ? AND status = 'pending_review'").bind(status, metadata, status, id).run();
  return { ok: true, id, status, idempotent: false };
}
