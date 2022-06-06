package io.agapps.vehicledetails.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.agapps.core.navigation.AppNavigationDestination
import io.agapps.vehicledetails.screens.VehicleDetailsRoute

object VehicleDetailsDestination : AppNavigationDestination {
    override val route = "vehicle_details_route"
    override val destination = "vehicle_details_destination"
    const val registrationArg = "registration"
}

fun NavGraphBuilder.vehicleDetailsGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = "${VehicleDetailsDestination.route}/{${VehicleDetailsDestination.registrationArg}}",
        arguments = listOf(
            navArgument(VehicleDetailsDestination.registrationArg) {
                type = NavType.StringType
            }
        )
    ) {
        VehicleDetailsRoute(onBackClick = onBackClick)
    }
}
