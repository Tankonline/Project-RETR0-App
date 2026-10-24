var __defProp = Object.defineProperty;
var __name = (target, value) => __defProp(target, "name", { value, configurable: true });

// ../save-files-worker-deploy.js
var __defProp2 = Object.defineProperty;
var __name2 = /* @__PURE__ */ __name((target, value) => __defProp2(target, "name", { value, configurable: true }), "__name");
var UUID_PATTERN = /^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/i;
var SHA256_PATTERN = /^[a-f0-9]{64}$/i;
var CAR_PATTERN = /^[a-z0-9][a-z0-9_-]{0,99}$/i;
var SLOT_PATTERN = /^(?:ext|ext\d+|ext_[a-z0-9_-]+|int|int\d+|int_[a-z0-9_-]+)$/i;
var FORMAT_PATTERN = /^[a-z0-9_-]{1,24}$/i;
var SKIN_FILE_PATTERN = /^[a-z0-9][a-z0-9_() .-]*\.dds\.z$/i;
var MAX_SKIN_BYTES = 5 * 1024 * 1024;
var MAX_SAVE_BYTES = 25 * 1024 * 1024;
var MAX_MUSIC_BYTES = 25 * 1024 * 1024;
var MAX_COVER_BYTES = 500 * 1024;
var MAX_SCREENSHOT_BYTES = 1024 * 1024;
var MAX_JSON_BYTES = 32 * 1024;
var AUTH_WINDOW_MS = 5 * 60 * 1e3;
var NONCE_TTL_SECONDS = 10 * 60;
var REPORT_REASONS = /* @__PURE__ */ new Set(["inappropriate", "copyright", "misleading", "broken", "spam", "other"]);
var CAR_MODEL_MAX_FILES = 256;
var CAR_MODEL_MAX_FILE_BYTES = 20 * 1024 * 1024;
var CAR_MODEL_MAX_TOTAL_BYTES = 520 * 1024 * 1024;
var CAR_MODEL_MAX_MANIFEST_BYTES = 256 * 1024;
var RR3_PACKAGE_IDS = /* @__PURE__ */ new Set(["com.ea.games.r3_row", "com.ea.games.r3_na", "com.ea.games.r3_ww"]);
async function handleV1(request, env, ctx, requestId) {
  const url = new URL(request.url);
  if (!url.pathname.startsWith("/v1/")) return null;
  const respond = /* @__PURE__ */ __name2((data, status = 200, headers = {}) => new Response(data === null ? null : JSON.stringify(data), {
    status,
    headers: {
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Expose-Headers": "Content-Length, Content-Range, Accept-Ranges, ETag, X-Request-ID",
      "Content-Type": "application/json; charset=utf-8",
      "Cache-Control": "no-store",
      "X-Request-ID": requestId,
      ...headers
    }
  }), "respond");
  const fail = /* @__PURE__ */ __name2((status, code, message, details) => respond({ error: { code, message, ...details ? { details } : {} }, requestId }, status), "fail");
  if (!env.SKINS_DB || !env.COMMUNITY_BUCKET) return fail(503, "service_unavailable", "Skin storage is unavailable");
  try {
    if (url.pathname === "/v1/installations" && request.method === "POST") {
      const bodyBytes2 = await boundedBody(request, MAX_JSON_BYTES);
      if (!bodyBytes2) return fail(400, "invalid_body", "A JSON request body is required");
      const body = parseJson(bodyBytes2);
      if (!body) return fail(400, "invalid_json", "The request body is not valid JSON");
      const installationId = clean(body.installationId, 36).toLowerCase();
      if (!UUID_PATTERN.test(installationId)) return fail(400, "invalid_installation_id", "installationId must be a UUID");
      if (!isP256PublicJwk(body.publicKey)) return fail(400, "invalid_public_key", "publicKey must be a P-256 public JWK");
      const verified = await verifySignedRequest(request, url, bodyBytes2, body.publicKey, installationId);
      if (!verified.ok) return fail(verified.status, verified.code, verified.message);
      const existing = await env.SKINS_DB.prepare("SELECT public_key_jwk FROM anonymous_installations WHERE id = ? LIMIT 1").bind(installationId).first();
      const publicKeyJwk = stablePublicJwk(body.publicKey);
      if (existing && existing.public_key_jwk !== publicKeyJwk) return fail(409, "installation_exists", "This installation is already registered with another key");
      await env.SKINS_DB.prepare(`
				INSERT INTO anonymous_installations (id, public_key_jwk, status, created_at, updated_at)
				VALUES (?, ?, 'active', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
				ON CONFLICT(id) DO UPDATE SET updated_at = CURRENT_TIMESTAMP
			`).bind(installationId, publicKeyJwk).run();
      try {
        await consumeNonce(env.SKINS_DB, installationId, verified.nonce);
      } catch {
        return fail(409, "replayed_request", "The request nonce was already used");
      }
      return respond({ installationId, status: "active", requestId }, existing ? 200 : 201);
    }
    if (url.pathname === "/v1/music" && request.method === "GET") {
      const auth = await authenticate(request, env.SKINS_DB, url, new Uint8Array()).catch(() => ({ ok: false }));
      return listMusic(env.SKINS_DB, env, url, respond, auth.ok ? auth.installationId : null);
    }
    const musicMatch = /^\/v1\/music\/([0-9a-f-]{36})(?:\/like)?$/i.exec(url.pathname);
    if (musicMatch && (request.method === "GET" || request.method === "HEAD")) {
      const identity2 = await authenticate(request, env.SKINS_DB, url, new Uint8Array());
      if (!identity2.ok) return fail(identity2.status, identity2.code, identity2.message);
      return readMusic(request, env, ctx, musicMatch[1].toLowerCase(), requestId, respond, fail, identity2.installationId);
    }
    if (musicMatch && url.pathname.endsWith("/like")) {
      const identity2 = await authenticate(request, env.SKINS_DB, url, new Uint8Array());
      if (!identity2.ok) return fail(identity2.status, identity2.code, identity2.message);
      return setItemLike(env.SKINS_DB, "music", musicMatch[1].toLowerCase(), identity2.installationId, request.method === "POST", requestId, respond, fail);
    }
    if (url.pathname === "/v1/saves" && request.method === "GET") {
      const auth = await authenticate(request, env.SKINS_DB, url, new Uint8Array()).catch(() => ({ ok: false }));
      return listSaves(env.SKINS_DB, env, url, respond, auth.ok ? auth.installationId : null);
    }
    const saveMatch = /^\/v1\/saves\/([0-9a-f-]{36})(?:\/like)?$/i.exec(url.pathname);
    if (saveMatch && (request.method === "GET" || request.method === "HEAD")) {
      const identity2 = await authenticate(request, env.SKINS_DB, url, new Uint8Array());
      if (!identity2.ok) return fail(identity2.status, identity2.code, identity2.message);
      return readSave(request, env, ctx, saveMatch[1].toLowerCase(), requestId, respond, fail, identity2.installationId);
    }
    if (saveMatch && url.pathname.endsWith("/like")) {
      if (request.method !== "POST" && request.method !== "DELETE") return fail(405, "method_not_allowed", "Method not allowed");
      const identity2 = await authenticate(request, env.SKINS_DB, url, new Uint8Array());
      if (!identity2.ok) return fail(identity2.status, identity2.code, identity2.message);
      return setItemLike(env.SKINS_DB, "save", saveMatch[1].toLowerCase(), identity2.installationId, request.method === "POST", requestId, respond, fail);
    }
    if (saveMatch && request.method === "DELETE") {
      const identity2 = await authenticate(request, env.SKINS_DB, url, new Uint8Array());
      if (!identity2.ok) return fail(identity2.status, identity2.code, identity2.message);
      return deleteSave(env, saveMatch[1].toLowerCase(), identity2.installationId, requestId, respond, fail);
    }
    if (url.pathname === "/v1/saves" && request.method === "POST") {
      const declaredLength = parseContentLength(request.headers.get("Content-Length"));
      if (declaredLength === null) return fail(411, "content_length_required", "A valid Content-Length is required");
      if (declaredLength <= 0) return fail(400, "empty_save", "Save file is required");
      if (declaredLength > MAX_SAVE_BYTES) return fail(413, "body_too_large", "Save file exceeds 25 MiB");
      const bodyBytes2 = await boundedBody(request, MAX_SAVE_BYTES);
      if (bodyBytes2 === null) return fail(413, "body_too_large", "Save file exceeds 25 MiB");
      if (bodyBytes2.length !== declaredLength) return fail(400, "content_length_mismatch", "Content-Length does not match the save body");
      const identity2 = await authenticate(request, env.SKINS_DB, url, bodyBytes2);
      if (!identity2.ok) return fail(identity2.status, identity2.code, identity2.message);
      return uploadSave(bodyBytes2, env, url, identity2.installationId, requestId, respond, fail);
    }
    if (url.pathname === "/v1/music" && request.method === "POST") {
      const declaredLength = parseContentLength(request.headers.get("Content-Length"));
      if (declaredLength === null) return fail(411, "content_length_required", "A valid Content-Length is required");
      if (declaredLength <= 0) return fail(400, "empty_music", "Music file is required");
      if (declaredLength > MAX_MUSIC_BYTES) return fail(413, "body_too_large", "Music file exceeds 25 MiB");
      const bodyBytes2 = await boundedBody(request, MAX_MUSIC_BYTES);
      if (bodyBytes2 === null) return fail(413, "body_too_large", "Music file exceeds 25 MiB");
      if (bodyBytes2.length !== declaredLength) return fail(400, "content_length_mismatch", "Content-Length does not match the music body");
      const identity2 = await authenticate(request, env.SKINS_DB, url, bodyBytes2);
      if (!identity2.ok) return fail(identity2.status, identity2.code, identity2.message);
      return uploadMusic(bodyBytes2, env, url, identity2.installationId, requestId, respond, fail);
    }
    const musicCoverMatch = /^\/v1\/music\/([0-9a-f-]{36})\/cover$/i.exec(url.pathname);
    if (musicCoverMatch && request.method === "PUT") {
      const bodyBytes2 = await boundedBody(request, MAX_COVER_BYTES);
      if (bodyBytes2 === null) return fail(413, "body_too_large", "Artwork exceeds 500 KiB");
      const identity2 = await authenticate(request, env.SKINS_DB, url, bodyBytes2);
      if (!identity2.ok) return fail(identity2.status, identity2.code, identity2.message);
      return uploadMusicCover(bodyBytes2, request, env, musicCoverMatch[1].toLowerCase(), identity2.installationId, requestId, respond, fail);
    }
    if (musicCoverMatch && (request.method === "GET" || request.method === "HEAD")) {
      return readMusicCover(request, env, musicCoverMatch[1].toLowerCase(), requestId, fail);
    }
    if (musicMatch && request.method === "DELETE") {
      const identity2 = await authenticate(request, env.SKINS_DB, url, new Uint8Array());
      if (!identity2.ok) return fail(identity2.status, identity2.code, identity2.message);
      return softDeleteMusic(env, ctx, musicMatch[1].toLowerCase(), identity2.installationId, requestId, respond, fail);
    }
    if (url.pathname === "/v1/installations" && request.method === "DELETE") {
      const identity2 = await authenticate(request, env.SKINS_DB, url, new Uint8Array());
      if (!identity2.ok) return fail(identity2.status, identity2.code, identity2.message);
      return deleteInstallation(env, ctx, identity2.installationId, requestId, respond, fail);
    }
    if (url.pathname === "/v1/car-models" || url.pathname.startsWith("/v1/car-models/")) {
      return handleCarModelRoute(request, env, ctx, url, requestId, respond, fail);
    }
    const listRoute = url.pathname === "/v1/skins";
    const route = matchSkinRoute(url.pathname);
    if (listRoute && request.method === "GET") {
      const auth = await optionalAuthentication(request, env.SKINS_DB, url, "skin_list");
      return listSkins(env.SKINS_DB, env, url, respond, auth.installationId);
    }
    if (route && (request.method === "GET" || request.method === "HEAD")) {
      const auth = await optionalAuthentication(request, env.SKINS_DB, url, "skin_read");
      return readSkin(request, env, ctx, url, route, requestId, respond, fail, auth.installationId);
    }
    const bodyLimit = route?.action === "binary" ? MAX_SKIN_BYTES : route?.action === "cover" ? MAX_COVER_BYTES : route?.action === "screenshot" ? MAX_SCREENSHOT_BYTES : MAX_JSON_BYTES;
    const bodyBytes = request.method === "GET" || request.method === "HEAD" || request.method === "DELETE" ? new Uint8Array() : await boundedBody(request, bodyLimit);
    if (bodyBytes === null) return fail(413, "body_too_large", "Request body exceeds the route limit");
    const identity = await authenticate(request, env.SKINS_DB, url, bodyBytes);
    if (!identity.ok) return fail(identity.status, identity.code, identity.message);
    if (listRoute && request.method === "POST") return createSkin(bodyBytes, request, env.SKINS_DB, env, url, identity.installationId, requestId, respond, fail);
    if (!route) return fail(404, "not_found", "Route not found");
    if (route.action === "binary" && request.method === "PUT") return uploadBinary(bodyBytes, env, route.id, identity.installationId, requestId, respond, fail);
    if (route.action === "cover" && request.method === "PUT") return uploadImage(bodyBytes, request, env, route, identity.installationId, "cover", requestId, respond, fail);
    if (route.action === "screenshot" && request.method === "PUT") return uploadImage(bodyBytes, request, env, route, identity.installationId, "screenshot", requestId, respond, fail);
    if (route.action === "manifest" && request.method === "PUT") return uploadManifest(bodyBytes, request, env, route.id, identity.installationId, requestId, respond, fail);
    if (route.action === "publish" && request.method === "POST") return publishSkin(env, route.id, identity.installationId, requestId, respond, fail);
    if (route.action === "like" && request.method === "POST") return setLike(env.SKINS_DB, route.id, identity.installationId, true, requestId, respond, fail);
    if (route.action === "like" && request.method === "DELETE") return setLike(env.SKINS_DB, route.id, identity.installationId, false, requestId, respond, fail);
    if (route.action === "report" && request.method === "POST") return reportSkin(bodyBytes, env.SKINS_DB, route.id, identity.installationId, requestId, respond, fail);
    if (route.action === "details" && request.method === "DELETE") return softDeleteSkin(env, ctx, route.id, identity.installationId, requestId, respond, fail);
    return fail(405, "method_not_allowed", "Method not allowed");
  } catch (error) {
    console.error(JSON.stringify({ event: "v1_request_failed", requestId, method: request.method, path: url.pathname, error: error instanceof Error ? error.message : String(error) }));
    return fail(500, "internal_error", "The request could not be completed");
  }
}
__name(handleV1, "handleV1");
__name2(handleV1, "handleV1");
function safeCarModelPath(value) {
  return typeof value === "string" && value.length > 0 && value.length <= 255 && !value.startsWith("/") && !value.includes("\\") && !/^[A-Za-z]:/.test(value) && value.split("/").every((part) => part && part !== "." && part !== "..") && !/[\u0000-\u001f]/.test(value);
}
function carModelKey(modelId, relativePath) { return `car-models/${modelId}/files/${relativePath}`; }
function validateCarModelManifest(manifest, modelId) {
  if (!manifest || typeof manifest !== "object" || Array.isArray(manifest)) return invalid("invalid_manifest", "Manifest must be an object");
  if (Number(manifest.manifestVersion ?? manifest.schemaVersion ?? 1) !== 1) return invalid("invalid_manifest_version", "Unsupported manifest version");
  const metadata = manifest.metadata && typeof manifest.metadata === "object" ? manifest.metadata : {};
  const target = clean(manifest.targetVehicleDirectory ?? manifest.target?.vehicleDirectory, 100);
  const packageId = clean(manifest.packageId ?? (Array.isArray(manifest.supportedGamePackages) ? manifest.supportedGamePackages[0] : ""), 100).toLowerCase();
  const files = Array.isArray(manifest.files) ? manifest.files : [];
  if (!clean(manifest.packageType, 64) || clean(manifest.packageType, 64) !== "rr3-car-model") return invalid("invalid_package_type", "Unsupported packageType");
  if (!clean(metadata.name ?? manifest.title, 120)) return invalid("invalid_package_metadata", "metadata.name is required");
  if (!CAR_PATTERN.test(target) || target !== (manifest.targetVehicleDirectory ?? manifest.target?.vehicleDirectory)) return invalid("invalid_target_vehicle_directory", "targetVehicleDirectory is invalid");
  if (!RR3_PACKAGE_IDS.has(packageId)) return invalid("invalid_package_id", "Unsupported RR3 packageId");
  if (!files.length || files.length > CAR_MODEL_MAX_FILES) return invalid("invalid_file_count", "Manifest file count is outside the allowed range");
  const seen = new Set(); const destinations = new Set(); let total = 0;
  const normalized = [];
  for (const file of files) {
    const path = file && (file.path ?? file.sourcePath);
    const destination = file && (file.destinationRelativePath ?? file.destinationPath);
    if (!file || !safeCarModelPath(path) || !safeCarModelPath(destination)) return invalid("invalid_file_path", "Files require safe source and destination relative paths");
    const addon = String(manifest.installMode ?? manifest.mode ?? "REPLACE").toUpperCase() === "ADDON";
    const root = String(file.root ?? file.destinationRoot ?? "VEHICLE").toUpperCase();
    const expected = root === "VEHICLE" ? `${target}/${path}` : root === "VEHICLES_DATA" ? `vehicles/data/${path}` : root === "DEPOT_DATA" ? `data/${path}` : root === "DEPOT_ROOT" ? path : null;
    const recognizedDepotRoot = root === "DEPOT_ROOT" && (path === "gametext.txt" || path === "asset_list_base.txtCache.txt" || /^text_[^/]+\.txt$/.test(path) || path === "gametext/asset_list_base.txtCache.txt" || /^gametext\/text_[^/]+\.txt$/.test(path));
    if (!expected || destination !== expected || (root !== "VEHICLE" && !addon) || (root === "DEPOT_ROOT" && !recognizedDepotRoot)) return invalid("invalid_destination_path", `Destination for ${path} is not allowed`);
    if (seen.has(path) || destinations.has(destination)) return invalid("duplicate_file_path", "Manifest contains duplicate or colliding paths");
    seen.add(path); destinations.add(destination);
    const size = Number(file.size ?? file.sizeBytes); const sha256 = clean(file.sha256, 64).toLowerCase();
    if (!Number.isSafeInteger(size) || size <= 0 || size > CAR_MODEL_MAX_FILE_BYTES) return invalid("invalid_file_size", `File size for ${path} is invalid`);
    if (!SHA256_PATTERN.test(sha256)) return invalid("invalid_file_sha256", `SHA-256 for ${path} is invalid`);
    total += size; if (total > CAR_MODEL_MAX_TOTAL_BYTES) return invalid("total_size_exceeded", "Manifest total size exceeds the limit");
    normalized.push({ path, destination, role: clean(file.role, 32) || "asset", detectedFormat: clean(file.detectedFormat, 24).toLowerCase() || null, variant: clean(file.variant, 64) || null, required: Boolean(file.required), size, sha256, contentType: clean(file.contentType, 120).toLowerCase() || "application/octet-stream" });
  }
  return { ok: true, target, packageId, manifestVersion: 1, packageName: clean(metadata.name ?? manifest.title, 120), supportedVersions: clean(Array.isArray(manifest.supportedVersions) ? manifest.supportedVersions.join(",") : manifest.supportedVersions, 512), files: normalized, fileCount: normalized.length, totalSize: total };
}
async function handleCarModelRoute(request, env, ctx, url, requestId, respond, fail) {
  const match = /^\/v1\/car-models(?:\/([^/]+))?(?:\/(manifest|files|cover|screenshots|publish|like|report))?(?:\/(.*))?$/i.exec(url.pathname);
  if (!match) return fail(404, "not_found", "Route not found");
  const modelId = match[1] && UUID_PATTERN.test(match[1]) ? match[1].toLowerCase() : null;
  let action = (match[2] || "").toLowerCase();
  let tail = null;
  try { tail = match[3] ? decodeURIComponent(match[3]) : null; } catch { return fail(400, "invalid_path", "Path encoding is invalid"); }
  if (tail && (tail.includes("?") || tail.includes("#"))) return fail(400, "invalid_path", "Path contains invalid delimiters");
  if (request.method === "GET" && !modelId) { const auth = await optionalAuthentication(request, env.SKINS_DB, url, "car_model_list"); return listCarModels(env, ctx, url, respond, auth.installationId); }
  if (request.method === "POST" && !modelId) { const body = await boundedBody(request, MAX_JSON_BYTES); if (body === null) return fail(413, "body_too_large", "Request body exceeds the route limit"); const identity = await authenticate(request, env.SKINS_DB, url, body); if (!identity.ok) return fail(identity.status, identity.code, identity.message); return createCarModel(body, request, env, url, identity.installationId, requestId, respond, fail); }
  const bodyLimit = action === "manifest" ? CAR_MODEL_MAX_MANIFEST_BYTES : action === "files" ? CAR_MODEL_MAX_FILE_BYTES : action === "cover" ? MAX_COVER_BYTES : action === "screenshots" ? MAX_SCREENSHOT_BYTES : MAX_JSON_BYTES;
  const requestBody = request.method === "GET" || request.method === "HEAD" ? new Uint8Array() : await boundedBody(request, bodyLimit);
  if (requestBody === null) return fail(413, "body_too_large", "Request body exceeds the route limit");
  const needsAuth = !(request.method === "GET" || request.method === "HEAD") || !modelId;
  let identity = { ok: true, installationId: null };
  if (modelId && (request.method === "GET" || request.method === "HEAD")) { identity = await optionalAuthentication(request, env.SKINS_DB, url, "car_model_read"); identity = { ok: true, installationId: identity.installationId }; }
  if (needsAuth || (modelId && action !== "" && action !== "cover" && action !== "screenshots" && action !== "files")) { identity = await authenticate(request, env.SKINS_DB, url, requestBody); if (!identity.ok) return fail(identity.status, identity.code, identity.message); }
  if (!modelId) return fail(404, "model_not_found", "Car model not found");
  if ((request.method === "GET" || request.method === "HEAD") && action === "files" && tail) return readCarModelFile(request, env, modelId, tail, identity.installationId, requestId, fail);
  if ((request.method === "GET" || request.method === "HEAD") && action === "manifest") return readCarModelManifest(request, env, modelId, requestId, fail);
  if ((request.method === "GET" || request.method === "HEAD") && action === "cover") return readCarModelAsset(request, env, modelId, "cover_key", requestId, fail);
  if ((request.method === "GET" || request.method === "HEAD") && action === "screenshots" && tail) return readCarModelScreenshot(request, env, modelId, Number(tail), requestId, fail);
  if (request.method === "GET" || request.method === "HEAD") return readCarModelDetails(request, env, modelId, identity.installationId, requestId, respond, fail);
  const bytes = requestBody;
  if (!action && request.method === "PUT") return updateCarModelManifest(bytes, request, env, modelId, identity.installationId, requestId, respond, fail);
  if (action === "manifest" && request.method === "PUT") return updateCarModelManifest(bytes, request, env, modelId, identity.installationId, requestId, respond, fail);
  if (action === "files" && request.method === "PUT" && tail) return uploadCarModelFile(bytes, env, modelId, tail, identity.installationId, requestId, respond, fail);
  if (action === "cover" && request.method === "PUT") return uploadCarModelImage(bytes, request, env, modelId, "cover", identity.installationId, requestId, respond, fail);
  if (action === "screenshots" && request.method === "PUT" && tail) return uploadCarModelImage(bytes, request, env, modelId, `screenshot:${Number(tail)}`, identity.installationId, requestId, respond, fail);
  if (action === "publish" && request.method === "POST") return publishCarModel(env, modelId, identity.installationId, requestId, respond, fail);
  if (action === "like" && (request.method === "POST" || request.method === "DELETE")) return setCarModelLike(env.SKINS_DB, modelId, identity.installationId, request.method === "POST", requestId, respond, fail);
  if (action === "report" && request.method === "POST") return reportCarModel(bytes, env.SKINS_DB, modelId, identity.installationId, requestId, respond, fail);
  if (!action && request.method === "DELETE") return deleteCarModel(env, ctx, modelId, identity.installationId, requestId, respond, fail);
  return fail(405, "method_not_allowed", "Method not allowed");
}
async function createCarModel(bytes, request, env, url, owner, requestId, respond, fail) { const body = parseJson(bytes); if (!body || !clean(body.title, 80) || !clean(body.creatorName, 50)) return fail(400, "invalid_metadata", "title and creatorName are required"); const key = clean(request.headers.get("Idempotency-Key"), 128); if (!/^[A-Za-z0-9._:-]{8,128}$/.test(key)) return fail(400, "invalid_idempotency_key", "Idempotency-Key must contain 8-128 safe characters"); const previous = await env.SKINS_DB.prepare("SELECT id, status FROM car_models WHERE owner_id = ? AND idempotency_key = ? LIMIT 1").bind(owner, key).first(); if (previous) return respond({ success: true, id: previous.id, status: previous.status, requestId }, 200); const id = crypto.randomUUID(); await env.SKINS_DB.prepare("INSERT INTO car_models (id, owner_id, title, creator_name, description, target_vehicle_directory, package_id, package_name, idempotency_key) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)").bind(id, owner, clean(body.title, 80), clean(body.creatorName, 50), clean(body.description, 1500), clean(body.targetVehicleDirectory, 100), clean(body.packageId, 100).toLowerCase(), clean(body.packageName ?? body.title, 120), key).run(); return respond({ success: true, id, status: "draft", manifestUrl: `${url.origin}/v1/car-models/${id}/manifest`, requestId }, 201); }
async function ownedCarModel(db, id, owner) { return db.prepare("SELECT * FROM car_models WHERE id = ? AND owner_id = ? AND deleted_at IS NULL LIMIT 1").bind(id, owner).first(); }
async function updateCarModelManifest(bytes, request, env, id, owner, requestId, respond, fail) { const declared = (request.headers.get("Content-Type") || "").split(";")[0].trim().toLowerCase(); if (declared !== "application/json") return fail(415, "manifest_type_mismatch", "Manifest Content-Type must be application/json"); const model = await ownedCarModel(env.SKINS_DB, id, owner); if (!model) return fail(404, "model_not_found", "Owned draft not found"); const parsed = parseJson(bytes); const valid = validateCarModelManifest(parsed, id); if (!valid.ok) return fail(422, valid.code, valid.message); const digest = await sha256Hex(bytes); const key = `car-models/${id}/manifest.json`; await env.COMMUNITY_BUCKET.put(key, bytes, { httpMetadata: { contentType: "application/json" }, customMetadata: { modelId: id, ownerId: owner, role: "manifest", sha256: digest } }); await env.SKINS_DB.prepare("UPDATE car_models SET target_vehicle_directory = ?, package_id = ?, package_name = ?, manifest_version = ?, supported_versions = ?, manifest_key = ?, manifest_size = ?, manifest_sha256 = ?, file_count = ?, total_size = ?, status = 'uploading', updated_at = CURRENT_TIMESTAMP WHERE id = ? AND owner_id = ?").bind(valid.target, valid.packageId, valid.packageName, valid.manifestVersion, valid.supportedVersions, key, bytes.length, digest, valid.fileCount, valid.totalSize, id, owner).run(); await env.SKINS_DB.prepare("DELETE FROM car_model_files WHERE model_id = ?").bind(id).run(); for (const file of valid.files) await env.SKINS_DB.prepare("INSERT INTO car_model_files (id, model_id, relative_path, destination_relative_path, role, detected_format, variant, required, object_key, file_size, file_sha256, content_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)").bind(crypto.randomUUID(), id, file.path, file.destination, file.role, file.detectedFormat, file.variant, file.required ? 1 : 0, carModelKey(id, file.path), file.size, file.sha256, file.contentType).run(); return respond({ success: true, fileCount: valid.fileCount, totalSize: valid.totalSize, sha256: digest, requestId }); }
async function uploadCarModelFile(bytes, env, id, path, owner, requestId, respond, fail) { const model = await ownedCarModel(env.SKINS_DB, id, owner); if (!model) return fail(404, "model_not_found", "Owned draft not found"); if (!safeCarModelPath(path)) return fail(400, "invalid_file_path", "File path is invalid"); const row = await env.SKINS_DB.prepare("SELECT * FROM car_model_files WHERE model_id = ? AND relative_path = ?").bind(id, path).first(); if (!row) return fail(404, "file_not_declared", "File is not declared by the manifest"); if (bytes.length !== Number(row.file_size)) return fail(422, "size_mismatch", "File size does not match the manifest"); const digest = await sha256Hex(bytes); if (digest !== row.file_sha256) return fail(422, "sha256_mismatch", "File SHA-256 does not match the manifest"); await env.COMMUNITY_BUCKET.put(row.object_key, bytes, { httpMetadata: { contentType: row.content_type || "application/octet-stream" }, customMetadata: { modelId: id, ownerId: owner, path, sha256: digest, contentType: row.content_type || "application/octet-stream" } }); const stored = await env.COMMUNITY_BUCKET.head(row.object_key); if (!stored || stored.size !== bytes.length || stored.httpMetadata?.contentType !== (row.content_type || "application/octet-stream")) { await env.COMMUNITY_BUCKET.delete(row.object_key); return fail(500, "storage_verification_failed", "Stored file could not be verified"); } await env.SKINS_DB.prepare("UPDATE car_models SET status = 'uploading', updated_at = CURRENT_TIMESTAMP WHERE id = ? AND owner_id = ?").bind(id, owner).run(); return respond({ success: true, path, size: bytes.length, sha256: digest, requestId }); }
async function readCarModelFile(request, env, id, path, viewer, requestId, fail) { if (!safeCarModelPath(path)) return fail(400, "invalid_file_path", "File path is invalid"); const row = await env.SKINS_DB.prepare("SELECT f.*, m.status, m.owner_id FROM car_model_files f JOIN car_models m ON m.id = f.model_id WHERE f.model_id = ? AND f.relative_path = ? AND m.deleted_at IS NULL AND (m.status = 'published' OR (m.owner_id = ? AND m.status IN ('draft','uploading')))").bind(id, path, viewer || "").first(); if (!row) return fail(404, "file_not_found", "File not found"); const object = await env.COMMUNITY_BUCKET.get(row.object_key); if (!object) { if (row.status === "published") await invalidateBrokenCarModel(env, { waitUntil: (promise) => promise }, row, "file_asset"); return fail(404, "asset_not_found", "File asset not found"); } const headers = new Headers({ "Content-Type": row.content_type || "application/octet-stream", "Content-Length": String(object.size), "Cache-Control": row.status === "published" ? "public, max-age=31536000, immutable" : "no-store", "X-Request-ID": requestId }); return new Response(request.method === "HEAD" ? null : object.body, { status: 200, headers }); }
async function readCarModelManifest(request, env, id, requestId, fail) { const row = await env.SKINS_DB.prepare("SELECT * FROM car_models WHERE id = ? AND deleted_at IS NULL AND status = 'published'").bind(id).first(); if (!row?.manifest_key) return fail(404, "asset_not_found", "Manifest not found"); const object = await env.COMMUNITY_BUCKET.get(row.manifest_key); if (!object || object.size !== Number(row.manifest_size) || String(object.customMetadata?.sha256 || "").toLowerCase() !== String(row.manifest_sha256 || "").toLowerCase()) { await invalidateBrokenCarModel(env, { waitUntil: (promise) => promise }, row, "manifest_asset"); return fail(404, "asset_not_found", "Manifest not found"); } const headers = new Headers({ "Content-Type": "application/json", "Content-Length": String(object.size), "ETag": object.httpEtag, "X-Request-ID": requestId, "Cache-Control": "public, max-age=86400" }); return new Response(request.method === "HEAD" ? null : object.body, { status: 200, headers }); }
async function readCarModelAsset(request, env, id, column, requestId, fail) { const row = await env.SKINS_DB.prepare(`SELECT * FROM car_models WHERE id = ? AND deleted_at IS NULL AND status = 'published'`).bind(id).first(); if (!row?.[column]) return fail(404, "asset_not_found", "Asset not found"); const object = await env.COMMUNITY_BUCKET.get(row[column]); if (!object) { await invalidateBrokenCarModel(env, { waitUntil: (promise) => promise }, row, column === "cover_key" ? "cover_asset" : "required_asset"); return fail(404, "asset_not_found", "Asset not found"); } const headers = new Headers({ "Content-Type": object.httpMetadata?.contentType || row.cover_content_type || "application/octet-stream", "Content-Length": String(object.size), "ETag": object.httpEtag, "X-Request-ID": requestId, "Cache-Control": "public, max-age=86400" }); return new Response(request.method === "HEAD" ? null : object.body, { status: 200, headers }); }
async function readCarModelScreenshot(request, env, id, position, requestId, fail) { const row = await env.SKINS_DB.prepare("SELECT object_key, content_type FROM car_model_screenshots s JOIN car_models m ON m.id = s.model_id WHERE model_id = ? AND position = ? AND m.status = 'published' AND m.deleted_at IS NULL").bind(id, position).first(); if (!row) return fail(404, "asset_not_found", "Screenshot not found"); const object = await env.COMMUNITY_BUCKET.get(row.object_key); if (!object) return fail(404, "asset_not_found", "Screenshot not found"); return new Response(request.method === "HEAD" ? null : object.body, { headers: { "Content-Type": row.content_type, "X-Request-ID": requestId } }); }
async function readCarModelDetails(request, env, id, viewer, requestId, respond, fail) {
  const row = await env.SKINS_DB.prepare("SELECT m.*, (SELECT COUNT(*) FROM car_model_likes l WHERE l.model_id = m.id) likes, (SELECT 1 FROM car_model_likes l WHERE l.model_id = m.id AND l.user_id = ?) liked FROM car_models m WHERE m.id = ? AND m.deleted_at IS NULL AND (m.status = 'published' OR m.owner_id = ?)").bind(viewer, id, viewer || "").first();
  if (!row) return fail(404, "model_not_found", "Car model not found");
  if (row.status === "published") {
    const integrity = await validatePublishedCarModel(env, row);
    if (!integrity.ok) {
      await invalidateBrokenCarModel(env, { waitUntil: (promise) => promise }, row, integrity.reason);
      return fail(404, "model_not_found", "Car model not found");
    }
  }
  const files = await env.SKINS_DB.prepare("SELECT relative_path AS path, destination_relative_path AS destinationRelativePath, role, detected_format AS detectedFormat, variant, required, file_size AS size, file_sha256 AS sha256, content_type AS contentType FROM car_model_files WHERE model_id = ? ORDER BY relative_path").bind(id).all();
  return respond({ id, title: row.title, creatorName: row.creator_name, description: row.description, targetVehicleDirectory: row.target_vehicle_directory, packageId: row.package_id, supportedVersions: row.supported_versions, status: row.status, manifestSha256: row.manifest_sha256, coverUploaded: Boolean(row.cover_key), screenshotPositions: (await env.SKINS_DB.prepare("SELECT position FROM car_model_screenshots WHERE model_id = ?").bind(id).all()).results?.map((item) => Number(item.position)) || [], fileCount: Number(row.file_count), totalSize: Number(row.total_size), files: files.results || [], likes: Number(row.likes || 0), likedByMe: Boolean(row.liked), requestId });
}
async function validatePublishedCarModel(env, row) {
  if (!row.manifest_key || !row.cover_key || !Number.isSafeInteger(Number(row.file_count)) || Number(row.file_count) < 1 || Number(row.file_count) > CAR_MODEL_MAX_FILES) return { ok: false, reason: "catalog_metadata" };
  const manifest = await env.COMMUNITY_BUCKET.head(row.manifest_key);
  const cover = await env.COMMUNITY_BUCKET.head(row.cover_key);
  if (!manifest || manifest.size !== Number(row.manifest_size) || String(manifest.customMetadata?.sha256 || "").toLowerCase() !== String(row.manifest_sha256 || "").toLowerCase() || !cover || cover.size <= 0 || cover.size > MAX_COVER_BYTES || String(cover.customMetadata?.sha256 || "").toLowerCase() !== String(row.cover_sha256 || "").toLowerCase()) return { ok: false, reason: "required_asset" };
  const files = await env.SKINS_DB.prepare("SELECT object_key, file_size, file_sha256 FROM car_model_files WHERE model_id = ? ORDER BY relative_path LIMIT ?").bind(row.id, CAR_MODEL_MAX_FILES + 1).all();
  const indexed = files.results || [];
  if (indexed.length !== Number(row.file_count)) return { ok: false, reason: "file_index" };
  const objects = await Promise.all(indexed.map((file) => env.COMMUNITY_BUCKET.head(file.object_key)));
  if (objects.some((object, index) => !object || object.size !== Number(indexed[index].file_size) || String(object.customMetadata?.sha256 || "").toLowerCase() !== String(indexed[index].file_sha256 || "").toLowerCase())) return { ok: false, reason: "file_asset" };
  return { ok: true };
}
async function invalidateBrokenCarModel(env, ctx, row, reason) {
  console.warn(JSON.stringify({ event: "car_model_invalidated", modelId: row.id, reason }));
  // The conditional update makes detection idempotent and preserves child metadata for diagnostics.
  const result = await env.SKINS_DB.prepare("UPDATE car_models SET invalidated_at = COALESCE(invalidated_at, CURRENT_TIMESTAMP), invalid_reason = COALESCE(invalid_reason, ?), updated_at = CURRENT_TIMESTAMP WHERE id = ? AND status = 'published' AND deleted_at IS NULL AND invalidated_at IS NULL").bind(reason, row.id).run();
  if (result.meta?.changes) {
    await env.SKINS_DB.prepare("DELETE FROM car_model_likes WHERE model_id = ?").bind(row.id).run();
    await env.SKINS_DB.prepare("UPDATE car_model_reports SET status = 'actioned', updated_at = CURRENT_TIMESTAMP WHERE model_id = ? AND status IN ('open','reviewed')").bind(row.id).run();
  }
  ctx.waitUntil(deleteR2Prefix(env.COMMUNITY_BUCKET, `car-models/${row.id}/`).catch(() => {}));
}
async function hideBrokenCarModel(env, ctx, row, reason) {
  return invalidateBrokenCarModel(env, ctx, row, reason);
}
async function listCarModels(env, ctx, url, respond, viewer) {
  const limit = clamp(url.searchParams.get("limit"), 1, 50, 20);
  // D1 is the catalog index; validate a bounded candidate window against R2 before exposing it.
  const result = await env.SKINS_DB.prepare("SELECT m.*, (SELECT COUNT(*) FROM car_model_likes l WHERE l.model_id = m.id) likes FROM car_models m WHERE m.status = 'published' AND m.deleted_at IS NULL AND m.invalidated_at IS NULL ORDER BY m.created_at DESC LIMIT ?").bind(Math.min(50, Math.max(limit * 2, limit + 10))).all();
  const valid = [];
  for (const row of result.results || []) {
    const integrity = await validatePublishedCarModel(env, row);
    if (!integrity.ok) { await hideBrokenCarModel(env, ctx, row, integrity.reason); continue; }
    valid.push({ id: row.id, title: row.title, creatorName: row.creator_name, targetVehicleDirectory: row.target_vehicle_directory, packageId: row.package_id, fileCount: Number(row.file_count), totalSize: Number(row.total_size), likes: Number(row.likes || 0), manifestSha256: row.manifest_sha256, coverUrl: `${url.origin}/v1/car-models/${row.id}/cover` });
    if (valid.length >= limit) break;
  }
  return respond({ items: valid, limit });
}
async function uploadCarModelImage(bytes, request, env, id, role, owner, requestId, respond, fail) { const model = await ownedCarModel(env.SKINS_DB, id, owner); if (!model) return fail(404, "model_not_found", "Owned draft not found"); const detected = detectImage(bytes); if (!detected || (request.headers.get("Content-Type") || "").split(";")[0].trim().toLowerCase() !== detected.mime) return fail(415, "invalid_image", "Image type does not match its bytes"); const digest = await sha256Hex(bytes); if (role === "cover") { const key = `car-models/${id}/cover.${detected.extension}`; await env.COMMUNITY_BUCKET.put(key, bytes, { httpMetadata: { contentType: detected.mime }, customMetadata: { modelId: id, ownerId: owner, role: "cover", sha256: digest } }); await env.SKINS_DB.prepare("UPDATE car_models SET cover_key = ?, cover_sha256 = ?, cover_content_type = ?, status = 'uploading', updated_at = CURRENT_TIMESTAMP WHERE id = ?").bind(key, digest, detected.mime, id).run(); return respond({ success: true, sha256: digest, requestId }); } const position = Number(role.split(":")[1]); if (!Number.isInteger(position) || position < 1 || position > 8) return fail(400, "invalid_position", "Screenshot position must be 1-8"); const key = `car-models/${id}/screenshots/${String(position).padStart(2, "0")}.${detected.extension}`; await env.COMMUNITY_BUCKET.put(key, bytes, { httpMetadata: { contentType: detected.mime } }); await env.SKINS_DB.prepare("INSERT INTO car_model_screenshots (id, model_id, position, object_key, sha256, content_type) VALUES (?, ?, ?, ?, ?, ?) ON CONFLICT(model_id, position) DO UPDATE SET object_key = excluded.object_key, sha256 = excluded.sha256, content_type = excluded.content_type").bind(crypto.randomUUID(), id, position, key, digest, detected.mime).run(); return respond({ success: true, position, sha256: digest, requestId }); }
async function publishCarModel(env, id, owner, requestId, respond, fail) { const model = await ownedCarModel(env.SKINS_DB, id, owner); if (!model) return fail(404, "model_not_found", "Owned draft not found"); if (!model.manifest_key || !model.cover_key) return fail(409, "uploads_incomplete", "Manifest and cover are required"); const manifest = await env.COMMUNITY_BUCKET.get(model.manifest_key); if (!manifest || manifest.size !== Number(model.manifest_size) || await sha256Hex(new Uint8Array(await manifest.arrayBuffer())) !== String(model.manifest_sha256).toLowerCase()) return fail(409, "uploads_incomplete", "Manifest asset could not be verified"); const cover = await env.COMMUNITY_BUCKET.head(model.cover_key); if (!cover || cover.size <= 0 || cover.size > MAX_COVER_BYTES || cover.httpMetadata?.contentType !== model.cover_content_type) return fail(409, "uploads_incomplete", "Cover asset could not be verified"); const files = await env.SKINS_DB.prepare("SELECT * FROM car_model_files WHERE model_id = ?").bind(id).all(); if ((files.results || []).length !== Number(model.file_count)) return fail(409, "uploads_incomplete", "Manifest file index is incomplete"); for (const file of files.results || []) { const object = await env.COMMUNITY_BUCKET.get(file.object_key); if (!object || object.size !== Number(file.file_size) || await sha256Hex(new Uint8Array(await object.arrayBuffer())) !== String(file.file_sha256).toLowerCase() || (object.httpMetadata?.contentType || "application/octet-stream") !== (file.content_type || "application/octet-stream")) return fail(409, "uploads_incomplete", `File ${file.relative_path} is missing or unverifiable`); } const screenshots = await env.SKINS_DB.prepare("SELECT * FROM car_model_screenshots WHERE model_id = ?").bind(id).all(); for (const screenshot of screenshots.results || []) { const object = await env.COMMUNITY_BUCKET.get(screenshot.object_key); if (!object || object.size <= 0 || await sha256Hex(new Uint8Array(await object.arrayBuffer())) !== String(screenshot.sha256).toLowerCase() || object.httpMetadata?.contentType !== screenshot.content_type) return fail(409, "uploads_incomplete", `Screenshot ${screenshot.position} is missing or unverifiable`); } const result = await env.SKINS_DB.prepare("UPDATE car_models SET status = 'published', updated_at = CURRENT_TIMESTAMP WHERE id = ? AND owner_id = ? AND status IN ('draft','uploading')").bind(id, owner).run(); if (!result.meta?.changes) return fail(409, "invalid_state_transition", "Model state changed before publishing"); return respond({ success: true, id, status: "published", requestId }); }
async function setCarModelLike(db, id, user, liked, requestId, respond, fail) { const exists = await db.prepare("SELECT id FROM car_models WHERE id = ? AND status = 'published' AND deleted_at IS NULL AND invalidated_at IS NULL").bind(id).first(); if (!exists) return fail(404, "model_not_found", "Car model not found"); if (liked) await db.prepare("INSERT OR IGNORE INTO car_model_likes (model_id, user_id) VALUES (?, ?)").bind(id, user).run(); else await db.prepare("DELETE FROM car_model_likes WHERE model_id = ? AND user_id = ?").bind(id, user).run(); const row = await db.prepare("SELECT COUNT(*) count FROM car_model_likes WHERE model_id = ?").bind(id).first(); return respond({ success: true, liked, likes: Number(row?.count || 0), requestId }); }
async function reportCarModel(bytes, db, id, user, requestId, respond, fail) { const body = parseJson(bytes); const reason = clean(body?.reason, 32).toLowerCase(); if (!body || !REPORT_REASONS.has(reason)) return fail(400, "invalid_report_reason", "Report reason is invalid"); const result = await db.prepare("INSERT OR IGNORE INTO car_model_reports (id, model_id, reporter_id, reason, details) VALUES (?, ?, ?, ?, ?)").bind(crypto.randomUUID(), id, user, reason, clean(body.details, 1000)).run(); return respond({ success: true, duplicate: !result.meta?.changes, requestId }, result.meta?.changes ? 201 : 200); }
async function deleteCarModel(env, ctx, id, owner, requestId, respond, fail) { const result = await env.SKINS_DB.prepare("UPDATE car_models SET status = 'hidden', deleted_at = COALESCE(deleted_at, CURRENT_TIMESTAMP), updated_at = CURRENT_TIMESTAMP WHERE id = ? AND owner_id = ? AND deleted_at IS NULL").bind(id, owner).run(); if (!result.meta?.changes) return fail(404, "model_not_found", "Owned car model not found"); ctx.waitUntil(deleteR2Prefix(env.COMMUNITY_BUCKET, `car-models/${id}/`).catch(() => {})); return respond({ success: true, deleted: true, requestId }, 202); }
async function optionalAuthentication(request, database, url, route) {
  const installationId = clean(request.headers.get("X-Installation-ID"), 36).toLowerCase();
  if (!installationId) return { installationId: null, authenticated: false, reason: "missing_identity" };
  try {
    const auth = await authenticate(request, database, url, new Uint8Array());
    if (auth.ok) return { installationId: auth.installationId, authenticated: true, reason: null };
    console.warn(JSON.stringify({ event: "optional_auth_failed", route, reason: auth.code, status: auth.status }));
    return { installationId: null, authenticated: false, reason: auth.code };
  } catch (error) {
    const reason = error instanceof Error ? error.name : "unknown_error";
    console.warn(JSON.stringify({ event: "optional_auth_exception", route, reason }));
    return { installationId: null, authenticated: false, reason };
  }
}
__name(optionalAuthentication, "optionalAuthentication");
__name2(optionalAuthentication, "optionalAuthentication");
async function authenticate(request, database, url, bodyBytes) {
  const installationId = clean(request.headers.get("X-Installation-ID"), 36).toLowerCase();
  if (!UUID_PATTERN.test(installationId)) return authFailure(401, "authentication_required", "A valid X-Installation-ID is required");
  const installation = await database.prepare("SELECT public_key_jwk FROM anonymous_installations WHERE id = ? AND status = 'active' LIMIT 1").bind(installationId).first();
  if (!installation) return authFailure(401, "unknown_installation", "Installation is not registered");
  let publicKey;
  try {
    publicKey = JSON.parse(installation.public_key_jwk);
  } catch {
    return authFailure(401, "invalid_installation", "Installation key is invalid");
  }
  const verified = await verifySignedRequest(request, url, bodyBytes, publicKey, installationId);
  if (!verified.ok) return verified;
  try {
    await consumeNonce(database, installationId, verified.nonce);
  } catch {
    return authFailure(409, "replayed_request", "The request nonce was already used");
  }
  return { ok: true, installationId };
}
__name(authenticate, "authenticate");
__name2(authenticate, "authenticate");
async function verifySignedRequest(request, url, bodyBytes, publicKey, installationId) {
  const timestamp = request.headers.get("X-Timestamp") || "";
  const nonce = request.headers.get("X-Nonce") || "";
  const signatureText = request.headers.get("X-Signature") || "";
  if (!/^\d{13}$/.test(timestamp) || Math.abs(Date.now() - Number(timestamp)) > AUTH_WINDOW_MS) return authFailure(401, "stale_request", "X-Timestamp must be within five minutes");
  if (!/^[A-Za-z0-9_-]{16,128}$/.test(nonce)) return authFailure(401, "invalid_nonce", "X-Nonce is invalid");
  if (!signatureText || signatureText.length > 256) return authFailure(401, "invalid_signature", "X-Signature is required");
  try {
    const bodyHash = await sha256Hex(bodyBytes);
    const canonical = `${request.method.toUpperCase()}
${url.pathname}${url.search}
${installationId}
${timestamp}
${nonce}
${bodyHash}`;
    const key = await crypto.subtle.importKey("jwk", publicKey, { name: "ECDSA", namedCurve: "P-256" }, false, ["verify"]);
    const supplied = base64UrlDecode(signatureText);
    const rawSignature = supplied.length === 64 ? supplied : derToP1363(supplied);
    const ok = await crypto.subtle.verify({ name: "ECDSA", hash: "SHA-256" }, key, rawSignature, new TextEncoder().encode(canonical));
    return ok ? { ok: true, nonce } : authFailure(401, "invalid_signature", "Request signature verification failed");
  } catch {
    return authFailure(401, "invalid_signature", "Request signature verification failed");
  }
}
__name(verifySignedRequest, "verifySignedRequest");
__name2(verifySignedRequest, "verifySignedRequest");
async function consumeNonce(database, installationId, nonce) {
  await database.prepare("DELETE FROM anonymous_nonces WHERE expires_at <= CURRENT_TIMESTAMP").run();
  const result = await database.prepare("INSERT OR IGNORE INTO anonymous_nonces (installation_id, nonce, expires_at) VALUES (?, ?, datetime('now', ?))").bind(installationId, nonce, `+${NONCE_TTL_SECONDS} seconds`).run();
  if (!result.meta?.changes) throw new Error("nonce_reused");
}
__name(consumeNonce, "consumeNonce");
__name2(consumeNonce, "consumeNonce");
async function createSkin(bodyBytes, request, database, env, url, ownerId, requestId, respond, fail) {
  const body = parseJson(bodyBytes);
  if (!body) return fail(400, "invalid_json", "The request body is not valid JSON");
  const metadata = validateMetadata(body);
  if (!metadata.ok) return fail(400, metadata.code, metadata.message);
  const idempotencyKey = clean(request.headers.get("Idempotency-Key"), 128);
  if (!/^[A-Za-z0-9._:-]{8,128}$/.test(idempotencyKey)) return fail(400, "invalid_idempotency_key", "Idempotency-Key must contain 8-128 safe characters");
  const previous = await database.prepare("SELECT id, status FROM skins WHERE owner_id = ? AND idempotency_key = ? LIMIT 1").bind(ownerId, idempotencyKey).first();
  if (previous) return respond(createSkinResult(previous.id, previous.status, url.origin, requestId), 200);
  const id = crypto.randomUUID();
  try {
    await database.prepare(`INSERT INTO skins (id, owner_id, title, creator_name, description, car_directory, slot_key, texture_format, source_filename, binary_key, binary_size, binary_sha256, status, game_versions, idempotency_key, created_at, updated_at)
			VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'draft', ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)`).bind(id, ownerId, metadata.title, metadata.creatorName, metadata.description, metadata.carDirectory, metadata.slotKey, metadata.textureFormat, metadata.sourceFilename, `skins/${id}/skin.dds.z`, metadata.binarySize, metadata.binarySha256, metadata.gameVersions, idempotencyKey).run();
  } catch (error) {
    const raced = await database.prepare("SELECT id, status FROM skins WHERE owner_id = ? AND idempotency_key = ? LIMIT 1").bind(ownerId, idempotencyKey).first();
    if (raced) return respond(createSkinResult(raced.id, raced.status, url.origin, requestId), 200);
    throw error;
  }
  return respond(createSkinResult(id, "draft", url.origin, requestId), 201);
}
__name(createSkin, "createSkin");
__name2(createSkin, "createSkin");
async function listSaves(database, env, url, respond, viewerId) {
  console.log(JSON.stringify({ event: "save_list_started", sort: url.searchParams.get("sort") || "newest" }));
  const sort = url.searchParams.get("sort") === "oldest" ? "ASC" : "DESC";
  const rows = await database.prepare(`
		SELECT
			s.id, s.owner_id, s.file_key, s.file_size, s.custom_name, s.uploader_name, s.player_level, s.player_gold, s.player_rs, s.player_ms, s.created_at,
			(SELECT COUNT(*) FROM save_likes l WHERE l.save_id = s.id) AS likes,
			(SELECT COUNT(*) FROM save_downloads d WHERE d.save_id = s.id) AS downloads,
			(SELECT 1 FROM save_likes l WHERE l.save_id = s.id AND l.user_id = ?) AS liked_by_me
		FROM accountless_save_uploads s
		ORDER BY s.created_at ${sort}
		LIMIT 100
	`).bind(viewerId).all();
  const items = [];
  for (const row of rows.results || []) {
    const object = await env.COMMUNITY_BUCKET.head(row.file_key);
    if (!object) {
      await database.prepare("DELETE FROM accountless_save_uploads WHERE id = ?").bind(row.id).run();
      console.log(JSON.stringify({ event: "save_index_pruned", id: row.id, fileKey: row.file_key }));
      continue;
    }
    items.push({
      id: row.id,
      file_key: row.file_key,
      file_size: Number(row.file_size),
      custom_name: row.custom_name,
      uploader_name: row.uploader_name,
      player_level: Number(row.player_level || 0),
      player_gold: Number(row.player_gold || 0),
      player_rs: Number(row.player_rs || 0),
      player_ms: Number(row.player_ms || 0),
      created_at: row.created_at,
      likes: Number(row.likes || 0),
      downloads: Number(row.downloads || 0),
      liked_by_me: Boolean(row.liked_by_me),
      is_owner: viewerId !== null && row.owner_id === viewerId,
      shareUrl: `${url.origin}/v1/saves/${row.id}`
    });
  }
  return respond({ items });
}
__name(listSaves, "listSaves");
__name2(listSaves, "listSaves");
async function readSave(request, env, ctx, id, requestId, respond, fail, viewerId) {
  const row = await env.SKINS_DB.prepare("SELECT file_key, custom_name FROM accountless_save_uploads WHERE id = ? OR file_key = ? LIMIT 1").bind(id, id).first();
  if (!row) return fail(404, "save_not_found", "Save not found");
  const object = await env.COMMUNITY_BUCKET.get(row.file_key);
  if (!object) return fail(404, "save_asset_not_found", "Save asset not found");
  if (request.method === "GET" && !request.headers.has("Range")) {
    ctx.waitUntil(env.SKINS_DB.prepare("INSERT INTO save_downloads (save_id, user_id) VALUES (?, ?)").bind(id, viewerId).run());
  }
  const headers = new Headers({ "Content-Type": "application/octet-stream", "Content-Length": String(object.size), "Cache-Control": "no-store", "X-Request-ID": requestId });
  return new Response(request.method === "HEAD" ? null : object.body, { status: 200, headers });
}
__name(readSave, "readSave");
__name2(readSave, "readSave");
async function uploadSave(bytes, env, url, ownerId, requestId, respond, fail) {
  const customName = clean(url.searchParams.get("name"), 80);
  const uploaderName = clean(url.searchParams.get("user"), 50);
  if (!customName) return fail(400, "invalid_save_name", "A save name is required");
  if (!uploaderName) return fail(400, "invalid_uploader_name", "An uploader name is required");
  const stats = {
    level: nonNegativeInteger(url.searchParams.get("level")),
    gold: nonNegativeInteger(url.searchParams.get("gold")),
    rs: nonNegativeInteger(url.searchParams.get("rs")),
    ms: nonNegativeInteger(url.searchParams.get("ms"))
  };
  if (Object.values(stats).some((value) => value === null)) return fail(400, "invalid_save_stats", "Save stats must be non-negative integers");
  const id = crypto.randomUUID();
  const key = `saves/${id}.dat`;
  const digest = await sha256Hex(bytes);
  await env.COMMUNITY_BUCKET.put(key, bytes, {
    httpMetadata: { contentType: "application/octet-stream" },
    customMetadata: { savename: customName, uploadername: uploaderName, level: String(stats.level), gold: String(stats.gold), rs: String(stats.rs), ms: String(stats.ms), ownerid: ownerId, sha256: digest }
  });
  const stored = await env.COMMUNITY_BUCKET.head(key);
  if (!stored || stored.size !== bytes.length) {
    await env.COMMUNITY_BUCKET.delete(key);
    return fail(500, "storage_verification_failed", "Stored save size verification failed");
  }
  try {
    await env.SKINS_DB.prepare(`INSERT INTO accountless_save_uploads (id, owner_id, file_key, file_size, file_sha256, custom_name, uploader_name, player_level, player_gold, player_rs, player_ms)
			VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)`).bind(id, ownerId, key, bytes.length, digest, customName, uploaderName, stats.level, stats.gold, stats.rs, stats.ms).run();
  } catch (error) {
    await env.COMMUNITY_BUCKET.delete(key);
    throw error;
  }
  return respond({ success: true, id: key, size: bytes.length, sha256: digest, requestId }, 201);
}
__name(uploadSave, "uploadSave");
__name2(uploadSave, "uploadSave");
async function deleteSave(env, saveId, ownerId, requestId, respond, fail) {
  const save = await env.SKINS_DB.prepare("SELECT file_key FROM accountless_save_uploads WHERE id = ? AND owner_id = ? LIMIT 1").bind(saveId, ownerId).first();
  if (!save) return fail(404, "save_not_found", "Owned save not found");
  await env.SKINS_DB.prepare("DELETE FROM accountless_save_uploads WHERE id = ? AND owner_id = ?").bind(saveId, ownerId).run();
  try {
    await env.COMMUNITY_BUCKET.delete(save.file_key);
  } catch (error) {
    console.error(JSON.stringify({ event: "save_object_cleanup_failed", requestId, saveId, fileKey: save.file_key, error: error instanceof Error ? error.message : String(error) }));
  }
  return respond({ success: true, deleted: true, requestId }, 202);
}
__name(deleteSave, "deleteSave");
__name2(deleteSave, "deleteSave");
async function listMusic(database, env, url, respond, viewerId) {
  const sort = url.searchParams.get("sort") === "oldest" ? "ASC" : "DESC";
  const rows = await database.prepare(`
		SELECT
			m.id, m.owner_id, m.file_key, m.file_size, m.custom_name, m.uploader_name, m.target_track, m.cover_key, m.cover_content_type, m.created_at,
			(SELECT COUNT(*) FROM music_likes l WHERE l.music_id = m.id) AS likes,
			(SELECT COUNT(*) FROM music_downloads d WHERE d.music_id = m.id) AS downloads,
			(SELECT 1 FROM music_likes l WHERE l.music_id = m.id AND l.user_id = ?) AS liked_by_me
		FROM community_music_uploads m
		WHERE m.deleted_at IS NULL
		ORDER BY m.created_at ${sort}
		LIMIT 100
	`).bind(viewerId).all();
  const items = [];
  for (const row of rows.results || []) {
    const object = await env.COMMUNITY_BUCKET.head(row.file_key);
    if (!object) {
      await database.prepare("UPDATE community_music_uploads SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?").bind(row.id).run();
      console.log(JSON.stringify({ event: "music_index_pruned", id: row.id, fileKey: row.file_key }));
      continue;
    }
    items.push({
      id: row.id,
      custom_name: row.custom_name,
      uploader_name: row.uploader_name,
      file_size: Number(row.file_size),
      target_track: row.target_track,
      created_at: row.created_at,
      likes: Number(row.likes || 0),
      downloads: Number(row.downloads || 0),
      liked_by_me: Boolean(row.liked_by_me),
      is_owner: viewerId !== null && row.owner_id === viewerId,
      coverUrl: row.cover_key ? `${url.origin}/v1/music/${row.id}/cover` : null,
      shareUrl: `${url.origin}/v1/music/${row.id}`
    });
  }
  return respond({ items });
}
__name(listMusic, "listMusic");
__name2(listMusic, "listMusic");
async function readMusic(request, env, ctx, id, requestId, respond, fail, viewerId) {
  const row = await env.SKINS_DB.prepare("SELECT file_key FROM community_music_uploads WHERE id = ? AND deleted_at IS NULL LIMIT 1").bind(id).first();
  if (!row) return fail(404, "music_not_found", "Music not found");
  const object = await env.COMMUNITY_BUCKET.get(row.file_key);
  if (!object) return fail(404, "music_asset_not_found", "Music asset not found");
  if (request.method === "GET" && !request.headers.has("Range")) {
    ctx.waitUntil(env.SKINS_DB.prepare("INSERT INTO music_downloads (music_id, user_id) VALUES (?, ?)").bind(id, viewerId).run());
  }
  const headers = new Headers({ "Content-Type": "audio/mpeg", "Content-Length": String(object.size), "Cache-Control": "no-store", "X-Request-ID": requestId });
  return new Response(request.method === "HEAD" ? null : object.body, { status: 200, headers });
}
__name(readMusic, "readMusic");
__name2(readMusic, "readMusic");
async function uploadMusic(bytes, env, url, ownerId, requestId, respond, fail) {
  const customName = clean(url.searchParams.get("name"), 80);
  const uploaderName = clean(url.searchParams.get("user"), 50);
  const targetTrack = clean(url.searchParams.get("track"), 120);
  if (!customName) return fail(400, "invalid_music_name", "A music name is required");
  if (!uploaderName) return fail(400, "invalid_uploader_name", "An uploader name is required");
  if (!targetTrack || targetTrack.includes("/") || targetTrack.includes("\\")) return fail(400, "invalid_target_track", "Target track is invalid");
  const id = crypto.randomUUID();
  const key = `music/${id}.mp3`;
  const digest = await sha256Hex(bytes);
  await env.COMMUNITY_BUCKET.put(key, bytes, {
    httpMetadata: { contentType: "audio/mpeg" },
    customMetadata: { musicname: customName, uploadername: uploaderName, targettrack: targetTrack, ownerid: ownerId, sha256: digest }
  });
  const stored = await env.COMMUNITY_BUCKET.head(key);
  if (!stored || stored.size !== bytes.length) {
    await env.COMMUNITY_BUCKET.delete(key);
    return fail(500, "storage_verification_failed", "Stored music size verification failed");
  }
  try {
    await env.SKINS_DB.prepare(`INSERT INTO community_music_uploads (id, owner_id, file_key, file_size, file_sha256, custom_name, uploader_name, target_track)
			VALUES (?, ?, ?, ?, ?, ?, ?, ?)`).bind(id, ownerId, key, bytes.length, digest, customName, uploaderName, targetTrack).run();
  } catch (error) {
    await env.COMMUNITY_BUCKET.delete(key);
    throw error;
  }
  return respond({ success: true, id, size: bytes.length, sha256: digest, requestId }, 201);
}
__name(uploadMusic, "uploadMusic");
__name2(uploadMusic, "uploadMusic");
async function uploadMusicCover(bytes, request, env, musicId, ownerId, requestId, respond, fail) {
  if (!bytes.length) return fail(400, "empty_image", "Artwork is required");
  const music = await env.SKINS_DB.prepare("SELECT cover_key FROM community_music_uploads WHERE id = ? AND owner_id = ? AND deleted_at IS NULL LIMIT 1").bind(musicId, ownerId).first();
  if (!music) return fail(404, "music_not_found", "Owned music not found");
  const detected = detectImage(bytes);
  if (!detected) return fail(415, "invalid_image", "Image magic must be PNG, JPEG, or WebP");
  const declared = (request.headers.get("Content-Type") || "").split(";")[0].trim().toLowerCase();
  if (declared !== detected.mime) return fail(415, "image_type_mismatch", "Content-Type does not match image bytes");
  const digest = await sha256Hex(bytes);
  const key = `music/${musicId}/cover.${detected.extension}`;
  if (music.cover_key && music.cover_key !== key) await env.COMMUNITY_BUCKET.delete(music.cover_key);
  await env.COMMUNITY_BUCKET.put(key, bytes, {
    httpMetadata: { contentType: detected.mime, cacheControl: "public, max-age=86400" },
    customMetadata: { musicId, ownerId, role: "cover", sha256: digest }
  });
  const stored = await env.COMMUNITY_BUCKET.head(key);
  if (!stored || stored.size !== bytes.length) {
    await env.COMMUNITY_BUCKET.delete(key);
    return fail(500, "storage_verification_failed", "Stored artwork could not be verified");
  }
  await env.SKINS_DB.prepare("UPDATE community_music_uploads SET cover_key = ?, cover_sha256 = ?, cover_content_type = ? WHERE id = ? AND owner_id = ? AND deleted_at IS NULL").bind(key, digest, detected.mime, musicId, ownerId).run();
  return respond({ success: true, coverUrl: `${new URL(request.url).origin}/v1/music/${musicId}/cover`, sha256: digest, requestId });
}
__name(uploadMusicCover, "uploadMusicCover");
__name2(uploadMusicCover, "uploadMusicCover");
async function readMusicCover(request, env, musicId, requestId, fail) {
  const row = await env.SKINS_DB.prepare("SELECT cover_key, cover_content_type FROM community_music_uploads WHERE id = ? AND deleted_at IS NULL LIMIT 1").bind(musicId).first();
  if (!row?.cover_key) return fail(404, "music_cover_not_found", "Music artwork not found");
  const object = await env.COMMUNITY_BUCKET.get(row.cover_key);
  if (!object) return fail(404, "music_cover_not_found", "Music artwork not found");
  const headers = new Headers({
    "Content-Type": row.cover_content_type || object.httpMetadata?.contentType || "application/octet-stream",
    "Content-Length": String(object.size),
    "Cache-Control": "public, max-age=86400",
    "X-Request-ID": requestId
  });
  return new Response(request.method === "HEAD" ? null : object.body, { status: 200, headers });
}
__name(readMusicCover, "readMusicCover");
__name2(readMusicCover, "readMusicCover");
async function softDeleteMusic(env, ctx, musicId, ownerId, requestId, respond, fail) {
  const result = await env.SKINS_DB.prepare("UPDATE community_music_uploads SET deleted_at = COALESCE(deleted_at, CURRENT_TIMESTAMP) WHERE id = ? AND owner_id = ? AND deleted_at IS NULL").bind(musicId, ownerId).run();
  if (!result.meta?.changes) {
    const deleted = await env.SKINS_DB.prepare("SELECT id FROM community_music_uploads WHERE id = ? AND owner_id = ? AND deleted_at IS NOT NULL").bind(musicId, ownerId).first();
    if (deleted) return respond({ success: true, deleted: true, requestId });
    return fail(404, "music_not_found", "Owned music not found");
  }
  const row = await env.SKINS_DB.prepare("SELECT file_key, cover_key FROM community_music_uploads WHERE id = ? AND owner_id = ?").bind(musicId, ownerId).first();
  if (row?.file_key) ctx.waitUntil(Promise.all([env.COMMUNITY_BUCKET.delete(row.file_key), row.cover_key ? env.COMMUNITY_BUCKET.delete(row.cover_key) : Promise.resolve()]));
  return respond({ success: true, deleted: true, requestId }, 202);
}
__name(softDeleteMusic, "softDeleteMusic");
__name2(softDeleteMusic, "softDeleteMusic");
async function deleteInstallation(env, ctx, ownerId, requestId, respond, fail) {
  const [saves, music, skins, carModels] = await Promise.all([
    env.SKINS_DB.prepare("SELECT file_key FROM accountless_save_uploads WHERE owner_id = ?").bind(ownerId).all(),
    env.SKINS_DB.prepare("SELECT file_key, cover_key FROM community_music_uploads WHERE owner_id = ?").bind(ownerId).all(),
    env.SKINS_DB.prepare("SELECT id FROM skins WHERE owner_id = ?").bind(ownerId).all(),
    env.SKINS_DB.prepare("SELECT id FROM car_models WHERE owner_id = ?").bind(ownerId).all()
  ]);
  await env.SKINS_DB.prepare("UPDATE anonymous_installations SET status = 'revoked', revoked_at = COALESCE(revoked_at, CURRENT_TIMESTAMP), updated_at = CURRENT_TIMESTAMP WHERE id = ? AND status = 'active'").bind(ownerId).run();
  await env.SKINS_DB.batch([
    env.SKINS_DB.prepare("DELETE FROM accountless_save_uploads WHERE owner_id = ?").bind(ownerId),
    env.SKINS_DB.prepare("DELETE FROM community_music_uploads WHERE owner_id = ?").bind(ownerId),
    env.SKINS_DB.prepare("UPDATE skins SET status = 'hidden', deleted_at = COALESCE(deleted_at, CURRENT_TIMESTAMP), updated_at = CURRENT_TIMESTAMP WHERE owner_id = ? AND deleted_at IS NULL").bind(ownerId),
    env.SKINS_DB.prepare("DELETE FROM skin_likes WHERE user_id = ?").bind(ownerId),
    env.SKINS_DB.prepare("DELETE FROM skin_reports WHERE reporter_id = ?").bind(ownerId),
    env.SKINS_DB.prepare("UPDATE car_models SET status = 'hidden', deleted_at = COALESCE(deleted_at, CURRENT_TIMESTAMP), updated_at = CURRENT_TIMESTAMP WHERE owner_id = ? AND deleted_at IS NULL").bind(ownerId),
    env.SKINS_DB.prepare("DELETE FROM car_model_likes WHERE user_id = ?").bind(ownerId),
    env.SKINS_DB.prepare("DELETE FROM car_model_reports WHERE reporter_id = ?").bind(ownerId)
  ]);
  const cleanup = /* @__PURE__ */ __name2(async () => {
    for (const row of saves.results || []) await env.COMMUNITY_BUCKET.delete(row.file_key);
    for (const row of music.results || []) {
      await env.COMMUNITY_BUCKET.delete(row.file_key);
      if (row.cover_key) await env.COMMUNITY_BUCKET.delete(row.cover_key);
    }
    for (const row of skins.results || []) await deletePrefix(env.COMMUNITY_BUCKET, `skins/${row.id}/`);
    for (const row of carModels.results || []) await deletePrefix(env.COMMUNITY_BUCKET, `car-models/${row.id}/`);
  }, "cleanup");
  ctx.waitUntil(cleanup().catch((error) => console.error(JSON.stringify({ event: "installation_cleanup_failed", requestId, ownerId, error: error instanceof Error ? error.message : String(error) }))));
  return respond({ success: true, deleted: true, requestId }, 202);
}
__name(deleteInstallation, "deleteInstallation");
__name2(deleteInstallation, "deleteInstallation");
async function uploadBinary(bytes, env, skinId, ownerId, requestId, respond, fail) {
  if (!bytes.length) return fail(400, "empty_binary", "Skin binary is required");
  const skin = await editableOwnedSkin(env.SKINS_DB, skinId, ownerId);
  if (!skin) return fail(404, "skin_not_editable", "Owned editable skin not found");
  if (bytes.length !== Number(skin.binary_size)) return fail(422, "size_mismatch", "Binary size does not match the draft");
  const actualSha256 = await sha256Hex(bytes);
  if (actualSha256 !== String(skin.binary_sha256).toLowerCase()) return fail(422, "sha256_mismatch", "Binary SHA-256 does not match the draft");
  await env.COMMUNITY_BUCKET.put(skin.binary_key, bytes, { httpMetadata: { contentType: "application/octet-stream" }, customMetadata: { skinId, ownerId, actualSha256 } });
  const stored = await env.COMMUNITY_BUCKET.get(skin.binary_key);
  if (!stored || stored.size !== bytes.length) {
    await env.COMMUNITY_BUCKET.delete(skin.binary_key);
    return fail(500, "storage_verification_failed", "Stored binary could not be verified");
  }
  const storedSha256 = await sha256Hex(new Uint8Array(await stored.arrayBuffer()));
  if (storedSha256 !== actualSha256) {
    await env.COMMUNITY_BUCKET.delete(skin.binary_key);
    return fail(500, "storage_verification_failed", "Stored binary hash verification failed");
  }
  await env.SKINS_DB.prepare("UPDATE skins SET actual_binary_sha256 = ?, binary_verified_at = CURRENT_TIMESTAMP, status = 'uploading', updated_at = CURRENT_TIMESTAMP WHERE id = ? AND owner_id = ? AND deleted_at IS NULL").bind(actualSha256, skinId, ownerId).run();
  return respond({ success: true, size: bytes.length, sha256: actualSha256, requestId });
}
__name(uploadBinary, "uploadBinary");
__name2(uploadBinary, "uploadBinary");
async function uploadImage(bytes, request, env, route, ownerId, role, requestId, respond, fail) {
  if (!bytes.length) return fail(400, "empty_image", "Image body is required");
  const skin = await editableOwnedSkin(env.SKINS_DB, route.id, ownerId);
  if (!skin) return fail(404, "skin_not_editable", "Owned editable skin not found");
  const detected = detectImage(bytes);
  if (!detected) return fail(415, "invalid_image", "Image magic must be PNG, JPEG, or WebP");
  const declared = (request.headers.get("Content-Type") || "").split(";")[0].trim().toLowerCase();
  if (declared !== detected.mime) return fail(415, "image_type_mismatch", "Content-Type does not match image bytes");
  const digest = await sha256Hex(bytes);
  if (role === "cover") {
    const key2 = `skins/${route.id}/cover.${detected.extension}`;
    if (skin.cover_key && skin.cover_key !== key2) await env.COMMUNITY_BUCKET.delete(skin.cover_key);
    await env.COMMUNITY_BUCKET.put(key2, bytes, { httpMetadata: { contentType: detected.mime }, customMetadata: { skinId: route.id, ownerId, role, sha256: digest } });
    await env.SKINS_DB.prepare("UPDATE skins SET cover_key = ?, cover_sha256 = ?, cover_content_type = ?, status = 'uploading', updated_at = CURRENT_TIMESTAMP WHERE id = ? AND owner_id = ? AND deleted_at IS NULL").bind(key2, digest, detected.mime, route.id, ownerId).run();
    return respond({ success: true, sha256: digest, requestId });
  }
  const position = route.position;
  if (position < 1 || position > 4) return fail(400, "invalid_position", "Screenshot position must be 1-4");
  const key = `skins/${route.id}/screenshots/${String(position).padStart(2, "0")}.${detected.extension}`;
  const previous = await env.SKINS_DB.prepare("SELECT object_key FROM skin_screenshots WHERE skin_id = ? AND position = ?").bind(route.id, position).first();
  if (previous?.object_key && previous.object_key !== key) await env.COMMUNITY_BUCKET.delete(previous.object_key);
  await env.COMMUNITY_BUCKET.put(key, bytes, { httpMetadata: { contentType: detected.mime }, customMetadata: { skinId: route.id, ownerId, role, position: String(position), sha256: digest } });
  const caption = clean(new URL(request.url).searchParams.get("caption"), 200);
  await env.SKINS_DB.batch([
    env.SKINS_DB.prepare(`INSERT INTO skin_screenshots (skin_id, position, object_key, caption, sha256, content_type) VALUES (?, ?, ?, ?, ?, ?)
			ON CONFLICT(skin_id, position) DO UPDATE SET object_key = excluded.object_key, caption = excluded.caption, sha256 = excluded.sha256, content_type = excluded.content_type`).bind(route.id, position, key, caption, digest, detected.mime),
    env.SKINS_DB.prepare("UPDATE skins SET screenshot_count = (SELECT COUNT(*) FROM skin_screenshots WHERE skin_id = ?), status = 'uploading', updated_at = CURRENT_TIMESTAMP WHERE id = ? AND owner_id = ? AND deleted_at IS NULL").bind(route.id, route.id, ownerId)
  ]);
  return respond({ success: true, position, sha256: digest, requestId });
}
__name(uploadImage, "uploadImage");
__name2(uploadImage, "uploadImage");
async function uploadManifest(bytes, request, env, skinId, ownerId, requestId, respond, fail) {
  if (!bytes.length) return fail(400, "empty_manifest", "Manifest body is required");
  const skin = await editableOwnedSkin(env.SKINS_DB, skinId, ownerId);
  if (!skin) return fail(404, "skin_not_editable", "Owned editable skin not found");
  const declared = (request.headers.get("Content-Type") || "").split(";")[0].trim().toLowerCase();
  if (declared !== "application/json") return fail(415, "manifest_type_mismatch", "Manifest Content-Type must be application/json");
  const text = new TextDecoder().decode(bytes);
  let manifest;
  try { manifest = JSON.parse(text); } catch { return fail(400, "invalid_manifest_json", "Manifest body is not valid JSON"); }
  const validation = validateManifest(manifest, skinId, bytes.length);
  if (!validation.ok) return fail(422, validation.code, validation.message);
  const digest = await sha256Hex(bytes);
  const key = `skins/${skinId}/manifest.json`;
  await env.COMMUNITY_BUCKET.put(key, bytes, { httpMetadata: { contentType: "application/json" }, customMetadata: { skinId, ownerId, role: "manifest", sha256: digest } });
  const stored = await env.COMMUNITY_BUCKET.head(key);
  if (!stored || stored.size !== bytes.length) { await env.COMMUNITY_BUCKET.delete(key); return fail(500, "storage_verification_failed", "Stored manifest could not be verified"); }
  await env.SKINS_DB.prepare("UPDATE skins SET manifest_key = ?, manifest_size = ?, manifest_sha256 = ?, manifest_content_type = ?, status = 'uploading', updated_at = CURRENT_TIMESTAMP WHERE id = ? AND owner_id = ? AND deleted_at IS NULL").bind(key, bytes.length, digest, "application/json", skinId, ownerId).run();
  console.log(JSON.stringify({ event: "skin_manifest_uploaded", requestId, skinId, size: bytes.length, sha256: digest }));
  return respond({ success: true, size: bytes.length, sha256: digest, requestId });
}
__name(uploadManifest, "uploadManifest");
__name2(uploadManifest, "uploadManifest");
function validateManifest(manifest, skinId, byteLength) {
  if (!manifest || typeof manifest !== "object" || Array.isArray(manifest)) return { ok: false, code: "invalid_manifest", message: "Manifest must be a JSON object" };
  if (manifest.skinId !== skinId) return { ok: false, code: "manifest_skin_mismatch", message: "Manifest skinId does not match the draft" };
  if (byteLength > MAX_JSON_BYTES) return { ok: false, code: "manifest_too_large", message: "Manifest exceeds the size limit" };
  if (!manifest.binary || typeof manifest.binary !== "object") return { ok: false, code: "manifest_binary_missing", message: "Manifest binary metadata is required" };
  if (!manifest.cover || typeof manifest.cover !== "object") return { ok: false, code: "manifest_cover_missing", message: "Manifest cover metadata is required" };
  const paths = [manifest.binary.path, manifest.cover.path, ...(Array.isArray(manifest.screenshots) ? manifest.screenshots.map((item) => item?.path) : [])];
  if (paths.some((path) => typeof path !== "string" || !isSafeManifestPath(path))) return { ok: false, code: "manifest_invalid_path", message: "Manifest paths must be safe relative paths" };
  return { ok: true };
}
__name(validateManifest, "validateManifest");
__name2(validateManifest, "validateManifest");
function isSafeManifestPath(path) {
  return path.length > 0 && path.length <= 255 && !path.startsWith("/") && !path.includes("\\") && !path.split("/").includes("..") && !path.split("/").includes("");
}
__name(isSafeManifestPath, "isSafeManifestPath");
__name2(isSafeManifestPath, "isSafeManifestPath");
async function publishSkin(env, skinId, ownerId, requestId, respond, fail) {
  const skin = await env.SKINS_DB.prepare("SELECT * FROM skins WHERE id = ? AND owner_id = ? AND deleted_at IS NULL AND status IN ('draft', 'uploading', 'pending', 'approved') LIMIT 1").bind(skinId, ownerId).first();
  if (!skin) return fail(404, "skin_not_publishable", "Owned publishable skin not found");
  if (skin.status === "approved") return respond({ success: true, id: skinId, status: "approved", requestId });
  if (!skin.cover_key || !skin.cover_sha256) return fail(409, "cover_required", "A verified cover image is required");
  if (!skin.manifest_key || !skin.manifest_sha256) return fail(409, "manifest_required", "A verified manifest is required");
  const manifestObject = await env.COMMUNITY_BUCKET.get(skin.manifest_key);
  if (!manifestObject || manifestObject.size !== Number(skin.manifest_size)) return fail(409, "manifest_integrity_failed", "Stored manifest is missing or has an invalid size");
  if (await sha256Hex(new Uint8Array(await manifestObject.arrayBuffer())) !== String(skin.manifest_sha256).toLowerCase()) return fail(409, "manifest_integrity_failed", "Stored manifest integrity verification failed");
  const binary = await env.COMMUNITY_BUCKET.get(skin.binary_key);
  const cover = await env.COMMUNITY_BUCKET.get(skin.cover_key);
  if (!binary || !cover) return fail(409, "uploads_incomplete", "Binary and cover uploads are required");
  const [binaryHash, coverBytes] = await Promise.all([sha256Hex(new Uint8Array(await binary.arrayBuffer())), cover.arrayBuffer()]);
  const coverArray = new Uint8Array(coverBytes);
  const coverMagic = detectImage(coverArray);
  const coverHash = await sha256Hex(coverArray);
  if (binary.size !== Number(skin.binary_size) || binaryHash !== skin.binary_sha256 || binaryHash !== skin.actual_binary_sha256) return fail(409, "binary_integrity_failed", "Stored binary integrity verification failed");
  if (!coverMagic || coverHash !== skin.cover_sha256 || coverMagic.mime !== skin.cover_content_type) return fail(409, "cover_integrity_failed", "Stored cover integrity verification failed");
  const screenshots = await env.SKINS_DB.prepare("SELECT position, object_key, sha256, content_type FROM skin_screenshots WHERE skin_id = ? ORDER BY position").bind(skinId).all();
  for (const screenshot of screenshots.results || []) {
    const object = await env.COMMUNITY_BUCKET.get(screenshot.object_key);
    if (!object || object.size > MAX_SCREENSHOT_BYTES) return fail(409, "screenshot_integrity_failed", `Screenshot ${screenshot.position} is missing or too large`);
    const bytes = new Uint8Array(await object.arrayBuffer());
    const magic = detectImage(bytes);
    if (!magic || magic.mime !== screenshot.content_type || await sha256Hex(bytes) !== screenshot.sha256) return fail(409, "screenshot_integrity_failed", `Screenshot ${screenshot.position} failed integrity verification`);
  }
  const result = await env.SKINS_DB.prepare("UPDATE skins SET status = 'approved', screenshot_count = ?, approved_at = CURRENT_TIMESTAMP, updated_at = CURRENT_TIMESTAMP WHERE id = ? AND owner_id = ? AND deleted_at IS NULL AND status IN ('draft', 'uploading', 'pending')").bind((screenshots.results || []).length, skinId, ownerId).run();
  if (!result.meta?.changes) return fail(409, "invalid_state_transition", "Skin state changed before publishing");
  return respond({ success: true, id: skinId, status: "approved", requestId });
}
__name(publishSkin, "publishSkin");
__name2(publishSkin, "publishSkin");
async function setLike(database, skinId, ownerId, liked, requestId, respond, fail) {
  if (!await approvedSkin(database, skinId)) return fail(404, "skin_not_found", "Skin not found");
  if (liked) await database.prepare("INSERT OR IGNORE INTO skin_likes (skin_id, user_id) VALUES (?, ?)").bind(skinId, ownerId).run();
  else await database.prepare("DELETE FROM skin_likes WHERE skin_id = ? AND user_id = ?").bind(skinId, ownerId).run();
  const row = await database.prepare("SELECT COUNT(*) AS count FROM skin_likes WHERE skin_id = ?").bind(skinId).first();
  return respond({ success: true, liked, likes: Number(row?.count || 0), requestId });
}
__name(setLike, "setLike");
__name2(setLike, "setLike");
async function reportSkin(bytes, database, skinId, ownerId, requestId, respond, fail) {
  if (!await approvedSkin(database, skinId)) return fail(404, "skin_not_found", "Skin not found");
  const body = parseJson(bytes);
  if (!body) return fail(400, "invalid_json", "The request body is not valid JSON");
  const reason = clean(body.reason, 32).toLowerCase();
  const details = clean(body.details, 1e3);
  if (!REPORT_REASONS.has(reason)) return fail(400, "invalid_report_reason", "Report reason is invalid");
  const result = await database.prepare("INSERT OR IGNORE INTO skin_reports (skin_id, reporter_id, reason, details, status) VALUES (?, ?, ?, ?, 'open')").bind(skinId, ownerId, reason, details).run();
  return respond({ success: true, duplicate: !result.meta?.changes, requestId }, result.meta?.changes ? 201 : 200);
}
__name(reportSkin, "reportSkin");
__name2(reportSkin, "reportSkin");
async function softDeleteSkin(env, ctx, skinId, ownerId, requestId, respond, fail) {
  const result = await env.SKINS_DB.prepare("UPDATE skins SET status = 'hidden', deleted_at = COALESCE(deleted_at, CURRENT_TIMESTAMP), updated_at = CURRENT_TIMESTAMP WHERE id = ? AND owner_id = ? AND deleted_at IS NULL").bind(skinId, ownerId).run();
  if (!result.meta?.changes) {
    const deleted = await env.SKINS_DB.prepare("SELECT id FROM skins WHERE id = ? AND owner_id = ? AND deleted_at IS NOT NULL").bind(skinId, ownerId).first();
    if (deleted) return respond({ success: true, deleted: true, requestId });
    return fail(404, "skin_not_found", "Owned skin not found");
  }
  ctx.waitUntil(deletePrefix(env.COMMUNITY_BUCKET, `skins/${skinId}/`).catch((error) => console.error(JSON.stringify({ event: "skin_cleanup_failed", requestId, skinId, error: error instanceof Error ? error.message : String(error) }))));
  return respond({ success: true, deleted: true, requestId }, 202);
}
__name(softDeleteSkin, "softDeleteSkin");
__name2(softDeleteSkin, "softDeleteSkin");
async function listSkins(database, env, url, respond, viewerId) {
  const query = clean(url.searchParams.get("query"), 80);
  const car = clean(url.searchParams.get("car"), 100);
  const format = clean(url.searchParams.get("format"), 24).toLowerCase();
  const sort = url.searchParams.get("sort") || "newest";
  const limit = clamp(url.searchParams.get("limit"), 1, 50, 20);
  const offset = clamp(url.searchParams.get("offset"), 0, 1e4, 0);
  const conditions = ["s.status = 'approved'", "s.deleted_at IS NULL"];
  const values = [];
  if (query) {
    const like = `%${escapeLike(query)}%`;
    conditions.push("(s.title LIKE ? ESCAPE '\\' OR s.creator_name LIKE ? ESCAPE '\\' OR s.description LIKE ? ESCAPE '\\' OR s.car_directory LIKE ? ESCAPE '\\')");
    values.push(like, like, like, like);
  }
  if (car) {
    conditions.push("s.car_directory = ?");
    values.push(car);
  }
  if (format) {
    conditions.push("s.texture_format = ?");
    values.push(format);
  }
  const order = sort === "likes" ? "likes DESC, s.created_at DESC" : sort === "downloads" ? "downloads DESC, s.created_at DESC" : "s.created_at DESC";
  const likeViewerClause = viewerId ? "(SELECT 1 FROM skin_likes l WHERE l.skin_id = s.id AND l.user_id = ?) AS liked_by_me" : "0 AS liked_by_me";
  const statement = database.prepare(`SELECT s.*, (SELECT COUNT(*) FROM skin_likes l WHERE l.skin_id = s.id) AS likes, (SELECT COUNT(*) FROM skin_downloads d WHERE d.skin_id = s.id) AS downloads, ${likeViewerClause} FROM skins s WHERE ${conditions.join(" AND ")} ORDER BY ${order} LIMIT ? OFFSET ?`);
  const bindings = viewerId ? [viewerId, ...values, limit, offset] : [...values, limit, offset];
  const result = await statement.bind(...bindings).all();
  const items = [];
  for (const row of result.results || []) {
    const object = await env.COMMUNITY_BUCKET.head(row.binary_key);
    if (!object) {
      await deleteOrphanedSkin(database, row.id);
      console.log(JSON.stringify({ event: "skin_index_deleted", id: row.id, binaryKey: row.binary_key }));
      continue;
    }
    items.push(skinDto(row, url.origin, "/v1", viewerId));
  }
  return respond({ items, limit, offset });
}
__name(listSkins, "listSkins");
__name2(listSkins, "listSkins");
async function readSkin(request, env, ctx, url, route, requestId, respond, fail, viewerId) {
  const skin = await env.SKINS_DB.prepare("SELECT s.*, (SELECT COUNT(*) FROM skin_likes WHERE skin_id = s.id) AS likes, (SELECT COUNT(*) FROM skin_downloads WHERE skin_id = s.id) AS downloads, (SELECT 1 FROM skin_likes WHERE skin_id = s.id AND user_id = ?) AS liked_by_me FROM skins s WHERE s.id = ? AND s.status = 'approved' AND s.deleted_at IS NULL LIMIT 1").bind(viewerId, route.id).first();
  if (!skin) return fail(404, "skin_not_found", "Skin not found");
  if (route.action === "details") {
    const object2 = await env.COMMUNITY_BUCKET.head(skin.binary_key);
    if (!object2) {
      await deleteOrphanedSkin(env.SKINS_DB, skin.id);
      return fail(404, "skin_not_found", "Skin no longer available");
    }
    const screenshotRows = await env.SKINS_DB.prepare("SELECT position, object_key, caption FROM skin_screenshots WHERE skin_id = ? ORDER BY position").bind(route.id).all();
    const screenshots = [];
    for (const item of screenshotRows.results || []) {
      if (await env.COMMUNITY_BUCKET.head(item.object_key)) {
        screenshots.push({ position: item.position, caption: item.caption, url: `${url.origin}/v1/skins/${route.id}/screenshots/${item.position}` });
      } else {
        await env.SKINS_DB.prepare("DELETE FROM skin_screenshots WHERE skin_id = ? AND position = ?").bind(route.id, item.position).run();
        console.log(JSON.stringify({ event: "skin_screenshot_orphan_deleted", skinId: route.id, position: item.position, objectKey: item.object_key }));
      }
    }
    return respond({ ...skinDto(skin, url.origin, "/v1", viewerId), screenshotCount: screenshots.length, screenshots, requestId });
  }
  let key;
  if (route.action === "binary") key = skin.binary_key;
  if (route.action === "cover") key = skin.cover_key;
  if (route.action === "manifest") key = skin.manifest_key;
  if (route.action === "screenshot") key = (await env.SKINS_DB.prepare("SELECT object_key FROM skin_screenshots WHERE skin_id = ? AND position = ?").bind(route.id, route.position).first())?.object_key;
  if (!key) return fail(404, "asset_not_found", "Skin asset not found");
  const object = await env.COMMUNITY_BUCKET.get(key, request.headers.has("Range") ? { range: request.headers } : void 0);
  if (!object) return fail(404, "asset_not_found", "Skin asset not found");
  if (request.method === "GET" && route.action === "binary" && !request.headers.has("Range")) {
    ctx.waitUntil(env.SKINS_DB.prepare("INSERT INTO skin_downloads (skin_id, user_id) VALUES (?, ?)").bind(route.id, viewerId).run());
  }
  const headers = new Headers({ "Access-Control-Allow-Origin": "*", "Access-Control-Expose-Headers": "Content-Length, Content-Range, Accept-Ranges, ETag, X-Request-ID", "Accept-Ranges": "bytes", "Cache-Control": "public, max-age=31536000, immutable", ETag: object.httpEtag, "X-Request-ID": requestId });
  object.writeHttpMetadata(headers);
  return new Response(request.method === "HEAD" ? null : object.body, { status: object.range ? 206 : 200, headers });
}
__name(readSkin, "readSkin");
__name2(readSkin, "readSkin");
function matchSkinRoute(path) {
  const match = /^\/v1\/skins\/([0-9a-f-]{36})(?:\/(binary|cover|manifest|publish|like|report)|\/screenshots\/([1-4]))?$/i.exec(path);
  if (!match || !UUID_PATTERN.test(match[1])) return null;
  return { id: match[1].toLowerCase(), action: match[3] ? "screenshot" : match[2]?.toLowerCase() || "details", position: match[3] ? Number(match[3]) : void 0 };
}
__name(matchSkinRoute, "matchSkinRoute");
__name2(matchSkinRoute, "matchSkinRoute");
function validateMetadata(body) {
  const value = {
    title: clean(body.title, 80),
    creatorName: clean(body.creatorName, 50),
    description: clean(body.description, 1500),
    carDirectory: clean(body.carDirectory, 100).toLowerCase(),
    slotKey: clean(body.slotKey, 100).toLowerCase(),
    textureFormat: clean(body.textureFormat, 24).toLowerCase(),
    sourceFilename: clean(body.sourceFilename, 180),
    binarySize: Number(body.binarySize),
    binarySha256: clean(body.binarySha256, 64).toLowerCase(),
    gameVersions: clean(body.gameVersions, 100)
  };
  if (!value.title || !value.creatorName) return invalid("invalid_metadata", "Title and creatorName are required");
  const invalidField = !CAR_PATTERN.test(value.carDirectory) ? "carDirectory" : !SLOT_PATTERN.test(value.slotKey) ? "slotKey" : !FORMAT_PATTERN.test(value.textureFormat) ? "textureFormat" : null;
  if (invalidField) {
    const received = value[invalidField];
    const expected = invalidField === "carDirectory" ? "1-100 letters, digits, underscores, or hyphens; must start with a letter or digit" : invalidField === "slotKey" ? "ext, ext<number>, ext_<name>, int, int<number>, or int_<name>" : "1-24 letters, digits, underscores, or hyphens";
    return invalid("invalid_target", `${invalidField} is invalid`, { field: invalidField, received, expected });
  }
  if (!SKIN_FILE_PATTERN.test(value.sourceFilename) || value.sourceFilename.includes("/") || value.sourceFilename.includes("\\")) return invalid("invalid_filename", "Source filename is invalid");
  if (!value.sourceFilename.toLowerCase().endsWith(`_${value.slotKey}.${value.textureFormat}.dds.z`)) return invalid("filename_target_mismatch", "Filename does not match the selected target");
  if (!Number.isSafeInteger(value.binarySize) || value.binarySize <= 0 || value.binarySize > MAX_SKIN_BYTES) return invalid("invalid_binary_size", "Binary size must be between 1 byte and 5 MiB");
  if (!SHA256_PATTERN.test(value.binarySha256)) return invalid("invalid_sha256", "binarySha256 must be a SHA-256 hex digest");
  return { ok: true, ...value };
}
__name(validateMetadata, "validateMetadata");
__name2(validateMetadata, "validateMetadata");
function isP256PublicJwk(key) {
  return key && key.kty === "EC" && key.crv === "P-256" && typeof key.x === "string" && typeof key.y === "string" && !key.d;
}
__name(isP256PublicJwk, "isP256PublicJwk");
__name2(isP256PublicJwk, "isP256PublicJwk");
function stablePublicJwk(key) {
  return JSON.stringify({ kty: "EC", crv: "P-256", x: key.x, y: key.y, ext: true, key_ops: ["verify"] });
}
__name(stablePublicJwk, "stablePublicJwk");
__name2(stablePublicJwk, "stablePublicJwk");
function authFailure(status, code, message) {
  return { ok: false, status, code, message };
}
__name(authFailure, "authFailure");
__name2(authFailure, "authFailure");
function invalid(code, message, details) {
  return { ok: false, code, message, details };
}
__name(invalid, "invalid");
__name2(invalid, "invalid");
function parseJson(bytes) {
  try {
    return JSON.parse(new TextDecoder().decode(bytes));
  } catch {
    return null;
  }
}
__name(parseJson, "parseJson");
__name2(parseJson, "parseJson");
function clean(value, max = 256) {
  return String(value || "").trim().slice(0, max);
}
__name(clean, "clean");
__name2(clean, "clean");
function escapeLike(value) {
  return value.replace(/[%_\\]/g, (match) => `\\${match}`);
}
__name(escapeLike, "escapeLike");
__name2(escapeLike, "escapeLike");
function clamp(value, min, max, fallback) {
  const number = Number.parseInt(String(value ?? ""), 10);
  return Number.isFinite(number) ? Math.min(max, Math.max(min, number)) : fallback;
}
__name(clamp, "clamp");
__name2(clamp, "clamp");
function parseContentLength(value) {
  if (!/^\d+$/.test(value || "")) return null;
  const number = Number(value);
  return Number.isSafeInteger(number) ? number : null;
}
__name(parseContentLength, "parseContentLength");
__name2(parseContentLength, "parseContentLength");
function nonNegativeInteger(value) {
  if (!/^\d+$/.test(value || "")) return null;
  const number = Number(value);
  return Number.isSafeInteger(number) && number >= 0 ? number : null;
}
__name(nonNegativeInteger, "nonNegativeInteger");
__name2(nonNegativeInteger, "nonNegativeInteger");
async function boundedBody(request, maximum) {
  const length = Number(request.headers.get("Content-Length"));
  if (Number.isFinite(length) && length > maximum) return null;
  const bytes = new Uint8Array(await request.arrayBuffer());
  return bytes.length <= maximum ? bytes : null;
}
__name(boundedBody, "boundedBody");
__name2(boundedBody, "boundedBody");
async function sha256Hex(bytes) {
  const digest = new Uint8Array(await crypto.subtle.digest("SHA-256", bytes));
  return [...digest].map((byte) => byte.toString(16).padStart(2, "0")).join("");
}
__name(sha256Hex, "sha256Hex");
__name2(sha256Hex, "sha256Hex");
function base64UrlDecode(value) {
  const normalized = value.replace(/-/g, "+").replace(/_/g, "/");
  const binary = atob(normalized.padEnd(Math.ceil(normalized.length / 4) * 4, "="));
  return Uint8Array.from(binary, (char) => char.charCodeAt(0));
}
__name(base64UrlDecode, "base64UrlDecode");
__name2(base64UrlDecode, "base64UrlDecode");
function derToP1363(der) {
  if (der[0] !== 48) throw new Error("bad_der");
  let offset = der[1] & 128 ? 2 + (der[1] & 127) : 2;
  if (der[offset++] !== 2) throw new Error("bad_der");
  const rLength = der[offset++];
  const r = der.slice(offset, offset + rLength);
  offset += rLength;
  if (der[offset++] !== 2) throw new Error("bad_der");
  const sLength = der[offset++];
  const s = der.slice(offset, offset + sLength);
  const result = new Uint8Array(64);
  result.set(r.slice(Math.max(0, r.length - 32)), 32 - Math.min(32, r.length));
  result.set(s.slice(Math.max(0, s.length - 32)), 64 - Math.min(32, s.length));
  return result;
}
__name(derToP1363, "derToP1363");
__name2(derToP1363, "derToP1363");
function detectImage(bytes) {
  if (bytes.length >= 8 && bytes[0] === 137 && bytes[1] === 80 && bytes[2] === 78 && bytes[3] === 71 && bytes[4] === 13 && bytes[5] === 10 && bytes[6] === 26 && bytes[7] === 10) return { mime: "image/png", extension: "png" };
  if (bytes.length >= 3 && bytes[0] === 255 && bytes[1] === 216 && bytes[2] === 255) return { mime: "image/jpeg", extension: "jpg" };
  if (bytes.length >= 12 && String.fromCharCode(...bytes.slice(0, 4)) === "RIFF" && String.fromCharCode(...bytes.slice(8, 12)) === "WEBP") return { mime: "image/webp", extension: "webp" };
  return null;
}
__name(detectImage, "detectImage");
__name2(detectImage, "detectImage");
function createSkinResult(id, status, origin, requestId) {
  return { success: true, id, status, binaryUploadUrl: `${origin}/v1/skins/${id}/binary`, coverUploadUrl: `${origin}/v1/skins/${id}/cover`, publishUrl: `${origin}/v1/skins/${id}/publish`, requestId };
}
__name(createSkinResult, "createSkinResult");
__name2(createSkinResult, "createSkinResult");
function skinDto(row, origin, prefix, viewerId) {
  return { id: row.id, title: row.title, creatorName: row.creator_name, description: row.description, carDirectory: row.car_directory, slotKey: row.slot_key, textureFormat: row.texture_format, sourceFilename: row.source_filename, binarySize: Number(row.binary_size), binarySha256: row.binary_sha256, screenshotCount: Number(row.screenshot_count || 0), gameVersions: row.game_versions, status: row.status, createdAt: row.created_at, updatedAt: row.updated_at, likes: Number(row.likes || 0), downloads: Number(row.downloads || 0), liked_by_me: Boolean(row.liked_by_me), coverUrl: row.cover_key ? `${origin}${prefix}/skins/${row.id}/cover` : null, binaryUrl: `${origin}${prefix}/skins/${row.id}/binary`, manifestUrl: row.manifest_key ? `${origin}${prefix}/skins/${row.id}/manifest` : null, manifestSha256: row.manifest_sha256 || null, manifestSize: row.manifest_size == null ? null : Number(row.manifest_size), detailsUrl: `${origin}${prefix}/skins/${row.id}`, shareUrl: `${origin}${prefix}/skins/${row.id}`, is_owner: viewerId !== null && row.owner_id === viewerId };
}
__name(skinDto, "skinDto");
__name2(skinDto, "skinDto");
async function setItemLike(database, type, itemId, userId, liked, requestId, respond, fail) {
  const table = `${type}_likes`;
  const idCol = `${type}_id`;
  let exists;
  if (type === "skin") exists = await approvedSkin(database, itemId);
  else if (type === "music") exists = await database.prepare("SELECT id FROM community_music_uploads WHERE id = ? AND deleted_at IS NULL").bind(itemId).first();
  else if (type === "save") exists = await database.prepare("SELECT id FROM accountless_save_uploads WHERE id = ?").bind(itemId).first();
  if (!exists) return fail(404, "not_found", `${type} not found`);
  if (liked) await database.prepare(`INSERT OR IGNORE INTO ${table} (${idCol}, user_id) VALUES (?, ?)`).bind(itemId, userId).run();
  else await database.prepare(`DELETE FROM ${table} WHERE ${idCol} = ? AND user_id = ?`).bind(itemId, userId).run();
  const row = await database.prepare(`SELECT COUNT(*) AS count FROM ${table} WHERE ${idCol} = ?`).bind(itemId).first();
  return respond({ success: true, liked, likes: Number(row?.count || 0), requestId });
}
__name(setItemLike, "setItemLike");
__name2(setItemLike, "setItemLike");
async function editableOwnedSkin(database, id, ownerId) {
  return database.prepare("SELECT * FROM skins WHERE id = ? AND owner_id = ? AND deleted_at IS NULL AND status IN ('draft', 'uploading') LIMIT 1").bind(id, ownerId).first();
}
__name(editableOwnedSkin, "editableOwnedSkin");
__name2(editableOwnedSkin, "editableOwnedSkin");
async function approvedSkin(database, id) {
  return database.prepare("SELECT id FROM skins WHERE id = ? AND status = 'approved' AND deleted_at IS NULL LIMIT 1").bind(id).first();
}
__name(approvedSkin, "approvedSkin");
__name2(approvedSkin, "approvedSkin");
async function deleteOrphanedSkin(database, skinId) {
  await database.batch([
    database.prepare("DELETE FROM skin_likes WHERE skin_id = ?").bind(skinId),
    database.prepare("DELETE FROM skin_downloads WHERE skin_id = ?").bind(skinId),
    database.prepare("DELETE FROM skin_reports WHERE skin_id = ?").bind(skinId),
    database.prepare("DELETE FROM skin_screenshots WHERE skin_id = ?").bind(skinId),
    database.prepare("DELETE FROM skins WHERE id = ?").bind(skinId)
  ]);
}
__name(deleteOrphanedSkin, "deleteOrphanedSkin");
__name2(deleteOrphanedSkin, "deleteOrphanedSkin");
async function deletePrefix(bucket, prefix) {
  let cursor;
  do {
    const list = await bucket.list({ prefix, cursor, limit: 1e3 });
    if (list.objects.length) await bucket.delete(list.objects.map((object) => object.key));
    cursor = list.truncated ? list.cursor : void 0;
  } while (cursor);
}
__name(deletePrefix, "deletePrefix");
__name2(deletePrefix, "deletePrefix");
var PAGE_SIZE = 100;
async function reconcileCommunityAssets(env) {
  if (!env.SKINS_DB || !env.COMMUNITY_BUCKET) {
    console.warn(JSON.stringify({ event: "community_asset_reconciliation_skipped", reason: "missing_binding" }));
    return;
  }
  const summary = {
    saves: await reconcileSaves(env),
    music: await reconcileMusic(env),
    skins: await reconcileSkins(env)
  };
  console.log(JSON.stringify({ event: "community_asset_reconciliation_complete", ...summary }));
}
__name(reconcileCommunityAssets, "reconcileCommunityAssets");
__name2(reconcileCommunityAssets, "reconcileCommunityAssets");
async function reconcileSaves(env) {
  let removed = 0;
  let lastId = "";
  while (true) {
    const rows = await env.SKINS_DB.prepare(`
      SELECT id, file_key
      FROM accountless_save_uploads
      WHERE id > ?
      ORDER BY id
      LIMIT ?
    `).bind(lastId, PAGE_SIZE).all();
    const items = rows.results || [];
    if (!items.length) return removed;
    for (const item of items) {
      if (!await env.COMMUNITY_BUCKET.head(item.file_key)) {
        await env.SKINS_DB.prepare("DELETE FROM accountless_save_uploads WHERE id = ?").bind(item.id).run();
        removed++;
        console.log(JSON.stringify({ event: "scheduled_save_orphan_pruned", id: item.id, fileKey: item.file_key }));
      }
    }
    lastId = items[items.length - 1].id;
    if (items.length < PAGE_SIZE) return removed;
  }
}
__name(reconcileSaves, "reconcileSaves");
__name2(reconcileSaves, "reconcileSaves");
async function reconcileMusic(env) {
  let removed = 0;
  let lastId = "";
  while (true) {
    const rows = await env.SKINS_DB.prepare(`
      SELECT id, file_key
      FROM community_music_uploads
      WHERE deleted_at IS NULL AND id > ?
      ORDER BY id
      LIMIT ?
    `).bind(lastId, PAGE_SIZE).all();
    const items = rows.results || [];
    if (!items.length) return removed;
    for (const item of items) {
      if (!await env.COMMUNITY_BUCKET.head(item.file_key)) {
        await env.SKINS_DB.batch([
          env.SKINS_DB.prepare("DELETE FROM music_likes WHERE music_id = ?").bind(item.id),
          env.SKINS_DB.prepare("DELETE FROM music_downloads WHERE music_id = ?").bind(item.id),
          env.SKINS_DB.prepare("DELETE FROM community_music_uploads WHERE id = ?").bind(item.id)
        ]);
        removed++;
        console.log(JSON.stringify({ event: "scheduled_music_orphan_deleted", id: item.id, fileKey: item.file_key }));
      }
    }
    lastId = items[items.length - 1].id;
    if (items.length < PAGE_SIZE) return removed;
  }
}
__name(reconcileMusic, "reconcileMusic");
__name2(reconcileMusic, "reconcileMusic");
async function reconcileSkins(env) {
  let removed = 0;
  let lastId = "";
  while (true) {
    const rows = await env.SKINS_DB.prepare(`
      SELECT id, binary_key
      FROM skins
      WHERE deleted_at IS NULL AND id > ?
      ORDER BY id
      LIMIT ?
    `).bind(lastId, PAGE_SIZE).all();
    const items = rows.results || [];
    if (!items.length) return removed;
    for (const item of items) {
      if (!await env.COMMUNITY_BUCKET.head(item.binary_key)) {
        await env.SKINS_DB.batch([
          env.SKINS_DB.prepare("DELETE FROM skin_likes WHERE skin_id = ?").bind(item.id),
          env.SKINS_DB.prepare("DELETE FROM skin_downloads WHERE skin_id = ?").bind(item.id),
          env.SKINS_DB.prepare("DELETE FROM skin_reports WHERE skin_id = ?").bind(item.id),
          env.SKINS_DB.prepare("DELETE FROM skin_screenshots WHERE skin_id = ?").bind(item.id),
          env.SKINS_DB.prepare("DELETE FROM skins WHERE id = ?").bind(item.id)
        ]);
        removed++;
        console.log(JSON.stringify({ event: "scheduled_skin_orphan_deleted", id: item.id, binaryKey: item.binary_key }));
      }
    }
    lastId = items[items.length - 1].id;
    if (items.length < PAGE_SIZE) return removed;
  }
}
__name(reconcileSkins, "reconcileSkins");
__name2(reconcileSkins, "reconcileSkins");
// Public release chunks are intentionally readable without the admin X-RETR0-KEY.
// Keep the allow-list versioned so private R2 objects remain behind authentication.
var RELEASE_PART_PATTERN = /^Project_RETR0_V1\.(?:1|2)_Part[1-3]\.zip\.(00[1-9]|0[1-9][0-9]|[1-9][0-9]{2})$/;
var SHA256_PATTERN2 = /^[a-f0-9]{64}$/i;
var CAR_DIRECTORY_PATTERN = /^[a-z0-9][a-z0-9_-]{0,99}$/i;
var SLOT_PATTERN2 = /^(?:ext|ext\d+|ext_[a-z0-9_-]+|int|int\d+|int_[a-z0-9_-]+)$/i;
var FORMAT_PATTERN2 = /^[a-z0-9_-]{1,24}$/i;
var SKIN_FILE_PATTERN2 = /^[a-z0-9][a-z0-9_() .-]*\.dds\.z$/i;
var SKIN_ID_PATTERN = /^[0-9a-f-]{36}$/i;
var MAX_UPLOAD_BYTES = 25 * 1024 * 1024;
var MAX_SKIN_BYTES2 = 5 * 1024 * 1024;
var MAX_COVER_BYTES2 = 500 * 1024;
var MAX_SCREENSHOT_BYTES2 = 1024 * 1024;
var MAX_SCREENSHOTS = 4;
var ALLOWED_IMAGE_TYPES = /* @__PURE__ */ new Set([
  "image/webp",
  "image/jpeg",
  "image/png"
]);
var ASSET_LINKS = [
  {
    relation: ["delegate_permission/common.handle_all_urls"],
    target: {
      namespace: "android_app",
      package_name: "com.retr0.projectretr0",
      sha256_cert_fingerprints: ["4B:E1:C8:BB:D7:6B:40:00:78:11:D7:5A:97:CF:CC:46:F4:AC:C2:BD:B8:FC:1D:78:3B:49:8F:EE:D0:2D:48:A5"]
    }
  }
];
var index_default = {
  async scheduled(event, env, ctx) {
    ctx.waitUntil(
      reconcileCommunityAssets(env).catch(
        (error) => console.error(JSON.stringify({
          event: "community_asset_reconciliation_failed",
          error: error instanceof Error ? error.message : String(error)
        }))
      )
    );
  },
  async fetch(request, env, ctx) {
    const url = new URL(request.url);
    const method = request.method.toUpperCase();
    const requestId = request.headers.get("CF-Ray") || crypto.randomUUID();
    const startedAt = Date.now();
    let v1Response;
    try {
      v1Response = await handleV1(
        request,
        env,
        ctx,
        requestId
      );
    } catch (error) {
      console.error(JSON.stringify({
        event: "v1_dispatch_failed",
        requestId,
        method,
        path: url.pathname,
        error: error instanceof Error ? error.message : String(error),
        stack: error instanceof Error ? error.stack : void 0
      }));
      v1Response = new Response(JSON.stringify({
        error: {
          code: "internal_error",
          message: "The request could not be completed"
        },
        requestId
      }), {
        status: 500,
        headers: {
          "Access-Control-Allow-Origin": "*",
          "Content-Type": "application/json; charset=utf-8",
          "Cache-Control": "no-store",
          "X-Request-ID": requestId
        }
      });
    }
    if (v1Response) {
      console.log(JSON.stringify({
        event: "request_completed",
        requestId,
        method,
        path: url.pathname,
        status: v1Response.status,
        durationMs: Date.now() - startedAt
      }));
      return v1Response;
    }
    if (url.pathname === "/.well-known/assetlinks.json" && (method === "GET" || method === "HEAD")) {
      return new Response(JSON.stringify(ASSET_LINKS), {
        status: 200,
        headers: {
          "Content-Type": "application/json; charset=utf-8",
          "Cache-Control": "public, max-age=86400"
        }
      });
    }
    const corsHeaders = {
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Methods": "GET, HEAD, POST, PUT, DELETE, OPTIONS",
      "Access-Control-Allow-Headers": "Content-Type, User-Agent, X-RETR0-KEY, X-Installation-ID, X-Timestamp, X-Nonce, X-Signature, Idempotency-Key, Accept, Accept-Encoding, Range, If-Range, If-None-Match",
      "Access-Control-Expose-Headers": "Content-Length, Content-Range, Accept-Ranges, ETag, Last-Modified, Cache-Control, X-Request-ID",
      "Access-Control-Max-Age": "86400",
      "X-Request-ID": requestId
    };
    if (method === "OPTIONS") {
      return new Response(null, {
        status: 204,
        headers: corsHeaders
      });
    }
    const textResponse = /* @__PURE__ */ __name2((message, status = 200, extraHeaders = {}) => new Response(message, {
      status,
      headers: {
        ...corsHeaders,
        "Content-Type": "text/plain; charset=utf-8",
        ...extraHeaders
      }
    }), "textResponse");
    const jsonResponse = /* @__PURE__ */ __name2((value, status = 200, extraHeaders = {}) => new Response(JSON.stringify(value), {
      status,
      headers: {
        ...corsHeaders,
        "Content-Type": "application/json; charset=utf-8",
        "Cache-Control": "no-store",
        ...extraHeaders
      }
    }), "jsonResponse");
    const normalizeKey = /* @__PURE__ */ __name2((rawKey) => {
      if (!rawKey) return null;
      let key;
      try {
        key = decodeURIComponent(rawKey);
      } catch {
        return null;
      }
      key = key.replace(/^\/+/, "");
      const segments = key.split("/");
      if (!key || key.includes("\0") || key.includes("\\") || segments.some(
        (segment) => !segment || segment === "." || segment === ".."
      )) {
        return null;
      }
      return key;
    }, "normalizeKey");
    const parseSingleRange = /* @__PURE__ */ __name2((header, objectSize) => {
      if (!header) return null;
      const match = /^bytes=(\d*)-(\d*)$/.exec(
        header.trim()
      );
      if (!match) {
        return { invalid: true };
      }
      const startText = match[1];
      const endText = match[2];
      if (!startText && !endText) {
        return { invalid: true };
      }
      if (!startText) {
        const suffixLength = Number(endText);
        if (!Number.isSafeInteger(suffixLength) || suffixLength <= 0) {
          return { invalid: true };
        }
        const length = Math.min(
          suffixLength,
          objectSize
        );
        return {
          offset: objectSize - length,
          length
        };
      }
      const offset = Number(startText);
      if (!Number.isSafeInteger(offset) || offset < 0 || offset >= objectSize) {
        return { unsatisfiable: true };
      }
      let end = objectSize - 1;
      if (endText) {
        end = Number(endText);
        if (!Number.isSafeInteger(end) || end < offset) {
          return { invalid: true };
        }
        end = Math.min(end, objectSize - 1);
      }
      return {
        offset,
        length: end - offset + 1
      };
    }, "parseSingleRange");
    const etagMatches = /* @__PURE__ */ __name2((requestValue, etag) => {
      if (!requestValue || !etag) return false;
      if (requestValue.trim() === "*") return true;
      return requestValue.split(",").map((value) => value.trim()).includes(etag);
    }, "etagMatches");
    const serveR2Object = /* @__PURE__ */ __name2(async ({
      bucket,
      key,
      immutable = false,
      countDownload = false
    }) => {
      if (!bucket) {
        return textResponse(
          "R2 bucket binding is unavailable",
          500
        );
      }
      const metadata = await bucket.head(key);
      if (!metadata) {
        return textResponse(
          `R2 object not found: ${key}`,
          404
        );
      }
      const cacheControl = immutable ? "public, max-age=31536000, immutable" : "public, max-age=86400";
      const ifNoneMatch = request.headers.get("If-None-Match");
      if (!request.headers.has("Range") && etagMatches(ifNoneMatch, metadata.httpEtag)) {
        return new Response(null, {
          status: 304,
          headers: {
            ...corsHeaders,
            ETag: metadata.httpEtag,
            "Accept-Ranges": "bytes",
            "Cache-Control": cacheControl
          }
        });
      }
      let requestedRange = parseSingleRange(
        request.headers.get("Range"),
        metadata.size
      );
      const ifRange = request.headers.get("If-Range");
      if (requestedRange && ifRange && ifRange !== metadata.httpEtag) {
        requestedRange = null;
      }
      if (requestedRange?.invalid || requestedRange?.unsatisfiable) {
        return new Response(null, {
          status: 416,
          headers: {
            ...corsHeaders,
            "Content-Range": `bytes */${metadata.size}`,
            "Accept-Ranges": "bytes",
            "Cache-Control": "no-store"
          }
        });
      }
      const object = requestedRange ? await bucket.get(key, {
        range: {
          offset: requestedRange.offset,
          length: requestedRange.length
        }
      }) : await bucket.get(key);
      if (!object) {
        return textResponse(
          `R2 object disappeared during request: ${key}`,
          404
        );
      }
      const headers = new Headers(corsHeaders);
      object.writeHttpMetadata(headers);
      headers.set(
        "Content-Type",
        object.httpMetadata?.contentType || "application/octet-stream"
      );
      headers.delete("Content-Encoding");
      headers.set("Accept-Ranges", "bytes");
      headers.set("ETag", object.httpEtag);
      headers.set("Cache-Control", cacheControl);
      if (requestedRange) {
        const start = requestedRange.offset;
        const end = requestedRange.offset + requestedRange.length - 1;
        headers.set(
          "Content-Range",
          `bytes ${start}-${end}/${metadata.size}`
        );
        headers.set(
          "Content-Length",
          String(requestedRange.length)
        );
      } else {
        headers.set(
          "Content-Length",
          String(metadata.size)
        );
      }
      if (countDownload && method === "GET" && !requestedRange && env.SOCIAL_KV) {
        ctx.waitUntil(
          incrementCounter(
            env.SOCIAL_KV,
            `dl:${key}`
          )
        );
      }
      return new Response(
        method === "HEAD" ? null : object.body,
        {
          status: requestedRange ? 206 : 200,
          headers
        }
      );
    }, "serveR2Object");
    const authenticated = !env.AUTH_KEY || request.headers.get("X-RETR0-KEY") === env.AUTH_KEY;
    if (url.pathname === "/manifest" && (method === "GET" || method === "HEAD")) {
      if (!env.ASSET_KV) {
        return textResponse(
          "ASSET_KV binding is unavailable",
          500
        );
      }
      const manifest = await env.ASSET_KV.get(
        "RELEASE_MANIFEST"
      );
      if (!manifest) {
        return textResponse(
          "RELEASE_MANIFEST not found in ASSET_KV",
          404
        );
      }
      return new Response(
        method === "HEAD" ? null : manifest,
        {
          status: 200,
          headers: {
            ...corsHeaders,
            "Content-Type": "application/json; charset=utf-8",
            "Cache-Control": "no-cache, no-store, must-revalidate"
          }
        }
      );
    }
    if (method === "GET" || method === "HEAD") {
      const directKey = normalizeKey(
        url.pathname
      );
      if (directKey && RELEASE_PART_PATTERN.test(directKey)) {
        return serveR2Object({
          bucket: env.ASSET_BUCKET,
          key: directKey,
          immutable: true
        });
      }
    }
    if (url.pathname === "/skins" && method === "GET") {
      if (!env.SKINS_DB) {
        return jsonResponse(
          { error: "SKINS_DB unavailable" },
          503
        );
      }
      const query = cleanMetadata(
        url.searchParams.get("query"),
        80
      );
      const car = cleanMetadata(
        url.searchParams.get("car"),
        100
      );
      const format = cleanMetadata(
        url.searchParams.get("format"),
        24
      ).toLowerCase();
      const sort = url.searchParams.get("sort") || "newest";
      const limit = clampInteger(
        url.searchParams.get("limit"),
        1,
        50,
        20
      );
      const offset = clampInteger(
        url.searchParams.get("offset"),
        0,
        1e4,
        0
      );
      const conditions = [
        "s.status = 'approved'"
      ];
      const values = [];
      if (query) {
        conditions.push(
          "(s.title LIKE ? ESCAPE '\\' OR s.creator_name LIKE ? ESCAPE '\\' OR s.description LIKE ? ESCAPE '\\' OR s.car_directory LIKE ? ESCAPE '\\')"
        );
        const likeValue = `%${escapeLike2(query)}%`;
        values.push(
          likeValue,
          likeValue,
          likeValue,
          likeValue
        );
      }
      if (car) {
        conditions.push("s.car_directory = ?");
        values.push(car);
      }
      if (format) {
        conditions.push("s.texture_format = ?");
        values.push(format);
      }
      const orderBy = sort === "likes" ? "likes DESC, s.created_at DESC" : sort === "downloads" ? "downloads DESC, s.created_at DESC" : "s.created_at DESC";
      const sql = `
        SELECT
          s.id,
          s.title,
          s.creator_name,
          s.description,
          s.car_directory,
          s.slot_key,
          s.texture_format,
          s.source_filename,
          s.binary_size,
          s.binary_sha256,
          s.cover_key,
          s.screenshot_count,
          s.game_versions,
          s.created_at,
          s.updated_at,
          (
            SELECT COUNT(*)
            FROM skin_likes l
            WHERE l.skin_id = s.id
          ) AS likes,
          (
            SELECT COUNT(*)
            FROM skin_downloads d
            WHERE d.skin_id = s.id
          ) AS downloads
        FROM skins s
        WHERE ${conditions.join(" AND ")}
        ORDER BY ${orderBy}
        LIMIT ? OFFSET ?
      `;
      values.push(limit, offset);
      const result = await bindStatement(
        env.SKINS_DB.prepare(sql),
        values
      ).all();
      const items = [];
      for (const row of result.results || []) {
        const object = await env.COMMUNITY_BUCKET.head(row.cover_key || row.binary_key);
        if (!object) {
          await env.SKINS_DB.prepare("UPDATE skins SET deleted_at = CURRENT_TIMESTAMP, status = 'hidden' WHERE id = ?").bind(row.id).run();
          continue;
        }
        items.push(skinResponse(row, url.origin));
      }
      return jsonResponse({
        items,
        limit,
        offset
      });
    }
    const skinRoute = matchSkinRoute2(
      url.pathname
    );
    if (skinRoute && (method === "GET" || method === "HEAD")) {
      if (!env.SKINS_DB || !env.COMMUNITY_BUCKET) {
        return jsonResponse(
          { error: "Skin bindings unavailable" },
          503
        );
      }
      const skin = await env.SKINS_DB.prepare(`
          SELECT
            s.*,
            (
              SELECT COUNT(*)
              FROM skin_likes l
              WHERE l.skin_id = s.id
            ) AS likes,
            (
              SELECT COUNT(*)
              FROM skin_downloads d
              WHERE d.skin_id = s.id
            ) AS downloads
          FROM skins s
          WHERE s.id = ?
            AND s.status = 'approved'
          LIMIT 1
        `).bind(skinRoute.id).first();
      if (!skin) {
        return jsonResponse(
          { error: "Skin not found" },
          404
        );
      }
      const binary = await env.COMMUNITY_BUCKET.head(skin.binary_key);
      if (!binary) {
        await env.SKINS_DB.prepare("UPDATE skins SET deleted_at = CURRENT_TIMESTAMP, status = 'hidden' WHERE id = ?").bind(skin.id).run();
        return jsonResponse(
          { error: "Skin no longer available" },
          404
        );
      }
      if (skinRoute.action === "details") {
        const screenshots = await env.SKINS_DB.prepare(`
            SELECT
              position,
              object_key,
              caption
            FROM skin_screenshots
            WHERE skin_id = ?
            ORDER BY position ASC
          `).bind(skinRoute.id).all();
        return jsonResponse({
          ...skinResponse(skin, url.origin),
          screenshots: (screenshots.results || []).map(
            (screenshot) => ({
              position: screenshot.position,
              caption: screenshot.caption,
              url: `${url.origin}/skins/${skin.id}/screenshots/${screenshot.position}`
            })
          )
        });
      }
      if (skinRoute.action === "binary") {
        if (method === "GET") {
          ctx.waitUntil(
            env.SKINS_DB.prepare(`
                INSERT INTO skin_downloads (
                  skin_id,
                  user_id
                ) VALUES (?, NULL)
              `).bind(skin.id).run()
          );
        }
        return serveR2Object({
          bucket: env.COMMUNITY_BUCKET,
          key: skin.binary_key,
          immutable: true
        });
      }
      if (skinRoute.action === "cover") {
        if (!skin.cover_key) {
          return textResponse(
            "Cover not found",
            404
          );
        }
        return serveR2Object({
          bucket: env.COMMUNITY_BUCKET,
          key: skin.cover_key,
          immutable: true
        });
      }
      if (skinRoute.action === "screenshot") {
        const screenshot = await env.SKINS_DB.prepare(`
              SELECT object_key
              FROM skin_screenshots
              WHERE skin_id = ?
                AND position = ?
              LIMIT 1
            `).bind(
          skin.id,
          skinRoute.position
        ).first();
        if (!screenshot) {
          return textResponse(
            "Screenshot not found",
            404
          );
        }
        return serveR2Object({
          bucket: env.COMMUNITY_BUCKET,
          key: screenshot.object_key,
          immutable: true
        });
      }
    }
    if (url.pathname === "/" && (method === "GET" || method === "HEAD")) {
      const body = {
        worker: "Online",
        engine: "v3.0-Car-Skins",
        hostname: url.hostname,
        assetBucket: env.ASSET_BUCKET ? "Ready" : "Missing",
        communityBucket: env.COMMUNITY_BUCKET ? "Ready" : "Missing",
        skinsDb: env.SKINS_DB ? "Ready" : "Missing",
        assetKv: env.ASSET_KV ? "Ready" : "Missing",
        socialKv: env.SOCIAL_KV ? "Ready" : "Missing",
        ai: env.AI ? "Ready" : "Missing",
        authSecret: env.AUTH_KEY ? "Ready" : "Missing"
      };
      return new Response(
        method === "HEAD" ? null : JSON.stringify(body),
        {
          status: 200,
          headers: {
            ...corsHeaders,
            "Content-Type": "application/json; charset=utf-8",
            "Cache-Control": "no-store"
          }
        }
      );
    }
    if (!authenticated) {
      return textResponse("Unauthorized", 401);
    }
    try {
      if (url.pathname === "/skins" && method === "POST") {
        if (!env.SKINS_DB || !env.COMMUNITY_BUCKET) {
          return jsonResponse(
            { error: "Skin bindings unavailable" },
            503
          );
        }
        const body = await readJson(request);
        if (!body) {
          return jsonResponse(
            { error: "Invalid JSON body" },
            400
          );
        }
        const metadata = validateSkinMetadata(body);
        if (!metadata.valid) {
          return jsonResponse(
            { error: metadata.error },
            400
          );
        }
        const duplicate = await env.SKINS_DB.prepare(`
              SELECT id, binary_key
              FROM skins
              WHERE binary_sha256 = ?
                AND car_directory = ?
                AND slot_key = ?
                AND texture_format = ?
                AND deleted_at IS NULL
                AND status NOT IN (
                  'rejected',
                  'hidden'
                )
              LIMIT 1
            `).bind(
          metadata.binarySha256,
          metadata.carDirectory,
          metadata.slotKey,
          metadata.textureFormat
        ).first();
        if (duplicate) {
          const object = await env.COMMUNITY_BUCKET.head(duplicate.binary_key);
          if (!object) {
            await env.SKINS_DB.prepare("UPDATE skins SET deleted_at = CURRENT_TIMESTAMP, status = 'hidden' WHERE id = ?").bind(duplicate.id).run();
          } else {
            return jsonResponse(
              {
                error: "An identical skin already exists for this target",
                duplicateId: duplicate.id
              },
              409
            );
          }
        }
        const id = crypto.randomUUID();
        const binaryKey = `skins/${id}/skin.dds.z`;
        await env.SKINS_DB.prepare(`
            INSERT INTO skins (
              id,
              owner_id,
              title,
              creator_name,
              description,
              car_directory,
              slot_key,
              texture_format,
              source_filename,
              binary_key,
              binary_size,
              binary_sha256,
              cover_key,
              screenshot_count,
              status,
              game_versions,
              created_at,
              updated_at
            ) VALUES (
              ?,
              ?,
              ?,
              ?,
              ?,
              ?,
              ?,
              ?,
              ?,
              ?,
              ?,
              ?,
              NULL,
              0,
              'draft',
              ?,
              CURRENT_TIMESTAMP,
              CURRENT_TIMESTAMP
            )
          `).bind(
          id,
          "shared-key-test-user",
          metadata.title,
          metadata.creatorName,
          metadata.description,
          metadata.carDirectory,
          metadata.slotKey,
          metadata.textureFormat,
          metadata.sourceFilename,
          binaryKey,
          metadata.binarySize,
          metadata.binarySha256,
          metadata.gameVersions
        ).run();
        return jsonResponse(
          {
            success: true,
            id,
            status: "draft",
            binaryUploadUrl: `${url.origin}/skins/${id}/binary`,
            coverUploadUrl: `${url.origin}/skins/${id}/cover`,
            publishUrl: `${url.origin}/skins/${id}/publish`
          },
          201
        );
      }
      const protectedSkinRoute = matchProtectedSkinRoute(url.pathname);
      if (protectedSkinRoute?.action === "binary" && method === "PUT") {
        if (!env.SKINS_DB || !env.COMMUNITY_BUCKET) {
          return jsonResponse(
            { error: "Skin bindings unavailable" },
            503
          );
        }
        const skin = await getEditableSkin(
          env.SKINS_DB,
          protectedSkinRoute.id
        );
        if (!skin) {
          return jsonResponse(
            { error: "Skin draft not found" },
            404
          );
        }
        const contentLength = getContentLength(request);
        if (contentLength === null || contentLength <= 0) {
          return jsonResponse(
            {
              error: "A valid Content-Length is required"
            },
            411
          );
        }
        if (contentLength > MAX_SKIN_BYTES2 || contentLength !== skin.binary_size) {
          return jsonResponse(
            {
              error: "Binary size does not match metadata or exceeds 5 MiB"
            },
            413
          );
        }
        if (!request.body) {
          return jsonResponse(
            { error: "Missing skin body" },
            400
          );
        }
        await env.COMMUNITY_BUCKET.put(
          skin.binary_key,
          request.body,
          {
            httpMetadata: {
              contentType: "application/octet-stream"
            },
            customMetadata: {
              skinId: skin.id,
              expectedSha256: skin.binary_sha256,
              carDirectory: skin.car_directory,
              slotKey: skin.slot_key,
              textureFormat: skin.texture_format
            }
          }
        );
        const stored = await env.COMMUNITY_BUCKET.head(
          skin.binary_key
        );
        if (!stored || stored.size !== skin.binary_size) {
          await env.COMMUNITY_BUCKET.delete(
            skin.binary_key
          );
          return jsonResponse(
            {
              error: "Stored binary size verification failed"
            },
            500
          );
        }
        await touchSkin(
          env.SKINS_DB,
          skin.id,
          "uploading"
        );
        return jsonResponse({
          success: true,
          size: stored.size
        });
      }
      if (protectedSkinRoute?.action === "cover" && method === "PUT") {
        if (!env.SKINS_DB || !env.COMMUNITY_BUCKET) {
          return jsonResponse(
            { error: "Skin bindings unavailable" },
            503
          );
        }
        const skin = await getEditableSkin(
          env.SKINS_DB,
          protectedSkinRoute.id
        );
        if (!skin) {
          return jsonResponse(
            { error: "Skin draft not found" },
            404
          );
        }
        const imageValidation = validateImageUpload(
          request,
          MAX_COVER_BYTES2
        );
        if (!imageValidation.valid) {
          return jsonResponse(
            { error: imageValidation.error },
            imageValidation.status
          );
        }
        const extension = extensionForImageType(
          imageValidation.contentType
        );
        const coverKey = `skins/${skin.id}/cover.${extension}`;
        await deleteOldCover(
          env.COMMUNITY_BUCKET,
          skin.cover_key,
          coverKey
        );
        await env.COMMUNITY_BUCKET.put(
          coverKey,
          request.body,
          {
            httpMetadata: {
              contentType: imageValidation.contentType
            },
            customMetadata: {
              skinId: skin.id,
              role: "cover"
            }
          }
        );
        await env.SKINS_DB.prepare(`
            UPDATE skins
            SET
              cover_key = ?,
              status = 'uploading',
              updated_at = CURRENT_TIMESTAMP
            WHERE id = ?
          `).bind(coverKey, skin.id).run();
        return jsonResponse({
          success: true,
          coverKey
        });
      }
      if (protectedSkinRoute?.action === "screenshot" && method === "PUT") {
        if (!env.SKINS_DB || !env.COMMUNITY_BUCKET) {
          return jsonResponse(
            { error: "Skin bindings unavailable" },
            503
          );
        }
        const skin = await getEditableSkin(
          env.SKINS_DB,
          protectedSkinRoute.id
        );
        if (!skin) {
          return jsonResponse(
            { error: "Skin draft not found" },
            404
          );
        }
        const position = protectedSkinRoute.position;
        if (position < 1 || position > MAX_SCREENSHOTS) {
          return jsonResponse(
            {
              error: `Screenshot position must be 1-${MAX_SCREENSHOTS}`
            },
            400
          );
        }
        const imageValidation = validateImageUpload(
          request,
          MAX_SCREENSHOT_BYTES2
        );
        if (!imageValidation.valid) {
          return jsonResponse(
            { error: imageValidation.error },
            imageValidation.status
          );
        }
        const extension = extensionForImageType(
          imageValidation.contentType
        );
        const existing = await env.SKINS_DB.prepare(`
              SELECT object_key
              FROM skin_screenshots
              WHERE skin_id = ?
                AND position = ?
              LIMIT 1
            `).bind(skin.id, position).first();
        const screenshotKey = `skins/${skin.id}/screenshots/${String(
          position
        ).padStart(2, "0")}.${extension}`;
        if (existing?.object_key && existing.object_key !== screenshotKey) {
          await env.COMMUNITY_BUCKET.delete(
            existing.object_key
          );
        }
        await env.COMMUNITY_BUCKET.put(
          screenshotKey,
          request.body,
          {
            httpMetadata: {
              contentType: imageValidation.contentType
            },
            customMetadata: {
              skinId: skin.id,
              role: "screenshot",
              position: String(position)
            }
          }
        );
        const caption = cleanMetadata(
          url.searchParams.get("caption"),
          200
        );
        await env.SKINS_DB.prepare(`
            INSERT INTO skin_screenshots (
              skin_id,
              position,
              object_key,
              caption
            ) VALUES (?, ?, ?, ?)
            ON CONFLICT (
              skin_id,
              position
            ) DO UPDATE SET
              object_key = excluded.object_key,
              caption = excluded.caption
          `).bind(
          skin.id,
          position,
          screenshotKey,
          caption
        ).run();
        await refreshScreenshotCount(
          env.SKINS_DB,
          skin.id
        );
        await touchSkin(
          env.SKINS_DB,
          skin.id,
          "uploading"
        );
        return jsonResponse({
          success: true,
          position,
          screenshotKey
        });
      }
      if (protectedSkinRoute?.action === "publish" && method === "POST") {
        if (!env.SKINS_DB || !env.COMMUNITY_BUCKET) {
          return jsonResponse(
            { error: "Skin bindings unavailable" },
            503
          );
        }
        const skin = await getEditableSkin(
          env.SKINS_DB,
          protectedSkinRoute.id
        );
        if (!skin) {
          return jsonResponse(
            {
              error: "Skin draft not found or already published"
            },
            404
          );
        }
        if (!skin.cover_key) {
          return jsonResponse(
            {
              error: "A cover image is required before publishing"
            },
            400
          );
        }
        const [binary, cover] = await Promise.all([
          env.COMMUNITY_BUCKET.head(
            skin.binary_key
          ),
          env.COMMUNITY_BUCKET.head(
            skin.cover_key
          )
        ]);
        if (!binary) {
          return jsonResponse(
            {
              error: "Skin binary has not been uploaded"
            },
            400
          );
        }
        if (binary.size !== skin.binary_size || binary.size > MAX_SKIN_BYTES2) {
          return jsonResponse(
            {
              error: "Skin binary size verification failed"
            },
            400
          );
        }
        if (!cover) {
          return jsonResponse(
            {
              error: "Cover image has not been uploaded"
            },
            400
          );
        }
        if (cover.size > MAX_COVER_BYTES2) {
          return jsonResponse(
            {
              error: "Cover image exceeds 500 KiB"
            },
            400
          );
        }
        const screenshots = await env.SKINS_DB.prepare(`
              SELECT
                position,
                object_key
              FROM skin_screenshots
              WHERE skin_id = ?
              ORDER BY position ASC
            `).bind(skin.id).all();
        if ((screenshots.results || []).length > MAX_SCREENSHOTS) {
          return jsonResponse(
            {
              error: "Too many screenshots"
            },
            400
          );
        }
        for (const screenshot of screenshots.results || []) {
          const object = await env.COMMUNITY_BUCKET.head(
            screenshot.object_key
          );
          if (!object || object.size > MAX_SCREENSHOT_BYTES2) {
            return jsonResponse(
              {
                error: `Screenshot ${screenshot.position} is missing or too large`
              },
              400
            );
          }
        }
        await env.SKINS_DB.prepare(`
            UPDATE skins
            SET
              status = 'approved',
              screenshot_count = ?,
              updated_at = CURRENT_TIMESTAMP
            WHERE id = ?
          `).bind(
          (screenshots.results || []).length,
          skin.id
        ).run();
        return jsonResponse({
          success: true,
          id: skin.id,
          status: "approved",
          detailsUrl: `${url.origin}/skins/${skin.id}`
        });
      }
      if (protectedSkinRoute?.action === "like" && method === "POST") {
        if (!env.SKINS_DB) {
          return jsonResponse(
            { error: "SKINS_DB unavailable" },
            503
          );
        }
        const approved = await env.SKINS_DB.prepare(`
              SELECT id
              FROM skins
              WHERE id = ?
                AND status = 'approved'
              LIMIT 1
            `).bind(protectedSkinRoute.id).first();
        if (!approved) {
          return jsonResponse(
            { error: "Skin not found" },
            404
          );
        }
        await env.SKINS_DB.prepare(`
            INSERT OR IGNORE INTO skin_likes (
              skin_id,
              user_id
            ) VALUES (?, ?)
          `).bind(
          protectedSkinRoute.id,
          "shared-key-test-user"
        ).run();
        const count = await env.SKINS_DB.prepare(`
              SELECT COUNT(*) AS count
              FROM skin_likes
              WHERE skin_id = ?
            `).bind(protectedSkinRoute.id).first();
        return jsonResponse({
          success: true,
          likes: Number(count?.count || 0)
        });
      }
      if (protectedSkinRoute?.action === "report" && method === "POST") {
        if (!env.SKINS_DB) {
          return jsonResponse(
            { error: "SKINS_DB unavailable" },
            503
          );
        }
        const body = await readJson(request);
        if (!body) {
          return jsonResponse(
            { error: "Invalid JSON body" },
            400
          );
        }
        const allowedReasons = /* @__PURE__ */ new Set([
          "inappropriate",
          "copyright",
          "misleading",
          "broken",
          "spam",
          "other"
        ]);
        const reason = String(
          body.reason || ""
        ).trim().toLowerCase();
        const details = cleanMetadata(
          body.details,
          1e3
        );
        if (!allowedReasons.has(reason)) {
          return jsonResponse(
            { error: "Invalid report reason" },
            400
          );
        }
        const approved = await env.SKINS_DB.prepare(`
              SELECT id
              FROM skins
              WHERE id = ?
                AND status = 'approved'
              LIMIT 1
            `).bind(protectedSkinRoute.id).first();
        if (!approved) {
          return jsonResponse(
            { error: "Skin not found" },
            404
          );
        }
        await env.SKINS_DB.prepare(`
            INSERT INTO skin_reports (
              skin_id,
              reporter_id,
              reason,
              details,
              status
            ) VALUES (?, ?, ?, ?, 'open')
          `).bind(
          protectedSkinRoute.id,
          "shared-key-test-user",
          reason,
          details
        ).run();
        return jsonResponse(
          {
            success: true,
            message: "Report submitted"
          },
          201
        );
      }
      if (protectedSkinRoute?.action === "details" && method === "DELETE") {
        if (!env.SKINS_DB || !env.COMMUNITY_BUCKET) {
          return jsonResponse(
            { error: "Skin bindings unavailable" },
            503
          );
        }
        const skin = await env.SKINS_DB.prepare(`
              SELECT *
              FROM skins
              WHERE id = ?
              LIMIT 1
            `).bind(protectedSkinRoute.id).first();
        if (!skin) {
          return jsonResponse(
            { error: "Skin not found" },
            404
          );
        }
        await deleteR2Prefix(
          env.COMMUNITY_BUCKET,
          `skins/${skin.id}/`
        );
        await env.SKINS_DB.batch([
          env.SKINS_DB.prepare(
            "DELETE FROM skin_likes WHERE skin_id = ?"
          ).bind(skin.id),
          env.SKINS_DB.prepare(
            "DELETE FROM skin_downloads WHERE skin_id = ?"
          ).bind(skin.id),
          env.SKINS_DB.prepare(
            "DELETE FROM skin_reports WHERE skin_id = ?"
          ).bind(skin.id),
          env.SKINS_DB.prepare(
            "DELETE FROM skin_screenshots WHERE skin_id = ?"
          ).bind(skin.id),
          env.SKINS_DB.prepare(
            "DELETE FROM skins WHERE id = ?"
          ).bind(skin.id)
        ]);
        return jsonResponse({
          success: true
        });
      }
      if (url.pathname === "/troubleshoot" && method === "POST") {
        if (!env.AI) {
          return jsonResponse(
            { error: "AI binding unavailable" },
            503
          );
        }
        const requestBody = await request.json();
        const error = String(
          requestBody.error || ""
        ).trim();
        const device = String(
          requestBody.device || ""
        ).trim();
        const type = String(
          requestBody.type || ""
        ).trim();
        if (!error || error.length > 4e3) {
          return jsonResponse(
            { error: "Invalid question" },
            400
          );
        }
        const systemPrompt = type === "general" ? "You are the Project RETR0 Modding Expert. Help with RR3 modding. Be friendly and use fewer than 60 words." : "You are the Project RETR0 Troubleshooting Agent. Provide a brief two-step fix for RR3 errors.";
        const aiResponse = await env.AI.run(
          "@cf/meta/llama-3.2-3b-instruct",
          {
            messages: [
              {
                role: "system",
                content: systemPrompt
              },
              {
                role: "user",
                content: `Question: ${error}
Device: ${device}`
              }
            ]
          }
        );
        return jsonResponse({
          response: aiResponse.response || aiResponse.text || "AI is resting. Please try again."
        });
      }
      if (url.pathname === "/like" && method === "POST") {
        if (!env.SOCIAL_KV) {
          return jsonResponse(
            { error: "SOCIAL_KV unavailable" },
            503
          );
        }
        const id = normalizeKey(
          url.searchParams.get("id")
        );
        if (!isCommunityKey(id)) {
          return textResponse(
            "Invalid ID",
            400
          );
        }
        const likes = await incrementCounter(
          env.SOCIAL_KV,
          `like:${id}`
        );
        return jsonResponse({
          success: true,
          likes
        });
      }
      if (url.pathname === "/download" && (method === "GET" || method === "HEAD")) {
        const id = normalizeKey(
          url.searchParams.get("id")
        );
        if (!id) {
          return textResponse(
            "Invalid ID",
            400
          );
        }
        const communityObject = isCommunityKey(id);
        return serveR2Object({
          bucket: communityObject ? env.COMMUNITY_BUCKET : env.ASSET_BUCKET,
          key: id,
          immutable: !communityObject,
          countDownload: communityObject
        });
      }
      if ((url.pathname === "/list" || url.pathname === "/music/list") && method === "GET") {
        if (!env.COMMUNITY_BUCKET || !env.SOCIAL_KV) {
          return jsonResponse(
            {
              error: "Community bindings unavailable"
            },
            503
          );
        }
        const prefix = url.pathname === "/list" ? "saves/" : "music/";
        const listed = await env.COMMUNITY_BUCKET.list({
          prefix,
          include: ["customMetadata"]
        });
        const sortType = url.searchParams.get("sort") || "newest";
        const items = await Promise.all(
          listed.objects.map(async (object) => {
            const likes = await readCounter(
              env.SOCIAL_KV,
              `like:${object.key}`
            );
            const downloads = await readCounter(
              env.SOCIAL_KV,
              `dl:${object.key}`
            );
            if (prefix === "saves/") {
              return {
                id: object.key,
                custom_name: object.customMetadata?.savename || "User Save",
                uploader_name: object.customMetadata?.uploadername || "Anonymous",
                player_level: parseInteger(
                  object.customMetadata?.level
                ),
                player_gold: parseInteger(
                  object.customMetadata?.gold
                ),
                player_rs: parseInteger(
                  object.customMetadata?.rs
                ),
                player_ms: parseInteger(
                  object.customMetadata?.ms
                ),
                file_size: object.size,
                created_at: object.uploaded,
                likes,
                downloads
              };
            }
            return {
              id: object.key,
              custom_name: object.customMetadata?.musicname || "Shared Track",
              uploader_name: object.customMetadata?.uploadername || "Anonymous",
              target_track: object.customMetadata?.targettrack || "Garage_Music.mp3",
              file_size: object.size,
              created_at: object.uploaded,
              likes,
              downloads
            };
          })
        );
        if (sortType === "popular") {
          items.sort(
            (a, b) => b.downloads - a.downloads
          );
        } else if (sortType === "likes") {
          items.sort(
            (a, b) => b.likes - a.likes
          );
        } else {
          items.sort(
            (a, b) => new Date(
              b.created_at
            ).getTime() - new Date(
              a.created_at
            ).getTime()
          );
        }
        return jsonResponse(items);
      }
      if (method === "POST" && (url.pathname === "/upload" || url.pathname === "/music/upload")) {
        if (!env.COMMUNITY_BUCKET) {
          return jsonResponse(
            {
              error: "COMMUNITY_BUCKET unavailable"
            },
            503
          );
        }
        const contentLength = getContentLength(request);
        if (contentLength !== null && contentLength > MAX_UPLOAD_BYTES) {
          return textResponse(
            "Upload is too large",
            413
          );
        }
        if (!request.body) {
          return textResponse(
            "Missing upload body",
            400
          );
        }
        const isMusic = url.pathname === "/music/upload";
        const id = isMusic ? `music/${Date.now()}.mp3` : `saves/${Date.now()}.dat`;
        const metadata = isMusic ? {
          musicname: cleanMetadata(
            url.searchParams.get("name")
          ),
          uploadername: cleanMetadata(
            url.searchParams.get("user")
          ),
          targettrack: cleanMetadata(
            url.searchParams.get("track")
          )
        } : {
          savename: cleanMetadata(
            url.searchParams.get("name")
          ),
          uploadername: cleanMetadata(
            url.searchParams.get("user")
          ),
          level: cleanMetadata(
            url.searchParams.get("level")
          ),
          gold: cleanMetadata(
            url.searchParams.get("gold")
          ),
          rs: cleanMetadata(
            url.searchParams.get("rs")
          ),
          ms: cleanMetadata(
            url.searchParams.get("ms")
          )
        };
        await env.COMMUNITY_BUCKET.put(
          id,
          request.body,
          {
            customMetadata: metadata
          }
        );
        return jsonResponse({
          success: true,
          id
        });
      }
      return textResponse("Not found", 404);
    } catch (error) {
      console.error(JSON.stringify({
        event: "request_failed",
        requestId,
        method,
        path: url.pathname,
        error: error instanceof Error ? error.message : String(error)
      }));
      const message = error instanceof Error ? error.message : String(error);
      if (message.toLowerCase().includes("limit") || message.toLowerCase().includes("quota")) {
        return jsonResponse(
          {
            response: "Daily free limit reached. Please try again later."
          },
          429
        );
      }
      return jsonResponse(
        {
          error: "Internal Worker error"
        },
        500
      );
    }
  }
};
function matchSkinRoute2(pathname) {
  const details = /^\/skins\/([0-9a-f-]{36})$/i.exec(
    pathname
  );
  if (details && SKIN_ID_PATTERN.test(details[1])) {
    return {
      id: details[1],
      action: "details"
    };
  }
  const binary = /^\/skins\/([0-9a-f-]{36})\/binary$/i.exec(
    pathname
  );
  if (binary && SKIN_ID_PATTERN.test(binary[1])) {
    return {
      id: binary[1],
      action: "binary"
    };
  }
  const cover = /^\/skins\/([0-9a-f-]{36})\/cover$/i.exec(
    pathname
  );
  if (cover && SKIN_ID_PATTERN.test(cover[1])) {
    return {
      id: cover[1],
      action: "cover"
    };
  }
  const screenshot = /^\/skins\/([0-9a-f-]{36})\/screenshots\/([1-4])$/i.exec(
    pathname
  );
  if (screenshot && SKIN_ID_PATTERN.test(screenshot[1])) {
    return {
      id: screenshot[1],
      action: "screenshot",
      position: Number(screenshot[2])
    };
  }
  return null;
}
__name(matchSkinRoute2, "matchSkinRoute");
__name2(matchSkinRoute2, "matchSkinRoute");
function matchProtectedSkinRoute(pathname) {
  const publicRoute = matchSkinRoute2(pathname);
  if (publicRoute) {
    return publicRoute;
  }
  const action = /^\/skins\/([0-9a-f-]{36})\/(publish|like|report)$/i.exec(
    pathname
  );
  if (action && SKIN_ID_PATTERN.test(action[1])) {
    return {
      id: action[1],
      action: action[2].toLowerCase()
    };
  }
  return null;
}
__name(matchProtectedSkinRoute, "matchProtectedSkinRoute");
__name2(matchProtectedSkinRoute, "matchProtectedSkinRoute");
function validateSkinMetadata(body) {
  const title = cleanMetadata(
    body.title,
    80
  );
  const creatorName = cleanMetadata(
    body.creatorName,
    50
  );
  const description = cleanMetadata(
    body.description,
    1500
  );
  const carDirectory = cleanMetadata(
    body.carDirectory,
    100
  ).toLowerCase();
  const slotKey = cleanMetadata(
    body.slotKey,
    100
  ).toLowerCase();
  const textureFormat = cleanMetadata(
    body.textureFormat,
    24
  ).toLowerCase();
  const sourceFilename = cleanMetadata(
    body.sourceFilename,
    180
  );
  const binarySize = Number(
    body.binarySize
  );
  const binarySha256 = String(
    body.binarySha256 || ""
  ).trim().toLowerCase();
  const gameVersions = cleanMetadata(
    body.gameVersions,
    100
  );
  if (!title) {
    return {
      valid: false,
      error: "Skin title is required"
    };
  }
  if (!creatorName) {
    return {
      valid: false,
      error: "Creator name is required"
    };
  }
  if (!CAR_DIRECTORY_PATTERN.test(
    carDirectory
  )) {
    return {
      valid: false,
      error: "Invalid car directory"
    };
  }
  if (!SLOT_PATTERN2.test(slotKey)) {
    return {
      valid: false,
      error: "Invalid skin slot"
    };
  }
  if (!FORMAT_PATTERN2.test(textureFormat)) {
    return {
      valid: false,
      error: "Invalid texture format"
    };
  }
  if (!SKIN_FILE_PATTERN2.test(
    sourceFilename
  ) || sourceFilename.includes("/") || sourceFilename.includes("\\")) {
    return {
      valid: false,
      error: "Invalid source filename"
    };
  }
  const expectedSuffix = `_${slotKey}.${textureFormat}.dds.z`.toLowerCase();
  if (!sourceFilename.toLowerCase().endsWith(expectedSuffix)) {
    return {
      valid: false,
      error: "Filename does not match the selected slot and texture format"
    };
  }
  if (!Number.isSafeInteger(binarySize) || binarySize <= 0 || binarySize > MAX_SKIN_BYTES2) {
    return {
      valid: false,
      error: "Binary size must be between 1 byte and 5 MiB"
    };
  }
  if (!SHA256_PATTERN2.test(binarySha256)) {
    return {
      valid: false,
      error: "binarySha256 must be a 64-character SHA-256 value"
    };
  }
  return {
    valid: true,
    title,
    creatorName,
    description,
    carDirectory,
    slotKey,
    textureFormat,
    sourceFilename,
    binarySize,
    binarySha256,
    gameVersions
  };
}
__name(validateSkinMetadata, "validateSkinMetadata");
__name2(validateSkinMetadata, "validateSkinMetadata");
function validateImageUpload(request, maximumBytes) {
  const contentType = String(
    request.headers.get("Content-Type") || ""
  ).split(";")[0].trim().toLowerCase();
  if (!ALLOWED_IMAGE_TYPES.has(
    contentType
  )) {
    return {
      valid: false,
      status: 415,
      error: "Images must be WebP, JPEG, or PNG"
    };
  }
  const contentLength = getContentLength(request);
  if (contentLength === null || contentLength <= 0) {
    return {
      valid: false,
      status: 411,
      error: "A valid Content-Length is required"
    };
  }
  if (contentLength > maximumBytes) {
    return {
      valid: false,
      status: 413,
      error: "Image is too large"
    };
  }
  if (!request.body) {
    return {
      valid: false,
      status: 400,
      error: "Missing image body"
    };
  }
  return {
    valid: true,
    contentType,
    contentLength
  };
}
__name(validateImageUpload, "validateImageUpload");
__name2(validateImageUpload, "validateImageUpload");
function extensionForImageType(contentType) {
  if (contentType === "image/webp") {
    return "webp";
  }
  if (contentType === "image/png") {
    return "png";
  }
  return "jpg";
}
__name(extensionForImageType, "extensionForImageType");
__name2(extensionForImageType, "extensionForImageType");
function skinResponse(row, origin) {
  return {
    id: row.id,
    title: row.title,
    creatorName: row.creator_name,
    description: row.description,
    carDirectory: row.car_directory,
    slotKey: row.slot_key,
    textureFormat: row.texture_format,
    sourceFilename: row.source_filename,
    binarySize: Number(
      row.binary_size || 0
    ),
    binarySha256: row.binary_sha256,
    screenshotCount: Number(
      row.screenshot_count || 0
    ),
    gameVersions: row.game_versions,
    status: row.status || "approved",
    createdAt: row.created_at,
    updatedAt: row.updated_at,
    likes: Number(row.likes || 0),
    downloads: Number(
      row.downloads || 0
    ),
    coverUrl: row.cover_key ? `${origin}/skins/${row.id}/cover` : null,
    binaryUrl: `${origin}/skins/${row.id}/binary`,
    detailsUrl: `${origin}/skins/${row.id}`
  };
}
__name(skinResponse, "skinResponse");
__name2(skinResponse, "skinResponse");
async function getEditableSkin(database, id) {
  return database.prepare(`
      SELECT *
      FROM skins
      WHERE id = ?
        AND status IN (
          'draft',
          'uploading'
        )
      LIMIT 1
    `).bind(id).first();
}
__name(getEditableSkin, "getEditableSkin");
__name2(getEditableSkin, "getEditableSkin");
async function touchSkin(database, id, status) {
  await database.prepare(`
      UPDATE skins
      SET
        status = ?,
        updated_at = CURRENT_TIMESTAMP
      WHERE id = ?
    `).bind(status, id).run();
}
__name(touchSkin, "touchSkin");
__name2(touchSkin, "touchSkin");
async function refreshScreenshotCount(database, id) {
  await database.prepare(`
      UPDATE skins
      SET screenshot_count = (
        SELECT COUNT(*)
        FROM skin_screenshots
        WHERE skin_id = ?
      ),
      updated_at = CURRENT_TIMESTAMP
      WHERE id = ?
    `).bind(id, id).run();
}
__name(refreshScreenshotCount, "refreshScreenshotCount");
__name2(refreshScreenshotCount, "refreshScreenshotCount");
async function deleteOldCover(bucket, oldKey, newKey) {
  if (oldKey && oldKey !== newKey) {
    await bucket.delete(oldKey);
  }
}
__name(deleteOldCover, "deleteOldCover");
__name2(deleteOldCover, "deleteOldCover");
async function deleteR2Prefix(bucket, prefix) {
  let cursor;
  do {
    const listed = await bucket.list({
      prefix,
      cursor,
      limit: 1e3
    });
    const keys = listed.objects.map(
      (object) => object.key
    );
    if (keys.length > 0) {
      await bucket.delete(keys);
    }
    cursor = listed.truncated ? listed.cursor : void 0;
  } while (cursor);
}
__name(deleteR2Prefix, "deleteR2Prefix");
__name2(deleteR2Prefix, "deleteR2Prefix");
function bindStatement(statement, values) {
  return values.length > 0 ? statement.bind(...values) : statement;
}
__name(bindStatement, "bindStatement");
__name2(bindStatement, "bindStatement");
function escapeLike2(value) {
  return value.replace(
    /[%_\\]/g,
    (match) => `\\${match}`
  );
}
__name(escapeLike2, "escapeLike");
__name2(escapeLike2, "escapeLike");
function getContentLength(request) {
  const value = request.headers.get(
    "Content-Length"
  );
  if (value === null) {
    return null;
  }
  const parsed = Number(value);
  return Number.isSafeInteger(parsed) && parsed >= 0 ? parsed : null;
}
__name(getContentLength, "getContentLength");
__name2(getContentLength, "getContentLength");
async function readJson(request) {
  const contentType = String(
    request.headers.get("Content-Type") || ""
  ).toLowerCase();
  if (!contentType.includes(
    "application/json"
  )) {
    return null;
  }
  try {
    return await request.json();
  } catch {
    return null;
  }
}
__name(readJson, "readJson");
__name2(readJson, "readJson");
function clampInteger(value, minimum, maximum, fallback) {
  const parsed = Number.parseInt(
    String(value ?? ""),
    10
  );
  if (!Number.isFinite(parsed)) {
    return fallback;
  }
  return Math.min(
    maximum,
    Math.max(minimum, parsed)
  );
}
__name(clampInteger, "clampInteger");
__name2(clampInteger, "clampInteger");
function isCommunityKey(key) {
  return Boolean(
    key && (key.startsWith("saves/") || key.startsWith("music/"))
  );
}
__name(isCommunityKey, "isCommunityKey");
__name2(isCommunityKey, "isCommunityKey");
function cleanMetadata(value, maximumLength = 256) {
  return String(value || "").trim().slice(0, maximumLength);
}
__name(cleanMetadata, "cleanMetadata");
__name2(cleanMetadata, "cleanMetadata");
function parseInteger(value) {
  const parsed = Number.parseInt(
    String(value || "0"),
    10
  );
  return Number.isFinite(parsed) ? parsed : 0;
}
__name(parseInteger, "parseInteger");
__name2(parseInteger, "parseInteger");
async function readCounter(namespace, key) {
  const value = Number.parseInt(
    await namespace.get(key) || "0",
    10
  );
  return Number.isFinite(value) ? value : 0;
}
__name(readCounter, "readCounter");
__name2(readCounter, "readCounter");
async function incrementCounter(namespace, key) {
  const current = await readCounter(
    namespace,
    key
  );
  const updated = current + 1;
  await namespace.put(
    key,
    String(updated)
  );
  return updated;
}
__name(incrementCounter, "incrementCounter");
__name2(incrementCounter, "incrementCounter");
export {
  index_default as default
};
//# sourceMappingURL=save-files-worker-deploy.js.map
