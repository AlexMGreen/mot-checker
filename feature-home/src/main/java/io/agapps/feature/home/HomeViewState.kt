package io.agapps.feature.home

import io.agapps.core.model.Vehicle

sealed interface HomeViewState {

    data class Home(
        val favouriteVehicles: List<Vehicle>
    ) : HomeViewState

}
