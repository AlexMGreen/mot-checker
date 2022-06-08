package io.agapps.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.agapps.core.model.Vehicle
import io.agapps.core.navigation.AppNavigationDestination
import io.agapps.feature.search.screens.SearchRoute

object SearchDestination : AppNavigationDestination {
    override val route = "search_route"
    override val destination = "search_destination"
}

fun NavGraphBuilder.searchGraph(
    onBackClick: () -> Unit,
    onVehicleClick: (Vehicle) -> Unit,
    onViewAllRecentVehiclesClick: () -> Unit,
) {
    composable(route = SearchDestination.route) {
        SearchRoute(
            onBackClick = onBackClick,
            onVehicleClick = onVehicleClick,
            onViewAllRecentVehiclesClick = onViewAllRecentVehiclesClick,
        )
    }
}
