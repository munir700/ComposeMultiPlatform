package app.sunreef.yachts.mobile.presentation.viewmodels.remotecontrol

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.usecases.remotecontrol.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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

