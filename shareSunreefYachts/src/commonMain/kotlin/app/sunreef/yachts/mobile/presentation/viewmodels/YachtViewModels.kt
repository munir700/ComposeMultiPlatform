package app.sunreef.yachts.mobile.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.usecases.*
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
    private val getEngineDataUseCase: GetEngineDataUseCase,
    private val streamEngineDataUseCase: StreamEngineDataUseCase
) : ViewModel() {

    private val _engineState = MutableStateFlow<EngineData?>(null)
    val engineState: StateFlow<EngineData?> = _engineState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadEngineData(engineId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            getEngineDataUseCase(engineId)
                .onSuccess { engine ->
                    _engineState.value = engine
                    _error.value = null
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }
            _isLoading.value = false
        }
    }

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

/**
 * ViewModel for Security
 */
class SecurityViewModel(
    private val getSecurityDataUseCase: GetSecurityDataUseCase,
    private val armAlarmUseCase: ArmAlarmUseCase,
    private val lockDoorsUseCase: LockDoorsUseCase,
    private val unlockDoorsUseCase: UnlockDoorsUseCase
) : ViewModel() {

    private val _securityState = MutableStateFlow<SecurityData?>(null)
    val securityState: StateFlow<SecurityData?> = _securityState.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadSecurityData() {
        viewModelScope.launch {
            _isProcessing.value = true
            getSecurityDataUseCase()
                .onSuccess { security ->
                    _securityState.value = security
                    _error.value = null
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }
            _isProcessing.value = false
        }
    }

    fun armAlarm(mode: String) {
        viewModelScope.launch {
            _isProcessing.value = true
            armAlarmUseCase(mode)
                .onSuccess { _error.value = null }
                .onFailure { exception ->
                    _error.value = exception.message
                }
            _isProcessing.value = false
        }
    }

    fun lockDoors() {
        viewModelScope.launch {
            _isProcessing.value = true
            lockDoorsUseCase()
                .onSuccess { _error.value = null }
                .onFailure { exception ->
                    _error.value = exception.message
                }
            _isProcessing.value = false
        }
    }

    fun unlockDoors() {
        viewModelScope.launch {
            _isProcessing.value = true
            unlockDoorsUseCase()
                .onSuccess { _error.value = null }
                .onFailure { exception ->
                    _error.value = exception.message
                }
            _isProcessing.value = false
        }
    }
}

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

/**
 * ViewModel for Remote Control
 */
class RemoteControlViewModel(
    private val sendRemoteCommandUseCase: SendRemoteCommandUseCase,
    private val getCommandStatusUseCase: GetCommandStatusUseCase,
    private val getCommandHistoryUseCase: GetCommandHistoryUseCase
) : ViewModel() {

    private val _commandHistory = MutableStateFlow<List<RemoteControlCommand>>(emptyList())
    val commandHistory: StateFlow<List<RemoteControlCommand>> = _commandHistory.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun sendCommand(command: RemoteControlCommand) {
        viewModelScope.launch {
            _isProcessing.value = true
            sendRemoteCommandUseCase(command)
                .onSuccess { _error.value = null }
                .onFailure { exception ->
                    _error.value = exception.message
                }
            _isProcessing.value = false
        }
    }

    fun loadCommandHistory() {
        viewModelScope.launch {
            _isProcessing.value = true
            getCommandHistoryUseCase()
                .onSuccess { history ->
                    _commandHistory.value = history
                    _error.value = null
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }
            _isProcessing.value = false
        }
    }

    fun checkCommandStatus(commandId: String) {
        viewModelScope.launch {
            getCommandStatusUseCase(commandId)
                .onSuccess { status ->
                    _error.value = null
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }
        }
    }
}

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

