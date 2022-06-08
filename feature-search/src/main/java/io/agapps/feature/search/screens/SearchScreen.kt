package io.agapps.feature.search.screens

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import io.agapps.core.model.Vehicle
import io.agapps.core.ui.component.AppBottomBar
import io.agapps.core.ui.theme.LightGrey
import io.agapps.core.ui.theme.MOTCheckerTheme
import io.agapps.core.ui.theme.Typography
import io.agapps.feature.recentvehicles.components.RecentVehicleCard
import io.agapps.feature.recentvehicles.components.RecentVehicleSectionHeader
import io.agapps.feature.search.SearchRecentsViewState
import io.agapps.feature.search.SearchVehicleViewState
import io.agapps.feature.search.SearchViewModel
import io.agapps.feature.search.components.NumberPlateTextField
import io.agapps.feature.search.components.SearchVehicleCard

@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onVehicleClick: (Vehicle) -> Unit,
    onViewAllRecentVehiclesClick: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val viewState by viewModel.viewState.collectAsState()
    SearchScreen(
        vehicleState = viewState.vehicleState,
        recentVehicleState = viewState.recentVehiclesState,
        onBackClick = onBackClick,
        onVehicleClick = onVehicleClick,
        onViewAllRecentVehiclesClick = onViewAllRecentVehiclesClick,
        onRegistrationEntered = { registration ->
            viewModel.onRegistrationNumberEntered(registration)
        },
        modifier = modifier
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    vehicleState: SearchVehicleViewState,
    recentVehicleState: SearchRecentsViewState,
    onBackClick: () -> Unit,
    onVehicleClick: (Vehicle) -> Unit,
    onViewAllRecentVehiclesClick: () -> Unit,
    onRegistrationEntered: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        bottomBar = { AppBottomBar(modifier = Modifier) }
    ) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            val toolbarAlpha = if (listState.firstVisibleItemIndex != 0) 1f else {
                @Suppress("MagicNumber")
                (listState.firstVisibleItemScrollOffset / 100f).coerceAtMost(1f)
            }

            Surface(color = LightGrey.copy(alpha = toolbarAlpha)) {
                NumberPlateTextField(
                    modifier = modifier
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    onTextChanged = { onRegistrationEntered(it) },
                    onBackClicked = {
                        focusManager.clearFocus()
                        onBackClick()
                    }
                )
            }

            SearchResultContent(
                vehicleState = vehicleState,
                lazyListState = listState,
                onVehicleClick = { vehicle ->
                    focusManager.clearFocus(true)
                    onVehicleClick(vehicle)
                },
                modifier = modifier
            )

            SearchRecentVehiclesContent(
                recentVehicleState = recentVehicleState,
                lazyListState = listState,
                onVehicleClick = { vehicle ->
                    focusManager.clearFocus(true)
                    onVehicleClick(vehicle)
                },
                onViewAllRecentVehiclesClick = {
                    focusManager.clearFocus(true)
                    onViewAllRecentVehiclesClick()
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun SearchResultContent(
    vehicleState: SearchVehicleViewState,
    lazyListState: LazyListState,
    onVehicleClick: (Vehicle) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        state = lazyListState
    ) {
        item { Spacer(modifier = Modifier.size(18.dp)) }

        when (vehicleState) {
            is SearchVehicleViewState.Empty -> {
                item { SearchBodyText(text = "Enter reg to search") }
            }
            is SearchVehicleViewState.Loading -> {
                item { SearchBodyText(stringResource(id = io.agapps.core.ui.R.string.loading)) }
            }
            is SearchVehicleViewState.Error -> {
                item {
                    val state = remember {
                        MutableTransitionState(false).apply {
                            // Start the animation immediately.
                            targetState = true
                        }
                    }
                    AnimatedVisibility(
                        visibleState = state,
                        enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
                        exit = fadeOut()
                    ) {
                        SearchBodyText(stringResource(id = io.agapps.core.ui.R.string.error))
                    }
                }
            }
            is SearchVehicleViewState.Success -> {
                item {
                    SearchVehicleCard(
                        modifier = modifier.padding(horizontal = 16.dp),
                        vehicle = vehicleState.vehicle,
                        onClick = { onVehicleClick(it) }
                    )
                }
            }
        }
    }
}

private const val BodyTextAlpha = 0.5f

@Composable
private fun SearchBodyText(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .alpha(BodyTextAlpha),
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        style = Typography.overline
    )
}

@Composable
private fun SearchRecentVehiclesContent(
    recentVehicleState: SearchRecentsViewState,
    lazyListState: LazyListState,
    onVehicleClick: (Vehicle) -> Unit,
    onViewAllRecentVehiclesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = Modifier.fillMaxWidth(), state = lazyListState) {
        if (recentVehicleState is SearchRecentsViewState.Success) {
            item { Spacer(modifier = Modifier.size(24.dp)) }

            item {
                if (recentVehicleState.recentVehicles.isNotEmpty()) {
                    RecentVehicleSectionHeader(
                        onViewAllClicked = {
                            onViewAllRecentVehiclesClick()
                        }
                    )
                }
            }

            items(recentVehicleState.recentVehicles) {
                RecentVehicleCard(
                    vehicle = it,
                    modifier = modifier.padding(horizontal = 16.dp),
                    onClick = { vehicle ->
                        onVehicleClick(vehicle)
                    }
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenPreview() {
    MOTCheckerTheme {
        SearchScreen(
            vehicleState = SearchVehicleViewState.Success(Vehicle.vehiclePreview()),
            recentVehicleState = SearchRecentsViewState.Success(listOf(Vehicle.vehiclePreview())),
            {},
            {},
            {},
            {}
        )
    }
}
