package com.retr0.projectretr0.data.carModels.manifest

import org.json.JSONArray
import org.json.JSONObject

/** Portable description of a complete, generic RR3 vehicle-directory package. */
data class CarModelManifest(
    val schemaVersion: Int = 1,
    /** Stable local transaction identity; never sent to Community Hub. */
    val transactionId: String? = null,
    /** Local-only pre-install collision/backup journal. Omitted from community manifests. */
    val localInstallJournal: LocalInstallJournal? = null,
    val packageType: String = "rr3-car-model",
    val metadata: PackageMetadata,
    val packageId: String = metadata.name,
    val supportedVersions: List<String> = emptyList(),
    val supportedGamePackages: List<String>,
    val target: TargetVehicle,
    /** Canonical vehicle directory root. Vehicle file destinations are relative to this root. */
    val vehicleRoot: String = target.vehicleDirectory,
    val installationPolicy: InstallationPolicy = InstallationPolicy.FULL_DIRECTORY_REPLACEMENT,
    val installMode: String = "REPLACE",
    val backupRequired: Boolean = true,
    val revertSupported: Boolean = true,
    val files: List<CarModelFile>,
    val warnings: List<String> = emptyList(),
) {
    fun toJson(): JSONObject = JSONObject().apply {
        put("manifestVersion", schemaVersion)
        put("schemaVersion", schemaVersion)
        transactionId?.let { put("transactionId", it) }
        put("packageType", packageType)
        put("metadata", metadata.toJson())
        put("packageId", packageId)
        put("supportedVersions", JSONArray(supportedVersions))
        put("supportedGamePackages", JSONArray(supportedGamePackages))
        put("target", target.toJson())
        put("vehicleRoot", vehicleRoot)
        put("installationPolicy", installationPolicy.wireValue)
        put("installMode", installMode)
        put("backupRequired", backupRequired)
        put("revertSupported", revertSupported)
        put("files", JSONArray(files.map { it.toJson() }))
        put("warnings", JSONArray(warnings))
        localInstallJournal?.let { put("localInstallJournal", it.toJson()) }
    }

    companion object {
        fun fromJson(json: JSONObject): CarModelManifest {
            val files = json.optJSONArray("files") ?: throw IllegalArgumentException("files is required")
            return CarModelManifest(
                schemaVersion = json.optInt("schemaVersion", 1),
                transactionId = json.optString("transactionId").takeIf { it.isNotBlank() },
                localInstallJournal = json.optJSONObject("localInstallJournal")?.let { LocalInstallJournal.fromJson(it) },
                packageType = json.optString("packageType", "rr3-car-model"),
                metadata = PackageMetadata.fromJson(json.optJSONObject("metadata") ?: throw IllegalArgumentException("metadata is required")),
                packageId = json.optString("packageId").ifBlank { json.optJSONObject("metadata")?.optString("name").orEmpty() },
                supportedVersions = json.optJSONArray("supportedVersions").strings(),
                supportedGamePackages = json.optJSONArray("supportedGamePackages").strings(),
                target = TargetVehicle.fromJson(json.optJSONObject("target") ?: throw IllegalArgumentException("target is required")),
                vehicleRoot = json.optString("vehicleRoot").ifBlank { json.optJSONObject("target")?.optString("vehicleDirectory").orEmpty() },
                installationPolicy = InstallationPolicy.fromWire(json.optString("installationPolicy")),
                installMode = json.optString("installMode", "REPLACE"),
                backupRequired = json.optBoolean("backupRequired", true),
                revertSupported = json.optBoolean("revertSupported", true),
                files = (0 until files.length()).map { CarModelFile.fromJson(files.getJSONObject(it)) },
                warnings = json.optJSONArray("warnings").strings(),
            )
        }
    }
}

data class PackageMetadata(val name: String, val version: String = "1", val description: String = "", val creator: String = "") {
    fun toJson() = JSONObject().put("name", name).put("version", version).put("description", description).put("creator", creator)
    companion object { fun fromJson(j: JSONObject) = PackageMetadata(j.optString("name"), j.optString("version", "1"), j.optString("description"), j.optString("creator")) }
}

enum class CollisionClassification { IDENTICAL, DIFFERENT, MISSING }

