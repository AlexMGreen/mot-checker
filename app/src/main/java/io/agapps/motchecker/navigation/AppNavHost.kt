package io.agapps.motchecker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.agapps.feature.home.navigation.HomeDestination
import io.agapps.feature.home.navigation.homeGraph
import io.agapps.feature.recentvehicles.navigation.RecentVehicleDestination
import io.agapps.feature.recentvehicles.navigation.recentVehicleGraph
import io.agapps.feature.search.navigation.SearchDestination
import io.agapps.feature.search.navigation.searchGraph
import io.agapps.vehicledetails.navigation.VehicleDetailsDestination
import io.agapps.vehicledetails.navigation.vehicleDetailsGraph

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
        homeGraph(
            navigateToSearch = { initialRegistration ->
                navigateToSearch(navController, initialRegistration)
            },
            navigateToRecentVehicle = {
                navController.navigate(RecentVehicleDestination.route)
            }
        )
        searchGraph(
            onBackClick = { navController.popBackStack() },
            onVehicleClick = { vehicle ->
                navController.navigate("${VehicleDetailsDestination.route}/${vehicle.registrationNumber}")
            },
            onViewAllRecentVehiclesClick = {
                navController.navigate(RecentVehicleDestination.route)
            }
        )
        vehicleDetailsGraph(
            onBackClick = { navController.popBackStack() }
        )
        recentVehicleGraph(
            onBackClick = { navController.popBackStack() },
            navigateToVehicleDetails = { registration ->
                navController.navigate("${VehicleDetailsDestination.route}/$registration")
            },
        )
    }
}

private fun navigateToSearch(
    navController: NavHostController,
    initialRegistration: String?
) {
    if (initialRegistration == null) {
        navController.navigate(SearchDestination.route)
    } else {
        navController.navigate("${SearchDestination.route}?${SearchDestination.initialRegistrationArg}=$initialRegistration")
    }
}
