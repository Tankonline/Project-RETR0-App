# Control Plane

Planned Cloudflare Worker and D1 coordination layer.

This foundation milestone supplies the additive D1 migration at
`cloudflare/save-files/migrations/0008_extension_storage_foundation.sql` and a
versioned protocol schema. It does not alter current Worker routes or Android
behavior.

Responsibilities for the next milestone:

- enroll and revoke extension nodes;
- issue signed, short-lived storage commands;
- track objects and replicas;
- queue replication and repair;
- verify node responses;
- route reads to healthy replicas;
- fall back to canonical R2;
- expose admin-only deletion and audit operations.

No insecure password authentication or fake Google verification is permitted.
Proper identity libraries, issuer/audience validation, secrets, and key rotation
must be selected before implementing authentication.
