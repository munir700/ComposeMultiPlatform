package app.sunreef.yachts.mobile.presentation.viewmodels.security

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.usecases.security.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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

