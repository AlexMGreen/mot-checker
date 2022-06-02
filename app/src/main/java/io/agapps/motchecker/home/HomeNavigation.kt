package io.agapps.motchecker.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.agapps.core.navigation.AppNavigationDestination
import io.agapps.motchecker.home.screens.HomeRoute

object HomeDestination : AppNavigationDestination {
    override val route = "home_route"
    override val destination = "home_destination"
}

fun NavGraphBuilder.homeGraph(
    navigateToSearch: () -> Unit,
) {
    composable(route = HomeDestination.route) {
        HomeRoute(navigateToSearch)
    }
}
