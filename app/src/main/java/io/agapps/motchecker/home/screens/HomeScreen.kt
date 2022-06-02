package io.agapps.motchecker.home.screens

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.agapps.motchecker.NavRoutes
import io.agapps.motchecker.R
import io.agapps.motchecker.home.components.CameraSearchCard
import io.agapps.motchecker.home.components.HomeHeader
import io.agapps.motchecker.ui.components.AppBottomBar
import io.agapps.motchecker.ui.theme.MOTCheckerTheme
import io.agapps.motchecker.ui.theme.SurfaceGrey

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(NavRoutes.Search.route) }) {
                Icon(Icons.Outlined.Search, stringResource(id = R.string.search), tint = SurfaceGrey)
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            AppBottomBar(modifier = Modifier) {
                IconButton(
                    onClick = {
                        // TODO: Navigate to saved vehicles
                    }) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = stringResource(id = R.string.saved_vehicles))
                }
            }
        }
    ) { paddingValues ->
        Column {
            HomeHeader(
                modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.30f)
            ) {
                navController.navigate(NavRoutes.Search.route)
            }

            // TODO: Permission request handling on click
            CameraSearchCard()
        }
    }
}

@Preview(heightDp = 800, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    MOTCheckerTheme {
        HomeScreen(navController = rememberNavController())
    }
}
