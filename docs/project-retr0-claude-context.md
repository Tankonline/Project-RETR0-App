# Project RETR0 — Claude Context

> Repository-derived engineering context for AI assistants. This document intentionally excludes secrets, private keys, tokens, `google-services.json` contents, generated artifacts, runtime data, and sensitive local machine paths.
>
> **Evidence labels:** **Verified** means directly supported by checked-in source/resources/config/docs. **Prepared/inactive** means present in code or build configuration but explicitly gated, not wired into the active UI, empty, or otherwise not proven active. **Documented intent** means described by repository documentation but not necessarily re-verified by a live device run.

## 1. Product identity and scope

**Project RETR0 installer** is an Android-native utility for user-authorized management of Real Racing 3 (RR3) assets: installation, restore/recovery, car skins, car-model media, saves, music, graphics presets, diagnostics, and optional Community Hub sharing. Its design goal is to work with modern Android storage restrictions without making the app itself a conventional root app; privileged operations are delegated through Shizuku.

- Namespace/application ID: `com.retr0.projectretr0` — **verified**, `app/build.gradle.kts`, `MainActivity`.
- Current checked-in build metadata: versionCode `18`, versionName `1.1.6`, min/target/compile SDK `26/35/35` — **verified**, `app/build.gradle.kts`.
- Release docs and metadata are aligned to v1.1.6/versionCode 18; older versions remain historical entries.
- Supported RR3 package targets: `com.ea.games.r3_row` (Global/ROW) and `com.ea.games.r3_na` (North America) — **verified**, manifest and installer/service constants.
- License/product distribution claims are documented in `README.md`; verify repository license files before publishing.

Primary references: [`README.md`](../README.md), [`app/build.gradle.kts`](../app/build.gradle.kts), [`MainActivity`](../app/src/main/java/com/retr0/projectretr0/MainActivity.kt), [`AndroidManifest.xml`](../app/src/main/AndroidManifest.xml).

## 2. Architecture

### Presentation and navigation

- Single large `ComponentActivity` with Jetpack Compose Material 3 UI; `MainActivity.AppMode` models Selection, Dashboard, Community, Skin Mods, AI Chat, experimental graphics, music slots, storage, music, graphics, saves, and diagnostics.
- Navigation is state-based in `MainActivity`, with `modeReturn`, `openMode`, `goBack`, and `NavStateCodec` saving/restoring mode and back history through `onSaveInstanceState`.
- UI is split into feature packages: `ui/carmodels`, `ui/skins`, `ui/storage`, `ui/components`, and `ui/theme`.
- Compose theme/colors/type are in `ui/theme/*`; reusable retro controls are in `ui/components/RetroUi.kt`.

### Services and long-running work

- `Retr0InstallService` is an Android non-exported foreground `dataSync` service. It owns a `MutableStateFlow<InstallProgress>`, receives commands such as install, safe test, repair, and graphics operations, and exposes a local binder to `MainActivity`.
- `Retr0UserService` is a Shizuku UserService implementing generated AIDL `IRetr0UserService`. It performs privileged filesystem/native operations in the Shizuku-authorized process.
- `BackupWorker` uses WorkManager for scheduled Smart Backup. The UI stores preference state in `retr0_prefs`; the worker retries if Shizuku is unavailable or binding times out.
- `Retr0Installer` orchestrates manifests, downloads, extraction, hashes, backups, save/music/community operations, AI calls, and graphics delegation.

### Data and transport

- Room is used for local skin/import state and download chunks (`SkinDatabase`, `DownloadDatabase`); KSP generates Room code.
- OkHttp is used by Community Hub clients; embedded Cronet is configured for a high-performance network path/cache (`NetworkStack`).
- Native CMake/C++17 builds `libsonic-extractor.so`, used for streaming archive extraction through pipes/AIDL.
- `DocumentFile`/SAF is used for user-selected folder/file imports; local files are staged before validation/application.

## 3. User-facing features and workflows

### Startup/system status

**Verified:** The app checks Shizuku binder/permission/service state, available storage, RR3 package paths, and background install state. Android 13+ requests `POST_NOTIFICATIONS`. Startup may request exemption from battery optimizations, but this is optional and failures do not block startup. UI state is restored after Activity recreation.

### RR3 asset installation

