package com.example.projectretr0installer.ui.skins

import com.example.projectretr0installer.IRetr0UserService
import com.example.projectretr0installer.data.skins.local.AppliedSkinEntity
import com.example.projectretr0installer.data.skins.local.LocalSkinEntity
import com.example.projectretr0installer.data.skins.local.LocalSkinRepository
import com.example.projectretr0installer.data.skins.local.SkinBackupEntity
import com.example.projectretr0installer.data.skins.local.SkinOperationEntity
import com.example.projectretr0installer.domain.skins.SkinTransactionCoordinator
import kotlinx.coroutines.flow.Flow

class SkinModsViewModel(
    private val local: LocalSkinRepository,
    private val transactions: SkinTransactionCoordinator,
) {
    val skins: Flow<List<LocalSkinEntity>> = local.observeSkins()
    val operations: Flow<List<SkinOperationEntity>> = local.observeOperations()
    val backups: Flow<List<SkinBackupEntity>> = local.observeBackups()
    val applied: Flow<List<AppliedSkinEntity>> = local.observeApplied()

    suspend fun apply(id: String, service: IRetr0UserService) = transactions.apply(id, service)
    suspend fun revert(id: String, service: IRetr0UserService) = transactions.revert(id, service)
    suspend fun recover(service: IRetr0UserService) = transactions.recover(service)
}
