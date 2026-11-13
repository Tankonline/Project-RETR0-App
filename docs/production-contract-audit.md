# Production app ↔ Community Hub contract audit

## Scope

This audit covers the checked-in production Wrangler configuration (`cloudflare/save-files/wrangler.jsonc`), the production Worker source, production migrations, and Android networking/authentication/storage/build configuration. No Cloudflare deployment, D1 mutation, R2 mutation, secret read, or remote rollout was performed.

## Confirmed contract

- Production base URL: `https://save-files.armaandeep08-ap.workers.dev`.
- Android routes are rooted at `/v1` and use `/skins`, `/saves`, `/music`, `/installations`, and `/car-models`.
- Signed installation requests use `X-Installation-ID`, `X-Timestamp`, `X-Nonce`, and `X-Signature`.
- Canonical signing input is method, encoded path plus query, installation UUID, timestamp, nonce, and lowercase SHA-256 body hash, separated by newlines.
- Production Worker authenticates installation writes against `anonymous_installations` and consumes nonces. Android stores the UUID in app-private SharedPreferences and the P-256 private key in Android Keystore.
- Production save/music limits and Android limits match: 25 MiB payloads; music artwork 500 KiB; save avatar 200 KiB.
- Production car-model route limits are stricter than the Android client in one area: the Worker allows 20 MiB per file, while Android allows 100 MiB. Android's lower client-side enforcement is safe but should be documented as intentional.

## Findings

### High priority before rollout

1. **Production Wrangler config does not declare `migrations_dir`.** Staging explicitly points at `migrations-staging`; production relies on Wrangler's default migrations directory. Verify the production migration history and applied remote migrations before any rollout. This audit intentionally did not run remote migration commands.
2. **Production config has no explicit production auth vars or `AUTH_RATE_LIMIT` KV binding.** The Worker has Firebase OIDC code that falls back to `FIREBASE_PROJECT_ID`, but production config does not declare those vars. Account-authenticated routes (`/v1/auth/*`) will not be usable unless equivalent production values are configured in the deployed environment. The rate limiter is optional and therefore falls back to no rate limit.
3. **The Android app has no Firebase bearer-token integration.** Installation-signed Community Hub operations are compatible, but account routes requiring `Authorization: Bearer` are not reachable from the current app. This is a capability gap, not a safe compatibility fix; it is deferred.
4. **`local.properties` contains `RETR0_API_KEY`.** It is outside the checked-in build configuration and must not be committed or copied into production artifacts. Rotate it if it was ever exposed.

### Verified safe behavior

- Production and staging use distinct Worker names, D1 IDs, R2 buckets, and KV IDs.
- Android production and debug build types both point to the production host; no staging host was found in app source/build configuration.
- Staging-only control-plane routes are guarded in Worker code by staging environment flags and a separate admin header/token path. Production Wrangler does not enable those flags.
- Production bindings include `SKINS_DB`, `COMMUNITY_BUCKET`, `ASSET_BUCKET`, `ASSET_KV`, `SOCIAL_KV`, and `AI`; they are distinct from staging bindings.
- The Worker source and Android canonicalizer agree on signing format and ECDSA P-256 signature handling.

## Change made

- Clarified the Android build-type comments in `app/build.gradle.kts`; no endpoint, credential, signing, or runtime behavior was changed.

## Deferred rollout steps

1. Confirm the production D1 migration ledger and production migration directory mapping using an approved, read-only operational procedure.
2. Configure production Firebase issuer/audience/project values and a dedicated production `AUTH_RATE_LIMIT` KV binding if account auth is intended.
3. Decide whether Android should implement Firebase account auth, then add a versioned contract and tests before enabling account-dependent UI.
4. Rotate any exposed local API key and verify repository history/secrets scanning.
5. Run Wrangler `deploy --dry-run` (production config only), Worker syntax/static guards, Android unit tests/lint, and release build in CI; deploy only after explicit approval.
