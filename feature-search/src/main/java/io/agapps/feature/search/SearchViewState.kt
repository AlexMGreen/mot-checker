package io.agapps.feature.search

import io.agapps.core.model.VehicleDetails

sealed class SearchViewState {

    data class SearchResult(
        val vehicleDetails: VehicleDetails
    ) : SearchViewState()

    object SearchLoading : SearchViewState()

    data class SearchError(
        val error: String
    ) : SearchViewState()
}
