package io.agapps.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.agapps.feature.favouritevehicles.data.FavouriteVehicleRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

private const val FavouriteVehicleLimit = 5

@HiltViewModel
class HomeViewModel @Inject constructor(
    favouriteVehicleRepository: FavouriteVehicleRepository,
) : ViewModel() {
    val viewState: StateFlow<HomeViewState> = favouriteVehicleRepository.getFavouriteVehicles(FavouriteVehicleLimit)
        .map { HomeViewState.Home(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = HomeViewState.Home(emptyList())
        )
}
