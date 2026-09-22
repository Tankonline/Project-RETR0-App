package com.example.projectretr0installer.data.skins.manifest

import org.json.JSONArray
import org.json.JSONObject

data class SkinManifest(
    val manifestVersion: Int = 1,
    val packageType: String = "project-retr0-skin",
    val skinId: String,
    val title: String,
    val description: String,
    val creator: String,
    val vehicle: String,
    val carDirectory: String,
    val slotKey: String,
    val textureFormat: String,
    val gamePackage: String,
    val sourceFilename: String,
    val targetRelativePath: String?,
    val binary: ManifestFile,
    val cover: ManifestFile?,
    val screenshots: List<ManifestScreenshot>,
    val communityId: String? = null,
) {
    fun toJson(): JSONObject = JSONObject().apply {
        put("manifestVersion", manifestVersion)
        put("packageType", packageType)
        put("skinId", skinId)
        put("metadata", JSONObject().apply {
            put("title", title); put("description", description); put("creator", creator)
            put("vehicle", vehicle); put("carDirectory", carDirectory); put("slotKey", slotKey)
            put("textureFormat", textureFormat); put("gamePackage", gamePackage)
        })
        put("source", JSONObject().apply {
            put("communityId", communityId ?: JSONObject.NULL); put("sourceFilename", sourceFilename)
        })
        put("target", JSONObject().apply { put("relativePath", targetRelativePath ?: JSONObject.NULL) })
        put("files", JSONObject().apply {
            put("binary", binary.toJson())
            put("cover", cover?.toJson() ?: JSONObject.NULL)
            put("screenshots", JSONArray(screenshots.map { it.toJson() }))
        })
    }

    companion object {
        fun fromJson(root: JSONObject): SkinManifest {
            val metadata = root.optJSONObject("metadata") ?: root
            val source = root.optJSONObject("source") ?: JSONObject()
            val target = root.optJSONObject("target") ?: JSONObject()
            val files = root.optJSONObject("files") ?: throw IllegalArgumentException("Manifest files are missing")
            val screenshotArray = files.optJSONArray("screenshots") ?: JSONArray()
            val screenshots = (0 until screenshotArray.length()).map { ManifestScreenshot.fromJson(screenshotArray.getJSONObject(it)) }
            return SkinManifest(
                manifestVersion = root.optInt("manifestVersion", 1),
                packageType = root.optString("packageType", "project-retr0-skin"),
                skinId = root.optString("skinId"), title = metadata.optString("title"),
                description = metadata.optString("description"), creator = metadata.optString("creator"),
                vehicle = metadata.optString("vehicle"), carDirectory = metadata.optString("carDirectory"),
                slotKey = metadata.optString("slotKey"), textureFormat = metadata.optString("textureFormat"),
                gamePackage = metadata.optString("gamePackage"), sourceFilename = source.optString("sourceFilename"),
                targetRelativePath = target.optString("relativePath").takeIf { it.isNotBlank() },
                binary = ManifestFile.fromJson(files.getJSONObject("binary")),
                cover = files.optJSONObject("cover")?.let(ManifestFile::fromJson), screenshots = screenshots,
                communityId = source.optString("communityId").takeIf { it.isNotBlank() && it != "null" },
            )
        }
    }
}

data class ManifestFile(val path: String, val contentType: String, val sizeBytes: Long, val sha256: String) {
    fun toJson() = JSONObject().put("path", path).put("contentType", contentType).put("sizeBytes", sizeBytes).put("sha256", sha256)
    companion object { fun fromJson(json: JSONObject) = ManifestFile(json.getString("path"), json.optString("contentType", "application/octet-stream"), json.getLong("sizeBytes"), json.getString("sha256")) }
}

data class ManifestScreenshot(val position: Int, val path: String, val contentType: String, val sizeBytes: Long, val sha256: String, val caption: String = "") {
    fun toJson() = JSONObject().put("position", position).put("path", path).put("contentType", contentType).put("sizeBytes", sizeBytes).put("sha256", sha256).put("caption", caption)
    companion object { fun fromJson(json: JSONObject) = ManifestScreenshot(json.getInt("position"), json.getString("path"), json.optString("contentType", "image/webp"), json.getLong("sizeBytes"), json.getString("sha256"), json.optString("caption")) }
}
