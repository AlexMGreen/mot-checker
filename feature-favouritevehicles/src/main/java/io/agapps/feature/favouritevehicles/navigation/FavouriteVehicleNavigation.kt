package io.agapps.feature.favouritevehicles.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.agapps.core.navigation.AppNavigationDestination
import io.agapps.feature.favouritevehicles.screen.FavouriteVehicleRoute

object FavouriteVehicleDestination : AppNavigationDestination {
    override val route = "favourite_vehicle_route"
    override val destination = "favourite_vehicle_destination"
}

fun NavGraphBuilder.favouriteVehicleGraph(
    navigateToVehicleDetails: (registration: String) -> Unit,
    onBackClick: () -> Unit,
) {
    composable(route = FavouriteVehicleDestination.route) {
        FavouriteVehicleRoute(
            onBackClick = onBackClick,
            navigateToVehicleDetails = navigateToVehicleDetails,
        )
    }
}
