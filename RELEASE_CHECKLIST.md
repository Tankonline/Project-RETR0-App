# Project RETR0 Release Checklist

## v1.1.7 Release
- [x] Version metadata is `versionName "1.1.7"` and `versionCode 19`.
- [x] Changelog documents canonical car-model cover fallback, image request diagnostics, decoded Coil success rendering, verification gates, and inactive/unreleased features.
- [x] README and release guidance describe v1.1.7 accurately.
- [x] IzzyOnDroid metadata includes v1.1.7/versionCode 19 and keeps historical builds.
- [x] No Firebase/account, Cloudflare, AI/web research, admin ingestion, production flags, secrets, generated files, runtime artifacts, or unrelated features were enabled by the release-preparation edits.
- [ ] Debug unit tests pass: `./gradlew :app:testDebugUnitTest`.
- [ ] Debug APK assembles: `./gradlew :app:assembleDebug`.
- [ ] Static checks pass: `./gradlew :app:lintDebug` and editor inspections have no new errors.
- [ ] Diff audit confirms only intended source metadata/documentation changes; no secrets or generated/runtime artifacts are included.
- [ ] Manual Community Hub/device smoke test: car-model covers render from canonical Worker routes, missing `coverUrl` fallback works, Coil success content is visible, and image diagnostics/retry paths are truthful.
- [ ] Manual Shizuku test on a supported device: unavailable binder, denied permission, delayed bind, retry, reconnect, and binder death provide truthful feedback.
- [ ] Full functional smoke test on Android 8.0+ with supported ROW and NA RR3 package variants.
- [ ] Release signing environment is available and `:app:assembleRelease` passes.
- [ ] Signed APK SHA-256 checksum is generated and independently verified.
- [ ] Pull request/required CI checks pass.
- [ ] Release tag `v1.1.7` points to the merged release commit.
- [ ] Signed APK and checksum are published.

### Explicitly inactive or unreleased
- Firebase email-link/Google authentication remains prepared but inactive.
- AI/web research and RAG help remain backend-dependent; no new AI capability is enabled by v1.1.7.
- Admin ingestion remains a guarded server-side workflow; no production ingestion flag is enabled.
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
