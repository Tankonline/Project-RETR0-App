# Provider GUI Specification

The provider GUI is a Tauri desktop application for Windows and Linux. It is
not an administrator dashboard and cannot change Community Hub authority.

## Screens

### Welcome

- Explain what the node stores.
- Explain that Cloudflare/R2 remains canonical.
- Link to privacy and operator policy.
- Start setup.

### Storage setup

- Select directory.
- Show free space.
- Set quota.
- Validate write permissions.
- Warn against removable/network drives unless supported.

### Enrollment

- Display node name.
- Display fingerprint.
- Display one-time enrollment code.
- Show pending/approved/revoked state.
- Never display the private key.

### Dashboard

- Service state.
- Control-plane connection.
- Last heartbeat.
- Used/quota/free storage.
- Object count.
- Replica queue.
- Recent errors.
- Health indicator with text and timestamp.

### Diagnostics

- DNS and TLS check.
- Control-plane reachability.
- Loopback node check.
- Disk read/write check.
- Clock-skew check.
- Heartbeat check.
- Export redacted report.

### Settings

- Storage directory.
- Quota.
- Start with operating system.
- Drain node.
- Restart service.
- Safe log level.
- Reset local enrollment with explicit confirmation.

## Security requirements

- No arbitrary shell execution from the renderer.
- Strict Tauri capability allowlist.
- Local API bound to loopback.
- Origin and CSRF validation.
- No remote web content with native privileges.
- OS keychain for protected local secrets.
- No Cloudflare API tokens.
- No arbitrary delete control.
- Signed installers and update metadata.