**Verified in code:** `Retr0Installer.installMod()`:

1. Resolves ROW or NA RR3 data root through the privileged service.
2. Fetches/pre-fetches an HTTPS installation manifest from the configured manifest host, with a Community Worker/booster fallback.
3. Filters required non-APK files and validates split archives.
4. Calculates compressed/uncompressed storage requirements plus safety buffer.
5. Checks free space.
6. Clears target `files`/`cache` directories and recreates them.
7. Downloads files/chunks with progress, retries and backoff.
8. Verifies SHA-256/size.
9. Streams ZIP extraction through Sonic/native extraction where possible; supports split ZIPs.
10. Reports success/failure and cleans temporary data as appropriate.

The destructive wipe means callers should use the app's backup/recovery workflow and should not treat a failed install as harmless.

### Shizuku authorization and lifecycle

**Verified:** `MainActivity` registers Shizuku binder/permission listeners, requests permission, binds a tagged UserService, pings it, checks helper API version, handles stale callbacks/binder death/timeouts, retries while started, and unbinds on destruction. The app does not receive elevated access automatically. `Retr0UserService.isPathAllowed()` canonicalizes paths and restricts operations to:

- RR3 `Android/data` and `Android/obb` roots for ROW/NA;
- `Documents/ProjectRETR0`;
- `Download`;
- the service's own app-root allowance.

The service supports existence/directory checks, create/delete, copy/move/import, native extraction, verified skin backup/import/restore, music operations, and graphics file operations. **Security note:** the service intentionally executes native `rm`/`cp` commands after path validation; changes must preserve canonical path validation and never broaden roots casually.

### Mods/skins

**Verified:** The Community Hub and local skin library support browsing, details, import, image processing/cropping, target matching, manifests, verified-file promotion, apply/revert, uploads, likes, reports, owner deletion, and profile-picture editing. Relevant code includes `SkinRepository`, `SkinApiClient`, `LocalSkinRepository`, `SkinDatabase`, `SkinImageProcessor`, `SkinImportWizard`, `SkinTargetBrowser`, `SkinTransactionCoordinator`, `VerifiedFilePromoter`, and `ui/skins/*`.

Typical local flow:

1. Select/import a skin source through SAF or community details.
2. Validate source/manifest and determine target car/slot.
3. Process images/metadata as needed.
4. Ensure game is stopped where required.
5. Preserve a verified original backup.
6. Apply atomically and verify hash.
7. Revert using the verified backup if needed.

Community skin write flow is explicitly sequenced: register installation → create idempotent draft → upload binary → cover → screenshots (positions 1–4) → optional canonical manifest → publish. Files are hashed and exact lengths/content types are enforced. Read/list/details endpoints do not require authenticated installation context; writes do.

### Car-model media

**Verified:** `ui/carmodels/*` and `data/carModels/*` implement a separate car-model catalog/import/apply/backup/revert/gallery workflow. Features include:

- Browse remote catalog/details.
- Import a local folder using SAF.
- Detect wrapper/effective depot layouts.
- Stage files with limits and traversal/absolute-path rejection.
- Discover files/manifest metadata, sizes, hashes and supported packages.
- Apply, backup, rollback/revert, remove, cover assignment, previews and Community Hub upload/publish flows.
- Stable `CM-*` error codes with user messages and structured logging.

The checked-in tests show explicit limits (including 100 MiB per-file in the public API test), unsafe path rejection, ordered upload behavior, and rollback-oriented local engine behavior. Treat exact limits as implementation contracts, not assumptions; consult `CarModelLimits`, `CarModelManifest`, and `CarModelApiClient` when changing behavior.

### Saves

**Verified:** `SaveScanner` scans local RR3 save profiles and extracts stats where possible. The UI supports named snapshots, rename/delete/restore, auto-backups, community save listing, upload and download/application workflows. Persistent backup areas are documented in installer constants as `Documents/ProjectRETR0/Backups`; Smart Backup is scheduled through WorkManager and retains up to five automatic snapshots. Community save uploads use installation registration plus signed requests, exact file hashing, metadata/stat fields, and server response/request IDs.

### Music

