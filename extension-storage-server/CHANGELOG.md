# Changelog

## Unreleased

### Added

- Versioned command, object, health, and server-directory schemas.
- Content-addressed Go storage-node filesystem implementation.
- Atomic writes with exact size and SHA-256 verification.
- Local health and capacity endpoints.
- Docker development image.
- Provider GUI, installation, operations, security, and contribution guides.

### Known limitations

- Signed control-plane command authentication is not yet wired into the HTTP
  object routes.
- Production enrollment is not enabled.
- Cloudflare replication and R2 fallback are not enabled.
- The provider GUI Tauri application is documented but not yet scaffolded.
- Capacity reporting is a portable development baseline and is not yet a full
  quota-aware filesystem measurement on every platform.
