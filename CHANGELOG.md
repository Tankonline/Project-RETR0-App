# Changelog

All notable changes to Project RETR0 installer are documented here.

## [1.1.6]

### Fixed
- Fixed the dashboard's primary installation action and renamed it to **Install Project RETR0** so the action is explicit and consistent with the product name.
- Fixed dashboard status handling so the UI does not report a privileged service as ready until a live Shizuku UserService binder has connected.

### Changed
- Hardened Shizuku feedback and recovery: binder/permission preflight, timeout diagnostics, stale-callback and binder-death handling, explicit retry/backoff, and a user-visible refresh/retry path now remain aligned.
- Preserved the dashboard's storage, graphics, diagnostics, music, saves, and privileged-service status cards while improving the dashboard's refresh behavior.

### Verification
- Debug unit tests, `assembleDebug`, lint/static analysis, and release documentation checks are tracked in `RELEASE_CHECKLIST.md`; their final status must be recorded from the current checkout before publication.
- This release does not enable new Firebase, Cloudflare, AI, authentication, or runtime-update behavior.

### Compatibility and limitations
- Minimum Android version remains Android 8.0/API 26; target and compile SDK remain 35.
- Shizuku is required for privileged RR3 file and package operations; OEM behavior, startup method, and RR3 package variant can affect availability.
- Community Hub, profile pictures, metadata lookup, AI help, and remote downloads require network access and their configured services.
- Firebase email-link/Google authentication remains prepared but inactive; it is not a release feature or a change to the active installation identity model.
- AI/RAG help remains backend-dependent and is not guaranteed offline; this release does not add or enable an AI capability.
- Music is imported from local storage or obtained externally; the app does not provide an in-app YouTube converter or APK self-updater.
- Graphics and car-model workflows are present where supported by the current RR3 data contracts, but device/package compatibility still requires validation.
- The release still requires a configured release keystore; no signing credentials are stored in the repository.

## [1.1.5]

### Release notes
- Current source metadata was advanced to `versionCode 17` / `versionName 1.1.5`; see v1.1.6 for the next documented release.

## [1.1.4]

### Added
- Added the car-model media workflow for selecting a cover and up to eight screenshots, with thumbnail previews and removal controls.
- Added Community Hub cover and screenshot uploads for car models.
- Added profile-picture editing and persistence for Community Hub identity.
- Added graphics-tier controls and restore support through the graphics hub.

### Changed
- Improved Shizuku authorization and privileged-service recovery, including explicit API_V23 declaration, reconnect handling, timeout/retry behavior, and diagnostics for affected OEM devices.
- Improved car-model search, remote file indexing, manifest/layout detection, path normalization, backup, rollback, and upload flows.
- Improved skin image processing with crop support, WebP conversion, size limits, duplicate detection, and persisted integrity metadata.
- Improved save snapshots, music library import/metadata/artwork handling, and background backup state reporting.

### Fixed
- Prevented duplicate vehicle-folder nesting during local and Community Hub car-model installation.
- Preserved lowercase vehicle directory names and corrected Applied/My Models state after removal or revert.
- Added validation and rollback safeguards around downloads, archives, backups, graphics changes, and remote media.

### Compatibility and limitations
- Minimum Android version remains Android 8.0/API 26; target and compile SDK remain 35.
- Shizuku is required for privileged RR3 file and package operations; behavior also depends on the RR3 package variant and OEM Shizuku support.
- Community Hub, profile pictures, metadata lookup, and remote downloads require network access and use the configured HTTPS services.
- Music is imported from local storage or obtained externally; the app does not provide an in-app YouTube converter or APK self-updater.
- The release still requires a configured release keystore; no signing credentials are stored in the repository.

## [1.1.3]

### Added
- Added skin-style media management to the car-model import wizard.
- Added car-model cover selection, screenshot selection, thumbnail previews, remove controls, and set-cover actions.
- Added Community Hub cover and screenshot uploads for car models.
- Added explicit vehicle-root metadata for generic RR3 car-model packages.

### Changed
- Car-model media is processed as WebP with the existing crop and size limits.
- Car-model media thumbnails support horizontal scrolling and up to eight screenshots.
- Added duplicate-image detection and persisted media integrity metadata.
- Updated Worker validation for car-model WebP media, size limits, screenshot positions, and R2 integrity.

### Fixed
- Prevented duplicate vehicle-folder nesting during local and Community Hub car-model installation.
- Preserved lowercase vehicle directory names.
- Removed reverted/removed installations from the Applied list while retaining models in My Models.

## [1.1.2]

### Release notes
- Added generic car-model manifest and Community Hub support.
- Added explicit vehicle-root handling for local and remote car-model installations.
- Improved car-model layout detection, path normalization, backup, add-on, revert, and upload workflows.
- Added car-model catalog and local library integration.

## [1.1.1]

### Release notes
- Prepared the Android app release metadata for V1.1.1 (`versionCode 13`).
- Retained the existing V1.1.0 Shizuku authorization and privileged-service reliability improvements.

## [1.1.0]

### Fixed
- Added the Shizuku API_V23 permission declaration required for the runtime authorization flow on affected OEM devices.
- Improved Shizuku permission-result handling and forced UserService reconnection after authorization.
- Preserved stale UserService invalidation, forced rebind recovery, and button-level diagnostics from the V1.0.9 fixes.

### Compatibility
- Improved Shizuku authorization and privileged-service startup behavior on OPPO/ColorOS devices.

## [1.0.9]

### Fixed
- Invalidated stale cached Shizuku UserService instances by incrementing the service version.
- Added forced rebind handling when an OEM silently drops or leaves a UserService bind pending.
- Added button-level Shizuku diagnostics for binder availability, permission state, and bind state.
- Updated the scheduled backup UserService version to avoid stale service reuse.

## [1.0.5]

### Added
- Documented the current release metadata: version `1.0.5`, `versionCode 7`, package/namespace `com.retr0.projectretr0`, and Android API range from API 26 with target/compile SDK 35.
- Documented verified Community Hub App Links for `/v1/skins/<id>`, `/v1/saves/<id>`, and `/v1/music/<id>` on the configured HTTPS host.
- Expanded documentation for RR3 skin apply/revert workflows, verified original-file backups, named save snapshots, Smart Backup scheduling, music slots/library workflows, and community skin/save/music/profile features.

### Changed
- Updated README build and release instructions to match the current Gradle configuration, signed release environment variables, APK output, and checksum step.
- Clarified that Shizuku is required for privileged RR3 file/package operations and that the app does not self-update or replace bundled yt-dlp at runtime.
- Clarified the supported RR3 package identifiers: `com.ea.games.r3_row` and `com.ea.games.r3_na`.

### Fixed
- Preserved Android, Shizuku, foreground-service, and AIDL entry points in minified release builds.
- Updated skin database test fixtures for the current metadata schema.

### Security
- Documented service-side path restrictions, archive-target validation, and SHA-256 verification used for supported privileged file and backup/restore operations.
- Documented that installation identity, profile pictures, and imported skins are excluded from Android cloud backup and device transfer.
- Reiterated that Community Hub uploads are user-selected remote data and must be treated as untrusted content; uploaders remain responsible for distribution rights.

### Compatibility
- Confirmed the release configuration targets Android 35 while retaining Android 8.0/API 26 as the minimum configured platform.
- Documented device/vendor, storage, Shizuku availability, and RR3 variant as compatibility factors.

### Verified
- Confirmed the release APK contains `com.retr0.projectretr0.MainActivity`.
