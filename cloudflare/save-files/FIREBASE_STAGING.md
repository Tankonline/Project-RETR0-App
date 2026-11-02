# Firebase staging authentication

1. Create a dedicated Firebase staging project; do not reuse production credentials.
2. Enable the provider(s) required by the staging client and record the project ID.
3. Replace the `REPLACE_WITH_STAGING_FIREBASE_PROJECT_ID` placeholders in the local/deployment staging configuration only. Do not commit service-account JSON, private keys, bearer tokens, or client secrets.
4. Apply the staging migration history before deploying. For this fix, run `npx wrangler@latest d1 migrations apply retr0-save-files-staging --config wrangler.staging.jsonc --remote` (operator approval required), which applies `migrations-staging/0005_accountless_save_uploads.sql` after `0001`-`0004`; then deploy the staging Worker with `npx wrangler@latest deploy --config wrangler.staging.jsonc`. Do not apply the production migration to staging.
5. Validate a Firebase ID token against `GET /v1/auth/me`, then exercise installation link/unlink. Existing installation-signature routes remain unchanged.
6. Configure `AUTH_RATE_LIMIT` as a staging KV binding before deployment if rate limiting is required; the code fails open only when the binding is absent in local dry-runs.

Production authentication, replication, Android auth UI, and production deployment are intentionally deferred.
