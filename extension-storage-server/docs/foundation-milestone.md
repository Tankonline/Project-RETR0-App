# Foundation milestone

This milestone adds the additive D1 schema, protocol envelope, and a signed-command-ready seam in the Go node without changing Android or the existing Worker routes.

## Deferred security work

- No insecure password authentication is implemented.
- No fake Google verification is implemented. Google identity verification requires the proper Google/Android libraries, issuer and audience validation, key rotation handling, and production configuration.
- Command signatures are not accepted by HTTP routes yet. Enrollment, public-key persistence, canonical serialization, replay protection, and short-lived command issuance belong to the next milestone.
- No production endpoints, API tokens, private keys, D1 IDs beyond existing configuration, or R2 credentials are added.

## D1 tables

Migration `0008_extension_storage_foundation.sql` creates `storage_nodes`,
`storage_objects`, `storage_replicas`, `storage_commands`, `storage_audits`, and
`storage_server_directory` using `IF NOT EXISTS`. It is additive and does not
alter existing Community Hub tables.

## Worker rollout

Apply locally first with the existing Worker configuration:

```bash
npx wrangler@latest d1 migrations apply save-files --local
npx wrangler@latest deploy --dry-run
```

Remote migration/deploy requires an operator with access to the existing
Cloudflare account. Secrets must be supplied through Wrangler secret management,
never committed to this repository.
