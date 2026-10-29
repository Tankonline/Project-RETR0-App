package com.retr0.projectretr0.data.carModels.manifest

/** Pure JVM detector for RR3 car-mod folder shapes. It uses content paths, never wrapper names. */
object CarModelLayoutDetector {
    enum class Kind { VEHICLE, DEPOT_CONTENT, WRAPPER }
    data class Detection(
        val kind: Kind,
        val effectivePrefix: String,
        val vehicleDirectory: String?,
        val paths: List<String>,
        val warnings: List<String> = emptyList(),
    )

    fun detect(inputPaths: Collection<String>): Detection {
        val paths = inputPaths.map { normalize(it) }.filter { it.isNotEmpty() }.distinct().sorted()
        require(paths.isNotEmpty()) { "No content paths found" }
        val wrapperCandidates = commonRoot(paths)
        val candidates = listOf("") + wrapperCandidates
        val matches = candidates.mapNotNull { prefix -> runCatching { classify(paths, prefix) }.getOrNull() }
            .distinctBy { Triple(it.kind, it.effectivePrefix, it.vehicleDirectory) }
        require(matches.isNotEmpty()) { "Folder does not contain a recognised RR3 vehicle or depot layout" }
        val best = matches.maxWithOrNull(compareBy<Detection> { score(it) }.thenByDescending { it.effectivePrefix.length })!!
        val equallyValid = matches.filter { score(it) == score(best) && it.effectivePrefix != best.effectivePrefix }
        require(equallyValid.isEmpty()) { "Ambiguous RR3 layout; select the effective depot-content folder" }
        val mixed = best.vehicleDirectory != null && best.paths.any { path ->
            path.startsWith("vehicles/") && path.split('/').size > 2 &&
                path.split('/')[1] != best.vehicleDirectory && path.split('/')[1] != "data"
        }
        require(!mixed) { "Mixed RR3 layout contains multiple vehicle directories" }
        return best.copy(warnings = if (best.effectivePrefix.isNotEmpty()) listOf("Wrapper folder ignored; using effective RR3 content root '${best.effectivePrefix}'") else emptyList())
    }

    private fun classify(paths: List<String>, prefix: String): Detection? {
        val relative = paths.mapNotNull { path ->
            when {
                prefix.isEmpty() -> path
                path == prefix -> null
                path.startsWith("$prefix/") -> path.removePrefix("$prefix/")
                else -> null
            }
        }
        if (relative.isEmpty()) return null
        val vehicleDirs = relative.mapNotNull { path ->
            if (path.startsWith("vehicles/") && path.split('/').size >= 3 && path.split('/')[1] != "data") path.split('/')[1] else null
        }.distinct()
        require(vehicleDirs.size <= 1) { "Mixed RR3 layout contains multiple vehicle directories" }
        val vehicle = vehicleDirs.singleOrNull()
        val hasDepotMarkers = relative.any { it == "data/events.dat.nct" || it.startsWith("vehicles/data/") || it.startsWith("gametext/") || it.startsWith("data/") || it == "asset_list_base.txtCache.txt" }
        val hasVehicleContent = vehicle != null && relative.any { it.startsWith("vehicles/$vehicle/") }
        return when {
            hasDepotMarkers && hasVehicleContent -> Detection(Kind.DEPOT_CONTENT, prefix, vehicle, relative)
            hasDepotMarkers -> Detection(Kind.DEPOT_CONTENT, prefix, null, relative)
            vehicle != null -> Detection(Kind.DEPOT_CONTENT, prefix, vehicle, relative)
            relative.any { it.contains('/') } -> Detection(Kind.WRAPPER, prefix, null, relative)
            else -> Detection(Kind.VEHICLE, prefix, null, relative)
        }
    }

    private fun score(detection: Detection): Int = when (detection.kind) {
        Kind.DEPOT_CONTENT -> 30 + if (detection.vehicleDirectory != null) 10 else 0
        Kind.VEHICLE -> 20
        Kind.WRAPPER -> 0
    }

    private fun commonRoot(paths: List<String>): String {
        val first = paths.firstOrNull() ?: return ""
        val firstSegment = first.substringBefore('/')
        return if (firstSegment !in setOf("vehicles", "data", "gametext") && paths.all { it.startsWith("$firstSegment/") }) firstSegment else ""
    }

    private fun normalize(path: String): String {
        val value = path.replace('\\', '/').trim('/')
        require(value.split('/').none { it.isEmpty() || it == "." || it == ".." }) { "Unsafe content path: $path" }
        return value
    }
}
