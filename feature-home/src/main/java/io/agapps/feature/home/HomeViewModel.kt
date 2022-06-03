package io.agapps.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.agapps.feature.recentvehicles.data.RecentVehicleRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

private const val RECENT_VEHICLE_LIMIT = 3

@HiltViewModel
class HomeViewModel @Inject constructor(
    recentVehicleRepository: RecentVehicleRepository
) : ViewModel() {
    val viewState: StateFlow<HomeViewState> = recentVehicleRepository.getRecentVehicles(RECENT_VEHICLE_LIMIT)
        .map { HomeViewState.Home(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = HomeViewState.Home(emptyList())
        )
}
