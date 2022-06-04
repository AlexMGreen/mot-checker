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

private const val RecentVehicleLimit = 3

@HiltViewModel
class HomeViewModel @Inject constructor(
    recentVehicleRepository: RecentVehicleRepository
) : ViewModel() {
    val viewState: StateFlow<HomeViewState> = recentVehicleRepository.getRecentVehicles(RecentVehicleLimit)
        .map { HomeViewState.Home(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = HomeViewState.Home(emptyList())
        )
}
