package io.agapps.feature.favouritevehicles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.agapps.feature.favouritevehicles.data.FavouriteVehicleRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavouriteVehicleViewModel @Inject constructor(favouriteVehicleRepository: FavouriteVehicleRepository) : ViewModel() {
    val viewState: StateFlow<FavouriteVehicleViewState> = favouriteVehicleRepository.getFavouriteVehicles()
        .map { favouriteVehicles -> FavouriteVehicleViewState.FavouriteVehicle(favouriteVehicles) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = FavouriteVehicleViewState.Empty
        )
}
