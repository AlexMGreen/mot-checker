package io.agapps.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.agapps.core.navigation.AppNavigationDestination
import io.agapps.motchecker.search.SearchRoute

object SearchDestination : AppNavigationDestination {
    override val route = "search_route"
    override val destination = "search_destination"
}

fun NavGraphBuilder.searchGraph(
    onBackClick: () -> Unit
) {
    composable(route = SearchDestination.route) {
        SearchRoute(onBackClick = onBackClick)
    }
}
