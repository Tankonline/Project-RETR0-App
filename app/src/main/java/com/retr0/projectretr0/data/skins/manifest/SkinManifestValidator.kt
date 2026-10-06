package com.retr0.projectretr0.data.skins.manifest

import java.io.File

object SkinManifestValidator {
    fun validate(manifest: SkinManifest, packageRoot: File) {
        require(manifest.manifestVersion == 1) { "Unsupported skin manifest version" }
        require(manifest.packageType == "project-retr0-skin") { "Unsupported skin package type" }
        require(manifest.title.isNotBlank() && manifest.carDirectory.isNotBlank() && manifest.slotKey.isNotBlank()) { "Manifest metadata is incomplete" }
        validatePath(manifest.binary.path, packageRoot)
        manifest.cover?.let { validatePath(it.path, packageRoot) }
        require(manifest.screenshots.map { it.position }.distinct().size == manifest.screenshots.size) { "Duplicate screenshot positions" }
        manifest.screenshots.forEach { require(it.position in 1..4); validatePath(it.path, packageRoot) }
        manifest.targetRelativePath?.let { require(!it.startsWith("/") && !it.contains("..")) { "Unsafe target path" } }
    }

    private fun validatePath(path: String, root: File) {
        require(path.isNotBlank() && !path.startsWith("/") && !path.contains("\\") && !path.split('/').contains("..")) { "Unsafe manifest file path" }
        val canonicalRoot = root.canonicalFile.toPath()
        require(root.resolve(path).canonicalFile.toPath().startsWith(canonicalRoot)) { "Manifest path escapes package" }
    }
}
