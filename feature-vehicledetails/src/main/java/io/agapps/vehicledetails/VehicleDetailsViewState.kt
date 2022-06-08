package io.agapps.vehicledetails

import io.agapps.core.model.Vehicle

data class VehicleDetailsViewState(
    val vehicleState: VehicleDetailsVehicleViewState,
    val favouriteState: VehicleDetailsFavouriteViewState,
)

sealed interface VehicleDetailsVehicleViewState {
    val registration: String

    data class Success(
        override val registration: String,
        val vehicle: Vehicle
    ) : VehicleDetailsVehicleViewState

    data class Error(override val registration: String) : VehicleDetailsVehicleViewState

    data class Loading(override val registration: String) : VehicleDetailsVehicleViewState
}

sealed interface VehicleDetailsFavouriteViewState {
    data class Favourite(val isFavourite: Boolean) : VehicleDetailsFavouriteViewState
    object Loading : VehicleDetailsFavouriteViewState
}
