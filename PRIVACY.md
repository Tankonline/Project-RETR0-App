# Privacy Policy

Project RETR0 installer is designed to prioritize local processing and user control. This policy explains how data is handled by the application.

## Local Data Processing

### RR3 Game Data
The app accesses and modifies Real Racing 3 game files only after user authorization via Shizuku. This processing happens entirely on the device.

### Local Database
The app uses a Room database to store metadata about downloaded assets, music library items, and installation state. This data stays on the device.

### Installation Identity
Upon first launch, the app generates a unique installation-specific identifier. This identity is used to manage your Community Hub contributions and profile. It is excluded from Android cloud backups and device transfers.

## Network Communication

The app connects to the internet for the following features:

### Community Hub (Optional)
If you use the Community Hub, the following data may be transmitted:
- Your installation identifier (to verify ownership of uploads/likes).
- User-generated content (skins, saves, music metadata) you choose to upload.
- Profile pictures you set.
- Interaction data (likes, reports).

Service: Hosted on Cloudflare (Workers and R2).

### Installer Content
When installing RR3 assets, the app fetches manifests and compressed content from remote storage services.

### Music Import
- **Local audio files:** Users select externally downloaded audio files from device storage.
- **iTunes Search API:** Used for optional music metadata lookup.

### Avatars
If no custom profile picture is set, the app may load a generated identicon.
Service: `api.dicebear.com`.

## Third-Party Services

The app bundles several third-party libraries and tools:
- **Shizuku:** Used for privileged file operations. Access must be explicitly granted by the user.
- **Cronet:** Chromium's network stack for high-performance downloads.

Users are encouraged to review the privacy policies of any external services they choose to interact with (e.g., YouTube, Discord).

## Data Retention and Deletion

- Local data can be cleared by clearing the app data in Android settings.
- Community Hub content you uploaded can be deleted by you within the app.
- We do not collect telemetry, usage analytics, or advertising data.

## Children's Privacy

This application is not directed at children. We do not knowingly collect personal information from children.

## Contact

For privacy-related inquiries, please use the project's issue tracker on GitHub.