**Verified:** Music is imported from a user-selected local audio file through Android's picker; the app does not provide an in-app YouTube converter. The library stores tracks under the ProjectRETR0 music area and uses sidecar metadata, artwork processing, iTunes Search metadata lookup, local artwork preference, and remote/community list/upload/download support. Music slot workflows list current RR3 slots, back up slots, swap a library track into a slot, and restore backups. Community music uses signed installation requests and SHA-256/exact file uploads.

Documented user workflow: external download/conversion → choose local file → optional metadata/artwork selection/search → library entry → choose target RR3 music slot → backup/swap → restore if required.

### Graphics

**Verified:** Graphics Hub discovers supported presets/tiers, scans HDR state, applies universal presets, backs up previous state, restores backups/factory state, validates package/path/preset compatibility, and rolls back on publish failure. `GraphicsHubEngineTest` covers idempotence, incompatibility blocking/override, snapshot integrity, tamper refusal, retention, running-game blocking, and HDR level B/C behavior. MainActivity exposes Android quality presets and an experimental graphics mode.

### Diagnostics and repair

**Verified:** Diagnostics exposes directory access, write-access/safe-test checks, log export/clear actions, game-asset repair, and installation/graphics diagnostics. The app records structured log tags such as `RETR0_INSTALLER`, `RETR0_INSTALLER_SVC`, `RETR0_SHIZUKU_DIAG`, `RETR0_AI`, and graphics tags. `docs/shizuku-infinix-diagnostics.md` documents expected lifecycle checkpoints and says physical OEM validation remains required.

### Storage

**Verified:** Storage UI/ViewModel reports cached/local ProjectRETR0 content, backup and staging usage, free space, and clearable temporary data. `StorageRepository` has snapshot caching/invalidation contracts and represents unavailable Shizuku reads as unreadable rather than falsely empty.

### App links

**Verified:** HTTPS auto-verify links for Community Hub skin/save/music paths route into Community mode and select the relevant tab/detail ID. Applying/downloading remains user initiated and still requires network/storage/Shizuku as applicable.

## 4. Authentication and identity models

### Active Community Hub identity

**Verified:** The app creates a device/installation-specific identity via `SkinInstallationIdentity`. Community write requests register the installation public JWK, then sign requests using method/path/body hash plus timestamp/nonce/signature headers. This is not a traditional user account/password model. Installation identity, profile pictures, imported skins and related identity-bound data are excluded from backup/device transfer by the backup rules/policy.

The clients intentionally log only presence/metadata of authentication fields; signatures, nonces, tokens, keys and request bodies must not be logged.

### Firebase email-link layer

**Prepared/inactive:** `auth/EmailLinkAuthManager.kt` includes Firebase email-link sign-in, encrypted pending-email storage, token refresh, admin-claim inspection, sign-out, and Google credential linking. Its `enabled` default is `false`; comments state it is not constructed by the active login flow. `EmailLinkHandlerActivity` only cancels/finishes while inactive. Firebase Auth dependency and Google services configuration exist, but no active user-facing authentication flow is verified. Never describe email-link/Google login as available unless this gate is deliberately activated and integrated.

### No secrets in this document

The repository contains a Firebase configuration file and build-time release signing hooks. This document does not reproduce them. Release keystore values are environment variables (`RELEASE_STORE_FILE`, `RELEASE_STORE_PASSWORD`, `RELEASE_KEY_ALIAS`, `RELEASE_KEY_PASSWORD`) and must remain outside source control.

## 5. AI troubleshooting and RAG integration

**Verified in source, but backend-dependent:** Diagnostics has `ASK AI FOR HELP`; `MainActivity` calls `Retr0Installer.troubleshootError`. An AI chat mode calls `chatWithAi`, but both currently send only the latest user message to the same `/troubleshoot` endpoint; `historyCount` is metadata, not a serialized conversation history.

Request contract:

```json
{
  "error": "user message or error text",
  "device": "manufacturer/model and Android release",
  "type": "troubleshoot | general"
}
```

The request is signed using the installation identity and sent over HTTPS with `X-Installation-ID`, `X-Timestamp`, `X-Nonce`, and `X-Signature`. Timeouts are 15 seconds for troubleshooting and 20 seconds for chat. Responses are parsed into `AiResponse`:

- `answer` or `response` (required, normalized and truncated for UI);
- `confidence`;
- `trustedSources` or `sources`;
- `researchSources`;
- `followUpQuestion`.

