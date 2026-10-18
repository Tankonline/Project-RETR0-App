package com.retr0.projectretr0.data.carModels

/**
 * Stable Car Models error codes: `CM-<CATEGORY>-<NNN>`.
 * Codes are never renumbered or reused — append new ones. Success lifecycle lines log `CM-<CATEGORY>-OK`.
 *
 * | Code | Fires when |
 * |---|---|
 * | CM-APPLY-001 | apply, no backup exists |
 * | CM-APPLY-002 | apply, backup not VERIFIED after re-hash |
 * | CM-APPLY-003 | apply / remove, model missing from library |
 * | CM-APPLY-004 | apply, model has no files |
 * | CM-APPLY-005 | apply, source library file changed/missing |
 * | CM-APPLY-006 | apply, copy into vehicle dir failed |
 * | CM-APPLY-007 | apply, post-copy hash mismatch |
 * | CM-APPLY-008 | apply, game still running |
 * | CM-TARGET-001 | target vehicle dir not a directory |
 * | CM-TARGET-002 | game package is not r3_row / r3_na |
 * | CM-TARGET-003 | vehicle directory name is unsafe |
 * | CM-TARGET-004 | the package's vehicles folder could not be listed |
 * | CM-BACKUP-001 | ensureBackup, backup verify = MISSING |
 * | CM-BACKUP-002 | ensureBackup, backup verify = CORRUPT |
 * | CM-BACKUP-003 | ensureBackup, model applied but backup gone |
 * | CM-BACKUP-004 | ensureBackup, target empty |
 * | CM-BACKUP-005 | verifyBackup / revert / deleteBackup, none exists |
 * | CM-BACKUP-006 | deleteBackup, path not the expected backup dir |
 * | CM-BACKUP-007 | deleteBackup, delete failed |
 * | CM-BACKUP-008 | ensureBackup, original file could not be hashed |
 * | CM-BACKUP-009 | ensureBackup, copy of original into backup failed |
 * | CM-BACKUP-010 | ensureBackup, backup copy hash differs from original |
 * | CM-BACKUP-011 | ensureBackup, manifest.json could not be written |
 * | CM-REVERT-001 | restore, copy of original failed |
 * | CM-REVERT-002 | restore, restored file hash mismatch |
 * | CM-REVERT-003 | restore, removing added file failed |
 * | CM-REVERT-004 | revert, backup verify = MISSING |
 * | CM-REVERT-005 | revert, backup verify = CORRUPT |
 * | CM-REVERT-006 | revert, game still running |
 * | CM-HUB-001 | Hub list/download/report while stubbed |
 * | CM-IMPORT-001 | folder pick, tree could not be opened |
 * | CM-IMPORT-002 | folder pick, selection is not a directory |
 * | CM-IMPORT-003 | folder pick, no installable files |
 * | CM-IMPORT-004 | staging, a source file could not be read |
 * | CM-IMPORT-005 | staging, total size over the limit |
 * | CM-IMPORT-006 | staging, folder nested too deeply |
 * | CM-IMPORT-007 | staging, too many files |
 * | CM-IMPORT-008 | save, model name blank |
 * | CM-IMPORT-009 | save, no valid target vehicle |
 * | CM-IMPORT-010 | save, staged files could not be moved into the library |
 * | CM-IMPORT-011 | save, manifest failed validation |
 * | CM-IMPORT-012 | remove, model is still applied |
 * | CM-IMPORT-013 | validate step, folder scan reported errors |
 * | CM-ADDON-001 | add-on apply, a file it would overwrite could not be backed up + verified |
 * | CM-ADDON-002 | add-on apply, destination folder already has an active car model |
 * | CM-ADDON-003 | add-on apply, copy into destination failed |
 * | CM-ADDON-004 | add-on apply, post-copy hash mismatch |
 * | CM-ADDON-005 | add-on remove, deleting an added file failed |
 * | CM-ADDON-006 | add-on remove, restoring an overwritten file failed / did not verify |
 * | CM-ADDON-007 | add-on remove, no active add-on on this folder |
 * | CM-ADDON-008 | replace requested on a folder that holds an active add-on |
 * | CM-FILEOP-001 | a relative path failed traversal validation |
 * | CM-FILEOP-002 | unexpected failure in a file operation (binder / IO) |
 * | CM-FILEOP-003 | privileged (Shizuku) service not connected |
 */
enum class CarModelErrorCode(val code: String, val userMessage: String) {
    BACKUP_REQUIRED("CM-APPLY-001", "A verified local backup is required before applying."),
    BACKUP_NOT_VERIFIED("CM-APPLY-002", "The original backup is not verified. Apply is blocked."),
    MODEL_NOT_FOUND("CM-APPLY-003", "Car model not found in your library."),
    MODEL_EMPTY("CM-APPLY-004", "This car model has no files."),
    LIBRARY_FILE_CHANGED("CM-APPLY-005", "Library file changed or missing."),
    APPLY_COPY_FAILED("CM-APPLY-006", "Unable to copy a model file."),
    APPLY_HASH_MISMATCH("CM-APPLY-007", "Hash mismatch after copying a model file."),
    GAME_RUNNING("CM-APPLY-008", "RR3 is still running."),

