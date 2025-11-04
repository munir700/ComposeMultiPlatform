package app.sunreef.yachts.mobile.presentation.viewmodels.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.usecases.navigation.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ViewModel for Navigation
 */
class NavigationViewModel(
    private val getCurrentPositionUseCase: GetCurrentPositionUseCase,
    private val streamNavigationDataUseCase: StreamNavigationDataUseCase,
    private val setDestinationUseCase: SetDestinationUseCase
) : ViewModel() {

    private val _navigationState = MutableStateFlow<NavigationData?>(null)
    val navigationState: StateFlow<NavigationData?> = _navigationState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadPosition() {
        viewModelScope.launch {
            _isLoading.value = true
            getCurrentPositionUseCase()
                .onSuccess { nav ->
                    _navigationState.value = nav
                    _error.value = null
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }
            _isLoading.value = false
        }
    }

    fun streamNavigation() {
        viewModelScope.launch {
            streamNavigationDataUseCase()
                .catch { exception ->
                    _error.value = exception.message
                }
                .collect { nav ->
                    _navigationState.value = nav
                }
        }
    }

    fun setDestination(waypoint: Waypoint) {
        viewModelScope.launch {
            setDestinationUseCase(waypoint)
                .onSuccess { _error.value = null }
                .onFailure { exception ->
                    _error.value = exception.message
                }
        }
    }
}

