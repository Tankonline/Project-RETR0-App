package com.example.projectretr0installer.data.skins

import org.json.JSONArray
import org.json.JSONObject
import java.net.URI
import android.util.Log

data class SkinListResponse(val items: List<SkinApiSkin>, val limit: Int, val offset: Int)
data class SkinApiScreenshot(val url: String, val order: Int, val caption: String? = null)
data class SkinApiSkin(
    val id: String,
    val title: String,
    val creatorName: String,
    val description: String?,
    val carDirectory: String,
    val slotKey: String,
    val textureFormat: String,
    val sourceFilename: String?,
    val binarySize: Long,
    val binarySha256: String?,
    val screenshotCount: Int,
    val gameVersions: String?,
    val status: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val likes: Int,
    val downloads: Int,
    val coverUrl: String?,
    val binaryUrl: String?,
    val detailsUrl: String?,
    val shareUrl: String?,
    val creatorId: String? = null,
    val creatorAvatarUrl: String? = null,
    val isOwner: Boolean = false,
    val likedByMe: Boolean = false,
    val screenshots: List<SkinApiScreenshot> = emptyList(),
)

data class SkinPublicJwk(val x: String, val y: String) {
    fun toJson() = JSONObject().put("kty", "EC").put("crv", "P-256").put("x", x).put("y", y)
}

data class SkinDraft(
    val title: String,
    val creatorName: String,
    val description: String = "",
    val carDirectory: String,
    val slotKey: String,
    val textureFormat: String,
    val sourceFilename: String,
    val binarySize: Long,
    val binarySha256: String,
    val gameVersions: String = "",
) {
    fun toJson() = JSONObject()
        .put("title", title).put("creatorName", creatorName).put("description", description)
        .put("carDirectory", carDirectory).put("slotKey", slotKey).put("textureFormat", textureFormat)
        .put("sourceFilename", sourceFilename).put("binarySize", binarySize).put("binarySha256", binarySha256)
        .put("gameVersions", gameVersions)
}

data class SkinDraftResponse(
    val id: String,
    val status: String,
    val binaryUploadUrl: String,
    val coverUploadUrl: String,
    val publishUrl: String,
    val requestId: String?,
)
data class SkinMutationResponse(
    val success: Boolean,
    val id: String? = null,
    val status: String? = null,
    val liked: Boolean? = null,
    val likes: Int? = null,
    val duplicate: Boolean? = null,
    val deleted: Boolean? = null,
    val requestId: String? = null,
)
enum class SkinReportReason(val wireValue: String) { INAPPROPRIATE("inappropriate"), COPYRIGHT("copyright"), MISLEADING("misleading"), BROKEN("broken"), SPAM("spam"), OTHER("other") }

data class SkinApiError(val code: String, val message: String, val requestId: String?, val duplicateId: String? = null)

object SkinApiParser {
    fun parseList(json: String): SkinListResponse {
        val root = JSONObject(json)
        return SkinListResponse(root.requireArray("items").mapObjects(::parseSkin), root.optInt("limit", 20).coerceAtLeast(0), root.optInt("offset", 0).coerceAtLeast(0))
    }

    fun parseDetails(json: String): SkinApiSkin = parseSkin(JSONObject(json))

    fun parseDraft(json: String): SkinDraftResponse = JSONObject(json).let {
        SkinDraftResponse(it.requireString("id"), it.requireString("status"), it.requireHttps("binaryUploadUrl"), it.requireHttps("coverUploadUrl"), it.requireHttps("publishUrl"), it.optionalString("requestId"))
    }

    fun parseMutation(json: String): SkinMutationResponse = JSONObject(json).let {
        SkinMutationResponse(it.optBoolean("success", false), it.optionalString("id"), it.optionalString("status"), it.optionalBoolean("liked"), it.optionalInt("likes"), it.optionalBoolean("duplicate"), it.optionalBoolean("deleted"), it.optionalString("requestId"))
    }

