package io.agapps.feature.search.screens

import android.annotation.SuppressLint
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
import androidx.compose.ui.Modifier
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
import io.agapps.feature.search.SearchViewModel
import io.agapps.feature.search.SearchViewState
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
        viewState = viewState,
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
    viewState: SearchViewState,
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
                    initialText = viewState.searchedRegistration,
                    onTextChanged = { onRegistrationEntered(it) },
                    onBackClicked = {
                        focusManager.clearFocus()
                        onBackClick()
                    }
                )
            }

            when (@Suppress("UnnecessaryVariable") val state = viewState) {
                is SearchViewState.SearchResult -> SearchResultContent(
                    vehicle = state.vehicle,
                    lazyListState = listState,
                    onVehicleClick = { vehicle ->
                        focusManager.clearFocus(true)
                        onVehicleClick(vehicle)
                    },
                    modifier = modifier
                )
                is SearchViewState.SearchError -> SearchErrorContent()
                is SearchViewState.SearchLoading -> SearchLoadingContent()
                is SearchViewState.SearchEmpty -> SearchEmptyContent(
                    recentVehicles = state.recentVehicles,
                    lazyListState = listState,
                    onVehicleClick = { vehicle ->
                        focusManager.clearFocus(true)
                        onVehicleClick(vehicle)
                    },
                    onViewAllRecentVehiclesClick = {
                        focusManager.clearFocus(true)
                        onViewAllRecentVehiclesClick()
                    },
                )
            }
        }
    }
}

@Composable
fun SearchResultContent(
    vehicle: Vehicle,
    lazyListState: LazyListState,
    onVehicleClick: (Vehicle) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        state = lazyListState
    ) {
        item { Spacer(modifier = Modifier.size(24.dp)) }

        item { SearchVehicleCard(modifier = modifier.padding(horizontal = 16.dp), vehicle = vehicle, onClick = { onVehicleClick(it) }) }
    }
}

@Composable
fun SearchEmptyContent(
    recentVehicles: List<Vehicle>,
    lazyListState: LazyListState,
    onVehicleClick: (Vehicle) -> Unit,
    onViewAllRecentVehiclesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        state = lazyListState
    ) {
        item { Spacer(modifier = Modifier.size(24.dp)) }

        item {
            if (recentVehicles.isNotEmpty()) {
                RecentVehicleSectionHeader(
                    onViewAllClicked = {
                        onViewAllRecentVehiclesClick()
                    }
                )
            }
        }

        items(recentVehicles) {
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


@Composable
fun SearchLoadingContent() {
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
fun SearchErrorContent() {
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
fun SearchResultContentPreview() {
    MOTCheckerTheme {
        SearchResultContent(Vehicle.vehiclePreview(), rememberLazyListState(), {})
    }
}
