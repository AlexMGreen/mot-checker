package io.agapps.feature.search

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import io.agapps.core.model.Vehicle
import io.agapps.feature.search.screens.SearchScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var clearSearch: String
    private lateinit var enterRegToSearch: String
    private lateinit var error: String
    private lateinit var recentVehicles: String
    private lateinit var viewAll: String

    private val searchedVehicle = Vehicle(
        registrationNumber = "ABC123",
        make = "FORD",
        model = "FOCUS",
        primaryColour = "Blue",
        fuelType = "Petrol",
        engineSizeCc = "1800",
        manufactureDate = "2010.11.13",
        emptyList()
    )

    private val recentVehicle = Vehicle(
        registrationNumber = "DEF345",
        make = "AUDI",
        model = "A4",
        primaryColour = "Blue",
        fuelType = "Petrol",
        engineSizeCc = "1800",
        manufactureDate = "2010.11.13",
        emptyList()
    )

    @Before
    fun setup() {
        composeTestRule.activity.apply {
            clearSearch = getString(R.string.clear_search)
            enterRegToSearch = getString(R.string.enter_reg_to_search)
            error = getString(io.agapps.core.ui.R.string.error)
            recentVehicles = getString(io.agapps.feature.recentvehicles.R.string.recent_vehicles)
            viewAll = getString(io.agapps.core.ui.R.string.view_all)
        }
    }

    @Test
    fun clear_button_not_shown_when_search_empty() {
        composeTestRule.setContent {
            SearchScreen(
                vehicleState = SearchVehicleViewState.Empty,
                recentVehicleState = SearchRecentsViewState.Loading,
                onBackClick = { },
                onVehicleClick = { },
                onViewAllRecentVehiclesClick = { },
                onRegistrationEntered = { }
            )
        }

        composeTestRule
            .onNodeWithContentDescription(clearSearch)
            .assertDoesNotExist()
    }

    @Test
    fun clear_button_shown_when_search_not_empty() {
        composeTestRule.setContent {
            SearchScreen(
                vehicleState = SearchVehicleViewState.Empty,
                recentVehicleState = SearchRecentsViewState.Loading,
                onBackClick = { },
                onVehicleClick = { },
                onViewAllRecentVehiclesClick = { },
                onRegistrationEntered = { }
            )
        }

        // Enter a search term
        composeTestRule
            .onNodeWithText("")
            .performTextInput("ABC")

        composeTestRule
            .onNodeWithContentDescription(clearSearch)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun search_prompt_shown_when_vehicle_viewstate_empty() {
        composeTestRule.setContent {
            SearchScreen(
                vehicleState = SearchVehicleViewState.Empty,
                recentVehicleState = SearchRecentsViewState.Loading,
                onBackClick = { },
                onVehicleClick = { },
                onViewAllRecentVehiclesClick = { },
                onRegistrationEntered = { }
            )
        }

        composeTestRule
            .onNodeWithText(enterRegToSearch)
            .assertIsDisplayed()
    }

    @Test
    fun error_shown_when_vehicle_viewstate_error() {
        composeTestRule.setContent {
            SearchScreen(
                vehicleState = SearchVehicleViewState.Error,
                recentVehicleState = SearchRecentsViewState.Loading,
                onBackClick = { },
                onVehicleClick = { },
                onViewAllRecentVehiclesClick = { },
                onRegistrationEntered = { }
            )
        }

        composeTestRule
            .onNodeWithText(error)
            .assertIsDisplayed()
    }


    @Test
    fun vehicle_registration_shown_when_vehicle_viewstate_success() {
        composeTestRule.setContent {
            SearchScreen(
                vehicleState = SearchVehicleViewState.Success(searchedVehicle),
                recentVehicleState = SearchRecentsViewState.Success(listOf(recentVehicle)),
                onBackClick = { },
                onVehicleClick = { },
                onViewAllRecentVehiclesClick = { },
                onRegistrationEntered = { }
            )
        }

        composeTestRule
            .onNodeWithText(searchedVehicle.registrationNumber)
            .assertIsDisplayed()
    }

    @Test
    fun vehicle_make_and_model_shown_when_vehicle_viewstate_success() {
        composeTestRule.setContent {
            SearchScreen(
                vehicleState = SearchVehicleViewState.Success(searchedVehicle),
                recentVehicleState = SearchRecentsViewState.Success(listOf(recentVehicle)),
                onBackClick = { },
                onVehicleClick = { },
                onViewAllRecentVehiclesClick = { },
                onRegistrationEntered = { }
            )
        }

        composeTestRule
            .onNodeWithText(searchedVehicle.capitalisedMakeAndModel)
            .assertIsDisplayed()
    }

    @Test
    fun vehicle_is_clickable_when_when_vehicle_viewstate_success() {
        composeTestRule.setContent {
            SearchScreen(
                vehicleState = SearchVehicleViewState.Success(searchedVehicle),
                recentVehicleState = SearchRecentsViewState.Success(listOf(recentVehicle)),
                onBackClick = { },
                onVehicleClick = { },
                onViewAllRecentVehiclesClick = { },
                onRegistrationEntered = { }
            )
        }

        composeTestRule
            .onNodeWithText(searchedVehicle.registrationNumber)
            .assertHasClickAction()
    }

    @Test
    fun recent_title_shown_when_recent_not_empty() {
        composeTestRule.setContent {
            SearchScreen(
                vehicleState = SearchVehicleViewState.Empty,
                recentVehicleState = SearchRecentsViewState.Success(listOf(recentVehicle)),
                onBackClick = { },
                onVehicleClick = { },
                onViewAllRecentVehiclesClick = { },
                onRegistrationEntered = { }
            )
        }

        composeTestRule
            .onNodeWithText(recentVehicles)
            .assertIsDisplayed()
    }

    @Test
    fun recent_title_not_shown_when_recent_empty() {
        composeTestRule.setContent {
            SearchScreen(
                vehicleState = SearchVehicleViewState.Empty,
                recentVehicleState = SearchRecentsViewState.Success(emptyList()),
                onBackClick = { },
                onVehicleClick = { },
                onViewAllRecentVehiclesClick = { },
                onRegistrationEntered = { }
            )
        }

        composeTestRule
            .onNodeWithText(recentVehicles)
            .assertDoesNotExist()
    }

    @Test
    fun view_all_shown_when_recent_not_empty() {
        composeTestRule.setContent {
            SearchScreen(
                vehicleState = SearchVehicleViewState.Empty,
                recentVehicleState = SearchRecentsViewState.Success(listOf(recentVehicle)),
                onBackClick = { },
                onVehicleClick = { },
                onViewAllRecentVehiclesClick = { },
                onRegistrationEntered = { }
            )
        }

        composeTestRule
            .onNodeWithText(viewAll)
            .assertIsDisplayed()
    }

    @Test
    fun view_allnot_shown_when_recent_empty() {
        composeTestRule.setContent {
            SearchScreen(
                vehicleState = SearchVehicleViewState.Empty,
                recentVehicleState = SearchRecentsViewState.Success(emptyList()),
                onBackClick = { },
                onVehicleClick = { },
                onViewAllRecentVehiclesClick = { },
                onRegistrationEntered = { }
            )
        }

        composeTestRule
            .onNodeWithText(viewAll)
            .assertDoesNotExist()
    }

    @Test
    fun vehicle_registration_shown_when_recents_not_empty() {
        composeTestRule.setContent {
            SearchScreen(
                vehicleState = SearchVehicleViewState.Empty,
                recentVehicleState = SearchRecentsViewState.Success(listOf(recentVehicle)),
                onBackClick = { },
                onVehicleClick = { },
                onViewAllRecentVehiclesClick = { },
                onRegistrationEntered = { }
            )
        }

        composeTestRule
            .onNodeWithText(recentVehicle.registrationNumber)
            .assertIsDisplayed()
    }

    @Test
    fun vehicle_make_and_model_shown_when_recents_not_empty() {
        composeTestRule.setContent {
            SearchScreen(
                vehicleState = SearchVehicleViewState.Empty,
                recentVehicleState = SearchRecentsViewState.Success(listOf(recentVehicle)),
                onBackClick = { },
                onVehicleClick = { },
                onViewAllRecentVehiclesClick = { },
                onRegistrationEntered = { }
            )
        }

        composeTestRule
            .onNodeWithText(recentVehicle.capitalisedMakeAndModel)
            .assertIsDisplayed()
    }

    @Test
    fun vehicle_is_clickable_when_recents_not_empty() {
        composeTestRule.setContent {
            SearchScreen(
                vehicleState = SearchVehicleViewState.Empty,
                recentVehicleState = SearchRecentsViewState.Success(listOf(recentVehicle)),
                onBackClick = { },
                onVehicleClick = { },
                onViewAllRecentVehiclesClick = { },
                onRegistrationEntered = { }
            )
        }

        composeTestRule
            .onNodeWithText(recentVehicle.registrationNumber)
            .assertHasClickAction()
    }
}
