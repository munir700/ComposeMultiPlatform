package app.shared.mobile.presentation.polling

import kmp.core.mobile.base.ViewEvent
import kmp.core.mobile.base.ViewSideEffect
import kmp.core.mobile.base.ViewState
import kotlinx.coroutines.Job

class PollingContract {
    data class State(
        val isInitialized: Boolean = false,
        val job: Job? = null,
        val items: List<Int> = emptyList()
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data object BackClick : Event()
    }

    sealed class Effect : ViewSideEffect
}