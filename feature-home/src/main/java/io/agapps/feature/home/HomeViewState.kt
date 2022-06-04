package io.agapps.feature.home

import io.agapps.core.model.Vehicle

sealed interface HomeViewState {

    data class Home(
        val recentVehicles: List<Vehicle>
    ) : HomeViewState

}
