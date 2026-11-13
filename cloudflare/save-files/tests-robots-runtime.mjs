import assert from "node:assert/strict";
import { robotsAllowed } from "./src/rr3-knowledge-scraper.js";
assert.equal(robotsAllowed("User-agent: *\nDisallow: /private/\n", "/private/x"), false);
assert.equal(robotsAllowed("User-agent: *\nDisallow: /private/", "/wiki/x"), true);
assert.equal(robotsAllowed("User-agent: *\nAllow: /private/public\nDisallow: /private/", "/private/public/x"), true);
console.log("robots allow/deny parsing passed");
