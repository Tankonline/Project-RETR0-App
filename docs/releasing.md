# Releasing Project RETR0 installer

## One canonical APK

Each release produces one signed APK, `app-release.apk`. That same artifact may be attached to the GitHub Release and used for the IzzyOnDroid submission. The app does not contain a separate GitHub or F-Droid product flavor.

The APK does not self-update. Users receive app updates from GitHub or IzzyOnDroid. Music is obtained externally and imported from local storage; no in-app YouTube converter or bundled media downloader is released.

## Prerequisites

- A clean checkout of the release commit.
- A monotonic `versionCode` and semantic `versionName` in `app/build.gradle.kts`.
- A release keystore stored outside the repository.
- All unit tests passing.
- Review of `docs/izzyondroid-compatibility.md` and `docs/privacy-and-community-policy.md`.

## Local signed build

Set signing values only in your shell or secure CI secret store:

```powershell
$env:RELEASE_STORE_FILE = "C:\secure\project-retr0-release.keystore"
$env:RELEASE_STORE_PASSWORD = "your-store-password"
$env:RELEASE_KEY_ALIAS = "your-key-alias"
$env:RELEASE_KEY_PASSWORD = "your-key-password"

.\gradlew :app:testDebugUnitTest :app:assembleRelease
Get-FileHash .\app\build\outputs\apk\release\app-release.apk -Algorithm SHA256
```

The release output is `app/build/outputs/apk/release/app-release.apk`.

## GitHub Actions release secrets

Configure these protected environment or repository secrets:

- `RELEASE_KEYSTORE_BASE64`: base64-encoded release keystore file.
- `RELEASE_STORE_PASSWORD`: keystore password.
- `RELEASE_KEY_ALIAS`: signing-key alias.
- `RELEASE_KEY_PASSWORD`: signing-key password.

The workflow at `.github/workflows/release.yml` validates unit tests, builds `:app:assembleRelease`, creates a SHA-256 checksum, and publishes both files on a semantic version tag matching `versionName`, such as `v1.1.5`.

For the locally prepared v1.1.5 release, the expected Android metadata is `versionName "1.1.5"` and `versionCode 17`. This task does not create the tag, GitHub Release, signed artifact, or deployment.

## Release procedure

1. Update `versionCode` and `versionName`.
2. Review local music-import documentation and any dependency changes for license/provenance implications.
3. Run the full release checklist.
4. Build from a clean checkout and verify the APK checksum independently.
5. Commit all release changes and create an annotated source tag matching `v<versionName>`.
6. Push the tag to run the protected GitHub release workflow.
7. Verify the GitHub Release contains the APK, checksum, source tag, and release notes.
8. Submit the same source/tag and documented build instructions to IzzyOnDroid.
9. If rollback is required, publish a newer corrective version. Do not reuse or move an existing release tag.

## v1.1.5 local release notes

This section records the release notes prepared from the current repository state. It is not a claim that v1.1.5 has been tagged, published, or deployed.

### Highlights
- AI troubleshooting responses are normalized across provider response shapes, concise by default, and retain structured confidence/source metadata.
- RAG and AI Search diagnostics expose retrieval usage, provenance, source identifiers, request/failure information, and fail-closed reasons without treating retrieved text as trusted instructions.
- Source handling applies robots.txt checks, HTTPS/allowlist/path validation, redirect revalidation, bounded responses, timeouts, and SSRF/private-network protections.
- Firebase email-link authentication is prepared in an isolated, encrypted, explicitly opt-in layer; the active login flow remains unchanged.
- Repository/security ignore rules keep Firebase configuration, signing credentials, generated bundles, build/runtime artifacts, and local secrets out of source control.
- Static/runtime checks and Android debug unit-test/debug-assembly gates are documented for validation.

### Explicitly not included
- Admin ingestion and manual knowledge promotion remain disabled.
- Web research remains disabled; no provider, model, budget, or production integration is included.
- RAG/AI Search production enablement, knowledge population, and deployment remain separate approved operational work; no production claim is made here.
- No GitHub release, tag, signed APK/checksum publication, deployment, or feature-flag enablement was performed.

## v1.1.4 release description

Project RETR0 installer v1.1.4 expands RR3 asset management with richer car-model media workflows, stronger Community Hub integration, and more resilient Shizuku-powered installation services. This release is suitable as the GitHub Release and IzzyOnDroid-facing summary; replace the checksum placeholder with the value generated from the signed APK before publishing.

Project RETR0 installer is a user-authorized RR3 utility: it does not self-update, and privileged file operations require Shizuku permission. Back up game data before applying changes and use the app's revert flow where available.

### Highlights
- Car-model media selection, previews, removal, cover assignment, and Community Hub uploads.
- Profile-picture editing and persisted Community Hub identity.
- Improved Shizuku authorization, reconnect/retry handling, stale-service recovery, and OEM diagnostics.
- Improved car-model discovery, path normalization, backup/rollback, skin image processing, save snapshots, and music-library metadata/artwork handling.
- Additional validation and rollback safeguards for downloads, archives, backups, graphics changes, and remote media.

### Compatibility and known limitations
- Android 8.0/API 26 minimum; target/compile SDK 35.
- Shizuku is required for privileged RR3 operations, and OEM/package support can vary.
- Network access is required for Community Hub, profile pictures, metadata lookup, and remote downloads.
- Music is imported locally or obtained externally; there is no in-app YouTube converter or APK self-updater.
- A release keystore is required to produce the signed APK.

## Release notes template

```markdown
## Project RETR0 installer vX.Y.Z

- Source commit: `<commit SHA>`
- Minimum Android version: Android 8.0 (API 26)
- Supported RR3 packages: `com.ea.games.r3_row`, `com.ea.games.r3_na`
- Distribution: one signed APK for GitHub and IzzyOnDroid
- Shizuku is required for privileged installation operations.
- Back up game data before applying asset changes. Use the app's revert flow where available.
- Music is downloaded or converted externally and imported from local storage; the app does not provide an in-app YouTube converter.
- Community Hub and profile-picture features use the documented remote service.
- Known limitations: `<list>`
- SHA-256: `<checksum>`
```
