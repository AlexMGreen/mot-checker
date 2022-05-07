package io.agapps.motchecker.search

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import io.agapps.domain.vehicledetails.VehicleDetails
import io.agapps.motchecker.R
import io.agapps.motchecker.home.components.NumberPlateTextField
import io.agapps.motchecker.search.components.VehicleMileage
import io.agapps.motchecker.search.components.VehicleSummary
import io.agapps.motchecker.ui.components.AppBottomBar
import io.agapps.motchecker.ui.theme.MOTCheckerTheme
import io.agapps.motchecker.ui.theme.SurfaceGrey

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val viewState: SearchViewState by viewModel.searchViewState.collectAsState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        floatingActionButton = { SearchFab(viewState) },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = { AppBottomBar(modifier = Modifier) }
    ) { paddingValues ->
        Column(modifier = modifier.padding(8.dp)) {
            NumberPlateTextField(
                initialText = "",
                modifier = modifier.statusBarsPadding(),
                onTextChanged = { viewModel.onRegistrationNumberEntered(it) },
                onCloseClicked = {
                    focusManager.clearFocus()
                    navController.popBackStack()
                }
            )

            // TODO: One ScreenContent composable per viewstate
            AnimatedVisibility(
                visible = viewState is SearchViewState.SearchResult,
                enter = fadeIn() + slideInVertically { fullHeight -> fullHeight },
                exit = fadeOut() + slideOutVertically { fullHeight -> fullHeight }
            ) {
                val searchResultViewState = (viewState as? SearchViewState.SearchResult) ?: return@AnimatedVisibility
                SearchResultContent(searchResultViewState.vehicleDetails, modifier)
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
fun SearchResultContent(vehicleDetails: VehicleDetails, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        Spacer(modifier = Modifier.size(36.dp))

        VehicleSummary(vehicleDetails, modifier)

        val maxMileage = vehicleDetails.maxMileage
        val motTests = vehicleDetails.motTests
        if (maxMileage != null && motTests != null) {
            VehicleMileage(motTests, vehicleDetails.parsedManufactureDate, maxMileage, modifier)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121516, widthDp = 300)
@Composable
fun SearchResultContentPreview() {
    MOTCheckerTheme {
        SearchResultContent(VehicleDetails.vehiclePreview())
    }
}
