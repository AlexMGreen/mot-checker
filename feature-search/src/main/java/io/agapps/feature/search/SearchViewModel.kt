package io.agapps.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.Timber
import dagger.hilt.android.lifecycle.HiltViewModel
import io.agapps.common.result.onFailure
import io.agapps.common.result.onSuccess
import io.agapps.core.data.repository.VehicleRepository
import io.agapps.feature.recentvehicles.data.RecentVehicleRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SearchDebounceMs = 1000L

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository,
    private val recentVehicleRepository: RecentVehicleRepository,
) : ViewModel() {
    private val debounceState = MutableStateFlow<String?>(null)
    private val _searchViewState: MutableStateFlow<SearchViewState> = MutableStateFlow(SearchViewState.SearchLoading)
    val searchViewState = _searchViewState.asStateFlow()

    init {
        viewModelScope.launch {
            debounceState
                .debounce(SearchDebounceMs)
                .collect { registrationNumber ->
                    if (!registrationNumber.isNullOrBlank()) {
                        Timber.d { "Searching for $registrationNumber" }
                        _searchViewState.value = SearchViewState.SearchLoading
                        vehicleRepository.getVehicle(registrationNumber)
                            .onSuccess { vehicle ->
                                recentVehicleRepository.addRecentVehicle(vehicle)
                                _searchViewState.value = SearchViewState.SearchResult(vehicle)
                            }
                            .onFailure {
                                _searchViewState.value = SearchViewState.SearchError(it.toString())
                            }
                    }
                }
        }
    }

    fun onRegistrationNumberEntered(registrationNumber: String) {
        debounceState.value = registrationNumber
    }
}
