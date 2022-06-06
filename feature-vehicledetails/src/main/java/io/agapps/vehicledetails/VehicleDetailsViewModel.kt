package io.agapps.vehicledetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.agapps.common.result.Result
import io.agapps.common.result.asResult
import io.agapps.core.data.repository.VehicleRepository
import io.agapps.feature.recentvehicles.data.RecentVehicleRepository
import io.agapps.vehicledetails.navigation.VehicleDetailsDestination
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class VehicleDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    vehicleRepository: VehicleRepository,
    private val recentVehicleRepository: RecentVehicleRepository,
) : ViewModel() {
    private val registration: String = checkNotNull(savedStateHandle[VehicleDetailsDestination.registrationArg])

    val viewState: StateFlow<VehicleDetailsViewState> = vehicleRepository.getVehicle(registration).asResult().map { vehicleResult ->
        when (vehicleResult) {
            is Result.Error -> VehicleDetailsViewState.VehicleDetailsError(registration, vehicleResult.exception.toString())
            is Result.Loading -> VehicleDetailsViewState.VehicleDetailsLoading(registration)
            is Result.Success -> {
                recentVehicleRepository.addRecentVehicle(vehicleResult.data)
                VehicleDetailsViewState.VehicleDetailsResult(
                    registration,
                    vehicleResult.data
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = VehicleDetailsViewState.VehicleDetailsLoading(registration)
    )

    init {
        viewModelScope.launch {
            vehicleRepository.updateVehicle(registration)
        }
    }
}
