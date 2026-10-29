package com.retr0.projectretr0.data.carModels.local

import com.retr0.projectretr0.data.carModels.manifest.CarModelFolderScanner

/** How the import wizard groups the files of a car-model folder. Purely presentational; nothing is renamed. */
enum class CarModelFileGroup(val label: String, val extensionLabel: String) {
    MESHES("Model meshes", ".m3g"),
    POINTS("Points", ".points"),
    LIVERIES("Liveries / textures", "livery/ · .dds.z"),
    BINARIES("Binaries", ".bin"),
    OTHER("Other files", "unrecognised"),
}

object CarModelFileGroups {
    private val textureFormats = setOf("dds.z", "dds", "etc", "atc", "ptc", "pvr", "dxt")
    // Only obvious host/download metadata is ignored. RR3 text/localisation/configuration
    // files are valid mod assets and must remain installable.
    private val ignoredExtensions = setOf("md", "nfo", "url", "htm", "html", "pdf", "doc", "docx", "rtf", "zip", "rar", "7z", "lnk")

    fun groupOf(relativePath: String): CarModelFileGroup {
        // File types come from the scanner so the wizard and the upload manifest agree.
        val lower = relativePath.replace('\\', '/').lowercase()
        val format = CarModelFolderScanner.classify(lower.substringAfterLast('/')).format
        return when {
            format == "m3g" -> CarModelFileGroup.MESHES
            format == "points" -> CarModelFileGroup.POINTS
            format in textureFormats || lower.startsWith("livery/") || "/livery/" in lower -> CarModelFileGroup.LIVERIES
            format == "bin" -> CarModelFileGroup.BINARIES
            else -> CarModelFileGroup.OTHER
        }
    }

    /** True only for metadata/archive files that are not part of a vehicle directory. */
    fun isIgnored(fileName: String): Boolean {
        val lower = fileName.lowercase()
        return lower.startsWith(".") || lower.substringAfterLast('.', "") in ignoredExtensions
    }

    fun summarize(relativePaths: List<String>): Map<CarModelFileGroup, Int> =
        relativePaths.groupingBy(::groupOf).eachCount().toSortedMap()
}
