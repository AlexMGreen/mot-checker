package io.agapps.feature.favouritevehicles.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import io.agapps.common.mapRange
import io.agapps.core.ui.component.AppBottomBar
import io.agapps.core.ui.component.BottomListItemSpacer
import io.agapps.core.ui.component.buttons.BackButton
import io.agapps.core.ui.theme.SurfaceGrey
import io.agapps.core.ui.theme.Typography
import io.agapps.feature.favouritevehicles.FavouriteVehicleViewModel
import io.agapps.feature.favouritevehicles.FavouriteVehicleViewState
import io.agapps.feature.favouritevehicles.components.FavouriteVehicleCard

@Composable
fun FavouriteVehicleRoute(
    modifier: Modifier = Modifier,
    navigateToVehicleDetails: (registration: String) -> Unit,
    onBackClick: () -> Unit,
    viewModel: FavouriteVehicleViewModel = hiltViewModel(),
) {
    val viewState by viewModel.viewState.collectAsState()
    FavouriteVehicleScreen(
        viewState = viewState,
        navigateToVehicleDetails = navigateToVehicleDetails,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

private const val ToolbarCollapseLimit = 300f

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavouriteVehicleScreen(
    viewState: FavouriteVehicleViewState,
    navigateToVehicleDetails: (registration: String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        bottomBar = { AppBottomBar(modifier = Modifier) }
    ) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            val fadeInAlpha = if (listState.firstVisibleItemIndex > 0) 1f else
                listState.firstVisibleItemScrollOffset.toFloat().mapRange(0f, ToolbarCollapseLimit, 0f, 1f).coerceIn(0f, 1f)
            val fadeOutAlpha = 1f - fadeInAlpha
            FavouriteVehicleToolbar(fadeInAlpha = fadeInAlpha, onBackClick = { onBackClick() })

            when (viewState) {
                is FavouriteVehicleViewState.FavouriteVehicle -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(),
                        state = listState
                    ) {
                        item {
                            FavouriteVehicleHeading(fadeOutAlpha)
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
                            BottomListItemSpacer(toolbarHeight = 64.dp)
                        }
                    }
                }

                is FavouriteVehicleViewState.Empty -> {
                    // TODO: Show empty state (e.g. when recent list cleared)
                }
            }

        }
    }
}

@Composable
private fun FavouriteVehicleToolbar(
    fadeInAlpha: Float,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = SurfaceGrey.copy(alpha = fadeInAlpha),
    ) {
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
        ) {
            BackButton(modifier = Modifier.align(Alignment.CenterVertically), onBackClicked = { onBackClick() })
            Text(
                text = "Favourite Vehicles",
                style = Typography.overline,
                fontSize = 18.sp,
                modifier = modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.CenterVertically)
                    .alpha(fadeInAlpha)
            )
        }
    }
}

@Composable
private fun FavouriteVehicleHeading(fadeOutAlpha: Float) {
    Text(
        text = "Favourite Vehicles",
        color = Color.White,
        style = MaterialTheme.typography.h4,
        modifier = Modifier
            .padding(top = 64.dp, bottom = 16.dp, start = 12.dp, end = 12.dp)
            .alpha(fadeOutAlpha)
    )
}
