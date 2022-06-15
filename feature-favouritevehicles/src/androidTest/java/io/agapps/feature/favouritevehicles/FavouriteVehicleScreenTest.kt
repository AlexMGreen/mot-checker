package io.agapps.feature.favouritevehicles

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import io.agapps.core.model.Vehicle
import io.agapps.feature.favouritevehicles.screen.FavouriteVehicleScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavouriteVehicleScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var favouriteVehicles: String
    private lateinit var navigateBack: String

    private val vehicle = Vehicle(
        registrationNumber = "ABC123",
        make = "FORD",
        model = "FOCUS",
        primaryColour = "Blue",
        fuelType = "Petrol",
        engineSizeCc = "1800",
        manufactureDate = "2010.11.13",
        emptyList()
    )

    @Before
    fun setup() {
        composeTestRule.activity.apply {
            favouriteVehicles = getString(R.string.favourite_vehicles)
            navigateBack = getString(io.agapps.core.ui.R.string.navigate_back)
        }
    }

    @Test
    fun favourite_vehicles_toolbar_title_shown_on_empty_viewstate() {
        composeTestRule.setContent {
            FavouriteVehicleScreen(
                viewState = FavouriteVehicleViewState.Empty,
                navigateToVehicleDetails = { },
                onBackClick = { }
            )
        }

        composeTestRule
            .onAllNodesWithText(favouriteVehicles)
            .assertCountEquals(2)
    }

    @Test
    fun favourite_vehicles_list_title_shown_on_favourite_vehicles_viewstate() {
        composeTestRule.setContent {
            FavouriteVehicleScreen(
                viewState = FavouriteVehicleViewState.FavouriteVehicle(emptyList()),
                navigateToVehicleDetails = { },
                onBackClick = { }
            )
        }

        composeTestRule
            .onAllNodesWithText(favouriteVehicles)
            .assertCountEquals(2)
    }

    @Test
    fun back_shown_on_empty_viewstate() {
        composeTestRule.setContent {
            FavouriteVehicleScreen(
                viewState = FavouriteVehicleViewState.Empty,
                navigateToVehicleDetails = { },
                onBackClick = { }
            )
        }

        composeTestRule
            .onNodeWithContentDescription(navigateBack)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun back_shown_on_favourite_vehicles_viewstate() {
        composeTestRule.setContent {
            FavouriteVehicleScreen(
                viewState = FavouriteVehicleViewState.FavouriteVehicle(emptyList()),
                navigateToVehicleDetails = { },
                onBackClick = { }
            )
        }

        composeTestRule
            .onNodeWithContentDescription(navigateBack)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun vehicle_registration_shown_when_favourites_not_empty() {
        composeTestRule.setContent {
            FavouriteVehicleScreen(
                viewState = FavouriteVehicleViewState.FavouriteVehicle(listOf(vehicle)),
                navigateToVehicleDetails = { },
                onBackClick = { }
            )
        }

        composeTestRule
            .onNodeWithText(vehicle.registrationNumber)
            .assertIsDisplayed()
    }

    @Test
    fun vehicle_make_and_model_shown_when_favourites_not_empty() {
        composeTestRule.setContent {
            FavouriteVehicleScreen(
                viewState = FavouriteVehicleViewState.FavouriteVehicle(listOf(vehicle)),
                navigateToVehicleDetails = { },
                onBackClick = { }
            )
        }

        composeTestRule
            .onNodeWithText(vehicle.capitalisedMakeAndModel)
            .assertIsDisplayed()
    }

    @Test
    fun vehicle_is_clickable_when_recents_not_empty() {
        composeTestRule.setContent {
            FavouriteVehicleScreen(
                viewState = FavouriteVehicleViewState.FavouriteVehicle(listOf(vehicle)),
                navigateToVehicleDetails = { },
                onBackClick = { }
            )
        }

        composeTestRule
            .onNodeWithText(vehicle.registrationNumber)
            .assertHasClickAction()
    }
}

