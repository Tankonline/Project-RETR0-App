package com.retr0.projectretr0.ui.carmodels

import com.retr0.projectretr0.data.carModels.CommunityCarModel

/**
 * A catalog refresh is authoritative. Failed or not-found responses must not fall back to
 * the previous downloadable rows.
 */
internal fun refreshedCatalogItems(
    result: Result<List<CommunityCarModel>>,
): List<CommunityCarModel> = result.getOrElse { emptyList() }
