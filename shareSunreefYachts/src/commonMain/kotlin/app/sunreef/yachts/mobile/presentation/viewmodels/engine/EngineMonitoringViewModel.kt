package app.sunreef.yachts.mobile.presentation.viewmodels.engine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.usecases.engine.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ViewModel for Engine Monitoring
 */
class EngineMonitoringViewModel(
    private val streamEngineDataUseCase: StreamEngineDataUseCase
) : ViewModel() {

    private val _engineState = MutableStateFlow<EngineData?>(null)
    val engineState: StateFlow<EngineData?> = _engineState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun streamEngineData(engineId: String) {
        viewModelScope.launch {
            streamEngineDataUseCase(engineId)
                .catch { exception ->
                    _error.value = exception.message
                }
                .collect { engine ->
                    _engineState.value = engine
                }
        }
    }
}

