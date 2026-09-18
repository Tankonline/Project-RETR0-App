package com.example.projectretr0installer.ui.skins

sealed interface SkinsRoute {
    data object Gallery : SkinsRoute
    data object Library : SkinsRoute
    data object Import : SkinsRoute
    data class CommunityDetails(val id: String) : SkinsRoute
    data class LocalDetails(val id: String) : SkinsRoute
    data class Progress(val op: OpKind, val skinId: String) : SkinsRoute
}
