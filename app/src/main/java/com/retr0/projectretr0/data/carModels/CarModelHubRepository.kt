package com.retr0.projectretr0.data.carModels

import java.io.File

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
    override suspend fun downloadPlan(model: CommunityCarModel): CarModelDownloadPlan = throw CarModelHubUnavailableException()
    override suspend fun fetch(url: String, output: File, onBytes: (Long) -> Unit) = throw CarModelHubUnavailableException()
    override suspend fun report(model: CommunityCarModel) = throw CarModelHubUnavailableException()
    override suspend fun deleteOwned(modelId: String): CarModelDeleteResult = throw CarModelHubUnavailableException()
}

/** Retained for error-code compatibility; production downloads go through CommunityCarModelInstaller. */
class CarModelHubDownloadUnsupportedException : CarModelException(CarModelErrorCode.HUB_DOWNLOAD_UNSUPPORTED)

/** Read side of the Community Hub for published car models. */
interface CarModelHubRepository : CarModelDownloadSource {
    suspend fun list(query: String, sort: String = "newest"): List<CommunityCarModel>
    suspend fun details(modelId: String): CommunityCarModel
    suspend fun report(model: CommunityCarModel)
    suspend fun deleteOwned(modelId: String): CarModelDeleteResult
}

class CloudflareCarModelHubRepository(private val api: CarModelApiClient) : CarModelHubRepository {
    override suspend fun list(query: String, sort: String): List<CommunityCarModel> = api.listPublished(query, sort)
    override suspend fun details(modelId: String): CommunityCarModel = api.getPublishedDetails(modelId)
    // Download only: verification, retries, media and library import are owned by CommunityCarModelInstaller.
    override suspend fun downloadPlan(model: CommunityCarModel): CarModelDownloadPlan = api.downloadPlan(model)
    override suspend fun fetch(url: String, output: File, onBytes: (Long) -> Unit) = api.fetchAsset(url, output, onBytes)
    override suspend fun report(model: CommunityCarModel) = throw UnsupportedOperationException("Public car-model reporting requires authenticated moderation flow")
    override suspend fun deleteOwned(modelId: String): CarModelDeleteResult = api.deleteOwned(modelId).let { CarModelDeleteResult(it.deleted, it.requestId) }
}

data class CarModelDeleteResult(val deleted: Boolean, val requestId: String?)
