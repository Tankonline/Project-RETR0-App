package com.retr0.projectretr0.ui.skins

enum class Compatibility(val label: String) { COMPATIBLE("Fits"), WRONG_FORMAT("Check format"), TARGET_MISSING("Target missing"), GAME_NOT_DETECTED("Game not found"), VERIFICATION_UNAVAILABLE("Check unavailable") }
enum class SkinSlot(val label: String) { EXTERIOR_1("Exterior · Slot 1"), EXTERIOR_2("Exterior · Slot 2"), INTERIOR("Interior") }
enum class GamePackage(val id: String, val short: String) { ROW("com.ea.games.r3_row", "r3_row"), NA("com.ea.games.r3_na", "r3_na") }
enum class SkinStatus { LOCAL, DOWNLOADED, ACTIVE }
enum class DetectionConfidence { HIGH, LOW, UNKNOWN }
enum class OpKind { APPLY, REVERT }

data class CarSkin(val id: String, val title: String, val vehicle: String, val carId: String, val creator: String, val slot: SkinSlot, val format: String, val sizeMb: Double, val status: SkinStatus, val compatibility: Compatibility, val screenshotCount: Int = 0, val coverUrl: String? = null, val sha256: String = "a3f9c1…7e2b", val hasBackup: Boolean = true, val importedLabel: String = "Apr 12")
data class CommunitySkin(
    val id: String,
    val title: String,
    val vehicle: String,
    val carId: String,
    val creator: String,
    val slotKey: String,
    val slotLabel: String,
    val format: String,
    val gameVersions: String,
    val binarySizeBytes: Long,
    val likes: Int,
    val downloads: Int,
    val createdAt: String?,
    val updatedAt: String?,
    val verified: Boolean,
    val featured: Boolean,
    val compatibility: Compatibility,
    val description: String,
    val creatorId: String? = null,
    val creatorAvatarUrl: String? = null,
    val coverUrl: String? = null,
    val screenshotUrls: List<String> = emptyList(),
    val sourceFilename: String? = null,
    val binaryUrl: String? = null,
    val detailsUrl: String? = null,
    val shareUrl: String? = null,
    val binarySha256: String? = null,
    val manifestUrl: String? = null,
    val manifestSha256: String? = null,
    val manifestSize: Long? = null,
    val status: String? = null,
    val isOwner: Boolean = false,
    val likedByMe: Boolean = false,
) {
    val sizeMb: Double get() = binarySizeBytes / 1024.0 / 1024.0
    val uploadedLabel: String get() = createdAt?.take(10) ?: "Unknown"
    val screenshotCount: Int get() = screenshotUrls.size
}

object FakeSkins {
    val library = listOf(
        CarSkin("l1", "Midnight Carbon", "Audi R18 e-tron", "2014_audi_r18etronquattro", "Retr0", SkinSlot.EXTERIOR_1, "etc(1)", 4.2, SkinStatus.ACTIVE, Compatibility.COMPATIBLE, 2),
        CarSkin("l2", "Sunset Livery", "Porsche 911 RSR", "2016_porsche_911rsr", "ApexTuner", SkinSlot.EXTERIOR_1, "etc(1)", 3.8, SkinStatus.DOWNLOADED, Compatibility.COMPATIBLE, 3),
        CarSkin("l3", "Crimson Ghost", "Ferrari 488 GTE", "2016_ferrari_488gte", "NovaMods", SkinSlot.EXTERIOR_2, "dxt5", 5.1, SkinStatus.LOCAL, Compatibility.WRONG_FORMAT, 1),
        CarSkin("l4", "Blue Steel", "McLaren P1 GTR", "2015_mclaren_p1gtr", "Retr0", SkinSlot.INTERIOR, "etc(1)", 2.9, SkinStatus.LOCAL, Compatibility.COMPATIBLE)
    )
    val community = listOf(
        CommunitySkin("c1", "Aurora Drift", "Porsche 911 RSR", "2016_porsche_911rsr", "NovaMods", "ext_one", "Exterior - Slot 1", "etc(1)", "12.x - 13.x", 4_928_307, 1200, 4800, "2026-04-12", "2026-04-12", true, true, Compatibility.COMPATIBLE, "Hand-painted aurora gradient with brushed-metal flake and matte race numbers.", creatorId = "uuid-1", screenshotUrls = List(4) { "https://example.com/shot$it.jpg" }),
        CommunitySkin("c2", "Midnight Carbon", "Audi R18 e-tron", "2014_audi_r18etronquattro", "Retr0", "ext_one", "Exterior - Slot 1", "etc(1)", "12.x - 13.x", 4_404_019, 842, 2100, "2026-04-09", "2026-04-09", false, false, Compatibility.COMPATIBLE, "Matte carbon wrap with blue accents.", creatorId = "uuid-2", screenshotUrls = List(2) { "https://example.com/shot$it.jpg" }),
        CommunitySkin("c3", "Crimson Ghost", "Ferrari 488 GTE", "2016_ferrari_488gte", "NovaMods", "ext_two", "Exterior - Slot 2", "dxt5", "13.x", 5_347_738, 1500, 3900, "2026-04-07", "2026-04-07", true, false, Compatibility.WRONG_FORMAT, "Deep red with ghosted stripes.", creatorId = "uuid-3", screenshotUrls = List(3) { "https://example.com/shot$it.jpg" })
    )
    fun community(id: String) = community.first { it.id == id }
    fun local(id: String) = library.first { it.id == id }
}
