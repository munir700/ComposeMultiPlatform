package app.sunreef.yachts.mobile.presentation.viewmodels.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.usecases.yacht.*
import app.sunreef.yachts.mobile.domain.usecases.telemetry.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for Dashboard Overview
 */
class DashboardViewModel(
    private val getYachtSystemsUseCase: GetYachtSystemsUseCase,
    private val getPerformanceMetricsUseCase: GetPerformanceMetricsUseCase
) : ViewModel() {

    private val _systems = MutableStateFlow<List<YachtSystem>>(emptyList())
    val systems: StateFlow<List<YachtSystem>> = _systems.asStateFlow()

    private val _metrics = MutableStateFlow<PerformanceMetrics?>(null)
    val metrics: StateFlow<PerformanceMetrics?> = _metrics.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadDashboard() {
        viewModelScope.launch {
            _isLoading.value = true
            getYachtSystemsUseCase()
                .onSuccess { systems ->
                    _systems.value = systems
                }
            getPerformanceMetricsUseCase()
                .onSuccess { metrics ->
                    _metrics.value = metrics
                }
            _isLoading.value = false
        }
    }
}

