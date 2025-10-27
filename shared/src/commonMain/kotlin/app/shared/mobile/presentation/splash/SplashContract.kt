package app.shared.mobile.presentation.splash

import kmp.core.mobile.base.ViewEvent
import kmp.core.mobile.base.ViewSideEffect
import kmp.core.mobile.base.ViewState

class SplashContract {
    data class State(
        val isInitialized: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
    }

    sealed class Effect : ViewSideEffect
}