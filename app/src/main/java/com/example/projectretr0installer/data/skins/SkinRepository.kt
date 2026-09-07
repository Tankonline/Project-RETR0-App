package com.example.projectretr0installer.data.skins

import com.example.projectretr0installer.ui.skins.CommunitySkin
import com.example.projectretr0installer.ui.skins.Compatibility
import java.util.Locale

open class SkinRepository(private val source: SkinDataSource = SkinApiClient()) {
    open suspend fun list(query: String = "", sort: String = "newest"): List<CommunitySkin> =
        source.list(query, sort).items.map(::map)

    open suspend fun details(id: String): CommunitySkin = map(source.details(id))

    internal fun map(api: SkinApiSkin): CommunitySkin = CommunitySkin(
        id = api.id,
        title = api.title,
        vehicle = vehicleName(api.carDirectory),
        carId = api.carDirectory,
        creator = api.creatorName,
        slotKey = api.slotKey,
        slotLabel = slotName(api.slotKey),
        format = api.textureFormat,
        gameVersions = api.gameVersions.orEmpty(),
        binarySizeBytes = api.binarySize,
        likes = api.likes,
        downloads = api.downloads,
        createdAt = api.createdAt,
        updatedAt = api.updatedAt,
        verified = false,
        featured = false,
        compatibility = Compatibility.GAME_NOT_DETECTED,
        description = api.description.orEmpty(),
        coverUrl = api.coverUrl,
        screenshotUrls = api.screenshots.map { it.url },
        sourceFilename = api.sourceFilename,
        binaryUrl = api.binaryUrl,
        binarySha256 = api.binarySha256,
        status = api.status,
    )

    private fun slotName(value: String): String = when (value.lowercase(Locale.ROOT)) {
        "ext_one", "exterior_1" -> "Exterior - Slot 1"
        "ext_two", "exterior_2" -> "Exterior - Slot 2"
        "interior" -> "Interior"
        else -> value.replace('_', ' ').trim().split(Regex("\\s+")).joinToString(" ") { word ->
            word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        }
    }

    private fun vehicleName(directory: String): String {
        val words = directory.substringAfter('_', directory).replace('_', ' ').trim()
        return words.split(Regex("\\s+")).joinToString(" ") { word ->
            word.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        }.ifBlank { directory }
    }
}
