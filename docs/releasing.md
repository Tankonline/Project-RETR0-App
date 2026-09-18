# Releasing Project RETR0 installer

## One canonical APK

Each release produces one signed APK, `app-release.apk`. That same artifact may be attached to the GitHub Release and used for the IzzyOnDroid submission. The app does not contain a separate GitHub or F-Droid product flavor.

The APK does not self-update. Users receive app updates from GitHub or IzzyOnDroid. yt-dlp and FFmpeg remain bundled in the APK; update their pinned dependency versions as part of a normal signed app release.

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

The workflow at `.github/workflows/release.yml` validates unit tests, builds `:app:assembleRelease`, creates a SHA-256 checksum, and publishes both files on a semantic version tag such as `v1.0.1`.

## Release procedure

1. Update `versionCode` and `versionName`.
2. Update bundled dependency versions, including yt-dlp, only after reviewing upstream release notes and license/provenance changes.
3. Run the full release checklist.
4. Build from a clean checkout and verify the APK checksum independently.
5. Commit all release changes and create an annotated source tag matching `v<versionName>`.
6. Push the tag to run the protected GitHub release workflow.
7. Verify the GitHub Release contains the APK, checksum, source tag, and release notes.
8. Submit the same source/tag and documented build instructions to IzzyOnDroid.
9. If rollback is required, publish a newer corrective version. Do not reuse or move an existing release tag.

## Release notes template

```markdown
## Project RETR0 installer vX.Y.Z

- Source commit: `<commit SHA>`
- Minimum Android version: Android 8.0 (API 26)
- Supported RR3 packages: `com.ea.games.r3_row`, `com.ea.games.r3_na`
- Distribution: one signed APK for GitHub and IzzyOnDroid
- Shizuku is required for privileged installation operations.
- Back up game data before applying asset changes. Use the app's revert flow where available.
- yt-dlp and FFmpeg are bundled. Their version is updated through normal signed app releases.
- Community Hub and profile-picture features use the documented remote service.
- Known limitations: `<list>`
- SHA-256: `<checksum>`
```
