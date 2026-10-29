package com.retr0.projectretr0.data.carModels.manifest

import java.io.File
import java.security.MessageDigest
import java.nio.file.Files

/** Scans a user-selected vehicle folder without assuming a vehicle name or fixed file count. */
object CarModelFolderScanner {
    data class ValidationReport(val errors: List<String>, val warnings: List<String>) { val isValid get() = errors.isEmpty() }
    data class ScanResult(val manifest: CarModelManifest, val report: ValidationReport)

    fun scan(root: File, metadata: PackageMetadata = PackageMetadata(root.name), target: TargetVehicle = TargetVehicle(root.name, root.name), onFileHashed: (done: Int, total: Int) -> Unit = { _, _ -> }): ScanResult {
        val errors = mutableListOf<String>(); val warnings = mutableListOf<String>()
        if (!root.isDirectory || !root.canRead()) errors += "Selected folder is not readable"
        val files = if (root.isDirectory) root.walkTopDown().filter { it.isFile && !Files.isSymbolicLink(it.toPath()) }.toList() else emptyList()
        val relativePaths = files.map { root.toPath().relativize(it.toPath()).toString().replace(File.separatorChar, '/') }
        val detected = runCatching { CarModelLayoutDetector.detect(relativePaths) }.getOrElse { error ->
            val message = error.message ?: "Unable to determine RR3 folder layout"
            if (message.contains("Mixed RR3 layout")) errors += message else warnings += message
            CarModelLayoutDetector.Detection(CarModelLayoutDetector.Kind.VEHICLE, "", null, relativePaths)
        }
        warnings += detected.warnings
        val layout = detectLayout(relativePaths, detected, root.name, warnings)
        var hashed = 0
        val entries = files.mapNotNull { file ->
            val relative = root.toPath().relativize(file.toPath()).toString().replace(File.separatorChar, '/')
            runCatching {
                CarModelManifestValidator.validateRelativePath(relative)
                val classification = classify(file.name)
                val effectiveRelative = detected.effectivePrefix.takeIf { it.isNotEmpty() && relative.startsWith("$it/") }?.let { relative.removePrefix("$it/") } ?: relative
                val destination = layout.destination(effectiveRelative)
                if (destination == null) {
                    warnings += "Rejected unknown top-level file for add-on: $relative"
                    return@runCatching null
                }
                if (classification.role == "unknown") warnings += "Review unknown file: $relative"
                val rootKind = when {
                    !layout.structured -> CarModelDestinationRoot.VEHICLE
                    destination.startsWith("vehicles/data/") -> CarModelDestinationRoot.VEHICLES_DATA
                    destination.startsWith("vehicles/${layout.vehicle}/") -> CarModelDestinationRoot.VEHICLE
                    destination.startsWith("data/") -> CarModelDestinationRoot.DEPOT_DATA
                    else -> CarModelDestinationRoot.DEPOT_ROOT
                }
                val targetRelative = when (rootKind) {
                    CarModelDestinationRoot.VEHICLE -> destination.removePrefix("vehicles/${layout.vehicle}/")
                    CarModelDestinationRoot.VEHICLES_DATA -> destination.removePrefix("vehicles/data/")
                    CarModelDestinationRoot.DEPOT_DATA -> destination.removePrefix("data/")
                    CarModelDestinationRoot.DEPOT_ROOT -> destination
                }.removePrefix("paste to depot/")
                val normalizedDestination = when (rootKind) {
                    CarModelDestinationRoot.VEHICLE -> targetRelative
                    CarModelDestinationRoot.VEHICLES_DATA -> "vehicles/data/$targetRelative"
                    CarModelDestinationRoot.DEPOT_DATA -> "data/$targetRelative"
                    CarModelDestinationRoot.DEPOT_ROOT -> targetRelative
                }
                CarModelManifestValidator.validateRelativePath(normalizedDestination)
                CarModelFile(relative, normalizedDestination, classification.role, classification.format, classification.variant, classification.role != "unknown", file.length(), sha256(file), contentType(file), rootKind)
            }.onFailure { errors += "$relative: ${it.message}" }.getOrNull().also { onFileHashed(++hashed, files.size) }
        }.filterNotNull()
        if (entries.isEmpty()) errors += "No regular files found"
        if (errors.isNotEmpty() && errors.all { it.contains("recognised RR3") || it.contains("Incomplete package metadata") || it.contains("Manifest contains no files") }) errors.clear()
        val manifest = CarModelManifest(metadata = metadata, supportedGamePackages = emptyList(), target = target.copy(vehicleDirectory = layout.vehicle, relativeRoot = "files/.depot"), vehicleRoot = layout.vehicle, files = entries, warnings = warnings.toList())
        runCatching { CarModelManifestValidator.validate(manifest.copy(supportedGamePackages = listOf("com.ea.games.r3_row"))) }.onFailure { error ->
            if (entries.isNotEmpty() && !error.message.orEmpty().contains("Manifest contains no files")) errors += error.message ?: "Invalid manifest"
        }
        return ScanResult(manifest, ValidationReport(errors.distinct(), warnings.distinct()))
    }

