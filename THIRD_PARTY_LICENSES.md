# Third-Party Licenses

Project RETR0 installer depends on several open-source components. This document provides a list of these dependencies and their respective licenses.

## Core Libraries

| Dependency | License |
| --- | --- |
| [AndroidX Libraries](https://developer.android.com/jetpack/androidx) | Apache-2.0 |
| [Jetpack Compose](https://developer.android.com/jetpack/compose) | Apache-2.0 |
| [Kotlin](https://kotlinlang.org/) | Apache-2.0 |
| [Shizuku API](https://github.com/RikkaApps/Shizuku) | Apache-2.0 |
| [OkHttp](https://square.github.io/okhttp/) | Apache-2.0 |
| [Coil](https://coil-kt.github.io/coil/) | Apache-2.0 |
| [Room](https://developer.android.com/training/data-storage/room) | Apache-2.0 |

## Bundled Native Components and Runtimes

| Component | Primary License |
| --- | --- |
| [QuickJS](https://bellard.org/quickjs/) | MIT |
| [Python Runtime](https://www.python.org/) | PSF License |
| [Cronet Embedded](https://chromium.googlesource.com/chromium/src/+/main/components/cronet/) | BSD-3-Clause |
| [zlib](https://zlib.net/) | zlib License |

### Cronet
Cronet is a Chromium-derived distribution. It includes various third-party components with their own notice requirements. The complete notices are available within the distributed binary artifact (`org.chromium.net:cronet-embedded`).

## Manual Verification Required
Before each release, the following must be manually verified from the upstream artifact provenance:
1. Presence of all required notice files for Python and QuickJS runtimes.
2. Compatibility of any newly added native libraries or dependencies.
