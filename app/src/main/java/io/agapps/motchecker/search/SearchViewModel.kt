package io.agapps.motchecker.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.Timber
import dagger.hilt.android.lifecycle.HiltViewModel
import io.agapps.domain.onFailure
import io.agapps.domain.onSuccess
import io.agapps.domain.vehicledetails.VehicleDetailsRepository
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
    private val repository: VehicleDetailsRepository
) : ViewModel() {
    private val debounceState = MutableStateFlow<String?>(null)
    private val _searchViewState: MutableStateFlow<SearchViewState> = MutableStateFlow(SearchViewState.SearchLoading)
    val searchViewState = _searchViewState.asStateFlow()

    init {
        viewModelScope.launch {
            debounceState
                .debounce(SearchDebounceMs)
                .collect { registrationNumber ->
                    if (registrationNumber != null) {
                        Timber.d { "Searching for $registrationNumber" }
                        repository.getVehicleDetails(registrationNumber)
                            .onSuccess {
                                _searchViewState.value = SearchViewState.SearchResult(it)
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