RAG/research is represented by source arrays and logs (`rag_used`, `research_used`); the Android client does not itself retrieve documents. Therefore RAG availability, source correctness and model behavior are server-side concerns and cannot be claimed from the client alone. The client strips source/citation sections from the displayed answer, shows a concise answer, and avoids logging prompts, response bodies, signatures, hashes or device identity. Relevant tests: `AiResponseParserTest`, `AiDiagnosticsContractTest`, `TroubleshootAuthContractTest`.

## 6. Storage, API and data contracts

### Local storage

- Room: imported skins and download/chunk persistence.
- Internal app cache: temporary downloads, extraction staging, upload buffers.
- Shared/public ProjectRETR0 areas: documented backups, music backups and music library under `Documents/ProjectRETR0/...`; access is mediated by the Shizuku service for privileged paths.
- Shared preferences: `retr0_prefs` for Smart Backup, graphics HDR level, last backup status and related UI state.
- Encrypted SharedPreferences: inactive email-link pending email only.
- Android saved-instance state: navigation mode/back-stack encoding.
- Backup/data-extraction XML excludes installation identity/profile/imported content as documented; inspect rules before changing privacy behavior.

### Remote endpoints (non-secret inventory)

- Community/installer Worker base URL is configured in BuildConfig and documented as the production HTTPS Worker.
- Manifest/content host is an HTTPS R2 public host used for RR3 installer manifests/content.
- Optional external hosts include DiceBear avatar fallback, iTunes Search metadata, and user-selected support/channel links. Exact endpoint inventory is in `docs/izzyondroid-compatibility.md`.
- Community API paths include `/v1/installations`, `/v1/skins`, `/v1/saves`, `/v1/music`, car-model routes, and `/troubleshoot`.

### Request integrity

Community mutation APIs use HTTPS, installation registration, canonical method/path/body hashing, timestamp/nonce/signature headers, idempotency keys for draft creation, content-length/hash checks, and request IDs. API errors are parsed into typed exceptions with status/error/duplicate/request IDs. Remote content remains untrusted.

### Installer manifest/file contract

Manifest entries carry required/type/name/URL/size/uncompressed size/hash/chunks/parts. Split archives require ordered, safe parts and matching sizes/hashes. `HashVerifier`, `VerifiedFilePromoter`, `SplitZipSupport`, `ArchiveExtractorSecurityTest`, and related tests enforce integrity/traversal rules.

## 7. Permissions, device and offline behavior

### Declared permissions

`AndroidManifest.xml` declares INTERNET, network/Wi-Fi state, foreground service/data sync, POST_NOTIFICATIONS, WAKE_LOCK, REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, and Shizuku API permission. Package visibility queries include both RR3 variants and Shizuku manager.

### Offline behavior

- The app can open local UI, browse local imports/library/storage, and inspect some local state without network.
- Community catalog/details, remote downloads/uploads, external metadata, profile/avatar fallback, AI, and installer manifest/content require network.
- Privileged RR3 reads/writes require a live, authorized Shizuku service even when offline.
- Background Smart Backup retries or reports failure if Shizuku is unavailable; it does not silently gain privilege.
- There is no APK self-updater and no runtime executable download mechanism documented as active.

### Device variance

Minimum API is 26, but behavior depends on OEM storage, Android version, Shizuku startup method, RR3 package variant, free space, battery/background policies and native ABI. Declared ABIs are armeabi-v7a, arm64-v8a, x86 and x86_64. Physical Infinix/XOS validation remains an explicitly open diagnostic gate.

## 8. Build/release configuration

- Gradle Kotlin DSL; root plugin setup in `build.gradle.kts`/`settings.gradle.kts`.
- AGP/Kotlin/dependency versions are pinned in `gradle/libs.versions.toml`.
- Java/Kotlin target 17; CMake 3.22.1; C++17; native build enabled.
- Compose, AIDL and BuildConfig enabled; Room schema directory configured.
- Debug: no minification, production Community Worker URL, empty API-key build field.
- Release: minification/resource shrinking enabled, production Worker URL, release signing config from environment, ProGuard/R8 rules in `app/proguard-rules.pro`.
- Release artifact is one signed APK for distribution; docs prescribe GitHub Releases/IzzyOnDroid. No in-app self-update.
- `google-services.json` exists for Firebase wiring but is intentionally not reproduced here.
- Release documentation and Gradle are aligned on v1.1.6/versionCode 18; older version references are historical.

