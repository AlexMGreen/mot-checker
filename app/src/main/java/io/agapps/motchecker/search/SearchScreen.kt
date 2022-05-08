package io.agapps.motchecker.search

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.navigation.NavHostController
import io.agapps.domain.vehicledetails.VehicleDetails
import io.agapps.motchecker.R
import io.agapps.motchecker.home.components.NumberPlateTextField
import io.agapps.motchecker.search.components.MotStatus
import io.agapps.motchecker.search.components.VehicleMileage
import io.agapps.motchecker.search.components.VehicleSummary
import io.agapps.motchecker.ui.components.AppBottomBar
import io.agapps.motchecker.ui.theme.LightGrey
import io.agapps.motchecker.ui.theme.MOTCheckerTheme
import io.agapps.motchecker.ui.theme.SurfaceGrey
import io.agapps.motchecker.ui.theme.Typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val viewState: SearchViewState by viewModel.searchViewState.collectAsState()
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        floatingActionButton = { SearchFab(viewState) },
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
                NumberPlateTextField(
                    initialText = "",
                    modifier = modifier.statusBarsPadding(),
                    onTextChanged = { viewModel.onRegistrationNumberEntered(it) },
                    onCloseClicked = {
                        focusManager.clearFocus()
                        navController.popBackStack()
                    }
                )
            }

            when (@Suppress("UnnecessaryVariable") val state = viewState) {
                is SearchViewState.SearchResult -> SearchResultContent(state.vehicleDetails, listState, modifier)
                is SearchViewState.SearchError -> SearchErrorContent()
                is SearchViewState.SearchLoading -> SearchLoadingContent()
            }
        }
    }
}

@Composable
fun SearchFab(searchViewState: SearchViewState) {
    AnimatedVisibility(
        visible = searchViewState is SearchViewState.SearchResult,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        FloatingActionButton(
            onClick = {
                // TODO: Save vehicle
            }) {
            Icon(Icons.Default.FavoriteBorder, stringResource(id = R.string.search), tint = SurfaceGrey)
        }
    }
}

@Composable
fun SearchResultContent(
    vehicleDetails: VehicleDetails,
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

        item { VehicleSummary(vehicleDetails, modifier) }

        item { MotStatus(vehicleDetails, modifier) }

        val maxMileage = vehicleDetails.maxMileage
        val motTests = vehicleDetails.motTests
        // TODO: Display 'No mileage information' message
        if (maxMileage != null && motTests != null) {
            item { VehicleMileage(motTests, vehicleDetails.parsedManufactureDate, maxMileage, modifier) }
        }

        // TODO: Display 'No MOT information' message
        items(motTests.orEmpty()) { motTest ->
            // TODO: Mot test result items
        }
    }
}

@Composable
fun SearchLoadingContent() {
    Text(
        text = stringResource(id = R.string.loading),
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
        text = stringResource(id = R.string.error),
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
        SearchResultContent(VehicleDetails.vehiclePreview(), rememberLazyListState())
    }
}
