# Releasing Project RETR0 installer

## v1.1.9 preparation status

The current checkout is prepared for Android release `v1.1.9` with `versionCode 21`. This hotfix updates Community Hub save downloads to signed installation authentication, adds bounded installation registration retry for AI troubleshooting, sanitises downloaded save names, prevents duplicate-name overwrites, and uses bounded atomic downloads.

## v1.1.9 release gates

1. Confirm `versionName "1.1.9"` and `versionCode 21` in Gradle and metadata.
2. Run unit tests, `assembleDebug`, and lint/static checks.
3. Audit the diff for only hotfix, version, documentation, and metadata changes.
4. Complete the device Community Hub and AI smoke tests.
5. Push the release branch and open the PR only after approval.
6. Merge only after approval.
7. Create and push `v1.1.9` only after separate approval.

## v1.1.8 preparation status

The current checkout is prepared for Android release `v1.1.8` with `versionCode 20`. Community Hub car-model media now falls back to the canonical Worker cover route when `coverUploaded` is true but `coverUrl` is absent, canonicalizes stale/foreign cover URLs, logs safe image request diagnostics, and renders Coil's decoded `SubcomposeAsyncImage` success content.

This release includes the v1.1.8 car-model download/import pipeline, Downloaded Mods UI, multi-vehicle and add-on package handling, stronger backup/restore verification, Shizuku reconnect hardening, atomic skin operations, and native Sonic archive-safety improvements. It does not enable new Firebase/account authentication, production Cloudflare behavior, AI/web research, admin-ingestion flags, or runtime-update behavior. Firebase email-link/Google authentication remains prepared but inactive; AI/RAG help remains backend-dependent; admin ingestion remains guarded server-side; music acquisition remains external; and no APK self-updater is released.

## v1.1.8 change summary

The Android release contains the following functional areas:

- Community Hub car-model download plans with manifest/file checksums, size limits, free-space checks, resumable verified downloads, optional media, and safe library import.
- Downloaded Mods navigation for downloaded Community Hub skins and car models.
- Multi-vehicle package inspection, shared-root collision detection, ADDON/REPLACE handling, backup-first application, stale-file cleanup, and rollback/revert behavior.
- Import staging and manifest validation that keeps picked folders out of RR3 until the user explicitly applies a verified model.
- Shizuku lifecycle hardening for binder death, delayed binding, permission state, reconnect throttling, and pending skin-operation recovery.
- Atomic skin replacement and restoration with pre/post SHA-256 verification.
- Native Sonic extractor hardening for unsafe paths, ZIP64/data-descriptor handling, compression checks, and secure directory creation.
- Worker contract validation for asset-backed downloads, checksums, cover/screenshots, R2 object availability, and related AI/diagnostics response schemas.

The release does not introduce Firebase account sign-in, an APK self-updater, an in-app music downloader, or an enabled production admin-ingestion flag.

## One canonical APK

Each release produces one signed APK, `app-release.apk`. That same artifact may be attached to the GitHub Release and used for the IzzyOnDroid submission. The app does not contain a separate GitHub or F-Droid product flavor.

The APK does not self-update. Users receive app updates from GitHub or IzzyOnDroid. Music is obtained externally and imported from local storage; no in-app YouTube converter or bundled media downloader is released.

## Prerequisites

- A clean checkout of the release commit.
- Monotonic `versionCode` and semantic `versionName` in `app/build.gradle.kts`.
- A release keystore stored outside the repository.
- All unit tests, debug build, lint/static checks, and release build passing.
- Review of `CHANGELOG.md`, `README.md`, `RELEASE_CHECKLIST.md`, `docs/izzyondroid-compatibility.md`, and `docs/privacy-and-community-policy.md`.
- Manual dashboard and Shizuku smoke testing on supported devices.

## PowerShell verification commands

Run from the repository root:

```powershell
Set-Location 'C:\Users\armaa\AndroidStudioProjects\ProjectRETR0installer'
& .\gradlew.bat :app:testDebugUnitTest
& .\gradlew.bat :app:assembleDebug
& .\gradlew.bat :app:lintDebug
& .\gradlew.bat :app:compileDebugKotlin
```

For a signed release build, set signing values only in your shell or secure CI secret store:

```powershell
$env:RELEASE_STORE_FILE = 'C:\secure\project-retr0-release.keystore'
$env:RELEASE_STORE_PASSWORD = '<secure value>'
$env:RELEASE_KEY_ALIAS = '<secure alias>'
$env:RELEASE_KEY_PASSWORD = '<secure value>'

& .\gradlew.bat :app:assembleRelease
Get-FileHash .\app\build\outputs\apk\release\app-release.apk -Algorithm SHA256
```

The debug output is `app/build/outputs/apk/debug/app-debug.apk`; the signed release output is `app/build/outputs/apk/release/app-release.apk`.

## GitHub Actions release secrets

Configure protected secrets only in CI:

- `RELEASE_KEYSTORE_BASE64`
- `RELEASE_STORE_PASSWORD`
- `RELEASE_KEY_ALIAS`
- `RELEASE_KEY_PASSWORD`
- `FIREBASE_GOOGLE_SERVICES_JSON_BASE64`

The existing workflow validates the version tag against `versionName`, runs unit tests, builds a signed release APK, creates a checksum, and publishes release assets. It is not run or changed as part of this preparation task.

## Release gates for v1.1.8

1. Confirm `versionName "1.1.8"` and `versionCode 20` in Gradle and metadata.
2. Run unit tests, `assembleDebug`, lint/static checks, and a signed `assembleRelease`.
3. Inspect the diff for documentation-only/version metadata scope; reject secrets, generated files, runtime artifacts, and unrelated flag changes.
4. Manually verify dashboard navigation, the **Install Project RETR0** label, status refresh, and Shizuku unavailable/denied/delayed/retry/reconnect paths.
5. Verify supported RR3 ROW and NA operations and recovery behavior on target devices.
6. Generate and independently verify the signed APK SHA-256 checksum.
7. Only after approval may a maintainer commit, create annotated tag `v1.1.8`, push it, and run the protected workflow.

Do not commit, tag, push, or create a GitHub release during release preparation.

## Known limitations

- Android 8.0/API 26 minimum; target/compile SDK 35.
- Shizuku is required for privileged RR3 operations, and OEM/package support can vary.
- Community Hub, profile pictures, metadata lookup, AI help, and remote downloads require network access.
- Firebase email-link/Google authentication is prepared but inactive.
- AI/RAG quality and backend availability are not guaranteed offline.
- Music is imported locally or obtained externally; there is no in-app YouTube converter or APK self-updater.
- A release keystore is required to produce the signed APK.
