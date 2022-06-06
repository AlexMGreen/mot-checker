package io.agapps.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.Timber
import dagger.hilt.android.lifecycle.HiltViewModel
import io.agapps.common.result.Result
import io.agapps.common.result.asResult
import io.agapps.core.data.repository.VehicleRepository
import io.agapps.core.model.Vehicle
import io.agapps.feature.recentvehicles.data.RecentVehicleRepository
import io.agapps.feature.search.navigation.SearchDestination
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SearchDebounceMs = 1000L

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val vehicleRepository: VehicleRepository,
    recentVehicleRepository: RecentVehicleRepository,
) : ViewModel() {
    private val initialRegistration: String? = savedStateHandle[SearchDestination.initialRegistrationArg]
    private val registrationSearchState = MutableStateFlow(initialRegistration)
    private val registrationNumberDebouncedState = MutableStateFlow<String?>(null)

    private val searchedVehicleResult: Flow<Result<Vehicle>> = registrationSearchState.flatMapMerge { searchedRegistration ->
        if (searchedRegistration.isNullOrBlank()) {
            flowOf(Result.Error())
        } else {
            vehicleRepository.updateVehicle(searchedRegistration)
            vehicleRepository.getVehicle(searchedRegistration).asResult()
        }
    }

    private val recentVehiclesResult: Flow<Result<List<Vehicle>>> = recentVehicleRepository.getRecentVehicles(5).asResult()

    val viewState: StateFlow<SearchViewState> = combine(
        registrationSearchState,
        searchedVehicleResult,
        recentVehiclesResult,
    ) { searchedRegistration, searchedVehicle, recentVehicles ->
        Timber.d { "Combining $searchedRegistration with $searchedVehicle with $recentVehicles" }
        val vehicle: SearchVehicleViewState = if (searchedRegistration.isNullOrBlank()) SearchVehicleViewState.Empty else when (searchedVehicle) {
            is Result.Loading -> SearchVehicleViewState.Loading
            is Result.Error -> SearchVehicleViewState.Error
            is Result.Success -> SearchVehicleViewState.Success(searchedVehicle.data)
        }
        val recents: SearchRecentsViewState = when (recentVehicles) {
            is Result.Loading -> SearchRecentsViewState.Loading
            is Result.Error -> SearchRecentsViewState.Error
            is Result.Success -> SearchRecentsViewState.Success(recentVehicles.data)
        }
        SearchViewState(vehicle, recents)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SearchViewState(SearchVehicleViewState.Loading, SearchRecentsViewState.Loading)
    )

    init {
        viewModelScope.launch {
            registrationNumberDebouncedState
                .debounce(SearchDebounceMs)
                .collect { registrationNumber ->
                    registrationSearchState.value = registrationNumber
                }
        }
    }

    fun onRegistrationNumberEntered(registrationNumber: String) {
        registrationNumberDebouncedState.value = registrationNumber
    }
}
