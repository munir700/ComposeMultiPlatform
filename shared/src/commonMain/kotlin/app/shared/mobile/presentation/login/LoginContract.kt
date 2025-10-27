package app.shared.mobile.presentation.login

import kmp.core.mobile.base.ViewEvent
import kmp.core.mobile.base.ViewSideEffect
import kmp.core.mobile.base.ViewState

class LoginContract {
    data class State(
        val isInitialized: Boolean = false,
        val userName: String? = null,
        val password: String? = null,
        val userNameError: String? = null,
        val passwordError: String? = null
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data object BackClicked : Event()
        data class UserNameChanged(val userName: String) : Event()
        data class PasswordChanged(val password: String) : Event()
        data object Login : Event()
    }

    sealed class Effect : ViewSideEffect
}