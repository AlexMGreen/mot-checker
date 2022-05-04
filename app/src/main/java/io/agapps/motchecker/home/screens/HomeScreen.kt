package io.agapps.motchecker.home.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.agapps.motchecker.NavRoutes
import io.agapps.motchecker.home.components.HomeHeader

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    Column {
        HomeHeader(
            modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.30f)
        ) {
            navController.navigate(NavRoutes.Search.route)
        }
        // TODO: Expanding camera card - add border stroke and fade + expand when clicked
    }
}

@Preview(heightDp = 800)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
