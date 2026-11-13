# Project RETR0 Release Checklist

## v1.1.6 Release
- [x] Version metadata is `versionName "1.1.6"` and `versionCode 18`.
- [x] Changelog documents the dashboard fix, **Install Project RETR0** rename, Shizuku feedback/retry hardening, tests/build gates, and inactive/unreleased features.
- [x] README and release guidance describe v1.1.6 accurately.
- [x] IzzyOnDroid metadata includes v1.1.6/versionCode 18 and keeps historical builds.
- [x] No Firebase, Cloudflare, AI flags, secrets, generated files, runtime artifacts, or new features were enabled by the release-preparation edits.
- [ ] Debug unit tests pass: `./gradlew :app:testDebugUnitTest`.
- [ ] Debug APK assembles: `./gradlew :app:assembleDebug`.
- [ ] Static checks pass: `./gradlew :app:lintDebug` and editor inspections have no new errors.
- [ ] Diff audit confirms only intended source metadata/documentation changes; no secrets or generated/runtime artifacts are included.
- [ ] Manual dashboard smoke test: dashboard opens, status is accurate, and the primary button reads **Install Project RETR0**.
- [ ] Manual Shizuku test on a supported device: unavailable binder, denied permission, delayed bind, retry, reconnect, and binder death provide truthful feedback.
- [ ] Full functional smoke test on Android 8.0+ with supported ROW and NA RR3 package variants.
- [ ] Release signing environment is available and `:app:assembleRelease` passes.
- [ ] Signed APK SHA-256 checksum is generated and independently verified.
- [ ] Pull request/required CI checks pass.
- [ ] Release tag `v1.1.6` points to the merged release commit.
- [ ] Signed APK and checksum are published.

### Explicitly inactive or unreleased
- Firebase email-link/Google authentication remains prepared but inactive.
- AI/RAG help remains backend-dependent; no new AI capability is enabled by v1.1.6.
- Music acquisition remains external; no in-app YouTube converter or APK self-updater is released.
- Community, metadata, avatar, graphics, and car-model behavior remains subject to network, RR3 data-contract, OEM, and Shizuku compatibility.

## Repository cleanliness and safety
- [ ] Source tree contains no unintended `.apk`, `.zip`, `.log`, temporary, build, `.cxx`, or generated artifacts.
- [ ] No IDE metadata or secrets/API keys/private tokens are committed.
- [ ] Firebase/Cloudflare/AI build flags and production endpoints are unchanged.

## Release commands (PowerShell)
```powershell
Set-Location 'C:\Users\armaa\AndroidStudioProjects\ProjectRETR0installer'
& .\gradlew.bat :app:testDebugUnitTest
& .\gradlew.bat :app:assembleDebug
& .\gradlew.bat :app:lintDebug
& .\gradlew.bat :app:compileDebugKotlin
& .\gradlew.bat :app:assembleRelease
Get-FileHash .\app\build\outputs\apk\debug\app-debug.apk -Algorithm SHA256
Get-FileHash .\app\build\outputs\apk\release\app-release.apk -Algorithm SHA256
```

Do not commit, tag, push, or create a GitHub release as part of this preparation task.
