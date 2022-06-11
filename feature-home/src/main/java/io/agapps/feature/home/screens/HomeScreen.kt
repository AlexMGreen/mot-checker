package io.agapps.feature.home.screens

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.agapps.common.mapRange
import io.agapps.core.model.Vehicle
import io.agapps.core.ui.component.AppBottomBar
import io.agapps.core.ui.component.BottomListItemSpacer
import io.agapps.core.ui.theme.MOTCheckerTheme
import io.agapps.core.ui.theme.SurfaceGrey
import io.agapps.feature.camerasearch.component.CameraSearchCard
import io.agapps.feature.favouritevehicles.components.FavouriteVehicleCard
import io.agapps.feature.favouritevehicles.components.FavouriteVehicleSectionHeader
import io.agapps.feature.home.HomeViewModel
import io.agapps.feature.home.HomeViewState
import io.agapps.feature.home.components.HomeHeader
import kotlin.math.roundToInt

@Composable
fun HomeRoute(
    navigateToSearch: () -> Unit,
    navigateToFavouriteVehicles: () -> Unit,
    navigateToVehicleDetails: (registration: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    HomeScreen(
        viewState = viewState,
        navigateToSearch = navigateToSearch,
        navigateToFavouriteVehicles = navigateToFavouriteVehicles,
        navigateToVehicleDetails = navigateToVehicleDetails,
        modifier = modifier,
    )
}

private const val ToolbarCollapseLimit = -525

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewState: HomeViewState,
    navigateToSearch: () -> Unit,
    navigateToFavouriteVehicles: () -> Unit,
    navigateToVehicleDetails: (registration: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    HomeScaffold(navigateToSearch = navigateToSearch) {
        val toolbarHeight = 300.dp
        val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
        var toolbarOffsetHeightPx by rememberSaveable { mutableStateOf(0f) }
        var toolbarContentsAlpha by rememberSaveable { mutableStateOf(1f) }

        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val delta = available.y
                    val newOffset = (toolbarOffsetHeightPx + delta).coerceIn(-toolbarHeightPx, 0f)
                    if (newOffset >= ToolbarCollapseLimit) {
                        toolbarOffsetHeightPx = newOffset
                        toolbarContentsAlpha = newOffset.mapRange(ToolbarCollapseLimit.toFloat(), 0f, 0f, 1f)
                    }
                    return Offset.Zero
                }
            }
        }

        Box(
            modifier = modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {
            when (viewState) {
                is HomeViewState.Home -> {
                    HomeContent(
                        toolbarHeight = toolbarHeight,
                        viewState = viewState,
                        navigateToVehicleDetails = navigateToVehicleDetails,
                        navigateToFavouriteVehicles = navigateToFavouriteVehicles
                    )

                    HomeHeader(
                        modifier = Modifier
                            .height(toolbarHeight)
                            .fillMaxWidth()
                            .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.roundToInt()) },
                        fadeOnCollapseAlpha = toolbarContentsAlpha,
                        onNumberPlateClicked = { navigateToSearch() }
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeScaffold(
    navigateToSearch: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
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
        bottomBar = { AppBottomBar(modifier = Modifier) }
    ) {
        content(it)
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    toolbarHeight: Dp,
    viewState: HomeViewState.Home,
    navigateToVehicleDetails: (registration: String) -> Unit,
    navigateToFavouriteVehicles: () -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(top = toolbarHeight, bottom = 64.dp),
    ) {
        item { Spacer(modifier = Modifier.size(8.dp)) }
        item {
            // TODO: Permission request handling on click
            CameraSearchCard()
        }

        item {
            if (viewState.favouriteVehicles.isNotEmpty()) {
                FavouriteVehicleSectionHeader(onViewAllClicked = { navigateToFavouriteVehicles() })
            }
        }

        items(viewState.favouriteVehicles) {
            FavouriteVehicleCard(
                vehicle = it,
                modifier = modifier.padding(horizontal = 16.dp),
                onClick = { vehicle ->
                    navigateToVehicleDetails(vehicle.registrationNumber)
                }
            )
        }

        item {
            BottomListItemSpacer(toolbarHeight = 300.dp)
        }
    }
}

@Preview(heightDp = 800, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    MOTCheckerTheme {
        HomeScreen(
            viewState = HomeViewState.Home(listOf(Vehicle.vehiclePreview(), Vehicle.vehiclePreview())),
            navigateToVehicleDetails = {},
            navigateToSearch = {},
            navigateToFavouriteVehicles = {}
        )
    }
}
