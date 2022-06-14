package io.agapps.vehicledetails

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import io.agapps.core.model.Vehicle
import io.agapps.vehicledetails.screens.VehicleDetailsScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VehicleDetailsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navigateBack: String
    private lateinit var selectToFavourite: String
    private lateinit var selectToUnFavourite: String

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
            navigateBack = getString(io.agapps.core.ui.R.string.navigate_back)
            selectToFavourite = getString(R.string.select_to_favourite)
            selectToUnFavourite = getString(R.string.select_to_un_favourite)
        }
    }

    @Test
    fun back_button_shown_on_success() {
        composeTestRule.setContent {
            VehicleDetailsScreen(
                viewState = VehicleDetailsViewState(
                    vehicleState = VehicleDetailsVehicleViewState.Success(vehicle.registrationNumber, vehicle),
                    favouriteState = VehicleDetailsFavouriteViewState.Favourite(true)
                ),
                onBackClick = { },
                setFavouriteState = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithContentDescription(navigateBack)
            .assertIsDisplayed()
    }

    @Test
    fun back_button_shown_on_error() {
        composeTestRule.setContent {
            VehicleDetailsScreen(
                viewState = VehicleDetailsViewState(
                    vehicleState = VehicleDetailsVehicleViewState.Error(vehicle.registrationNumber),
                    favouriteState = VehicleDetailsFavouriteViewState.Loading
                ),
                onBackClick = { },
                setFavouriteState = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithContentDescription(navigateBack)
            .assertIsDisplayed()
    }

    @Test
    fun vehicle_registration_shown_on_vehicle_success() {
        composeTestRule.setContent {
            VehicleDetailsScreen(
                viewState = VehicleDetailsViewState(
                    vehicleState = VehicleDetailsVehicleViewState.Success(vehicle.registrationNumber, vehicle),
                    favouriteState = VehicleDetailsFavouriteViewState.Favourite(true)
                ),
                onBackClick = { },
                setFavouriteState = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithText(vehicle.registrationNumber)
            .assertIsDisplayed()
    }

    @Test
    fun vehicle_make_and_model_shown_on_vehicle_success() {
        composeTestRule.setContent {
            VehicleDetailsScreen(
                viewState = VehicleDetailsViewState(
                    vehicleState = VehicleDetailsVehicleViewState.Success(vehicle.registrationNumber, vehicle),
                    favouriteState = VehicleDetailsFavouriteViewState.Favourite(true)
                ),
                onBackClick = { },
                setFavouriteState = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithText(vehicle.capitalisedMakeAndModel)
            .assertIsDisplayed()
    }

    @Test
    fun vehicle_colour_shown_on_vehicle_success() {
        composeTestRule.setContent {
            VehicleDetailsScreen(
                viewState = VehicleDetailsViewState(
                    vehicleState = VehicleDetailsVehicleViewState.Success(vehicle.registrationNumber, vehicle),
                    favouriteState = VehicleDetailsFavouriteViewState.Favourite(true)
                ),
                onBackClick = { },
                setFavouriteState = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithText(vehicle.primaryColour)
            .assertIsDisplayed()
    }

    @Test
    fun vehicle_fuel_type_shown_on_vehicle_success() {
        composeTestRule.setContent {
            VehicleDetailsScreen(
                viewState = VehicleDetailsViewState(
                    vehicleState = VehicleDetailsVehicleViewState.Success(vehicle.registrationNumber, vehicle),
                    favouriteState = VehicleDetailsFavouriteViewState.Favourite(true)
                ),
                onBackClick = { },
                setFavouriteState = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithText(vehicle.fuelType)
            .assertIsDisplayed()
    }

    @Test
    fun vehicle_manufacturer_date_shown_on_vehicle_success() {
        composeTestRule.setContent {
            VehicleDetailsScreen(
                viewState = VehicleDetailsViewState(
                    vehicleState = VehicleDetailsVehicleViewState.Success(vehicle.registrationNumber, vehicle),
                    favouriteState = VehicleDetailsFavouriteViewState.Favourite(true)
                ),
                onBackClick = { },
                setFavouriteState = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithText("13 Nov 2010")
            .assertIsDisplayed()
    }

    @Test
    fun vehicle_engine_size_shown_on_vehicle_success() {
        composeTestRule.setContent {
            VehicleDetailsScreen(
                viewState = VehicleDetailsViewState(
                    vehicleState = VehicleDetailsVehicleViewState.Success(vehicle.registrationNumber, vehicle),
                    favouriteState = VehicleDetailsFavouriteViewState.Favourite(true)
                ),
                onBackClick = { },
                setFavouriteState = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithText("${vehicle.engineSizeCc}cc")
            .assertIsDisplayed()
    }

    @Test
    fun fab_shown_on_favourite_success_and_is_favourite() {
        composeTestRule.setContent {
            VehicleDetailsScreen(
                viewState = VehicleDetailsViewState(
                    vehicleState = VehicleDetailsVehicleViewState.Success(vehicle.registrationNumber, vehicle),
                    favouriteState = VehicleDetailsFavouriteViewState.Favourite(true)
                ),
                onBackClick = { },
                setFavouriteState = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithContentDescription(selectToUnFavourite)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun fab_shown_on_favourite_success_and_is_not_favourite() {
        composeTestRule.setContent {
            VehicleDetailsScreen(
                viewState = VehicleDetailsViewState(
                    vehicleState = VehicleDetailsVehicleViewState.Success(vehicle.registrationNumber, vehicle),
                    favouriteState = VehicleDetailsFavouriteViewState.Favourite(false)
                ),
                onBackClick = { },
                setFavouriteState = { _, _ -> }
            )
        }

        composeTestRule
            .onNodeWithContentDescription(selectToFavourite)
            .assertIsDisplayed()
            .assertHasClickAction()
    }
}
