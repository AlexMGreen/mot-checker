package io.agapps.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.agapps.core.model.Vehicle
import io.agapps.core.navigation.AppNavigationDestination
import io.agapps.feature.search.screens.SearchRoute

object SearchDestination : AppNavigationDestination {
    override val route = "search_route"
    override val destination = "search_destination"
    const val initialRegistrationArg = "initial_registration"
}

fun NavGraphBuilder.searchGraph(
    onBackClick: () -> Unit,
    onVehicleClick: (Vehicle) -> Unit,
    onViewAllRecentVehiclesClick: () -> Unit,
) {
    composable(
        route = "${SearchDestination.route}?${SearchDestination.initialRegistrationArg}={${SearchDestination.initialRegistrationArg}}",
        arguments = listOf(
            navArgument(SearchDestination.initialRegistrationArg) {
                type = NavType.StringType
                nullable = true
            }
        )
    ) {
        SearchRoute(
            onBackClick = onBackClick,
            onVehicleClick = onVehicleClick,
            onViewAllRecentVehiclesClick = onViewAllRecentVehiclesClick,
        )
    }
}