## 9. Security constraints and safe-change rules

1. Preserve explicit Shizuku authorization and binder/service lifecycle checks.
2. Never broaden `Retr0UserService` allowed roots without a threat-model review.
3. Canonicalize and validate every path; reject traversal, absolute paths, symlinks/unsafe archive entries and unexpected file types.
4. Keep downloads HTTPS-only and do not weaken TLS validation.
5. Verify exact sizes and SHA-256 before promotion/apply/restore.
6. Keep apply/revert transactional: backup → stage → atomic publish → verify → rollback on failure.
7. Treat Community Hub files, manifests, media, screenshots and car-model packages as untrusted.
8. Do not log auth headers, installation IDs, signatures, nonces, body hashes, prompts, unrestricted server bodies, cookies or private content.
9. Keep release signing material, Firebase config secrets, API credentials and tokens out of source/docs/issues.
10. Do not turn inactive Firebase/email-link authentication on accidentally; it changes the identity/privacy model.
11. Preserve backup/data-extraction exclusions for identity-bound and imported content unless privacy policy is updated.
12. Avoid destructive installer changes without a tested recovery path and a real-device test.

## 10. Tests and verification

### Unit tests

The repository has broad JVM coverage for navigation, inactive auth contracts, car-model API/manifest/layout/engine/upload/error codes, AI response normalization/auth contracts, skin API/manifest/repository/uploads, verified file promotion, storage caching, archive traversal, split ZIPs, music artwork/sidecars, graphics engine/HDR patching, crop/image processing, target matching and import workflows. Test locations are under `app/src/test/java/com/retr0/projectretr0/**`.

### Instrumentation tests

`app/src/androidTest` includes a basic application-context test and `SkinDatabaseTest` covering Room persistence round trips. Device/emulator execution is required for instrumentation.

### Commands

Documented commands include:

```text
./gradlew test
./gradlew :app:testDebugUnitTest
./gradlew connectedAndroidTest
./gradlew :app:assembleRelease
```

On Windows use `gradlew.bat`. Do not run release signing commands with secrets in chat or commit them.

### What tests do not prove

Tests are mostly pure/local and mocked/fake-engine based. They do not prove Community Worker production availability, RAG quality, Shizuku behavior on every OEM, RR3 compatibility on physical devices, native extraction performance on large real archives, release signing correctness, or successful end-to-end upload/download against production.

## 11. Known limitations and open questions

- Current Gradle version is 1.1.6/versionCode 18; v1.1.5 and earlier entries are historical.
- Firebase email-link/Google auth is prepared but inactive; active Community identity is installation-based.
- AI chat does not send full prior message content despite accepting a history list; only the latest user message is posted.
- AI/RAG quality and backend availability are not guaranteed offline or by client tests.
- Music acquisition is intentionally external; no in-app YouTube converter is active.
- Community/metadata/AI/avatar functions need network.
- Shizuku and OEM storage/package behavior vary; physical Infinix/XOS diagnostic validation remains pending per docs.
- Native release strip and manifest merge warnings are documented as non-blocking but unresolved.
- Some documentation references files/endpoints/components that must be rechecked against the current checkout before publishing.
- The app handles destructive game-data operations; backups are recovery aids, not absolute guarantees.
- Car-model and graphics feature contracts are extensive but still depend on compatible RR3 depot layouts, supported packages, enough storage, and a stopped game where required.

## 12. Safe maintenance checklist for Claude

Before changing production behavior:

- Read the relevant feature package and its tests together.
- Preserve verified/inactive labels in docs and UI claims.
- Keep secrets and generated/runtime data out of commits and context output.
- Update API contract tests when request/response shapes change.
- Add or update rollback/hash/path-validation tests for file operations.
- Run unit tests for affected packages; use a real device for Shizuku, foreground-service, native, storage and RR3 workflows.
- Reconcile Gradle version, release docs, changelog and distribution metadata before release.
- Do not enable Firebase auth, staging endpoints, flags or self-update behavior without an explicit product/security decision.
