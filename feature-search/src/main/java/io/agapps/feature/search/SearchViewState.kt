package io.agapps.feature.search

import io.agapps.core.model.Vehicle

sealed class SearchViewState {
    abstract val searchedRegistration: String

    data class SearchResult(
        override val searchedRegistration: String,
        val vehicle: Vehicle,
    ) : SearchViewState()

    data class SearchLoading(
        override val searchedRegistration: String,
    ) : SearchViewState()

    data class SearchError(
        override val searchedRegistration: String,
        val error: String
    ) : SearchViewState()
}
