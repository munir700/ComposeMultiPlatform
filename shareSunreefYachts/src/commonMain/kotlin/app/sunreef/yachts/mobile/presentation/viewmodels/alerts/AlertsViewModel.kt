package app.sunreef.yachts.mobile.presentation.viewmodels.alerts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.usecases.alert.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ViewModel for System Alerts
 */
class AlertsViewModel(
    private val getActiveAlertsUseCase: GetActiveAlertsUseCase,
    private val streamAlertsUseCase: StreamAlertsUseCase,
    private val acknowledgeAlertUseCase: AcknowledgeAlertUseCase
) : ViewModel() {

    private val _alertsState = MutableStateFlow<List<SystemAlert>>(emptyList())
    val alertsState: StateFlow<List<SystemAlert>> = _alertsState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadActiveAlerts() {
        viewModelScope.launch {
            _isLoading.value = true
            getActiveAlertsUseCase()
                .onSuccess { alerts ->
                    _alertsState.value = alerts
                }
            _isLoading.value = false
        }
    }

    fun streamAlerts() {
        viewModelScope.launch {
            streamAlertsUseCase()
                .catch { }
                .collect { alert ->
                    _alertsState.value = _alertsState.value + alert
                }
        }
    }

    fun acknowledgeAlert(alertId: String) {
        viewModelScope.launch {
            acknowledgeAlertUseCase(alertId)
                .onSuccess {
                    _alertsState.value = _alertsState.value.filter { it.id != alertId }
                }
        }
    }
}

