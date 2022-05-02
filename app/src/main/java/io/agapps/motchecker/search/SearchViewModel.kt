package io.agapps.motchecker.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.Timber
import dagger.hilt.android.lifecycle.HiltViewModel
import io.agapps.data.vehicledetails.VehicleDetailsRepositoryImpl
import io.agapps.domain.onFailure
import io.agapps.domain.onSuccess
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SearchDebounceMs = 1000L

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: VehicleDetailsRepositoryImpl
) : ViewModel() {
    private val debounceState = MutableStateFlow<String?>(null)

    init {
        viewModelScope.launch {
            debounceState
                .debounce(SearchDebounceMs)
                .collect { registrationNumber ->
                    if (registrationNumber != null) {
                        Timber.d { "Searching for $registrationNumber" }
                        repository.getVehicleDetails(registrationNumber)
                            .onSuccess {
                                Timber.d { "Success: $it" }
                            }
                            .onFailure {
                                Timber.e(it)
                            }
                    }
                }
        }
    }

    fun onRegistrationNumberEntered(registrationNumber: String) {
        debounceState.value = registrationNumber
    }
}
