# Project RETR0 Release Checklist

## v1.1.3 Release
- [ ] Version metadata is `versionName "1.1.3"` and `versionCode 15`.
- [ ] Car-model media wizard tested: cover, screenshots, remove, set cover, and horizontal scrolling.
- [ ] Car-model Community Hub upload tested with cover and screenshots.
- [ ] Worker media validation and R2 verification tested.
- [ ] Pull request merged and required CI checks passed.
- [ ] Release tag `v1.1.3` points to the merged master commit.
- [ ] Signed APK and SHA-256 checksum published.

## v1.0.5 Release
- [x] Version metadata updated to `versionName "1.0.5"` and `versionCode 7`.
- [x] APK self-updater removal verified in current master.
- [x] Debug unit tests passed.
- [x] Debug APK assembly passed.
- [x] Debug lint passed.
- [x] Release workflow and signed APK publication completed for tag `v1.0.5`.
- [x] Release APK launch verified on a Samsung Galaxy A16 running Android 16/API 36.
- [x] Release APK contains `com.retr0.projectretr0.MainActivity` after R8 shrinking.

## Repository Cleanliness
- [ ] Source tree clean (no `.apk`, `.zip`, `.log`, or temporary artifacts).
- [ ] No IDE metadata (`.idea/`, `.iml`) in version control.
- [ ] No generated build artifacts (`build/`, `.cxx/`, `.artifacts/`).
- [ ] No secrets, API keys, or private tokens committed.
- [ ] All documentation updated (README, CHANGELOG, LICENSE, PRIVACY, SECURITY, CONTRIBUTING, CODE_OF_CONDUCT, THIRD_PARTY_LICENSES).

## Build and Configuration
- [ ] `versionName` and `versionCode` incremented in `app/build.gradle.kts`.
- [ ] Clean build successful: `./gradlew clean`.
- [ ] Debug build successful: `./gradlew :app:assembleDebug`.
- [ ] Release build successful (local check): `./gradlew :app:assembleRelease` (requires local signing env).
- [ ] Unit tests passed: `./gradlew :app:testDebugUnitTest`.
- [ ] R8/Minification and resource shrinking enabled for release.
- [ ] All required ABIs included (arm64-v8a, armeabi-v7a, x86, x86_64).

## Functional Testing
- [ ] Fresh installation and startup on Android 8.0+.
- [ ] RR3 detection (Global and North America).
- [ ] Shizuku authorization flow.
- [ ] Asset installation via streaming extraction.
- [ ] Backup and revert workflows.
- [ ] Community Hub: list, download, upload, like, report, delete.
- [ ] Music import: local audio selection, metadata/artwork editing, library save, and slot swapping.
- [ ] Profile picture management (identity-bound).
- [ ] App Links (deep linking) verification.

## Security and Privacy
- [ ] No hard-coded credentials in the APK.
- [ ] Signing key verified and stored securely.
- [ ] Privacy policy accurately reflects data handling.
- [ ] Installation ID excluded from Android backups.
- [ ] All remote endpoints use HTTPS.

## Release and Distribution
- [ ] APK SHA-256 checksum generated.
- [ ] Release notes prepared for GitHub.
- [ ] Git tag matching `v<versionName>` created.
- [ ] GitHub Release configured with APK and checksum.
- [ ] IzzyOnDroid metadata and compatibility audited.
