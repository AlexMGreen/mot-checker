package io.agapps.feature.recentvehicles.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.agapps.core.navigation.AppNavigationDestination
import io.agapps.feature.recentvehicles.screen.RecentVehicleRoute

object RecentVehicleDestination : AppNavigationDestination {
    override val route = "recent_vehicle_route"
    override val destination = "recent_vehicle_destination"
}

fun NavGraphBuilder.recentVehicleGraph(
    navigateToSearch: (initialRegistration: String?) -> Unit,
    onBackClick: () -> Unit,
) {
    composable(route = RecentVehicleDestination.route) {
        RecentVehicleRoute(
            onBackClick = { onBackClick() },
            navigateToSearch = { initialRegistration -> navigateToSearch(initialRegistration) }
        )
    }
}
