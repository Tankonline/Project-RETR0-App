# Privacy and Community Policy

## Summary

Project RETR0 installer operates locally on the device and connects to the Community Hub only for features the user chooses to use. It requires Shizuku authorization only for privileged RR3 installation and file-management operations.

## Data handled by the app

### Installation identity

The app creates an installation-specific identity for Community Hub ownership and profile features. This identity is stored locally and is excluded from Android cloud backup and device transfer.

### User-generated content

If a user chooses to use Community Hub features, the app may transmit content and metadata needed for that feature, including:

- Profile-picture images.
- Skin, save, or music content selected for upload.
- Titles, descriptions, compatibility details, creator information, and user actions such as likes or reports.

The Community Hub is a remote service. Do not upload content that you are not permitted to share.

### Local storage

The app stores imported skins, profile pictures, installer state, backups, and cached content as needed to perform requested operations. Identity-bound profile pictures and imported skins are excluded from Android device transfer and cloud backup.

## Network services

The app uses HTTPS services for Community Hub, RR3 installer manifests/content, optional avatar fallback images, and music metadata lookup. The current endpoint inventory is maintained in `docs/izzyondroid-compatibility.md`.

The app does not download app updates or replacement yt-dlp executables at runtime.

## Shizuku privileges

Shizuku access is requested through the Shizuku service and must be granted by the user. When granted, it is used for privileged RR3-related file operations and package/install operations initiated by the user. Denying Shizuku permission prevents those privileged operations but does not grant the app elevated access.

## Community conduct and reporting

Community Hub content may be uploaded, downloaded, liked, reported, or deleted by its owner. Reports are intended for content moderation. Content ownership, copyright, and distribution rights remain the responsibility of the person uploading the content.

## Safety notice

RR3 asset changes can affect game files. Back up game data before applying changes, keep enough free storage for backup and extraction operations, and use the app's revert workflow when available.

## Contact and updates

Add a public support contact and repository issue-tracker URL before publishing this policy in an IzzyOnDroid listing. Update this document whenever app data collection, storage, network endpoints, or Community Hub behavior changes.
