package io.agapps.motchecker.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.FlowRow
import io.agapps.domain.vehicledetails.VehicleDetails
import io.agapps.motchecker.R
import io.agapps.motchecker.home.components.NumberPlateTextField
import io.agapps.motchecker.ui.components.IconLabel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val viewState: SearchViewState by viewModel.searchViewState.collectAsState()
    val focusManager = LocalFocusManager.current

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
            SearchResultContent(searchResultViewState, modifier)
        }
    }
}

@Composable
fun SearchResultContent(searchResultViewState: SearchViewState.SearchResult, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        Spacer(modifier = Modifier.size(36.dp))

        VehicleSummary(searchResultViewState.vehicleDetails, modifier)

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VehicleSummary(vehicleDetails: VehicleDetails, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = vehicleDetails.capitalisedMakeAndModel,
            color = Color.White,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
        )

        FlowRow {
            IconLabel(modifier, vehicleDetails.primaryColour) { Icon(painterResource(id = R.drawable.ic_color), "") }
            IconLabel(modifier, vehicleDetails.manufactureDate) { Icon(painterResource(id = R.drawable.ic_calendar), "") }
        }
        FlowRow {
            IconLabel(modifier, vehicleDetails.fuelType) { Icon(painterResource(id = R.drawable.ic_fuel), "") }
            vehicleDetails.engineSizeCc?.let { engineSizeCc ->
                IconLabel(modifier, "${engineSizeCc}cc") { Icon(Icons.Filled.Info, "") }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121516, widthDp = 300)
@Composable
fun SearchResultContentPreview() {
    SearchResultContent(
        searchResultViewState = SearchViewState.SearchResult(
            VehicleDetails(
                "AB66XYZ",
                "Ford",
                "Focus",
                "Blue",
                "Petrol",
                "1600",
                "12.06.1990"
            )
        )
    )
}
