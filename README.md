# Project RETR0 installer

> **A modern Android asset manager for Real Racing 3 — install, restore, customise, and manage your game assets with user-authorized access.**

[![Platform](https://img.shields.io/badge/platform-Android-3DDC84?logo=android&logoColor=white)](https://www.android.com/)
[![Language](https://img.shields.io/badge/Kotlin-2.0.21-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![UI](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?logo=jetpackcompose&logoColor=white)](https://developer.android.com/compose)
[![Min SDK](https://img.shields.io/badge/min%20SDK-26-informational)](https://developer.android.com/about/versions/oreo)
[![Target SDK](https://img.shields.io/badge/target%20SDK-35-informational)](https://developer.android.com/about/versions/15)
[![License](https://img.shields.io/badge/license-GPL--3.0--or--later-blue)](https://www.gnu.org/licenses/gpl-3.0.html)

**Project RETR0 installer** is an Android-native, open-source utility for managing user-authorized modifications and custom assets for **Real Racing 3 (RR3)**.

The application is built around a simple principle:

> **Give the user control over their own game data without requiring the installer itself to operate as a traditional root application.**

It combines streaming asset installation, backup and recovery workflows, Shizuku-powered privileged operations, an optional community hub, and local media processing into one Android application.

---

## Table of Contents

- [Overview](#overview)
- [Why Project RETR0 installer?](#why-project-retr0-installer)
- [Features](#features)
- [Supported RR3 Variants](#supported-rr3-variants)
- [How the Installation Pipeline Works](#how-the-installation-pipeline-works)
- [Shizuku](#shizuku)
- [Community Hub](#community-hub)
- [Music Import](#music-import)
- [Backup and Recovery](#backup-and-recovery)
- [Privacy and Data Handling](#privacy-and-data-handling)
- [Distribution Model](#distribution-model)
- [System Requirements](#system-requirements)
- [Installation for Users](#installation-for-users)
- [Building from Source](#building-from-source)
- [Project Structure](#project-structure)
- [Architecture](#architecture)
- [Networking](#networking)
- [Security Considerations](#security-considerations)
- [Development Guidelines](#development-guidelines)
- [Testing](#testing)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [Attribution](#attribution)
- [Legal Disclaimer](#legal-disclaimer)
- [License](#license)
- [Project Status](#project-status)

---

## Overview

Project RETR0 installer is designed for the Android modding and preservation community surrounding Real Racing 3.

Rather than treating a modification as a single destructive copy operation, the application is intended to provide a complete workflow:

1. Discover or select a supported asset package.
2. Validate the intended operation where applicable.
3. Download or receive the asset data.
4. Stream or pipe the data through the extraction pipeline.
5. Perform the required file operations through the user's authorized Shizuku session.
6. Track the installation as a background operation where appropriate.
7. Preserve recovery information so supported changes can be reverted.
8. Report installation progress and failures clearly.

The application also provides an optional community layer for discovering and sharing supported skins, saves, music, and other community content.

---

## Why Project RETR0 installer?

Traditional Android file manipulation became increasingly restricted as Android's storage and security model evolved.

Project RETR0 installer is designed around Android's modern permission model while still supporting workflows that require access to application-owned game data.

The application therefore uses **Shizuku** as an intermediary for supported privileged operations rather than requiring the installer itself to be a conventional root application.

This provides a clearer separation between:

- The Android application UI and business logic.
- The user's explicit authorization.
- The privileged operation layer.
- The asset-processing pipeline.
- Local application state.
- Optional community services.

---

# Features

## 🏎️ Asset Installation

Project RETR0 installer provides an asset installation pipeline for supported RR3 modifications.

### Streaming / piped extraction

Where supported, asset data can be processed as a stream instead of requiring the complete source archive to be duplicated into multiple temporary locations.

This can reduce unnecessary intermediate storage requirements and makes it possible to construct installation pipelines around streams and pipes.

The intended flow is conceptually:

```text
Remote / local asset
       │
       ▼
   Input stream
       │
       ▼
 Downloader / transport
       │
       ▼
 Extraction / processing
       │
       ▼
 Installation pipeline
       │
       ▼
 Shizuku-authorized file operation
       │
       ▼
      RR3
```

The exact pipeline depends on the asset type and installation operation.

### Background installation

Long-running operations are designed to run outside the main UI thread.

The project includes Android background-work infrastructure and a dedicated foreground installation service for installation tasks that need persistent user-visible execution.

This prevents large operations from being tied directly to the lifecycle of a single Compose screen.

---

## 🌍 Supported RR3 Variants

Project RETR0 installer explicitly recognises the following Real Racing 3 Android package identifiers:

| Variant | Android package |
|---|---|
| Global / ROW | `com.ea.games.r3_row` |
| North America | `com.ea.games.r3_na` |

The Android manifest declares both packages for package visibility, allowing the application to determine which supported RR3 installation is present.

### Why package detection matters

Different regional builds can have different application package identifiers.

A modding utility should not blindly assume that every RR3 installation is located under the same package name.

Project RETR0 installer therefore treats the package identifier as part of the installation target.

---

# Shizuku

Project RETR0 installer uses the **Shizuku API** for supported privileged operations.

Shizuku allows Android applications to request access to system-level APIs through a user-authorized service without requiring the application to embed a traditional root shell workflow.

Official project:

https://github.com/RikkaApps/Shizuku

## Why Shizuku?

Using Shizuku provides several advantages:

- Explicit user authorization.
- Separation between the application and privileged execution.
- Compatibility with supported non-root Shizuku startup methods.
- Reduced need for the installer to behave like a conventional root application.
- A well-defined Android integration point for privileged operations.

## Shizuku requirement

Users should install and start Shizuku before attempting operations that require privileged access.

Shizuku can be started through methods supported by the user's device, including wireless debugging and other supported configurations.

Project RETR0 installer does **not** assume that the user has granted the application unrestricted root access.

## Provider integration

The application includes Shizuku's provider integration in its Android manifest so that the Shizuku service can establish the required communication channel.

The provider contract should not be modified casually because it forms part of the Shizuku SDK integration.

---

# Community Hub

The Community Hub is an **optional** component of Project RETR0 installer.

It provides a community-oriented layer for supported content such as:

- 🚗 Car skins
- 💾 Game saves
- 🎵 Custom music
- 👤 User profiles
- 🖼️ Profile pictures
- 📦 Community asset packages

The local installer does not fundamentally depend on users participating in the community.

## Optional by design

Users who only want to install locally stored assets can use the relevant local functionality without treating the Community Hub as a required social platform.

## Device-specific installation identity

The installation identity used by the application is **device-specific**.

It should not be interpreted as a universal identity that automatically follows a person across multiple devices.

Changing devices should therefore be treated as a separate installation context.

---

# Music Import

Project RETR0 installer includes Android integration for **yt-dlp** and **FFmpeg** through the project's youtubedl-android dependencies.

These components are used to support importing media from user-provided YouTube URLs for supported game-audio workflows.

## Processing pipeline

A simplified workflow is:

```text
YouTube URL
    │
    ▼
yt-dlp integration
    │
    ▼
Media stream / file
    │
    ▼
FFmpeg processing
    │
    ▼
Supported audio output
    │
    ▼
Asset preparation
    │
    ▼
User-authorized installation
```

The exact output and processing behaviour depends on the supported asset format and current implementation.

## Bundled executable policy

Project RETR0 installer is designed so that executable components required for supported media processing are **bundled with the signed APK** rather than being downloaded as executable payloads at runtime.

This is important for:

- Reproducibility.
- Distribution transparency.
- Repository policies.
- Reduced runtime supply-chain risk.
- Offline availability of the bundled executable components.

### Important legal note

Users are responsible for ensuring that any media they download, convert, modify, or install is used in accordance with applicable law and the terms governing the source material.

Project RETR0 installer does not grant users rights to copyrighted media.

---

# Backup and Recovery

Modding can alter files that are important to the game installation.

Project RETR0 installer therefore includes a backup/revert concept as part of the installation workflow.

## Recommended workflow

Before applying significant modifications:

```text
Existing RR3 data
       │
       ▼
    BACKUP
       │
       ▼
Install modification
       │
       ├── Success ──► Continue using RR3
       │
       └── Failure ──► Revert / restore
```

Users should still maintain independent backups of valuable data.

A backup system is a recovery mechanism, **not a guarantee against every possible form of data loss**.

---

# Privacy and Data Handling

Project RETR0 installer separates local installation functionality from optional community functionality.

## Local functionality

Local application state can be stored using Android's local database/storage mechanisms.

The project uses **Room** for structured local persistence.

## Community functionality

Community features are optional and are separate from purely local asset installation.

## Network permissions

The application declares permissions for:

- Internet access.
- Network-state information.
- Wi-Fi-state information.

These permissions support network-based asset and community workflows.

## Notifications

The application declares the Android notification permission because long-running/background operations may need to provide user-visible progress or completion information.

---

# Distribution Model

Project RETR0 installer is intended for transparent repository-based distribution.

Primary distribution targets include:

- GitHub Releases
- IzzyOnDroid / F-Droid-compatible distribution

## No self-updater

Project RETR0 installer **does not implement an application self-update mechanism**.

Users should obtain new versions through the project's supported distribution channels.

This keeps release installation under the user's control and avoids an application silently replacing itself.

## No runtime executable downloads

The application is designed not to download executable binaries dynamically during normal operation.

Required executable components are bundled into the application package and shipped as part of the signed APK.

This is particularly relevant to the yt-dlp/FFmpeg workflow.

---

# System Requirements

## Android

The current Gradle configuration specifies:

| Requirement | Version |
|---|---:|
| Minimum Android SDK | **26** |
| Target Android SDK | **35** |
| Compile SDK | **35** |
| Java | **17** |
| Kotlin JVM target | **17** |

Android 8.0 / API 26 is therefore the minimum configured platform.

Actual feature availability may depend on the Android version, device manufacturer, storage implementation, Shizuku availability, and the installed RR3 variant.

## Required for privileged installation

- Shizuku
- A supported RR3 installation
- Sufficient storage for the selected asset operation

---

# Installation for Users

## 1. Install Shizuku

Install Shizuku from its official project/distribution channels:

https://github.com/RikkaApps/Shizuku

Start Shizuku using a supported method for your device.

## 2. Install Project RETR0 installer

Obtain the signed APK from an official Project RETR0 distribution channel.

The primary project repository is:

https://github.com/Tankonline/Project-RETR0-App

Do not rely on random APK mirrors or modified third-party builds when a trusted release is available.

## 3. Install Real Racing 3

Install a supported RR3 variant:

```text
com.ea.games.r3_row
```

or

```text
com.ea.games.r3_na
```

## 4. Launch the installer

Open Project RETR0 installer and complete its setup process.

## 5. Authorize Shizuku

When an operation requires privileged access, grant the application's Shizuku permission.

## 6. Back up before modifying

Create or verify a backup before performing substantial modifications.

## 7. Install the asset

Select the supported asset/package and follow the installation progress shown by the application.

---

# Building from Source

Project RETR0 installer is an Android Gradle project using Kotlin DSL.

## Prerequisites

Recommended development environment:

- Android Studio
- JDK 17
- Android SDK 35
- Android SDK Platform Tools
- Android SDK Build Tools compatible with the project
- CMake 3.22.1
- Git

The application currently declares an external native build using CMake.

## Clone

```bash
git clone https://github.com/Tankonline/Project-RETR0-App.git
cd Project-RETR0-App
```

## Windows

```powershell
.\gradlew.bat assembleDebug
```

## Linux / macOS

```bash
./gradlew assembleDebug
```

The generated APK will normally be under:

```text
app/build/outputs/apk/
```

## Android Studio

1. Open the repository in Android Studio.
2. Allow Gradle to synchronise.
3. Install any missing Android SDK/CMake components requested by the project.
4. Select the `app` module.
5. Select a debug build variant.
6. Build the application.
7. Install the resulting APK on a test device.

---

# Build Configuration

The application module currently specifies:

```text
Namespace:     com.example.projectretr0installer
Application ID: com.example.projectretr0installer

compileSdk:    35
minSdk:        26
targetSdk:     35

versionCode:   1
versionName:   1.0.0-STABLE

Java:          17
Kotlin JVM:    17

CMake:         3.22.1
C++ standard:  C++17
```

> The application ID and namespace are implementation details of the current development build. They should be treated as authoritative from the Gradle configuration rather than inferred from the project name.

---

# Technology Stack

| Layer | Technology |
|---|---|
| Primary language | Kotlin |
| UI | Jetpack Compose |
| Material UI | Material 3 |
| Persistence | Room |
| Background work | WorkManager |
| Long-running installation service | Android Foreground Service |
| Privileged Android operations | Shizuku |
| HTTP networking | OkHttp |
| Alternative networking stack | Chromium Cronet |
| Media acquisition | yt-dlp via youtubedl-android |
| Media processing | FFmpeg via youtubedl-android |
| Image loading | Coil |
| Metadata/image handling | AndroidX ExifInterface |
| Annotation/code generation | KSP |
| Native build | CMake / C++17 |
| Testing | JUnit, AndroidX Test, Espresso, Compose UI Test |

---

# Architecture

Project RETR0 installer is structured as a native Android application with Compose-based presentation and dedicated components for persistence, networking, installation, and privileged operations.

A conceptual architecture is:

```text
┌──────────────────────────────────────────────┐
│                Jetpack Compose               │
│              User Interface Layer            │
└──────────────────────┬───────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────┐
│          Application / ViewModel Layer       │
│     State • Validation • User workflows      │
└───────────────┬───────────────┬──────────────┘
                │               │
        ┌───────▼──────┐  ┌────▼─────────────┐
        │    Room      │  │ Networking       │
        │ Local State  │  │ OkHttp / Cronet  │
        └──────────────┘  └────┬─────────────┘
                               │
                    ┌──────────▼──────────┐
                    │ Asset / Media       │
                    │ Processing Pipeline │
                    └──────────┬──────────┘
                               │
                    ┌──────────▼──────────┐
                    │ Installation Layer  │
                    │ WorkManager / FGS    │
                    └──────────┬──────────┘
                               │
                    ┌──────────▼──────────┐
                    │ Shizuku-authorized  │
                    │ Android operations  │
                    └──────────┬──────────┘
                               │
                               ▼
                         RR3 data
```

This separation helps keep UI lifecycle concerns away from potentially long-running file and network operations.

---

# Project Structure

The repository currently follows a standard Android Gradle structure.

```text
Project-RETR0-App/
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/
│       ├── androidTest/
│       ├── main/
│       │   ├── aidl/
│       │   ├── java/
│       │   ├── keepRules/
│       │   ├── res/
│       │   └── AndroidManifest.xml
│       └── test/
├── gradle/
│   └── libs.versions.toml
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── settings.gradle.kts
```

### Important directories

#### `app/src/main/java/`

Application source code.

#### `app/src/main/aidl/`

AIDL interfaces used by Android components where required.

#### `app/src/main/res/`

Android resources, including:

- Drawables
- Icons
- Strings
- Themes
- XML configuration
- Backup/data-extraction configuration

#### `app/src/test/`

Local JVM unit tests.

#### `app/src/androidTest/`

Android instrumentation and UI tests.

#### `app/src/main/keepRules/`

Rules supporting code/resource preservation where required by the build.

---

# Networking

The project uses both **OkHttp** and **Cronet-related components**.

## OkHttp

OkHttp provides the standard HTTP client layer for network operations.

The project also includes OkHttp's logging interceptor for development/debugging workflows.

## Cronet

Chromium Cronet dependencies are included to support high-performance networking where the implementation chooses to use them.

The project declares:

- Cronet API
- Embedded Cronet
- OkHttp-related Cronet integration dependency

---

# Security Considerations

Project RETR0 installer handles game files and, in some workflows, privileged Android operations. Security should therefore be considered at multiple layers.

## User authorization

Privileged operations should only be performed after the user has explicitly authorized the relevant Shizuku access.

## Least privilege

Application code should request only the access required for the current operation.

A privileged operation should not automatically imply unrestricted access to unrelated device data.

## Asset validation

Downloaded or imported assets should be treated as untrusted input.

Future installation features should consider:

- Archive traversal.
- Unexpected absolute paths.
- Symbolic links.
- Malformed archives.
- Excessive extraction sizes.
- Unexpected file types.
- Corrupted downloads.
- Partial installations.
- Incorrect target paths.

## Network security

Community and asset downloads should use HTTPS endpoints wherever possible.

Do not disable TLS certificate validation or introduce permissive trust managers simply to work around a server configuration problem.

## Secrets

Never commit:

- API keys
- Signing keys
- Keystores
- Passwords
- OAuth tokens
- Personal access tokens
- Private certificates

The current build configuration contains a community API key build field that is empty in the repository configuration. Any production secret should be supplied through an appropriate secure build/deployment mechanism rather than committed to source control.

---

# Development Guidelines

## Keep privileged code isolated

Code interacting with Shizuku should remain clearly separated from ordinary UI logic.

Avoid putting privileged filesystem operations directly inside Compose functions.

## Keep long operations off the main thread

Downloads, archive extraction, media processing, hashing, and file installation should not block the UI thread.

Use appropriate coroutine dispatchers and Android background execution mechanisms.

## Prefer recoverable operations

Installation workflows should be designed so that failures can be detected and reported without leaving the user's game data in an ambiguous state.

Where practical:

1. Validate.
2. Prepare.
3. Back up.
4. Apply.
5. Verify.
6. Commit the operation.
7. Provide a recovery path if verification fails.

## Treat community content as untrusted

A file uploaded by another user should never automatically be treated as safe merely because it is hosted by the Community Hub.

---

# Testing

The project contains both local and Android test source sets.

## Unit tests

Run:

```bash
./gradlew test
```

Windows:

```powershell
.\gradlew.bat test
```

## Android instrumentation tests

Run:

```bash
./gradlew connectedAndroidTest
```

Windows:

```powershell
.\gradlew.bat connectedAndroidTest
```

Instrumentation testing requires an available Android emulator or physical device.

## Recommended test areas

Contributors should test changes affecting:

- RR3 package detection.
- Asset path handling.
- Archive extraction.
- Backup/revert behaviour.
- Shizuku authorization.
- Interrupted installations.
- Background installation recovery.
- Network failures.
- Community API failures.
- Media import.
- Different Android versions.
- Both supported RR3 package variants.

---

# Troubleshooting

## “Shizuku is not running”

Make sure:

1. Shizuku is installed.
2. Shizuku has been started.
3. The device's supported authorization method is active.
4. Project RETR0 installer has been granted Shizuku access.
5. Shizuku has not been stopped or revoked by the system.

## RR3 is not detected

Verify that your installed RR3 package is one of:

```text
com.ea.games.r3_row
com.ea.games.r3_na
```

If the game uses another package identifier, it is outside the currently declared package-detection targets.

## Installation fails part-way through

Do not repeatedly overwrite the same installation until the cause is understood.

Recommended steps:

1. Check the installation error/log output.
2. Confirm sufficient free storage.
3. Confirm Shizuku is still running.
4. Confirm the target RR3 package has not changed.
5. Use the backup/revert mechanism where appropriate.
6. Retry with a known-good asset package.

## Music import fails

Check:

- The URL is supported.
- Network access is available.
- The media source is reachable.
- The required bundled processing components are present.
- Sufficient storage is available.
- The resulting media format is supported by the intended RR3 workflow.

---

# Contributing

Contributions are welcome.

## Before submitting a change

Please:

- Build the project successfully.
- Run relevant unit tests.
- Run instrumentation/UI tests where applicable.
- Avoid committing generated build output.
- Avoid committing credentials or signing material.
- Document significant user-facing changes.
- Test privileged operations carefully.
- Preserve support for both declared RR3 package variants unless a change intentionally changes the compatibility matrix.

## Pull requests

A useful pull request should explain:

- **What changed**
- **Why it changed**
- **How it was tested**
- **Any compatibility implications**
- **Any migration/recovery considerations**

For installation-related changes, include reproduction steps for both successful and failed cases where practical.

---

# Attribution

Project RETR0 installer would not be possible without the open-source ecosystem it builds upon.

## Shizuku

Used for user-authorized privileged Android operations.

Project:

https://github.com/RikkaApps/Shizuku

## youtubedl-android

Android integration used for the yt-dlp/media workflow.

Project:

https://github.com/yausername/youtubedl-android

## yt-dlp

The underlying open-source media downloading project used through the Android integration.

Project:

https://github.com/yt-dlp/yt-dlp

## FFmpeg

Used for supported media processing and conversion workflows.

Project:

https://ffmpeg.org/

## AndroidX / Jetpack

Project RETR0 installer uses multiple AndroidX libraries, including:

- Jetpack Compose
- Room
- WorkManager
- DocumentFile
- Lifecycle
- Activity
- Core KTX
- ExifInterface
- AndroidX testing libraries

These projects are developed as part of the Android Open Source ecosystem.

## OkHttp

Used for HTTP networking.

https://square.github.io/okhttp/

## Coil

Used for image loading in Compose.

https://coil-kt.github.io/coil/

---

# Legal Disclaimer

Project RETR0 installer is a **third-party modding and asset-management utility**.

It is **not affiliated with, endorsed by, sponsored by, or otherwise associated with Electronic Arts, Firemonkeys Studios, or Real Racing 3**.

Real Racing 3, its name, trademarks, logos, game code, game assets, and related intellectual property belong to their respective owners.

## User responsibility

You are responsible for:

- The game installation you modify.
- The assets you download, create, import, or distribute.
- Maintaining backups of your own data.
- Ensuring that your use of the application complies with applicable laws.
- Respecting the rights and licenses associated with third-party content.
- Ensuring that content uploaded to the Community Hub is content you have the right to distribute.

Project RETR0 installer does not provide ownership or redistribution rights to third-party copyrighted material.

## No warranty

The application modifies game-related data and is provided subject to the terms of its open-source license.

Modifying game files can result in unexpected behaviour, corrupted data, incompatibility after game updates, or other problems.

**Back up your data before making significant modifications.**

---

# License

Project RETR0 installer is intended to be distributed under:

**GNU General Public License v3.0 or later (GPL-3.0-or-later).**

SPDX identifier:

```text
GPL-3.0-or-later
```

The complete license text should be included in the repository as `LICENSE`.

---

# Project Status

Project RETR0 installer is an active development project.

The current repository configuration identifies the application as:

```text
Version: 1.0.0-STABLE
Version code: 1
```

The implementation and documentation may evolve as additional asset types, installation safeguards, community functionality, and compatibility improvements are introduced.

Features documented here describe the project's intended and currently integrated functionality; individual asset formats or workflows may have additional compatibility requirements.

---

# Repository

**Project RETR0 installer**

https://github.com/Tankonline/Project-RETR0-App

---

## Project RETR0

**Preserve. Modify. Restore. Race.**

Made for the community surrounding Real Racing 3.
