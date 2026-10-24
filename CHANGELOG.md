# Changelog

All notable changes to Project RETR0 installer are documented here.

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

### Compatibility
- Improved privileged-service recovery on affected OPPO/ColorOS and other OEM devices.


## [1.0.5]

### Added
- Documented the current release metadata: version `1.0.5`, `versionCode 7`, package/namespace `com.retr0.projectretr0`, and Android API range from API 26 with target/compile SDK 35.
- Documented verified Community Hub App Links for `/v1/skins/<id>`, `/v1/saves/<id>`, and `/v1/music/<id>` on the configured HTTPS host.
- Expanded documentation for RR3 skin apply/revert workflows, verified original-file backups, named save snapshots, Smart Backup scheduling, music slots/library workflows, and community skin/save/music/profile features.
- Documented the checked-in native extraction component and the CMake/C++17 build requirements alongside the bundled Cronet, yt-dlp, and FFmpeg dependencies.

### Changed
- Updated README build and release instructions to match the current Gradle configuration, signed release environment variables, APK output, and checksum step.
- Clarified that Shizuku is required for privileged RR3 file/package operations and that the app does not self-update or replace bundled yt-dlp at runtime.
- Clarified the supported RR3 package identifiers: `com.ea.games.r3_row` and `com.ea.games.r3_na`.

### Fixed
- Preserved Android, Shizuku, foreground-service, and AIDL entry points in minified release builds.
- Updated skin database test fixtures for the current metadata schema.

### Security
- Documented service-side path restrictions, archive-target validation, and SHA-256 verification used for supported privileged file and backup/restore operations.
- Documented that installation identity, profile pictures, imported skins, and encrypted YouTube cookies are excluded from Android cloud backup and device transfer.
- Reiterated that Community Hub uploads are user-selected remote data and must be treated as untrusted content; uploaders remain responsible for distribution rights.

### Compatibility
- Confirmed the release configuration targets Android 35 while retaining Android 8.0/API 26 as the minimum configured platform.
- Documented device/vendor, storage, Shizuku availability, RR3 variant, and current YouTube delivery behavior as compatibility factors.

### Verified
- Confirmed the release APK contains `com.retr0.projectretr0.MainActivity`.

## [1.0.2] - 2026-09-19

### Changed
- Removed the obsolete in-app APK self-updater; app updates remain distributed through normal signed releases.
- Updated the application version metadata for the v1.0.2 release.

## [1.0.1] - 2026-09-18

### Changed
- Upgraded Android Gradle Plugin to 9.4.1 for improved build stability.
- Upgraded Kotlin to 2.2.10.

### Fixed
- Corrected Gradle distribution checksum in wrapper configuration.
- Fixed `gradlew` executable permissions for CI environments.
- Synced IzzyOnDroid compatibility documentation with current build tools.

## [1.0.0] - 2026-09-18

### Added
- User-authorized asset management for Real Racing 3 (RR3).
- Support for Global (com.ea.games.r3_row) and North American (com.ea.games.r3_na) packages.
- Shizuku integration for privileged file and package operations.
- Piped/streaming extraction of game assets directly to the device.
- Community Hub for sharing car skins, game saves, and custom music.
- YouTube music import using bundled yt-dlp and FFmpeg.
- Backup and revert system for RR3 game data.
- Installation identity and profile picture management.
- Android App Links for community content.

### Changed
- Consolidated build process into a single canonical release APK.
- Disabled in-app self-updates to comply with IzzyOnDroid/F-Droid policies.
- Disabled runtime yt-dlp binary updates; all dependencies are now bundled.

### Security
- Configured environment-based release signing.
- Implemented secure data extraction and backup rules to protect local identity data.
- Restrict all installer and community downloads to HTTPS.
