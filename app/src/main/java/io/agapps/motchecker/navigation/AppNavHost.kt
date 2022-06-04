package io.agapps.motchecker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.agapps.feature.home.navigation.HomeDestination
import io.agapps.feature.home.navigation.homeGraph
import io.agapps.feature.search.navigation.SearchDestination
import io.agapps.feature.search.navigation.searchGraph

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HomeDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeGraph(navigateToSearch = { initialRegistration ->
            if (initialRegistration == null) {
                navController.navigate(SearchDestination.route)
            } else {
                navController.navigate("${SearchDestination.route}?${SearchDestination.initialRegistrationArg}=$initialRegistration")
            }
        })
        searchGraph(onBackClick = { navController.popBackStack() })
    }
}
