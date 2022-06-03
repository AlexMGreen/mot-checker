package io.agapps.feature.home.screens

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import io.agapps.core.model.Vehicle
import io.agapps.core.ui.component.AppBottomBar
import io.agapps.core.ui.theme.MOTCheckerTheme
import io.agapps.core.ui.theme.SurfaceGrey
import io.agapps.feature.camerasearch.component.CameraSearchCard
import io.agapps.feature.home.HomeViewModel
import io.agapps.feature.home.HomeViewState
import io.agapps.feature.home.R
import io.agapps.feature.home.components.HomeHeader
import io.agapps.feature.recentvehicles.components.RecentVehicleList

@Composable
fun HomeRoute(
    navigateToSearch: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    HomeScreen(
        viewState = viewState,
        navigateToSearch = navigateToSearch,
        modifier = modifier,
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewState: HomeViewState,
    navigateToSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(onClick = { navigateToSearch() }) {
                Icon(Icons.Outlined.Search, stringResource(id = io.agapps.core.ui.R.string.search), tint = SurfaceGrey)
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
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.30f),
                onNumberPlateClicked = { navigateToSearch() }
            )

            when (viewState) {
                is HomeViewState.Home -> {
                    // TODO: Permission request handling on click
                    CameraSearchCard()
                    RecentVehicleList(
                        vehicles = viewState.recentVehicles,
                        onVehicleClicked = {
                            // TODO: Navigate to search for vehicle reg
                        },
                        onViewAllClicked = {
                            // TODO: Show all recents
                        }
                    )
                }
            }
        }
    }
}

@Preview(heightDp = 800, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    MOTCheckerTheme {
        HomeScreen(viewState = HomeViewState.Home(listOf(Vehicle.vehiclePreview(), Vehicle.vehiclePreview())), navigateToSearch = {})
    }
}
