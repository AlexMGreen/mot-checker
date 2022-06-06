package io.agapps.feature.search

import io.agapps.core.model.Vehicle

data class SearchViewState(
    val vehicleState: SearchVehicleViewState,
    val recentVehiclesState: SearchRecentsViewState,
)

sealed interface SearchVehicleViewState {
    data class Success(val vehicle: Vehicle) : SearchVehicleViewState
    object Error : SearchVehicleViewState
    object Loading : SearchVehicleViewState
    object Empty : SearchVehicleViewState
}

sealed interface SearchRecentsViewState {
    data class Success(val recentVehicles: List<Vehicle>) : SearchRecentsViewState
    object Error : SearchRecentsViewState
    object Loading : SearchRecentsViewState
}
