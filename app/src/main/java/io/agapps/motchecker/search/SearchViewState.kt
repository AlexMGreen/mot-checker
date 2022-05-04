package io.agapps.motchecker.search

import io.agapps.domain.vehicledetails.VehicleDetails

sealed class SearchViewState {

    data class SearchResult(
        val vehicleDetails: VehicleDetails
    ) : SearchViewState()

    object SearchLoading : SearchViewState()

    data class SearchError(
        val error: String
    ) : SearchViewState()
}
