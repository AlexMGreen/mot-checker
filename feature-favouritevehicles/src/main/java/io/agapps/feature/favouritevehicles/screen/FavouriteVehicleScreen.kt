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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import io.agapps.core.ui.component.AppBottomBar
import io.agapps.core.ui.component.BottomListItemSpacer
import io.agapps.core.ui.component.buttons.BackButton
import io.agapps.core.ui.extensions.toFadeInOnScrollAlpha
import io.agapps.core.ui.extensions.toFadeOutOnScrollAlpha
import io.agapps.core.ui.theme.SurfaceGrey
import io.agapps.core.ui.theme.Typography
import io.agapps.feature.favouritevehicles.FavouriteVehicleViewModel
import io.agapps.feature.favouritevehicles.FavouriteVehicleViewState
import io.agapps.feature.favouritevehicles.R
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavouriteVehicleScreen(
    viewState: FavouriteVehicleViewState,
    navigateToVehicleDetails: (registration: String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        bottomBar = { AppBottomBar(modifier = Modifier) }
    ) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            val listState = rememberLazyListState()

            FavouriteVehicleToolbar(fadeInAlpha = listState.toFadeInOnScrollAlpha(), onBackClick = { onBackClick() })

            LazyColumn(modifier = Modifier.fillMaxWidth(), state = listState) {
                item {
                    FavouriteVehicleHeading(Modifier.alpha(listState.toFadeOutOnScrollAlpha()))
                }

                when (viewState) {
                    is FavouriteVehicleViewState.FavouriteVehicle -> items(viewState.favouriteVehicles) {
                        FavouriteVehicleCard(
                            vehicle = it,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            onClick = { vehicle ->
                                navigateToVehicleDetails(vehicle.registrationNumber)
                            }
                        )
                    }

                    is FavouriteVehicleViewState.Empty -> {
                        // TODO: Show empty state (e.g. when favourites list cleared)
                    }
                }

                item {
                    BottomListItemSpacer(toolbarHeight = 64.dp)
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
    Surface(color = SurfaceGrey.copy(alpha = fadeInAlpha)) {
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
        ) {
            BackButton(modifier = Modifier.align(Alignment.CenterVertically), onBackClicked = { onBackClick() })
            Text(
                text = stringResource(id = R.string.favourite_vehicles),
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
private fun FavouriteVehicleHeading(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.favourite_vehicles),
        color = Color.White,
        style = MaterialTheme.typography.h4,
        modifier = modifier.padding(top = 64.dp, bottom = 16.dp, start = 12.dp, end = 12.dp)
    )
}
