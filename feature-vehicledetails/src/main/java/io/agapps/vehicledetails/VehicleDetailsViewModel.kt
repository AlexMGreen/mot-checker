package io.agapps.vehicledetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.Timber
import dagger.hilt.android.lifecycle.HiltViewModel
import io.agapps.common.result.Result
import io.agapps.common.result.asResult
import io.agapps.core.data.repository.VehicleRepository
import io.agapps.core.model.Vehicle
import io.agapps.feature.favouritevehicles.data.FavouriteVehicleRepository
import io.agapps.feature.recentvehicles.data.RecentVehicleRepository
import io.agapps.vehicledetails.navigation.VehicleDetailsDestination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    vehicleRepository: VehicleRepository,
    private val favouriteVehicleRepository: FavouriteVehicleRepository,
    private val recentVehicleRepository: RecentVehicleRepository,
) : ViewModel() {
    private val registration: String = checkNotNull(savedStateHandle[VehicleDetailsDestination.registrationArg])
    private val vehicle: Flow<Result<Vehicle?>> = vehicleRepository.getVehicle(registration).asResult()
    private val favouriteVehicle: Flow<Result<Vehicle?>> = favouriteVehicleRepository.getFavouriteVehicle(registration).asResult()

    val viewState: StateFlow<VehicleDetailsViewState> = combine(
        vehicle,
        favouriteVehicle
    ) { vehicle, favouriteVehicle ->
        Timber.d { "Combining $vehicle WITH $favouriteVehicle" }
        val favouriteViewState: VehicleDetailsFavouriteViewState = when (favouriteVehicle) {
            is Result.Success -> VehicleDetailsFavouriteViewState.Favourite(isFavourite = favouriteVehicle.data != null)
            is Result.Error -> VehicleDetailsFavouriteViewState.Favourite(false)
            is Result.Loading -> VehicleDetailsFavouriteViewState.Loading
        }

        val vehicleViewState: VehicleDetailsVehicleViewState = when (vehicle) {
            is Result.Error -> VehicleDetailsVehicleViewState.Error(registration)
            is Result.Loading -> VehicleDetailsVehicleViewState.Loading(registration)
            is Result.Success -> {
                val vehicleData = vehicle.data
                if (vehicleData != null) {
                    recentVehicleRepository.addRecentVehicle(vehicleData)
                    VehicleDetailsVehicleViewState.Success(registration, vehicleData)
                } else {
                    VehicleDetailsVehicleViewState.Error(registration)
                }
            }
        }
        
        VehicleDetailsViewState(
            vehicleState = vehicleViewState,
            favouriteState = favouriteViewState,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = VehicleDetailsViewState(
            vehicleState = VehicleDetailsVehicleViewState.Loading(registration),
            favouriteState = VehicleDetailsFavouriteViewState.Loading
        )
    )

    init {
        viewModelScope.launch {
            vehicleRepository.updateVehicle(registration)
        }
    }

    fun setFavouriteState(vehicle: Vehicle, isFavourite: Boolean) {
        viewModelScope.launch {
            if (isFavourite) {
                favouriteVehicleRepository.addFavouriteVehicle(vehicle)
            } else {
                favouriteVehicleRepository.removeFavouriteVehicle(vehicle)
            }
        }
    }
}
