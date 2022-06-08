package io.agapps.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.Timber
import dagger.hilt.android.lifecycle.HiltViewModel
import io.agapps.common.result.Result
import io.agapps.common.result.asResult
import io.agapps.core.data.repository.VehicleRepository
import io.agapps.core.model.Vehicle
import io.agapps.feature.recentvehicles.data.RecentVehicleRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SearchDebounceMs = 1000L

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository,
    recentVehicleRepository: RecentVehicleRepository,
) : ViewModel() {
    private val registrationSearchState = MutableStateFlow("")
    private val registrationNumberDebouncedState = MutableStateFlow<String?>(null)

    private val searchedVehicleResult: Flow<Result<Vehicle?>> = registrationSearchState.flatMapMerge { searchedRegistration ->
        if (searchedRegistration.isBlank()) {
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
        val vehicle: SearchVehicleViewState = if (searchedRegistration.isBlank()) SearchVehicleViewState.Empty else when (searchedVehicle) {
            is Result.Loading -> SearchVehicleViewState.Loading
            is Result.Error -> SearchVehicleViewState.Error
            is Result.Success -> {
                val searchedVehicleData = searchedVehicle.data
                if (searchedVehicleData != null) SearchVehicleViewState.Success(searchedVehicleData) else SearchVehicleViewState.Error
            }
        }

        val recents: SearchRecentsViewState = when (recentVehicles) {
            is Result.Loading -> SearchRecentsViewState.Loading
            is Result.Error -> SearchRecentsViewState.Error
            is Result.Success -> SearchRecentsViewState.Success(recentVehicles.data)
        }

        SearchViewState(vehicle, recents)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SearchViewState(SearchVehicleViewState.Loading, SearchRecentsViewState.Loading)
    )

    init {
        viewModelScope.launch {
            registrationNumberDebouncedState
                .debounce(SearchDebounceMs)
                .filterNotNull()
                .collect { registrationNumber ->
                    registrationSearchState.value = registrationNumber
                }
        }
    }

    fun onRegistrationNumberEntered(registrationNumber: String) {
        registrationNumberDebouncedState.value = registrationNumber
    }
}
