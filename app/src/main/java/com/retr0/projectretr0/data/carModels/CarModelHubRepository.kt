package com.retr0.projectretr0.data.carModels

/** A car model as listed in the Community Hub. */
data class CommunityCarModel(
    val id: String,
    val name: String,
    val creator: String,
    val description: String,
    val vehicleDirectory: String,
    val supportedPackages: List<String>,
    val gameBuild: String,
    val fileCount: Int,
    val totalBytes: Long,
    val likes: Int,
    val downloads: Int,
    val verifiedManifest: Boolean,
    val updatedAt: String,
    val coverUrl: String? = null,
    val screenshotUrls: List<String> = emptyList(),
    val shareUrl: String? = null,
)

/** Legacy marker retained for preview/error compatibility; the production repository is read-capable. */
@Deprecated("Use CloudflareCarModelHubRepository")
class CarModelHubUnavailableException : CarModelException(CarModelErrorCode.HUB_UNAVAILABLE)

@Deprecated("Preview-only; use CloudflareCarModelHubRepository")
object UnavailableCarModelHubRepository : CarModelHubRepository {
    override suspend fun list(query: String, sort: String): List<CommunityCarModel> = throw CarModelHubUnavailableException()
    override suspend fun details(modelId: String): CommunityCarModel = throw CarModelHubUnavailableException()
    override suspend fun download(model: CommunityCarModel, onProgress: (Int, Int) -> Unit): String = throw CarModelHubUnavailableException()
    override suspend fun report(model: CommunityCarModel) = throw CarModelHubUnavailableException()
    override suspend fun deleteOwned(modelId: String): CarModelDeleteResult = throw CarModelHubUnavailableException()
}

/** Download is intentionally explicit until the public manifest/file flow is wired into the importer. */
class CarModelHubDownloadUnsupportedException : CarModelException(CarModelErrorCode.HUB_DOWNLOAD_UNSUPPORTED)

/** Read side of the Community Hub for published car models. */
interface CarModelHubRepository {
    suspend fun list(query: String, sort: String = "newest"): List<CommunityCarModel>
    suspend fun details(modelId: String): CommunityCarModel
    suspend fun download(model: CommunityCarModel, onProgress: (Int, Int) -> Unit): String
    suspend fun report(model: CommunityCarModel)
    suspend fun deleteOwned(modelId: String): CarModelDeleteResult
}

class CloudflareCarModelHubRepository(private val api: CarModelApiClient) : CarModelHubRepository {
    override suspend fun list(query: String, sort: String): List<CommunityCarModel> = api.listPublished(query, sort)
    override suspend fun details(modelId: String): CommunityCarModel = api.getPublishedDetails(modelId)
    override suspend fun download(model: CommunityCarModel, onProgress: (Int, Int) -> Unit): String = throw CarModelHubDownloadUnsupportedException()
    override suspend fun report(model: CommunityCarModel) = throw UnsupportedOperationException("Public car-model reporting requires authenticated moderation flow")
    override suspend fun deleteOwned(modelId: String): CarModelDeleteResult = api.deleteOwned(modelId).let { CarModelDeleteResult(it.deleted, it.requestId) }
}

data class CarModelDeleteResult(val deleted: Boolean, val requestId: String?)
