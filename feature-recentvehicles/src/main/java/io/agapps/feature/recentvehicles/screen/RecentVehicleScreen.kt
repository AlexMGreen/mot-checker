package io.agapps.feature.recentvehicles.screen

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
import io.agapps.common.mapRange
import io.agapps.core.ui.component.AppBottomBar
import io.agapps.core.ui.component.BottomListItemSpacer
import io.agapps.core.ui.component.buttons.BackButton
import io.agapps.core.ui.theme.SurfaceGrey
import io.agapps.core.ui.theme.Typography
import io.agapps.feature.recentvehicles.R
import io.agapps.feature.recentvehicles.RecentVehicleViewModel
import io.agapps.feature.recentvehicles.RecentVehicleViewState
import io.agapps.feature.recentvehicles.components.RecentVehicleCard

@Composable
fun RecentVehicleRoute(
    modifier: Modifier = Modifier,
    navigateToSearch: (initialRegistration: String?) -> Unit,
    onBackClick: () -> Unit,
    viewModel: RecentVehicleViewModel = hiltViewModel(),
) {
    val viewState by viewModel.viewState.collectAsState()
    RecentVehicleScreen(
        viewState = viewState,
        navigateToSearch = navigateToSearch,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

private const val ToolbarCollapseLimit = 300f

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RecentVehicleScreen(
    viewState: RecentVehicleViewState,
    navigateToSearch: (initialRegistration: String?) -> Unit,
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
            RecentVehicleToolbar(fadeInAlpha = fadeInAlpha, onBackClick = { onBackClick() })

            when (viewState) {
                is RecentVehicleViewState.RecentVehicle -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(),
                        state = listState
                    ) {
                        item {
                            RecentVehicleHeading(fadeOutAlpha)
                        }

                        items(viewState.recentVehicles) {
                            RecentVehicleCard(
                                vehicle = it,
                                modifier = modifier.padding(horizontal = 16.dp),
                                onClick = { vehicle ->
                                    navigateToSearch(vehicle.registrationNumber)
                                }
                            )
                        }

                        item {
                            BottomListItemSpacer(toolbarHeight = 64.dp)
                        }
                    }
                }

                is RecentVehicleViewState.Empty -> {
                    // TODO: Show empty state (e.g. when recent list cleared)
                }
            }

        }
    }
}

@Composable
private fun RecentVehicleToolbar(
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
                text = stringResource(id = R.string.recent_vehicles),
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
private fun RecentVehicleHeading(fadeOutAlpha: Float) {
    Text(
        text = stringResource(id = R.string.recent_vehicles),
        color = Color.White,
        style = MaterialTheme.typography.h4,
        modifier = Modifier
            .padding(top = 64.dp, bottom = 16.dp, start = 12.dp, end = 12.dp)
            .alpha(fadeOutAlpha)
    )
}
