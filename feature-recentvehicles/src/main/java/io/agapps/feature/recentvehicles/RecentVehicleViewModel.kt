package io.agapps.feature.recentvehicles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.agapps.feature.recentvehicles.data.RecentVehicleRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RecentVehicleViewModel @Inject constructor(
    recentVehicleRepository: RecentVehicleRepository,
) : ViewModel() {
    val viewState: StateFlow<RecentVehicleViewState> = recentVehicleRepository.getRecentVehicles()
        .map { recentVehicles -> RecentVehicleViewState.RecentVehicle(recentVehicles) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = RecentVehicleViewState.RecentVehicle(emptyList())
        )
}
