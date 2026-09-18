package com.example.projectretr0installer.ui.skins

enum class Health { GOOD, WARNING, ERROR }
data class SystemStatus(val connected: Boolean, val gameInstalled: Boolean, val gamePackage: GamePackage, val backupHealth: Health, val storage: String)
data class OverviewStats(val local: Int, val applied: Int, val backups: Int, val failures: Int)
enum class WarningKind { SHIZUKU, GAME, BACKUP, STORAGE }
data class SkinWarning(val kind: WarningKind, val title: String, val message: String)
data class AppliedOverride(val id: String, val skinId: String, val vehicle: String, val targetName: String, val appliedAt: String, val backupHealth: Health, val verified: Boolean)
enum class BackupState { VALID, MISSING, CORRUPT, IN_USE }
data class OriginalBackup(val id: String, val vehicle: String, val targetName: String, val hash: String, val createdAt: String, val size: String, val state: BackupState, val skin: String?)
enum class OpType { APPLY, REVERT, RESTORE_ALL, SCAN, VERIFY }
enum class OpResult { ACTIVE, SUCCESS, FAILED, PARTIAL, CANCELLED }
data class ActivityOp(val id: String, val type: OpType, val title: String, val stage: String, val progress: Int, val bytes: String, val timestamp: String, val result: OpResult, val error: String? = null)
enum class SkinModsSection(val label: String) { OVERVIEW("Overview"), LIBRARY("My Skins"), APPLIED("Applied"), BACKUPS("Backups"), ACTIVITY("Activity") }
enum class SkinModsPreviewState { LOADING, EMPTY, CONTENT, ERROR, OFFLINE, DISCONNECTED, MISSING_GAME, ACTIVE, SUCCESS, PARTIAL_FAILURE }

object FakeSkinMods {
    const val AUDI_TARGET = "2014_audi_r18_livery_etc(1).dds.z"
    const val PORSCHE_TARGET = "2016_porsche_911rsr_livery_etc(1).dds.z"
    val status = SystemStatus(true, true, GamePackage.ROW, Health.GOOD, "18.4 GB available")
    val applied = listOf(AppliedOverride("a1", "l1", "Audi R18 e-tron", AUDI_TARGET, "Today, 14:32", Health.GOOD, true))
    val backups = listOf(
        OriginalBackup("b1", "Audi R18 e-tron", AUDI_TARGET, "94b6d3f26d988b742da9bd2c77833e7fac4e0cbb58e07bb7a79cb25b77a023bd", "Apr 12, 2026", "4.20 MiB", BackupState.IN_USE, "Midnight Carbon"),
        OriginalBackup("b2", "Porsche 911 RSR", PORSCHE_TARGET, "0f87d41f53f149bbc9ca2f4010791fa602cad5aa2edbb2f97f123930401565cb", "Apr 10, 2026", "3.80 MiB", BackupState.VALID, null),
    )
    val history = listOf(ActivityOp("h1", OpType.APPLY, "Midnight Carbon", "Verified target and backup", 100, "4.20 / 4.20 MiB", "Today, 14:32", OpResult.SUCCESS))
}