data class LocalInstallJournal(
    val transactionId: String,
    val installMode: String,
    val createdAt: Long,
    val completedAt: Long? = null,
    val entries: List<LocalInstallJournalEntry> = emptyList(),
) {
    fun toJson() = JSONObject().put("transactionId", transactionId).put("installMode", installMode)
        .put("createdAt", createdAt).put("completedAt", completedAt ?: JSONObject.NULL)
        .put("entries", JSONArray(entries.map { it.toJson() }))
    companion object {
        fun fromJson(j: JSONObject) = LocalInstallJournal(
            j.getString("transactionId"), j.optString("installMode", "ADDON"), j.optLong("createdAt"),
            j.optLong("completedAt").takeIf { it > 0 },
            (j.optJSONArray("entries")?.let { array -> (0 until array.length()).mapNotNull { index -> runCatching { LocalInstallJournalEntry.fromJson(array.getJSONObject(index)) }.getOrNull() } } ?: emptyList()),
        )
    }
}

data class LocalInstallJournalEntry(
    val sourcePath: String,
    val destinationPath: String,
    val destinationRoot: String,
    val existedBeforeInstall: Boolean,
    val existingSizeBytes: Long? = null,
    val existingSha256: String? = null,
    val existingContentType: String? = null,
    val incomingSizeBytes: Long,
    val incomingSha256: String,
    val incomingContentType: String,
    val collision: CollisionClassification,
    val backupPath: String? = null,
    val backupSha256: String? = null,
) {
    fun toJson() = JSONObject().put("sourcePath", sourcePath).put("destinationPath", destinationPath)
        .put("destinationRoot", destinationRoot).put("existedBeforeInstall", existedBeforeInstall)
        .put("existingSizeBytes", existingSizeBytes ?: JSONObject.NULL).put("existingSha256", existingSha256 ?: JSONObject.NULL)
        .put("existingContentType", existingContentType ?: JSONObject.NULL).put("incomingSizeBytes", incomingSizeBytes)
        .put("incomingSha256", incomingSha256).put("incomingContentType", incomingContentType)
        .put("collision", collision.name).put("backupPath", backupPath ?: JSONObject.NULL).put("backupSha256", backupSha256 ?: JSONObject.NULL)
    companion object {
        fun fromJson(j: JSONObject) = LocalInstallJournalEntry(
            j.getString("sourcePath"), j.getString("destinationPath"), j.optString("destinationRoot", "VEHICLE"),
            j.optBoolean("existedBeforeInstall"), j.optLong("existingSizeBytes").takeIf { it > 0 },
            j.optString("existingSha256").takeIf { it.isNotBlank() && it != "null" }, j.optString("existingContentType").takeIf { it.isNotBlank() && it != "null" },
            j.getLong("incomingSizeBytes"), j.getString("incomingSha256"), j.optString("incomingContentType", "application/octet-stream"),
            runCatching { CollisionClassification.valueOf(j.optString("collision")) }.getOrDefault(CollisionClassification.MISSING),
            j.optString("backupPath").takeIf { it.isNotBlank() && it != "null" }, j.optString("backupSha256").takeIf { it.isNotBlank() && it != "null" },
        )
    }
}

data class TargetVehicle(val vehicleDirectory: String, val relativeRoot: String) {
    fun toJson() = JSONObject().put("vehicleDirectory", vehicleDirectory).put("relativeRoot", relativeRoot)
    companion object { fun fromJson(j: JSONObject) = TargetVehicle(j.optString("vehicleDirectory"), j.optString("relativeRoot")) }
}

enum class InstallationPolicy(val wireValue: String) {
    FULL_DIRECTORY_REPLACEMENT("FULL_DIRECTORY_REPLACEMENT"),
    ADDON("ADDON");
    companion object { fun fromWire(value: String) = entries.firstOrNull { it.wireValue == value } ?: FULL_DIRECTORY_REPLACEMENT }
}

enum class CarModelDestinationRoot { VEHICLE, VEHICLES_DATA, DEPOT_DATA, DEPOT_ROOT }

private fun normalizeStoredDestination(value: String): String {
    return value.replace('\\', '/')
        .replace(Regex("^paste\\s+to\\s+depot/", RegexOption.IGNORE_CASE), "")
        .replace(Regex("^depot/", RegexOption.IGNORE_CASE), "")
}

