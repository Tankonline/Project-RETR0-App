# Contributing to Project RETR0 installer

Thank you for your interest in contributing!

## Development Prerequisites

### Recommended Tools
- **Android Studio:** Ladybug (2024.2.1) or newer recommended.
- **Physical Device:** Recommended for testing Shizuku-powered features.

### Build Requirements
- **JDK:** 17 (Required for Gradle build).
- **Gradle:** 9.7.1 (Managed via `./gradlew`).
- **Android Gradle Plugin:** 9.4.0.
- **Kotlin:** 2.0.21.
- **NDK:** 26.x or newer (Required for native components).
- **CMake:** 3.22.1+.

## How to Build

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Tankonline/Project-RETR0-App.git
   ```
2. **Open in Android Studio.**
3. **Build the debug variant:**
   ```bash
   ./gradlew :app:assembleDebug
   ```

## Running Tests

Run unit tests using Gradle:
```bash
./gradlew :app:testDebugUnitTest
```

## Coding Conventions

- **Kotlin:** Follow idiomatic Kotlin and Compose patterns.
- **UI:** Use Jetpack Compose with Material 3 components.
- **Clean Architecture:** Maintain separation between UI, Data, and Installer layers.

## Pull Request Process

1. Create a feature branch from `master`.
2. Ensure all unit tests pass.
3. Keep changes atomic.
4. Update documentation if necessary.
5. Submit your PR for review.

## Security & Secrets

- **DO NOT** commit keystores, passwords, or private API keys.
- **DO NOT** commit `.env` or local configuration files.
- Report vulnerabilities using the instructions in [SECURITY.md](SECURITY.md).

## Reporting Bugs

Use the GitHub Issue Tracker. Include:
- App version and Android version.
- Device model.
- RR3 package variant (Global/NA).
- Shizuku status.
- Reproduction steps and logs.