    private data class Layout(val vehicle: String, val structured: Boolean, val destination: (String) -> String?)
    private fun targetFallback(fallback: String, warnings: MutableList<String>): String {
        val safe = fallback.replace(Regex("[^A-Za-z0-9_-]"), "_")
        warnings += "Vehicle directory was not present in the depot layout; explicit target '$safe' is required for installation."
        return safe
    }
    private fun detectLayout(paths: List<String>, detected: CarModelLayoutDetector.Detection, fallback: String, warnings: MutableList<String>): Layout {
        val prefix = detected.effectivePrefix
        val effectivePaths = paths.mapNotNull { path ->
            when {
                prefix.isEmpty() -> path
                path.startsWith("$prefix/") -> path.removePrefix("$prefix/")
                else -> null
            }
        }
        val vehicle = detected.vehicleDirectory
            ?: effectivePaths.firstOrNull { it.startsWith("vehicles/") && it.split('/').size >= 3 }?.split('/')?.getOrNull(1)
            ?: targetFallback(fallback, warnings)
        val hasStructured = detected.kind == CarModelLayoutDetector.Kind.DEPOT_CONTENT || effectivePaths.any {
            it == "data/events.dat.nct" || it.startsWith("vehicles/") ||
                it == "gametext.txt" || it == "asset_list_base.txtCache.txt" ||
                it.startsWith("gametext/") || it.startsWith("text_")
        }
        return if (hasStructured) Layout(vehicle, true) { originalPath ->
            val path = if (prefix.isNotEmpty() && originalPath.startsWith("$prefix/")) originalPath.removePrefix("$prefix/") else originalPath
            when {
                path.startsWith("vehicles/$vehicle/") || path.startsWith("vehicles/data/") || path.startsWith("data/") -> path
                path == "vehicles/$vehicle" -> null
                path.startsWith("vehicles/") -> { warnings += "Rejected vehicle path for a different vehicle: $path"; null }
                path.substringAfterLast('/').equals("gametext.txt", true) || path.substringAfterLast('/').startsWith("text_", true) || path.substringAfterLast('/').equals("asset_list_base.txtCache.txt", true) -> path
                path.contains('/') -> { warnings += "Rejected unknown depot path: $path"; null }
                else -> { warnings += "Rejected unknown top-level file for add-on: $path"; null }
            }
        } else Layout(fallback, false) { it }
    }

    data class Classification(val role: String, val format: String, val variant: String?)
    fun classify(name: String): Classification {
        val lower = name.lowercase()
        val format = when { lower.endsWith(".dds.z") -> "dds.z"; lower.endsWith(".dds") -> "dds"; lower.endsWith(".etc") -> "etc"; lower.endsWith(".atc") -> "atc"; lower.endsWith(".ptc") -> "ptc"; lower.endsWith(".pvr") -> "pvr"; lower.endsWith(".dxt") -> "dxt"; lower.endsWith(".png") -> "png"; lower.endsWith(".jpg") || lower.endsWith(".jpeg") -> "jpeg"; lower.endsWith(".m3g") -> "m3g"; lower.endsWith(".points") -> "points"; lower.endsWith(".bin") -> "bin"; else -> "unknown" }
        val role = when {
            format == "m3g" -> "mesh"
            format == "points" -> "points"
            format == "bin" -> "binary"
            lower.contains("livery") || lower.contains("skin") || lower.contains("ext") -> "livery"
            lower.contains("interior") || lower.contains("int_") -> "interior"
            lower.contains("wheel") || lower.contains("rim") -> "wheel"
            lower.contains("shadow") -> "shadow"
            format != "unknown" -> "texture"
            else -> "unknown"
        }
        val variant = Regex("(?:livery|skin|ext)[_-]?(\\d+)", RegexOption.IGNORE_CASE).find(name)?.groupValues?.get(1)?.let { "livery_$it" }
        return Classification(role, format, variant)
    }
    private fun sha256(file: File): String { val digest = MessageDigest.getInstance("SHA-256"); file.inputStream().use { input -> val buffer = ByteArray(8192); var read = input.read(buffer); while (read >= 0) { if (read > 0) digest.update(buffer, 0, read); read = input.read(buffer) } }; return digest.digest().joinToString("") { "%02x".format(it) } }
    private fun contentType(file: File) = when (file.extension.lowercase()) { "png" -> "image/png"; "jpg", "jpeg" -> "image/jpeg"; else -> "application/octet-stream" }
}
