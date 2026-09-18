# IzzyOnDroid Compatibility Audit

## Scope

This document records the dependency, network, native-code, and runtime-behavior review for the single Project RETR0 installer APK distributed through GitHub and proposed for IzzyOnDroid.

The app does not perform an in-app APK self-update. App updates are delivered through the distribution source. The app also uses the yt-dlp version bundled in the APK and does not download a replacement executable at runtime.

## Build reproducibility

- Build system: Gradle wrapper 9.7.1 with a pinned distribution SHA-256.
- Android Gradle Plugin: 9.4.0.
- Kotlin/JVM target: Java 17.
- Maven repositories: Google Maven and Maven Central only.
- Direct dependency versions are pinned in `gradle/libs.versions.toml`.
- Release signing credentials are read from environment variables and are not committed.
- Native code is built through the checked-in CMake project at `app/src/main/cpp/CMakeLists.txt`.

## Dependency inventory

| Component | Purpose | Source / license review | IzzyOnDroid review note |
| --- | --- | --- | --- |
| AndroidX Compose, Core, Lifecycle, DocumentFile, WorkManager, Room, ExifInterface | Android UI, storage, background work, database, image metadata | AndroidX source repositories, Apache-2.0 | Standard dependencies; verify final artifact provenance during submission. |
| Shizuku API and provider | User-authorized privileged operations | RikkaApps/Shizuku source, Apache-2.0 | Requires clear user disclosure. The app must work safely when permission is absent or denied. |
| OkHttp | Community and installer HTTP networking | square/okhttp source, Apache-2.0 | Standard dependency. HTTPS endpoints only should be maintained. |
| Cronet embedded | Streaming installer downloads | Chromium source, BSD-style licensing | Bundled native dependency. Confirm the exact Maven artifact source and license notices before submission. |
| Coil Compose | Local and remote image loading | coil-kt/coil source, Apache-2.0 | Standard dependency; remote image hosts are disclosed below. |
| youtubedl-android and FFmpeg | User-initiated music import/download processing | junkfood02/youtubedl-android and its bundled components | High-review-risk dependency. Confirm source, codec licensing, and the bundled executable provenance. Runtime yt-dlp updates are disabled. |
| `libsonic-extractor.so` and C/C++ sources | Archive extraction support | Checked-in native source/CMake configuration must remain auditable | Confirm each native library source and license before submission. |

## Network and external behavior

| Endpoint / host | Purpose | User action / behavior |
| --- | --- | --- |
| `https://save-files.armaandeep08-ap.workers.dev` | Community Hub API and installer proxy base URL | Used for community content and installer-related requests. |
| `https://pub-f87b4daf1b344ca39c9ba6de6812a777.r2.dev` | Installer manifest/content host | Used when the user starts RR3 asset installation. |
| `https://api.dicebear.com` | Generated community creator avatar fallback | Used when an avatar is displayed without a local profile image. |
| `https://itunes.apple.com/search` | Music metadata lookup | Used as part of music metadata lookup. |
| `https://discord.gg/projectretr0` | Community support link | Opened only when the user selects it. |
| `https://www.youtube.com/@Retrospect69` | Project channel link | Opened only when the user selects it. |

All streaming installer downloads are restricted to HTTPS by `StreamDownloader`. Any additional remote hosts introduced in future releases must be added to this table before publishing.

## Runtime behavior relevant to distribution review

- RR3 assets, community downloads, and user-selected music/media are user-driven application content operations, not app updates.
- The app may use Shizuku to perform user-requested privileged file and package operations. It does not receive Shizuku access automatically.
- The app does not self-update its APK.
- The app does not replace its bundled yt-dlp executable at runtime.
- Community content can be uploaded, downloaded, liked, reported, and deleted by its owner through the Community Hub service.
- Installation identity, profile pictures, and imported skins are excluded from cloud backup and device transfer by the app backup rules.

## Submission blockers to resolve before requesting inclusion

1. Add a repository-root license file and third-party attribution notice covering all bundled native, FFmpeg, yt-dlp, and Cronet components.
2. Confirm the public source repository, issue tracker, maintainer contact, and canonical license identifier for metadata.
3. Confirm whether the Community Hub's upload/download behavior requires an IzzyOnDroid anti-feature declaration.
4. Verify the app can be built from a clean checkout using only documented repositories and no undeclared binary inputs.
5. Provide a stable public privacy and community-policy URL for the repository listing.
