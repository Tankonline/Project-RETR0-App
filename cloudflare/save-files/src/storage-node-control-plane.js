const MAX_NODE_BODY = 64 * 1024;
const MAX_SEED_BODY = 8 * 1024;
const MAX_SEED_OBJECT_BYTES = 25 * 1024 * 1024;
const STAGING_BUCKETS = new Set(["community", "assets"]);
const SAFE_R2_KEY = /^(?:saves|music|covers|assets)\/[A-Za-z0-9._() -]{1,240}$/;
const NODE_ID = /^[A-Za-z0-9._:-]{1,128}$/;
const B64 = /^[A-Za-z0-9_-]{43,86}$/;
const NONCE = /^[A-Za-z0-9_-]{16,128}$/;
const MAX_CLOCK_SKEW_MS = 5 * 60 * 1000;
const MAX_COMMAND_TTL_MS = 15 * 60 * 1000;
const OBJECT_HASH = /^[a-f0-9]{64}$/;
const COMMAND_ID = /^[A-Za-z0-9._:-]{1,128}$/;
const COMMAND_TYPES = new Set(["PUT_OBJECT"]);
const TRANSFER_PATH = /^\/v1\/storage\/transfer\/([A-Za-z0-9._:-]{1,128})$/;
const nowIso = () => new Date().toISOString();
const cors = requestId => ({ "Access-Control-Allow-Origin": "*", "Access-Control-Allow-Methods": "GET,POST,OPTIONS", "Access-Control-Allow-Headers": "Content-Type,X-Request-ID,X-Node-ID,X-Timestamp,X-Nonce,X-Signature,X-Storage-Admin,X-Storage-Target-Node", "Access-Control-Expose-Headers": "X-Request-ID", "X-Request-ID": requestId });
const json = (v, status = 200, requestId = null) => new Response(JSON.stringify(v), { status, headers: { ...cors(requestId), "Content-Type": "application/json; charset=utf-8", "Cache-Control": "no-store" } });
const fail = (code, status, requestId, message = code, details = undefined) => json({ error: { code, message, ...(details ? { details } : {}) }, requestId }, status, requestId);
const parseTime = value => { const time = Date.parse(value); return Number.isFinite(time) ? time : null; };
const validateWindow = (issuedAt, expiresAt) => { const issued=parseTime(issuedAt), expires=parseTime(expiresAt), now=Date.now(); return issued!==null && expires!==null && issued<=now+MAX_CLOCK_SKEW_MS && issued>=now-MAX_CLOCK_SKEW_MS && expires>issued && expires-issued<=MAX_COMMAND_TTL_MS && expires>=now; };
const sha256Hex = async bytes => { const b=await crypto.subtle.digest("SHA-256", bytes); return [...new Uint8Array(b)].map(x=>x.toString(16).padStart(2,"0")).join(""); };
const hashCode = value => sha256Hex(new TextEncoder().encode(value));
const readNodeBody = async request => { const len=Number(request.headers.get("Content-Length")||0); if(!Number.isFinite(len)||len<=0||len>MAX_NODE_BODY)return null; const bytes=new Uint8Array(await request.arrayBuffer()); return bytes.length===len?bytes:null; };
const validBase64 = value => typeof value === "string" && B64.test(value);
const parseNodeJson = bytes => { try { const value=JSON.parse(new TextDecoder().decode(bytes)); return value&&typeof value==="object"&&!Array.isArray(value)?value:null; } catch { return null; } };
const auditNode = (db,action,actorId,nodeId,details={}) => db.prepare("INSERT INTO storage_audits (id, actor_type, actor_id, action, node_id, details_json) VALUES (?, ?, ?, ?, ?, ?)").bind(crypto.randomUUID(),"staging_node",actorId||"staging",action,nodeId||null,JSON.stringify(details));
const auditDetails = (requestId, outcome, reason = null, metadata = {}) => ({ requestId, outcome, ...(reason ? { reason } : {}), ...metadata });
const heartbeatReject = (requestId, nodeId, code, flags = {}) => {
  console.warn(JSON.stringify({ event: "staging_heartbeat_rejected", requestId, nodeId, code, ...flags }));
  return fail(code, code === "replayed_request" ? 409 : code === "node_not_approved" ? 403 : code === "invalid_heartbeat" ? 400 : 401, requestId, code === "node_not_approved" ? "Node is not approved" : code === "replayed_request" ? "Nonce already used" : "Heartbeat rejected");
};
const nodeRate = async (env,key,limit=60) => { if(!env.STORAGE_NODE_RATE_LIMIT)return true; const count=Number(await env.STORAGE_NODE_RATE_LIMIT.get(key)||0); if(count>=limit)return false; await env.STORAGE_NODE_RATE_LIMIT.put(key,String(count+1),{expirationTtl:60}); return true; };
const decodeB64 = value => { try { const normalized=String(value).replace(/-/g,"+").replace(/_/g,"/").padEnd(Math.ceil(String(value).length/4)*4,"="); return Uint8Array.from(atob(normalized),c=>c.charCodeAt(0)); } catch{return null;} };
const requireApproval = row => row && ["approved","draining"].includes(row.status);
const canonical = async value => {
  // The signed payload is the exact compact JSON value emitted by Go.
  // The envelope body is not the payload: hash the payload member only.
  // Diagnostics expose only lengths/hashes, never signed material.
  const payloadBytes = value.payload === undefined
    ? new TextEncoder().encode("null")
    : new TextEncoder().encode(JSON.stringify(value.payload));
  const payloadHash = await sha256Hex(payloadBytes);
  return JSON.stringify({version:String(value.version),commandId:String(value.commandId),nodeId:String(value.nodeId),type:String(value.type),issuedAt:String(value.issuedAt),expiresAt:String(value.expiresAt),nonce:String(value.nonce),payloadHash});
};
const consumeNonce = async (db,nodeId,nonce,expiresAt) => { const result=await db.prepare("INSERT OR IGNORE INTO storage_node_nonces (node_id, nonce, expires_at) VALUES (?, ?, ?)").bind(nodeId,nonce,expiresAt).run(); return Boolean(result.meta?.changes); };
const adminAuthorized = async (request, env) => {
  if (env.STORAGE_ENVIRONMENT !== "staging" || env.STORAGE_CONTROL_PLANE_ENABLED !== "true") return false;
  const expected = typeof env.STORAGE_STAGING_ADMIN_TOKEN === "string" ? env.STORAGE_STAGING_ADMIN_TOKEN : "";
  const provided = request.headers.get("X-Storage-Admin") || "";
  if (!expected || !provided || provided === "staging" || provided.length !== expected.length) return false;
  const providedBytes = new TextEncoder().encode(provided);
  const expectedBytes = new TextEncoder().encode(expected);
  let mismatch = 0;
  for (let i = 0; i < expectedBytes.length; i++) mismatch |= providedBytes[i] ^ expectedBytes[i];
  return mismatch === 0;
};

