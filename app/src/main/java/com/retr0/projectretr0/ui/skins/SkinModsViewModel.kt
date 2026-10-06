package com.retr0.projectretr0.ui.skins

import com.retr0.projectretr0.IRetr0UserService
import com.retr0.projectretr0.data.skins.local.AppliedSkinEntity
import com.retr0.projectretr0.data.skins.local.LocalSkinEntity
import com.retr0.projectretr0.data.skins.local.LocalSkinRepository
import com.retr0.projectretr0.data.skins.local.SkinBackupEntity
import com.retr0.projectretr0.data.skins.local.SkinOperationEntity
import com.retr0.projectretr0.domain.skins.SkinTransactionCoordinator
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
    suspend fun restoreAll(service: IRetr0UserService) = transactions.restoreAll(service)
    suspend fun recover(service: IRetr0UserService) = transactions.recover(service)
}
