package com.example.projectretr0installer.data.skins

import android.content.Context
import com.example.projectretr0installer.data.skins.local.LocalSkinEntity
import com.example.projectretr0installer.data.skins.local.LocalSkinRepository
import com.example.projectretr0installer.data.skins.local.VerifiedFilePromoter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.util.UUID

class SkinDownloadManager(
    private val filesRoot: File,
    private val localRepository: LocalSkinRepository,
    private val client: OkHttpClient = OkHttpClient(),
) {
    constructor(context: Context) : this(File(context.filesDir, "skins"), LocalSkinRepository(context))

    suspend fun download(skin: CommunityDownload, onProgress: (Int) -> Unit = {}): LocalSkinEntity = withContext(Dispatchers.IO) {
        require(skin.url.startsWith("https://")) { "Only HTTPS downloads are allowed" }
        require(skin.size in (1..VerifiedFilePromoter.MAX_BINARY_BYTES)) { "Invalid skin download size" }
        require(skin.sha256.matches(Regex("[0-9a-fA-F]{64}"))) { "Invalid skin SHA-256" }
        val id = UUID.randomUUID().toString()
        val directory = File(filesRoot, id).apply { mkdirs() }
        val safeFilename = skin.sourceFilename.substringAfterLast('/').substringAfterLast('\\')
        require(safeFilename == skin.sourceFilename && safeFilename.isNotBlank() && safeFilename != "." && safeFilename != "..") { "Invalid skin filename" }
        val destination = File(directory, safeFilename)
        try {
            client.newCall(Request.Builder().url(skin.url).get().build()).execute().use { response ->
                require(response.isSuccessful) { "Skin download failed with HTTP ${response.code}" }
                val body = response.body ?: error("Skin download had no body")
                val headerLength = body.contentLength()
                require(headerLength < 0 || headerLength == skin.size) { "Download Content-Length does not match metadata" }
                val verified = VerifiedFilePromoter.promote(body.byteStream(), destination, skin.size, skin.sha256) { bytes -> onProgress(((bytes * 90) / skin.size).toInt()) }
                val entity = LocalSkinEntity(id, skin.communityId, skin.title, skin.creator, skin.vehicle, skin.carDirectory, skin.slotKey, skin.textureFormat, skin.sourceFilename, verified.file.absolutePath, verified.size, verified.sha256, null, skin.gamePackage, System.currentTimeMillis())
                localRepository.saveDownloaded(entity)
                onProgress(100)
                entity
            }
        } catch (error: Throwable) {
            directory.deleteRecursively()
            throw error
        }
    }
}

data class CommunityDownload(
    val communityId: String,
    val title: String,
    val creator: String,
    val vehicle: String,
    val carDirectory: String,
    val slotKey: String,
    val textureFormat: String,
    val sourceFilename: String,
    val gamePackage: String,
    val url: String,
    val size: Long,
    val sha256: String,
    val companionUrl: String? = null,
    val companionFilename: String? = null,
)