data class CarModelFile(
    val sourcePath: String,
    val destinationPath: String,
    val role: String,
    val detectedFormat: String,
    val variant: String? = null,
    val required: Boolean = true,
    val sizeBytes: Long,
    val sha256: String,
    val contentType: String = "application/octet-stream",
    val destinationRoot: CarModelDestinationRoot = CarModelDestinationRoot.VEHICLE,
) {
    fun toJson() = JSONObject().put("sourcePath", sourcePath).put("path", sourcePath)
        .put("destinationPath", normalizeStoredDestination(destinationPath)).put("destinationRelativePath", normalizeStoredDestination(destinationPath)).put("role", role)
        .put("detectedFormat", detectedFormat).put("variant", variant ?: JSONObject.NULL).put("required", required)
        .put("sizeBytes", sizeBytes).put("size", sizeBytes).put("sha256", sha256).put("contentType", contentType)
        .put("destinationRoot", destinationRoot.name).put("root", destinationRoot.name)
    companion object { fun fromJson(j: JSONObject) = CarModelFile(j.getString("sourcePath"), normalizeStoredDestination(j.getString("destinationPath")), j.optString("role", "unknown"), j.optString("detectedFormat", "unknown"), j.optString("variant").takeIf { it.isNotBlank() && it != "null" }, j.optBoolean("required", true), j.getLong("sizeBytes"), j.getString("sha256"), j.optString("contentType", "application/octet-stream"), runCatching { CarModelDestinationRoot.valueOf(j.optString("destinationRoot")) }.getOrDefault(CarModelDestinationRoot.VEHICLE)) }
}

private fun JSONArray?.strings(): List<String> = if (this == null) emptyList() else (0 until length()).map { getString(it) }

object CarModelManifestValidator {
    private val sha256 = Regex("^[0-9a-fA-F]{64}$")
    fun validate(manifest: CarModelManifest) {
        require(manifest.schemaVersion == 1) { "Unsupported schema version" }
        require(manifest.packageType == "rr3-car-model") { "Unsupported package type" }
        val mode = manifest.installMode.uppercase()
        require(mode in setOf("REPLACE", "ADDON")) { "Unsupported install mode" }
        require(manifest.metadata.name.isNotBlank() && manifest.packageId.isNotBlank() && manifest.target.vehicleDirectory.isNotBlank()) { "Incomplete package metadata" }
        if (manifest.supportedGamePackages.isNotEmpty()) {
            require(manifest.supportedGamePackages.all { it.isNotBlank() }) { "Supported game packages must not be blank" }
        }
        if (mode == "ADDON") require(manifest.installationPolicy == InstallationPolicy.ADDON || manifest.installationPolicy == InstallationPolicy.FULL_DIRECTORY_REPLACEMENT) { "Unsupported ADDON installation policy" }
        if (mode == "REPLACE") require(manifest.installationPolicy == InstallationPolicy.FULL_DIRECTORY_REPLACEMENT) { "REPLACE manifests require FULL_DIRECTORY_REPLACEMENT installationPolicy" }
        validateRelativePath(manifest.target.relativeRoot)
        require(manifest.files.isNotEmpty()) { "Manifest contains no files" }
        require(manifest.files.size <= CarModelLimits.MAX_FILES) { "Car models can contain at most ${CarModelLimits.MAX_FILES} files." }
        var total = 0L
        manifest.files.forEach { file ->
            validateRelativePath(file.sourcePath); validateRelativePath(file.destinationPath)
            require(file.sizeBytes > 0 && file.sizeBytes <= CarModelLimits.MAX_FILE_BYTES && sha256.matches(file.sha256)) { "Invalid file metadata: ${file.sourcePath}" }
            total = Math.addExact(total, file.sizeBytes)
            require(total <= CarModelLimits.MAX_TOTAL_BYTES) { "Car model package exceeds ${CarModelLimits.MAX_TOTAL_BYTES / 1024 / 1024} MiB." }
        }
    }
    fun validateRelativePath(path: String): String {
        val normalized = path.replace('\\', '/')
        require(normalized.isNotBlank() && !normalized.startsWith('/') && !Regex("^[A-Za-z]:").containsMatchIn(normalized)) { "Path must be relative: $path" }
        require(normalized.split('/').none { it.isEmpty() || it == "." || it == ".." }) { "Unsafe relative path: $path" }
        return normalized
    }
}
