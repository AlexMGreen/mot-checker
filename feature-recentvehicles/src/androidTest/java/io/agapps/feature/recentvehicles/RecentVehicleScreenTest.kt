package io.agapps.feature.recentvehicles

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import io.agapps.core.model.Vehicle
import io.agapps.feature.recentvehicles.screen.RecentVehicleScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RecentVehicleScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var recentVehicles: String
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
            recentVehicles = getString(R.string.recent_vehicles)
            navigateBack = getString(io.agapps.core.ui.R.string.navigate_back)
        }
    }

    @Test
    fun recent_vehicles_toolbar_title_shown_on_empty_viewstate() {
        composeTestRule.setContent {
            RecentVehicleScreen(
                viewState = RecentVehicleViewState.Empty,
                navigateToVehicleDetails = { },
                onBackClick = { }
            )
        }

        composeTestRule
            .onNodeWithText(recentVehicles)
            .assertIsDisplayed()
    }

    @Test
    fun recent_vehicles_list_title_shown_on_recent_vehicles_viewstate() {
        composeTestRule.setContent {
            RecentVehicleScreen(
                viewState = RecentVehicleViewState.RecentVehicle(emptyList()),
                navigateToVehicleDetails = { },
                onBackClick = { }
            )
        }

        composeTestRule
            .onAllNodesWithText(recentVehicles)
            .assertCountEquals(2)
    }

    @Test
    fun back_shown_on_empty_viewstate() {
        composeTestRule.setContent {
            RecentVehicleScreen(
                viewState = RecentVehicleViewState.Empty,
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
    fun back_shown_on_recent_vehicles_viewstate() {
        composeTestRule.setContent {
            RecentVehicleScreen(
                viewState = RecentVehicleViewState.RecentVehicle(emptyList()),
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
    fun vehicle_registration_shown_when_recents_not_empty() {
        composeTestRule.setContent {
            RecentVehicleScreen(
                viewState = RecentVehicleViewState.RecentVehicle(listOf(vehicle)),
                navigateToVehicleDetails = { },
                onBackClick = { }
            )
        }

        composeTestRule
            .onNodeWithText(vehicle.registrationNumber)
            .assertIsDisplayed()
    }

    @Test
    fun vehicle_make_and_model_shown_when_recents_not_empty() {
        composeTestRule.setContent {
            RecentVehicleScreen(
                viewState = RecentVehicleViewState.RecentVehicle(listOf(vehicle)),
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
            RecentVehicleScreen(
                viewState = RecentVehicleViewState.RecentVehicle(listOf(vehicle)),
                navigateToVehicleDetails = { },
                onBackClick = { }
            )
        }

        composeTestRule
            .onNodeWithText(vehicle.registrationNumber)
            .assertHasClickAction()
    }
}