    fun parseError(json: String): SkinApiError? = runCatching {
        val root = JSONObject(json)
        val error = root.getJSONObject("error")
        SkinApiError(error.requireString("code"), error.requireString("message"), root.optionalString("requestId"), error.optJSONObject("details")?.optionalString("duplicateId"))
    }.getOrNull()

    private fun parseSkin(value: JSONObject): SkinApiSkin {
        val screenshotValues = value.optJSONArray("screenshots") ?: JSONArray()
        val screenshots = screenshotValues.mapObjectsIndexed { index, item ->
            val order = when { item.has("position") -> item.optInt("position"); item.has("order") -> item.optInt("order"); else -> index }
            SkinApiScreenshot(item.requireHttps("url"), order, item.optionalString("caption"))
        }.sortedBy { it.order }
        return SkinApiSkin(
            value.requireString("id"), value.requireString("title"), value.requireString("creatorName"), value.optionalString("description"),
            value.requireString("carDirectory"), value.requireString("slotKey"), value.requireString("textureFormat"), value.optionalString("sourceFilename"),
            value.requireLong("binarySize").also { require(it >= 0) }, value.optionalString("binarySha256"), value.optInt("screenshotCount", screenshots.size).coerceAtLeast(0),
            value.optionalString("gameVersions"), value.optionalString("status"), value.optionalString("createdAt"), value.optionalString("updatedAt"),
            value.optInt("likes", 0).coerceAtLeast(0), value.optInt("downloads", 0).coerceAtLeast(0), value.optionalHttps("coverUrl"),
            value.optionalHttps("binaryUrl"), value.optionalHttps("detailsUrl"),
            value.optionalHttps("shareUrl").also { if (it == null && value.has("shareUrl")) Log.w("SKIN_API", "shareUrl parsing failed for skin ${value.optString("id")}") },
            value.optionalString("creator_id") ?: value.optionalString("installation_id"),
        value.optionalHttps("creator_avatar_url"),
        value.optBoolean("is_owner", false),
            value.optBoolean("liked_by_me", false), screenshots,
        )
    }

    private fun JSONObject.requireString(name: String): String = optionalString(name) ?: throw IllegalArgumentException("$name is required")
    private fun JSONObject.requireLong(name: String): Long {
        if (!has(name) || isNull(name)) throw IllegalArgumentException("$name is required")
        return runCatching { getLong(name) }.getOrElse { throw IllegalArgumentException("$name must be numeric", it) }
    }
    private fun JSONObject.optionalString(name: String): String? = if (!has(name) || isNull(name)) null else optString(name).trim().takeIf(String::isNotEmpty)
    private fun JSONObject.optionalBoolean(name: String): Boolean? = if (!has(name) || isNull(name)) null else getBoolean(name)
    private fun JSONObject.optionalInt(name: String): Int? = if (!has(name) || isNull(name)) null else getInt(name)
    private fun JSONObject.optionalHttps(name: String): String? = optionalString(name)?.let(::requireHttpsValue)
    private fun JSONObject.requireHttps(name: String): String = requireHttpsValue(requireString(name))
    private fun JSONObject.requireArray(name: String): JSONArray = optJSONArray(name) ?: throw IllegalArgumentException("$name must be an array")
    private fun requireHttpsValue(value: String): String {
        val uri = runCatching { URI(value) }.getOrElse { throw IllegalArgumentException("Invalid URL", it) }
        require(uri.scheme.equals("https", true) && !uri.host.isNullOrBlank()) { "Only HTTPS URLs are allowed" }
        return value
    }
    private inline fun <T> JSONArray.mapObjects(transform: (JSONObject) -> T) = (0 until length()).map { transform(getJSONObject(it)) }
    private inline fun <T> JSONArray.mapObjectsIndexed(transform: (Int, JSONObject) -> T) = (0 until length()).map { transform(it, getJSONObject(it)) }
}