export async function handleStorageNodeRoute(request, env, url, requestId) {
  if (!url.pathname.startsWith("/v1/storage/nodes") && !url.pathname.startsWith("/v1/storage/admin/storage-objects/seed") && !url.pathname.startsWith("/v1/storage/admin/nodes/")) return null;
  if (env.STORAGE_CONTROL_PLANE_ENABLED !== "true" || env.STORAGE_ENVIRONMENT !== "staging") return fail("staging_control_plane_disabled",404,requestId,"Staging control plane is disabled");
  if (!env.SKINS_DB || !env.COMMUNITY_BUCKET) return fail("service_unavailable",503,requestId,"Storage database or R2 is unavailable");
  if (request.method === "OPTIONS") return new Response(null,{status:204,headers:cors(requestId)});
  const ip=request.headers.get("CF-Connecting-IP")||"unknown"; if(!(await nodeRate(env,`node:${ip}`)))return fail("rate_limited",429,requestId,"Too many requests");
  try {
    const body=request.method==="GET"?{}:await readNodeBody(request); if(body===null)return fail("invalid_body",400,requestId,"A bounded request body is required");
    const value=body instanceof Uint8Array?parseNodeJson(body):body; if(!value)return fail("invalid_json",400,requestId,"Invalid JSON");
    const approveMatch=/^\/v1\/storage\/nodes\/([^/]+)\/enroll\/approve$/.exec(url.pathname);
    if(approveMatch&&request.method==="POST"){
      if(!(await adminAuthorized(request,env)))return fail("admin_required",403,requestId,"Admin authorization required");
      const routeNodeId=decodeURIComponent(approveMatch[1]);
      const nodeId=String(value.nodeId||"");
      if(nodeId!==routeNodeId)return fail("node_id_mismatch",400,requestId,"nodeId does not match the route");
      const publicKey=String(value.publicKey||""), code=String(value.enrollmentCode||"");
      if(!NODE_ID.test(nodeId)||!validBase64(publicKey)||code.length<8||code.length>256||!NONCE.test(String(value.nonce||""))||!value.signature||!validateWindow(value.issuedAt,value.expiresAt))return fail("invalid_enrollment",400,requestId,"Invalid enrollment envelope");
      const row=await env.SKINS_DB.prepare("SELECT id,enrollment_code_hash,enrollment_expires_at,status,public_key FROM storage_nodes WHERE id=? LIMIT 1").bind(nodeId).first();
      if(!row||row.status==="revoked"||!row.enrollment_code_hash||(row.enrollment_expires_at&&Date.parse(row.enrollment_expires_at)<=Date.now()))return fail("enrollment_not_available",403,requestId,"Enrollment is unavailable");
      if(await hashCode(code)!==row.enrollment_code_hash||row.public_key!==publicKey)return fail("enrollment_rejected",403,requestId,"Enrollment rejected");
      if(!(await consumeNonce(env.SKINS_DB,nodeId,value.nonce,value.expiresAt)))return fail("replayed_request",409,requestId,"Nonce already used");
      // D1 prepare() accepts the SQL string only; bind parameters on the statement.
      await env.SKINS_DB.batch([env.SKINS_DB.prepare("UPDATE storage_nodes SET status='approved',enrolled_at=CURRENT_TIMESTAMP,approved_at=CURRENT_TIMESTAMP,updated_at=CURRENT_TIMESTAMP WHERE id=?").bind(nodeId), auditNode(env.SKINS_DB,"node_enroll",nodeId,nodeId,auditDetails(requestId,"approved",null,{route:"enroll/approve",method:request.method}))]);
      return json({nodeId,status:"approved",requestId},200,requestId);
    }
    const requestMatch=/^\/v1\/storage\/nodes\/([^/]+)\/enroll\/request$/.exec(url.pathname);
    if(requestMatch&&request.method==="POST"){
      if(value.nodeId && String(value.nodeId)!==decodeURIComponent(requestMatch[1]))return fail("node_id_mismatch",400,requestId,"nodeId does not match the route");
      const nodeId=decodeURIComponent(requestMatch[1]); if(!NODE_ID.test(nodeId)||!validBase64(value.publicKey)||typeof value.displayName!=="string"||value.displayName.length>128||typeof value.enrollmentCode!=="string"||value.enrollmentCode.length<8)return fail("invalid_enrollment_request",400,requestId,"Invalid enrollment request");
      const expiresAt=parseTime(value.expiresAt||"")?new Date(value.expiresAt).toISOString():new Date(Date.now()+15*60*1000).toISOString();
      try { await env.SKINS_DB.prepare("INSERT INTO storage_nodes (id,display_name,endpoint,public_key,fingerprint,status,enrollment_code_hash,enrollment_expires_at) VALUES (?,?,?,?,?,'pending',?,?)").bind(nodeId,value.displayName,String(value.endpoint||"").slice(0,512),value.publicKey,value.fingerprint||null,await hashCode(value.enrollmentCode),expiresAt).run(); } catch(e) { const existing=await env.SKINS_DB.prepare("SELECT id,public_key,status FROM storage_nodes WHERE id=?").bind(nodeId).first(); if(existing&&existing.public_key===value.publicKey)return json({nodeId,status:existing.status,requestId},200,requestId); throw e; }
      await auditNode(env.SKINS_DB,"node_enrollment_requested",nodeId,nodeId,auditDetails(requestId,"pending",null,{route:"enroll/request",method:request.method})); return json({nodeId,status:"pending",requestId},201,requestId);
    }
    const transferMatch=TRANSFER_PATH.exec(url.pathname);
    if(transferMatch&&request.method==="GET"){
      if (!env.STORAGE_TRANSFER_SIGNING_KEY || env.STORAGE_ENVIRONMENT !== "staging") return fail("staging_transfer_disabled",404,requestId,"Staging transfer endpoint is disabled");
      const commandId=decodeURIComponent(transferMatch[1]);
      const row=await env.SKINS_DB.prepare("SELECT c.id,c.node_id,c.command_type,c.expires_at,p.payload_json FROM storage_commands c JOIN storage_command_payloads p ON p.command_id=c.id WHERE c.id=? AND c.command_type='PUT_OBJECT' AND c.status IN ('issued','accepted') LIMIT 1").bind(commandId).first();
      if(!row||parseTime(row.expires_at)<=Date.now()) return fail("transfer_unavailable",404,requestId,"Transfer is unavailable");
      const target=request.headers.get("X-Storage-Target-Node")||"";
      if(target!==row.node_id||!NODE_ID.test(target)) return fail("transfer_target_mismatch",403,requestId,"Transfer target mismatch");
      let payload; try{payload=JSON.parse(row.payload_json)}catch{return fail("transfer_unavailable",404,requestId,"Transfer is unavailable")}
      if(!payload.r2Key||!OBJECT_HASH.test(String(payload.objectHash||""))) return fail("transfer_unavailable",404,requestId,"Transfer is unavailable");
      const object=await env.COMMUNITY_BUCKET.get(String(payload.r2Key));
      if (object && OBJECT_HASH.test(String(payload.objectHash)) && Number(object.size) !== Number(payload.objectSize)) return fail("object_size_mismatch",409,requestId,"Object size does not match command payload");
      if(!object) return fail("object_not_found",404,requestId,"Object not found");
      await env.SKINS_DB.prepare("UPDATE storage_commands SET status='accepted' WHERE id=? AND status='issued'").bind(commandId).run();
      return new Response(object.body,{status:200,headers:{...cors(requestId),"Content-Type":object.httpMetadata?.contentType||payload.contentType||"application/octet-stream","Content-Length":String(object.size),"Cache-Control":"no-store","X-Request-ID":requestId}});
    }
    const commandMatch=/^\/v1\/storage\/nodes\/([^/]+)\/commands$/.exec(url.pathname);
    if(commandMatch&&request.method==="GET"){
      const nodeId=decodeURIComponent(commandMatch[1]);
      if(!NODE_ID.test(nodeId))return fail("invalid_node",400,requestId,"Invalid node ID");
      const row=await env.SKINS_DB.prepare("SELECT status FROM storage_nodes WHERE id=? LIMIT 1").bind(nodeId).first();
      if(!row||!requireApproval(row))return fail("node_not_approved",403,requestId,"Node is not approved");
      const rows=await env.SKINS_DB.prepare("SELECT c.id,c.node_id,c.command_type,c.nonce,c.issued_at,c.expires_at,c.signature,p.payload_json FROM storage_commands c JOIN storage_command_payloads p ON p.command_id=c.id WHERE c.node_id=? AND c.status IN ('issued','accepted') AND c.expires_at>CURRENT_TIMESTAMP ORDER BY c.issued_at LIMIT 20").bind(nodeId).all();
      const commands=(rows.results||[]).map(row=>{let payload;try{payload=JSON.parse(row.payload_json)}catch{payload=null}return {version:"v1",commandId:row.id,nodeId:row.node_id,type:row.command_type,issuedAt:row.issued_at,expiresAt:row.expires_at,nonce:row.nonce,payload,signature:row.signature};});
      return json({commands,requestId},200,requestId);
    }
    const ackMatch=/^\/v1\/storage\/nodes\/([^/]+)\/commands\/([^/]+)\/ack$/.exec(url.pathname);
    if(ackMatch&&request.method==="POST"){
      const nodeId=decodeURIComponent(ackMatch[1]), commandId=decodeURIComponent(ackMatch[2]);
      if(!NODE_ID.test(nodeId)||!COMMAND_ID.test(commandId)||String(value.nodeId||"")!==nodeId||String(value.commandId||"")!==commandId)return fail("invalid_ack",400,requestId,"Invalid command acknowledgement");
      const row=await env.SKINS_DB.prepare("SELECT id,status,expires_at FROM storage_commands WHERE id=? AND node_id=? LIMIT 1").bind(commandId,nodeId).first();
      if(!row)return fail("command_not_found",404,requestId,"Command not found");
      if(!["issued","accepted"].includes(row.status))return json({status:row.status,requestId},200,requestId);
      const next=value.success===true?"completed":"failed";
      await env.SKINS_DB.batch([env.SKINS_DB.prepare("UPDATE storage_commands SET status=?,completed_at=CURRENT_TIMESTAMP,result_json=? WHERE id=? AND status IN ('issued','accepted')").bind(next,JSON.stringify({error:String(value.error||"").slice(0,256),sizeBytes:Number(value.sizeBytes)||0,sha256:String(value.sha256||"")}),commandId),env.SKINS_DB.prepare("UPDATE storage_replicas SET state=?,verified_at=CASE WHEN ?='completed' THEN CURRENT_TIMESTAMP ELSE verified_at END,last_error=?,updated_at=CURRENT_TIMESTAMP WHERE node_id=? AND object_hash=(SELECT json_extract(payload_json,'$.objectHash') FROM storage_command_payloads WHERE command_id=?)").bind(next==="completed"?"healthy":"failed",next,String(value.error||"").slice(0,256)||null,nodeId,commandId),auditNode(env.SKINS_DB,"storage_command_ack",nodeId,nodeId,auditDetails(requestId,next,null,{commandId,method:request.method}))]);
      return json({status:next,requestId},200,requestId);
    }
    const seedMatch=/^\/v1\/storage\/admin\/storage-objects\/seed$/.exec(url.pathname);
    if(seedMatch&&request.method==="POST"){
      if(!(await adminAuthorized(request,env)))return fail("admin_required",403,requestId,"Admin authorization required");
      if(!(env.STORAGE_SEED_ENABLED === "true"))return fail("seed_disabled",404,requestId,"Staging seed route is disabled");
      // Consume the request body exactly once. From this point onward every
      // validation step uses only the parsed value, never the Request stream.
      const raw=await request.arrayBuffer(); if(raw.byteLength===0||raw.byteLength>MAX_SEED_BODY)return fail("invalid_body",400,requestId,"A bounded JSON body is required");
      let seed; try{seed=JSON.parse(new TextDecoder().decode(raw))}catch{return fail("invalid_json",400,requestId,"Invalid JSON")}
      const bucketName=String(seed.bucket||""); const r2Key=String(seed.r2Key||"");
      if(!STAGING_BUCKETS.has(bucketName)||!SAFE_R2_KEY.test(r2Key)||r2Key.includes(".."))return fail("invalid_source",400,requestId,"Only an allowed staging bucket and object key are accepted");
      const bucket=bucketName==="community"?env.COMMUNITY_BUCKET:env.ASSET_BUCKET; if(!bucket)return fail("service_unavailable",503,requestId,"Selected staging bucket is unavailable");
      const object=await bucket.get(r2Key); if(!object||!Number.isSafeInteger(object.size)||object.size<=0)return fail("object_unavailable",404,requestId,"Object is missing or empty");
      if(object.size>MAX_SEED_OBJECT_BYTES)return fail("object_too_large",413,requestId,"Object exceeds the staging seed limit");
      // Consume the R2 body exactly once. From this point onward every check,
      // digest, and persistence operation uses only the bounded byte buffer.
      const bytes=new Uint8Array(await object.arrayBuffer()); if(bytes.length!==object.size||bytes.length===0)return fail("object_unavailable",404,requestId,"Object size could not be verified");
      const hash=await sha256Hex(bytes), contentType=String(object.httpMetadata?.contentType||"application/octet-stream").slice(0,128), now=new Date().toISOString();
      await env.SKINS_DB.batch([
        env.SKINS_DB.prepare("INSERT INTO storage_objects (hash,size_bytes,content_type,r2_key,created_at) VALUES (?,?,?,?,?) ON CONFLICT(hash) DO UPDATE SET size_bytes=excluded.size_bytes,content_type=excluded.content_type,r2_key=excluded.r2_key").bind(hash,bytes.length,contentType,r2Key,now),
        auditNode(env.SKINS_DB,"storage_object_seed","staging-admin",null,auditDetails(requestId,"accepted",null,{bucket:bucketName,r2Key,hash,sizeBytes:bytes.length,contentType,idempotent:true}))
      ]);
      return json({hash,sizeBytes:bytes.length,contentType,r2Key,createdAt:now,idempotent:true,requestId},200,requestId);
    }
    const commandCreateMatch=/^\/v1\/storage\/admin\/nodes\/([^/]+)\/commands$/.exec(url.pathname);
    if(commandCreateMatch&&request.method==="POST"){
      if(!(await adminAuthorized(request,env)))return fail("admin_required",403,requestId,"Admin authorization required");
      const nodeId=decodeURIComponent(commandCreateMatch[1]);
      if(!NODE_ID.test(nodeId)||value.type!=="PUT_OBJECT")return fail("invalid_command",400,requestId,"Only PUT_OBJECT is supported");
      const objectId=String(value.objectId||value.objectHash||"");
      const object=await env.SKINS_DB.prepare("SELECT hash,size_bytes,content_type,r2_key FROM storage_objects WHERE hash=? OR r2_key=? LIMIT 1").bind(objectId,objectId).first();
      const node=await env.SKINS_DB.prepare("SELECT id,status FROM storage_nodes WHERE id=? LIMIT 1").bind(nodeId).first();
      if(!object||!node||!requireApproval(node))return fail("not_available",404,requestId,"Object or node unavailable");
      const commandId=crypto.randomUUID(), issued=new Date(), expires=new Date(Date.now()+10*60*1000), nonce=crypto.randomUUID();
      const payload={objectHash:object.hash,objectSize:Number(object.size_bytes),contentType:object.content_type||"application/octet-stream",r2Key:object.r2_key,transferUrl:`${new URL(request.url).origin}/v1/storage/transfer/${commandId}`};
      const signature=await sha256Hex(new TextEncoder().encode(`${commandId}:${nodeId}:${nonce}:${expires.toISOString()}:${env.STORAGE_TRANSFER_SIGNING_KEY||""}`));
      await env.SKINS_DB.batch([env.SKINS_DB.prepare("INSERT INTO storage_commands (id,node_id,command_type,nonce,issued_at,expires_at,signature,status) VALUES (?,?,?,?,?,?,?,'issued')").bind(commandId,nodeId,"PUT_OBJECT",nonce,issued.toISOString(),expires.toISOString(),signature),env.SKINS_DB.prepare("INSERT INTO storage_command_payloads (command_id,payload_json) VALUES (?,?)").bind(commandId,JSON.stringify(payload)),env.SKINS_DB.prepare("INSERT OR REPLACE INTO storage_replicas (object_hash,node_id,state,updated_at) VALUES (?,?, 'queued', CURRENT_TIMESTAMP)").bind(object.hash,nodeId),auditNode(env.SKINS_DB,"storage_command_issued","staging-admin",nodeId,auditDetails(requestId,"issued",null,{commandId,commandType:"PUT_OBJECT"}))]);
      return json({commandId,nodeId,status:"issued",requestId},201,requestId);
    }
    const match=/^\/v1\/storage\/nodes\/([^/]+)(?:\/(heartbeat|enroll\/status|approve|cancel|drain|undrain|revoke))?$/.exec(url.pathname); if(!match)return fail("not_found",404,requestId,"Route not found");
    const nodeId=decodeURIComponent(match[1]); if(!NODE_ID.test(nodeId))return fail("invalid_node",400,requestId,"Invalid node ID");
    const row=await env.SKINS_DB.prepare("SELECT * FROM storage_nodes WHERE id=? LIMIT 1").bind(nodeId).first(); if(!row)return fail("not_found",404,requestId,"Node not found");
    const action=match[2]||"status";
    if(["approve","cancel","drain","undrain","revoke"].includes(action)){
      if(request.method!=="POST")return fail("method_not_allowed",405,requestId,"Method not allowed");
      if(!(await adminAuthorized(request,env)))return fail("admin_required",403,requestId,"Admin authorization required");
      const transitions={approve:["approved","approved_at=CURRENT_TIMESTAMP"],cancel:["offline",""],drain:["draining",""],undrain:["approved",""],revoke:["revoked","revoked_at=CURRENT_TIMESTAMP"]}; const [status,extra]=transitions[action];
      await env.SKINS_DB.prepare(`UPDATE storage_nodes SET status=?,${extra?extra+",":""}updated_at=CURRENT_TIMESTAMP WHERE id=?`).bind(status,nodeId).run(); await auditNode(env.SKINS_DB,`node_${action}`,"staging-admin",nodeId,auditDetails(requestId,status,null,{route:action,method:request.method})); return json({nodeId,status,requestId},200,requestId);
    }
    if(action==="status"){if(request.method!=="GET")return fail("method_not_allowed",405,requestId,"Method not allowed");return json({nodeId,status:row.status,enrolledAt:row.enrolled_at,lastHeartbeatAt:row.last_heartbeat_at,requestId},200,requestId);}
    if(action!=="heartbeat"||request.method!=="POST")return fail("method_not_allowed",405,requestId,"Method not allowed");
    if(!requireApproval(row))return heartbeatReject(requestId,nodeId,"node_not_approved",{nodeFound:true,statusApproved:false,fingerprintMatch:null,timestampValid:null,nonceSeen:null,signatureValid:null});
    const timestampValid = value.version==="v1"&&value.type==="HEARTBEAT"&&value.nodeId===nodeId&&NODE_ID.test(value.commandId||"")&&NONCE.test(value.nonce||"")&&Boolean(value.signature)&&validateWindow(value.issuedAt,value.expiresAt);
    if(!timestampValid)return heartbeatReject(requestId,nodeId,"invalid_heartbeat",{nodeFound:true,statusApproved:true,fingerprintMatch:null,timestampValid:false,nonceSeen:null,signatureValid:null});
    const pub=decodeB64(row.public_key),sig=decodeB64(value.signature); if(!pub||pub.length!==32||!sig||sig.length!==64)return heartbeatReject(requestId,nodeId,"invalid_signature",{nodeFound:true,statusApproved:true,fingerprintMatch:null,timestampValid:true,nonceSeen:null,signatureValid:false});
    const expectedFingerprint = await sha256Hex(pub);
    const fingerprintMatch = !row.fingerprint || row.fingerprint === expectedFingerprint;
    if (!fingerprintMatch) return heartbeatReject(requestId,nodeId,"invalid_signature",{nodeFound:true,statusApproved:true,fingerprintMatch:false,timestampValid:true,nonceSeen:null,signatureValid:false});
    const key=await crypto.subtle.importKey("raw",pub,{name:"Ed25519"},false,["verify"]); const signatureValid=await crypto.subtle.verify("Ed25519",key,sig,new TextEncoder().encode(await canonical(value)));
    if(!signatureValid)return heartbeatReject(requestId,nodeId,"invalid_signature",{nodeFound:true,statusApproved:true,fingerprintMatch:true,timestampValid:true,nonceSeen:null,signatureValid:false});
    const nonceAccepted=await consumeNonce(env.SKINS_DB,nodeId,value.nonce,value.expiresAt); if(!nonceAccepted)return heartbeatReject(requestId,nodeId,"replayed_request",{nodeFound:true,statusApproved:true,fingerprintMatch:true,timestampValid:true,nonceSeen:true,signatureValid:true});
    const payload=value.payload&&typeof value.payload==="object"?value.payload:{}; const capacity=Number(payload.capacityBytes),free=Number(payload.freeBytes),count=Number(payload.objectCount); if(![capacity,free,count].every(Number.isSafeInteger)||capacity<0||free<0||count<0||free>capacity){ console.warn(JSON.stringify({event:"staging_heartbeat_rejected",requestId,nodeId,code:"invalid_heartbeat",nodeFound:true,statusApproved:true,fingerprintMatch:true,timestampValid:true,nonceSeen:true,signatureValid:true,capacityValid:false})); return fail("invalid_heartbeat",400,requestId,"Invalid capacity values"); }
    await env.SKINS_DB.batch([env.SKINS_DB.prepare("UPDATE storage_nodes SET last_heartbeat_nonce=?,last_heartbeat_at=CURRENT_TIMESTAMP,free_bytes=?,capacity_bytes=?,object_count=?,updated_at=CURRENT_TIMESTAMP WHERE id=?").bind(value.nonce,free,capacity,count,nodeId),auditNode(env.SKINS_DB,"node_heartbeat",nodeId,nodeId,auditDetails(requestId,"accepted",null,{route:"heartbeat",method:request.method}))]); return json({status:"accepted",requestId},200,requestId);
  } catch(error){const errorType=error?.constructor?.name||typeof error;const errorMessage=error instanceof Error?error.message:String(error);console.error(JSON.stringify({event:"staging_node_route_failed",requestId,errorType,errorMessage}));return fail("internal_error",500,requestId,"The request could not be completed");}
}
