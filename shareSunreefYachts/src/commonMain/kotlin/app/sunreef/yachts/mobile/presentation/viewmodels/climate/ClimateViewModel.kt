package app.sunreef.yachts.mobile.presentation.viewmodels.climate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.usecases.climate.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for Climate Control
 */
class ClimateViewModel(
    private val getClimateDataUseCase: GetClimateDataUseCase,
    private val setTemperatureUseCase: SetTemperatureUseCase
) : ViewModel() {

    private val _climateState = MutableStateFlow<ClimateData?>(null)
    val climateState: StateFlow<ClimateData?> = _climateState.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadClimateData() {
        viewModelScope.launch {
            _isProcessing.value = true
            getClimateDataUseCase()
                .onSuccess { climate ->
                    _climateState.value = climate
                    _error.value = null
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }
            _isProcessing.value = false
        }
    }

    fun setTemperature(temperature: Float) {
        viewModelScope.launch {
            _isProcessing.value = true
            setTemperatureUseCase(temperature)
                .onSuccess { _error.value = null }
                .onFailure { exception ->
                    _error.value = exception.message
                }
            _isProcessing.value = false
        }
    }
}

