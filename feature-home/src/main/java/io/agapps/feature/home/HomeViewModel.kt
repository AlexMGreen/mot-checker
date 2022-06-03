package io.agapps.feature.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeViewState> = MutableStateFlow(HomeViewState.Home(emptyList()))
    val uiState = _uiState.asStateFlow()
}
