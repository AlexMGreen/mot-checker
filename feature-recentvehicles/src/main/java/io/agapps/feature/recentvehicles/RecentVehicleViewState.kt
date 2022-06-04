package io.agapps.feature.recentvehicles

import io.agapps.core.model.Vehicle

sealed interface RecentVehicleViewState {

    data class RecentVehicle(
        val recentVehicles: List<Vehicle>
    ) : RecentVehicleViewState

    object Empty : RecentVehicleViewState
}
