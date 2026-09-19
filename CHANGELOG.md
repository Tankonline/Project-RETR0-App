# Changelog

All notable changes to Project RETR0 installer are documented here.

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
