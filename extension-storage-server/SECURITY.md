# Security Policy

## Scope

This project handles storage-node software and control-plane protocols. The
Cloudflare control plane remains authoritative for Community Hub ownership,
publication, moderation, hashes, and deletion authorization.

## Do not report publicly

Do not publish details of an unpatched vulnerability involving:

- command-signature bypass;
- replay protection;
- node enrollment or revocation;
- arbitrary filesystem access;
- unauthorized object deletion;
- secret leakage;
- Cloudflare control-plane authorization.

## Reporting

Until a dedicated security contact is configured, open a private GitHub security
advisory for this repository. Do not include live tokens, private keys, node
credentials, or user data in the report.

## Security expectations

- Nodes are replaceable replicas, not authorities.
- Nodes must default to loopback or authenticated control-plane access.
- Object identity is SHA-256 plus exact byte length.
- Commands are short-lived, node-bound, signed, and replay-protected.
- Diagnostics must redact secrets and signatures.
- Provider GUI native capabilities must be allowlisted.
- Production secrets must remain in Cloudflare secret bindings or approved secret
  managers.
