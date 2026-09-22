package com.example.projectretr0installer.data.skins.manifest

import org.json.JSONObject
import java.io.File

object SkinManifestStore {
    private const val FILE_NAME = "skin-manifest.json"

    fun read(packageRoot: File): SkinManifest? {
        val file = packageRoot.resolve(FILE_NAME)
        if (!file.isFile) return null
        return SkinManifest.fromJson(JSONObject(file.readText())).also { SkinManifestValidator.validate(it, packageRoot) }
    }

    fun write(packageRoot: File, manifest: SkinManifest) {
        SkinManifestValidator.validate(manifest, packageRoot)
        val temporary = packageRoot.resolve("$FILE_NAME.tmp")
        temporary.writeText(manifest.toJson().toString(2))
        require(temporary.renameTo(packageRoot.resolve(FILE_NAME))) { "Unable to save skin manifest" }
    }
}
