import assert from "node:assert/strict";
import fs from "node:fs";

const source = fs.readFileSync(new URL("src/storage-node-control-plane.js", import.meta.url), "utf8");
const seed = source.slice(source.indexOf('const seedMatch='), source.indexOf('const commandCreateMatch='));
assert.equal((seed.match(/object\.arrayBuffer\(\)/g) || []).length, 1);
assert.equal((seed.match(/object\.body/g) || []).length, 0);
assert.equal((seed.match(/request\.arrayBuffer\(\)/g) || []).length, 1);
assert.match(seed, /const bytes=new Uint8Array\(await object\.arrayBuffer\(\)\)/);
assert.match(seed, /sha256Hex\(bytes\)/);
assert.match(seed, /sizeBytes:bytes\.length/);
assert.match(seed, /contentType/);

let r2Reads = 0;
let requestReads = 0;
const singleUseR2 = { async arrayBuffer() { r2Reads += 1; if (r2Reads > 1) throw new TypeError("R2 Body has already been used"); return new Uint8Array([1, 2, 3]).buffer; } };
const singleUseRequest = { async arrayBuffer() { requestReads += 1; if (requestReads > 1) throw new TypeError("Request body has already been used"); return new TextEncoder().encode('{"bucket":"community","r2Key":"assets/example"}').buffer; } };
await singleUseRequest.arrayBuffer();
await singleUseR2.arrayBuffer();
assert.equal(requestReads, 1);
assert.equal(r2Reads, 1);
console.log("single-use request/R2 seed body regression guard passed");
