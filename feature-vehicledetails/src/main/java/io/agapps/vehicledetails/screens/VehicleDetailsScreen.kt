package io.agapps.vehicledetails.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import io.agapps.core.model.MotTest
import io.agapps.core.model.Vehicle
import io.agapps.core.ui.component.AppBottomBar
import io.agapps.core.ui.component.NumberPlateText
import io.agapps.core.ui.theme.Black70
import io.agapps.core.ui.theme.LightGrey
import io.agapps.core.ui.theme.MOTCheckerTheme
import io.agapps.core.ui.theme.Typography
import io.agapps.vehicledetails.R
import io.agapps.vehicledetails.VehicleDetailsFavouriteViewState
import io.agapps.vehicledetails.VehicleDetailsVehicleViewState
import io.agapps.vehicledetails.VehicleDetailsViewModel
import io.agapps.vehicledetails.VehicleDetailsViewState
import io.agapps.vehicledetails.components.MotHistoryTitle
import io.agapps.vehicledetails.components.MotStatus
import io.agapps.vehicledetails.components.MotTestItem
import io.agapps.vehicledetails.components.VehicleMileage
import io.agapps.vehicledetails.components.VehicleSummary

@Composable
fun VehicleDetailsRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: VehicleDetailsViewModel = hiltViewModel(),
) {
    val viewState by viewModel.viewState.collectAsState()
    VehicleDetailsScreen(
        viewState = viewState,
        onBackClick = onBackClick,
        setFavouriteState = { vehicle, isFavourite ->
            viewModel.setFavouriteState(vehicle, isFavourite)
        },
        modifier = modifier
    )
}

@Composable
fun VehicleDetailsScreen(
    viewState: VehicleDetailsViewState,
    onBackClick: () -> Unit,
    setFavouriteState: (vehicle: Vehicle, isFavourite: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        floatingActionButton = {
            VehicleDetailsFab(
                viewState = viewState,
                setFavouriteState = setFavouriteState
            )
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = { AppBottomBar(modifier = Modifier) }
    ) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            val toolbarAlpha = if (listState.firstVisibleItemIndex != 0) 1f else {
                @Suppress("MagicNumber")
                (listState.firstVisibleItemScrollOffset / 100f).coerceAtMost(1f)
            }

            Surface(color = LightGrey.copy(alpha = toolbarAlpha)) {
                NumberPlateText(
                    modifier = modifier
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    text = viewState.vehicleState.registration,
                    onBackClicked = { onBackClick() }
                )
            }

            when (@Suppress("UnnecessaryVariable") val state = viewState.vehicleState) {
                is VehicleDetailsVehicleViewState.Success -> VehicleDetailsResultContent(state, listState, modifier)
                is VehicleDetailsVehicleViewState.Error -> VehicleDetailsErrorContent()
                is VehicleDetailsVehicleViewState.Loading -> VehicleDetailsLoadingContent()
            }
        }
    }
}

@Composable
fun VehicleDetailsFab(
    viewState: VehicleDetailsViewState,
    setFavouriteState: (vehicle: Vehicle, isFavourite: Boolean) -> Unit,
) {

    val vehicle = (viewState.vehicleState as? VehicleDetailsVehicleViewState.Success)?.vehicle

    AnimatedVisibility(
        visible = viewState.vehicleState is VehicleDetailsVehicleViewState.Success,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        FloatingActionButton(
            onClick = {
                vehicle?.let {
                    val isFavourite = (viewState.favouriteState as? VehicleDetailsFavouriteViewState.Favourite)?.isFavourite ?: return@let
                    setFavouriteState(it, !isFavourite)
                }
            }) {
            if (viewState.favouriteState is VehicleDetailsFavouriteViewState.Favourite) {
                if (viewState.favouriteState.isFavourite) {
                    Icon(Icons.Default.Favorite, stringResource(id = R.string.select_to_un_favourite), tint = Black70)
                } else {
                    Icon(Icons.Default.FavoriteBorder, stringResource(id = R.string.select_to_favourite), tint = Black70)
                }
            }
        }
    }
}

@Composable
fun VehicleDetailsResultContent(
    state: VehicleDetailsVehicleViewState.Success,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        state = lazyListState
    ) {
        item { Spacer(modifier = Modifier.size(48.dp)) }

        item { VehicleSummary(state.vehicle, modifier) }

        item { MotStatus(state.vehicle, modifier) }

        val maxMileage = state.vehicle.maxMileage
        val motTests = state.vehicle.motTests
        // TODO: Display 'No mileage information' message
        if (maxMileage != null && motTests != null) {
            item { VehicleMileage(motTests, state.vehicle.parsedManufactureDate, maxMileage, modifier) }
        }

        // TODO: Display 'No MOT information' message
        item { MotHistoryTitle(state.vehicle, modifier) }

        itemsIndexed(motTests.orEmpty()) { index: Int, motTest: MotTest ->
            // TODO: Pass in previous item's mileage to diff
            MotTestItem(motTest = motTest, modifier)
        }
    }
}

@Composable
fun VehicleDetailsLoadingContent() {
    Text(
        text = stringResource(id = io.agapps.core.ui.R.string.loading),
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        textAlign = TextAlign.Center,
        fontSize = 24.sp,
        style = Typography.overline
    )
}

@Composable
fun VehicleDetailsErrorContent() {
    Text(
        text = stringResource(id = io.agapps.core.ui.R.string.error),
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        textAlign = TextAlign.Center,
        fontSize = 24.sp,
        style = Typography.overline
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF121516, widthDp = 300)
@Composable
fun VehicleDetailsResultContentPreview() {
    MOTCheckerTheme {
        VehicleDetailsScreen(
            viewState = VehicleDetailsViewState(
                vehicleState = VehicleDetailsVehicleViewState.Success("AB12 ABC", Vehicle.vehiclePreview()),
                favouriteState = VehicleDetailsFavouriteViewState.Favourite(false)
            ),
            {},
            { x, y -> }
        )
    }
}
