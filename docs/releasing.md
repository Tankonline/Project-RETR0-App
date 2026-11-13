# Releasing Project RETR0 installer

## v1.1.6 preparation status

The current checkout is prepared for Android release `v1.1.6` with `versionCode 18`. The dashboard's primary action is named **Install Project RETR0**. The dashboard fix keeps status truthful while Shizuku UserService binding is pending or unavailable, and the Shizuku lifecycle now documents feedback, timeout, stale-callback, binder-death, and retry handling.

This preparation does not enable new Firebase, Cloudflare, AI, authentication, or runtime-update behavior. Firebase email-link/Google authentication remains prepared but inactive; AI/RAG help remains backend-dependent; music acquisition remains external; and no APK self-updater is released.

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

## Release gates for v1.1.6

1. Confirm `versionName "1.1.6"` and `versionCode 18` in Gradle and metadata.
2. Run unit tests, `assembleDebug`, lint/static checks, and a signed `assembleRelease`.
3. Inspect the diff for documentation-only/version metadata scope; reject secrets, generated files, runtime artifacts, and unrelated flag changes.
4. Manually verify dashboard navigation, the **Install Project RETR0** label, status refresh, and Shizuku unavailable/denied/delayed/retry/reconnect paths.
5. Verify supported RR3 ROW and NA operations and recovery behavior on target devices.
6. Generate and independently verify the signed APK SHA-256 checksum.
7. Only after approval may a maintainer commit, create annotated tag `v1.1.6`, push it, and run the protected workflow.

Do not commit, tag, push, or create a GitHub release during release preparation.

## Known limitations

- Android 8.0/API 26 minimum; target/compile SDK 35.
- Shizuku is required for privileged RR3 operations, and OEM/package support can vary.
- Community Hub, profile pictures, metadata lookup, AI help, and remote downloads require network access.
- Firebase email-link/Google authentication is prepared but inactive.
- AI/RAG quality and backend availability are not guaranteed offline.
- Music is imported locally or obtained externally; there is no in-app YouTube converter or APK self-updater.
- A release keystore is required to produce the signed APK.
