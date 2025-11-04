package app.sunreef.yachts.mobile.presentation.viewmodels.electrical

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.usecases.electrical.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ViewModel for Electrical System
 */
class ElectricalViewModel(
    private val getElectricalDataUseCase: GetElectricalDataUseCase,
    private val streamElectricalDataUseCase: StreamElectricalDataUseCase
) : ViewModel() {

    private val _electricalState = MutableStateFlow<ElectricalData?>(null)
    val electricalState: StateFlow<ElectricalData?> = _electricalState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadElectricalData() {
        viewModelScope.launch {
            _isLoading.value = true
            getElectricalDataUseCase()
                .onSuccess { electrical ->
                    _electricalState.value = electrical
                }
            _isLoading.value = false
        }
    }

    fun streamElectricalData() {
        viewModelScope.launch {
            streamElectricalDataUseCase()
                .collect { electrical ->
                    _electricalState.value = electrical
                }
        }
    }
}

