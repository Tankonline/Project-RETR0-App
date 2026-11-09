const encoder = new TextEncoder();
const jwksCache = new Map();

function b64urlDecode(value) {
  const padded = value.replace(/-/g, "+").replace(/_/g, "/") + "===";
  const raw = atob(padded.slice(0, padded.length - padded.length % 4));
  return Uint8Array.from(raw, (c) => c.charCodeAt(0));
}
function decodeJson(value) { return JSON.parse(new TextDecoder().decode(b64urlDecode(value))); }
function timingSafeEqual(a, b) { if (a.length !== b.length) return false; let result = 0; for (let i = 0; i < a.length; i++) result |= a[i] ^ b[i]; return result === 0; }
async function sha256(value) { const bytes = typeof value === "string" ? encoder.encode(value) : value; return crypto.subtle.digest("SHA-256", bytes); }
async function hexDigest(value) { return [...new Uint8Array(await sha256(value))].map((b) => b.toString(16).padStart(2, "0")).join(""); }

export async function validateFirebaseOidcJwt(request, env) {
  const header = request.headers.get("Authorization") || "";
  if (!header.startsWith("Bearer ")) return { ok: false, status: 401, code: "authentication_required", message: "Bearer authentication is required" };
  const token = header.slice(7).trim();
  const parts = token.split(".");
  if (parts.length !== 3 || token.length > 8192) return { ok: false, status: 401, code: "invalid_token", message: "The bearer token is invalid" };
  let h; let p;
  try { h = decodeJson(parts[0]); p = decodeJson(parts[1]); } catch { return { ok: false, status: 401, code: "invalid_token", message: "The bearer token is invalid" }; }
  const issuer = env.AUTH_ISSUER || (env.FIREBASE_PROJECT_ID ? `https://securetoken.google.com/${env.FIREBASE_PROJECT_ID}` : "");
  const audience = env.AUTH_AUDIENCE || env.FIREBASE_PROJECT_ID || "";
  if (h.alg !== "RS256" || !h.kid || p.iss !== issuer || p.aud !== audience || !p.sub || typeof p.exp !== "number" || p.exp <= Math.floor(Date.now() / 1000) || (p.iat && p.iat > Math.floor(Date.now() / 1000) + 60)) return { ok: false, status: 401, code: "invalid_token", message: "The bearer token claims are invalid" };
  const jwksUrl = env.AUTH_JWKS_URL || "https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system.gserviceaccount.com";
  let keys = jwksCache.get(jwksUrl);
  if (!keys || keys.expires < Date.now()) { const response = await fetch(jwksUrl); if (!response.ok) return { ok: false, status: 503, code: "jwks_unavailable", message: "Authentication keys are unavailable" }; keys = { keys: await response.json(), expires: Date.now() + 300000 }; jwksCache.set(jwksUrl, keys); }
  const jwk = keys.keys[h.kid];
  if (!jwk) return { ok: false, status: 401, code: "invalid_token", message: "The signing key is unknown" };
  try {
    const key = await crypto.subtle.importKey("jwk", { ...jwk, alg: "RS256", use: "sig" }, { name: "RSASSA-PKCS1-v1_5", hash: "SHA-256" }, false, ["verify"]);
    const valid = await crypto.subtle.verify("RSASSA-PKCS1-v1_5", key, b64urlDecode(parts[2]), encoder.encode(`${parts[0]}.${parts[1]}`));
    if (!valid) throw new Error("signature");
  } catch { return { ok: false, status: 401, code: "invalid_token", message: "The bearer token signature is invalid" }; }
  return { ok: true, issuer, subject: String(p.sub), email: typeof p.email === "string" ? p.email.slice(0, 320) : null, displayName: typeof p.name === "string" ? p.name.slice(0, 200) : null };
}

export async function getOrCreateAccount(db, identity) {
  const existing = await db.prepare("SELECT * FROM accounts WHERE issuer = ? AND subject = ? LIMIT 1").bind(identity.issuer, identity.subject).first();
  if (existing) return existing;
  const id = crypto.randomUUID();
  await db.prepare("INSERT INTO accounts (id, issuer, subject, email, display_name) VALUES (?, ?, ?, ?, ?)").bind(id, identity.issuer, identity.subject, identity.email, identity.displayName).run();
  return await db.prepare("SELECT * FROM accounts WHERE id = ?").bind(id).first();
}

export async function authenticatedAccount(request, env, requestId) {
  const identity = await validateFirebaseOidcJwt(request, env);
  if (!identity.ok) return identity;
  const account = await getOrCreateAccount(env.SKINS_DB, identity);
  if (!account || account.status !== "active") return { ok: false, status: 403, code: "account_disabled", message: "The account is disabled" };
  return { ok: true, account, identity, requestId };
}

// Deliberately separate from installation authentication: this guard is only
// for admin ingestion routes and fails closed when claim configuration is
// absent. Firebase custom claims must include the exact configured admin claim.
export async function authenticatedAdmin(request, env, requestId) {
  const identity = await validateFirebaseOidcJwt(request, env);
  if (!identity.ok) return identity;
  const claim = String(env.ADMIN_CLAIM || "admin").trim();
  const raw = request.headers.get("X-Firebase-Admin-Claim");
  if (raw !== "true" && raw !== claim) return { ok: false, status: 403, code: "admin_claim_required", message: "An administrator claim is required" };
  const account = await getOrCreateAccount(env.SKINS_DB, identity);
  if (!account || account.status !== "active") return { ok: false, status: 403, code: "account_disabled", message: "The account is disabled" };
  return { ok: true, account, identity, requestId, claim };
}

export async function auditAuth(db, eventType, accountId, request, requestId, metadata = {}) {
  const ip = request.headers.get("CF-Connecting-IP") || "";
  const ipHash = ip ? (await hexDigest(ip)).slice(0, 32) : null;
  await db.prepare("INSERT INTO auth_audit_events (account_id, event_type, installation_id, request_id, ip_hash, metadata_json) VALUES (?, ?, ?, ?, ?, ?)").bind(accountId || null, eventType, request.headers.get("X-Installation-ID") || null, requestId || null, ipHash, JSON.stringify(metadata).slice(0, 2000)).run();
}

export async function authRateLimit(env, request) {
  const key = await hexDigest(`${request.headers.get("CF-Connecting-IP") || "unknown"}:${new URL(request.url).pathname}`);
  if (!env.AUTH_RATE_LIMIT || typeof env.AUTH_RATE_LIMIT.get !== "function") return true;
  const current = Number(await env.AUTH_RATE_LIMIT.get(key)) || 0;
  if (current >= Number(env.AUTH_RATE_LIMIT_MAX || 30)) return false;
  await env.AUTH_RATE_LIMIT.put(key, String(current + 1), { expirationTtl: Number(env.AUTH_RATE_LIMIT_TTL || 60) });
  return true;
}
