package io.agapps.feature.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import io.agapps.core.model.Vehicle
import io.agapps.feature.home.screens.HomeScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var favouriteVehicles: String
    private lateinit var viewAll: String

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
            favouriteVehicles = getString(io.agapps.feature.favouritevehicles.R.string.favourite_vehicles)
            viewAll = getString(io.agapps.core.ui.R.string.view_all)
        }
    }

    @Test
    fun favourites_title_hidden_when_favourites_empty() {
        composeTestRule.setContent {
            HomeScreen(
                viewState = HomeViewState.Home(emptyList()),
                navigateToSearch = { },
                navigateToFavouriteVehicles = { },
                navigateToVehicleDetails = { }
            )
        }

        composeTestRule
            .onNodeWithText(favouriteVehicles)
            .assertDoesNotExist()
    }

    @Test
    fun favourites_view_all_hidden_when_favourites_empty() {
        composeTestRule.setContent {
            HomeScreen(
                viewState = HomeViewState.Home(emptyList()),
                navigateToSearch = { },
                navigateToFavouriteVehicles = { },
                navigateToVehicleDetails = { }
            )
        }

        composeTestRule
            .onNodeWithText(viewAll)
            .assertDoesNotExist()
    }

    @Test
    fun favourites_title_shown_when_favourites_not_empty() {
        composeTestRule.setContent {
            HomeScreen(
                viewState = HomeViewState.Home(listOf(vehicle)),
                navigateToSearch = { },
                navigateToFavouriteVehicles = { },
                navigateToVehicleDetails = { }
            )
        }

        composeTestRule
            .onNodeWithText(favouriteVehicles)
            .assertIsDisplayed()
    }

    @Test
    fun favourites_view_all_shown_when_favourites_not_empty() {
        composeTestRule.setContent {
            HomeScreen(
                viewState = HomeViewState.Home(listOf(vehicle)),
                navigateToSearch = { },
                navigateToFavouriteVehicles = { },
                navigateToVehicleDetails = { }
            )
        }

        composeTestRule
            .onNodeWithText(viewAll)
            .assertIsDisplayed()
    }

    @Test
    fun vehicle_registration_shown_when_favourites_not_empty() {
        composeTestRule.setContent {
            HomeScreen(
                viewState = HomeViewState.Home(listOf(vehicle)),
                navigateToSearch = { },
                navigateToFavouriteVehicles = { },
                navigateToVehicleDetails = { }
            )
        }

        composeTestRule
            .onNodeWithText(vehicle.registrationNumber)
            .assertIsDisplayed()
    }

    @Test
    fun vehicle_make_and_model_shown_when_favourites_not_empty() {
        composeTestRule.setContent {
            HomeScreen(
                viewState = HomeViewState.Home(listOf(vehicle)),
                navigateToSearch = { },
                navigateToFavouriteVehicles = { },
                navigateToVehicleDetails = { }
            )
        }

        composeTestRule
            .onNodeWithText(vehicle.capitalisedMakeAndModel)
            .assertIsDisplayed()
    }
}
