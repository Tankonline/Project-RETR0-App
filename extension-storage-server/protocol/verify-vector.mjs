import fs from "node:fs";
import crypto from "node:crypto";
const v = JSON.parse(fs.readFileSync(new URL("./cross-language-vector.json", import.meta.url), "utf8"));
const payloadBytes = Buffer.from(v.payloadJson, "utf8");
const payloadHash = crypto.createHash("sha256").update(payloadBytes).digest("hex");
if (payloadHash !== v.payloadHash) throw new Error(`payload hash mismatch: ${payloadHash}`);
const canonical = JSON.stringify({version:v.version,commandId:v.commandId,nodeId:v.nodeId,type:v.type,issuedAt:v.issuedAt,expiresAt:v.expiresAt,nonce:v.nonce,payloadHash});
if (canonical !== v.canonicalJson) throw new Error("canonical JSON mismatch");
console.log(JSON.stringify({ok:true,payloadHash,canonicalBytes:Buffer.byteLength(canonical),canonical}));
