package io.agapps.feature.favouritevehicles

import io.agapps.core.model.Vehicle

sealed interface FavouriteVehicleViewState {

    data class FavouriteVehicle(
        val recentVehicles: List<Vehicle>
    ) : FavouriteVehicleViewState

    object Empty : FavouriteVehicleViewState
}
