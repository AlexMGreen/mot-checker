package io.agapps.feature.search

import io.agapps.core.model.Vehicle

sealed class SearchViewState {

    data class SearchResult(
        val vehicle: Vehicle
    ) : SearchViewState()

    object SearchLoading : SearchViewState()

    data class SearchError(
        val error: String
    ) : SearchViewState()
}
