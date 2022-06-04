package io.agapps.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.agapps.core.navigation.AppNavigationDestination
import io.agapps.feature.home.screens.HomeRoute

object HomeDestination : AppNavigationDestination {
    override val route = "home_route"
    override val destination = "home_destination"
}

fun NavGraphBuilder.homeGraph(
    navigateToSearch: (initialRegistration: String?) -> Unit,
) {
    composable(route = HomeDestination.route) {
        HomeRoute(navigateToSearch)
    }
}