    TARGET_NOT_FOUND("CM-TARGET-001", "Target vehicle folder not found."),
    UNSUPPORTED_PACKAGE("CM-TARGET-002", "Unsupported RR3 package."),
    UNSAFE_VEHICLE_DIRECTORY("CM-TARGET-003", "Unsafe vehicle directory name."),
    VEHICLES_UNREADABLE("CM-TARGET-004", "RR3 vehicles could not be read. Is the game installed?"),

    BACKUP_MISSING("CM-BACKUP-001", "The original backup for this vehicle is missing."),
    BACKUP_CORRUPT("CM-BACKUP-002", "The original backup for this vehicle is corrupt."),
    BACKUP_GONE_WHILE_APPLIED("CM-BACKUP-003", "A car model is applied to this vehicle but its original backup is gone. Restore the game files before applying another model."),
    BACKUP_TARGET_EMPTY("CM-BACKUP-004", "Target vehicle folder has no files to back up."),
    NO_BACKUP("CM-BACKUP-005", "No original backup exists for this vehicle."),
    BACKUP_LOCATION_UNRECOGNISED("CM-BACKUP-006", "Backup location is not recognised."),
    BACKUP_DELETE_FAILED("CM-BACKUP-007", "Unable to delete the backup files."),
    BACKUP_READ_FAILED("CM-BACKUP-008", "Unable to read an original file."),
    BACKUP_COPY_FAILED("CM-BACKUP-009", "Unable to copy an original file into the backup."),
    BACKUP_COPY_MISMATCH("CM-BACKUP-010", "A backup copy does not match the original."),
    BACKUP_MANIFEST_WRITE_FAILED("CM-BACKUP-011", "Unable to write the backup manifest"),

    RESTORE_COPY_FAILED("CM-REVERT-001", "Unable to restore an original file."),
    RESTORE_HASH_MISMATCH("CM-REVERT-002", "Restored file failed verification."),
    RESTORE_REMOVE_FAILED("CM-REVERT-003", "Unable to remove a file the model added."),
    REVERT_BACKUP_MISSING("CM-REVERT-004", "Backup files not found. Restore is unavailable."),
    REVERT_BACKUP_CORRUPT("CM-REVERT-005", "Hash mismatch in the backup. Restore is unavailable."),
    REVERT_GAME_RUNNING("CM-REVERT-006", "RR3 is still running."),

    HUB_UNAVAILABLE("CM-HUB-001", "Unable to load car models from the Community Hub."),
    HUB_DOWNLOAD_UNSUPPORTED("CM-HUB-002", "Car-model downloads are not available in this build yet."),

    IMPORT_FOLDER_UNOPENABLE("CM-IMPORT-001", "The selected folder could not be opened."),
    IMPORT_NOT_A_FOLDER("CM-IMPORT-002", "Select a car-model folder, not a file."),
    IMPORT_NO_FILES("CM-IMPORT-003", "No car-model files were found in this folder."),
    IMPORT_READ_FAILED("CM-IMPORT-004", "Unable to read a file in the selected folder."),
    IMPORT_TOO_LARGE("CM-IMPORT-005", "The car model is too large."),
    IMPORT_TOO_DEEP("CM-IMPORT-006", "The selected folder is nested too deeply."),
    IMPORT_TOO_MANY_FILES("CM-IMPORT-007", "The car model has too many files."),
    IMPORT_NAME_REQUIRED("CM-IMPORT-008", "Model name is required."),
    IMPORT_TARGET_REQUIRED("CM-IMPORT-009", "Choose a target vehicle."),
    IMPORT_SAVE_FAILED("CM-IMPORT-010", "Unable to save the model to your library."),
    IMPORT_INVALID_MANIFEST("CM-IMPORT-011", "The car-model manifest is invalid."),
    MODEL_STILL_APPLIED("CM-IMPORT-012", "Revert this model before removing it from your library."),
    IMPORT_VALIDATION_FAILED("CM-IMPORT-013", "The selected folder failed validation."),

    ADDON_BACKUP_FAILED("CM-ADDON-001", "Unable to back up a file the add-on would overwrite. Nothing was installed."),
    ADDON_TARGET_OCCUPIED("CM-ADDON-002", "This folder already has a car model installed. Remove or revert it first."),
    ADDON_COPY_FAILED("CM-ADDON-003", "Unable to copy an add-on file."),
    ADDON_HASH_MISMATCH("CM-ADDON-004", "Hash mismatch after copying an add-on file."),
    ADDON_REMOVE_FAILED("CM-ADDON-005", "Unable to delete a file the add-on installed."),
    ADDON_RESTORE_FAILED("CM-ADDON-006", "Unable to restore a file the add-on overwrote."),
    ADDON_NOT_INSTALLED("CM-ADDON-007", "No add-on is installed on this folder."),
    REPLACE_ON_ADDON("CM-ADDON-008", "An add-on is installed on this vehicle. Remove it before replacing the vehicle."),

    UNSAFE_PATH("CM-FILEOP-001", "A file path in this car model is unsafe."),
    UNEXPECTED("CM-FILEOP-002", "A file operation failed unexpectedly."),
    SERVICE_UNAVAILABLE("CM-FILEOP-003", "Shizuku service is disconnected."),
}
