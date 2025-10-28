package app.shared.mobile.presentation.polling

import app.shared.mobile.presentation.base.AppBaseViewModel
import app.shared.mobile.presentation.polling.PollingContract.Effect
import app.shared.mobile.presentation.polling.PollingContract.Event
import app.shared.mobile.presentation.polling.PollingContract.State
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PollingViewModel() : AppBaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        Event.BackClick -> navManager.goBack()
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        println("PollingViewModel initialized")

        // Collect the StateFlow and update state with each emission
        val job: Job = viewModelScope.launch {
            updatesFlow().collect { value ->
                println("PollingViewModel counter $value")
                setState { copy(items = (items + value)) }
            }
        }

        setState { copy(job = job) }
        setState { copy(isInitialized = true) }
    }

    /**
     * Called automatically by the navigator when this screen is popped.
     * Cleans up resources like cancelling polling jobs.
     */
    override fun onScreenDestroyed() {
        super.onScreenDestroyed()
        currentState.job?.cancel()
    }

    private fun updatesFlow() = flow {
        var counter = 0
        while (true) {
            delay(1000)
            emit(listOf(counter++))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = currentState.items
    )
}
