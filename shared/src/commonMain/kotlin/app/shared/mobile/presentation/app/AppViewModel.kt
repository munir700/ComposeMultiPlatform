package app.shared.mobile.presentation.app

import app.shared.mobile.presentation.base.AppBaseViewModel
import kmp.core.mobile.AppLogger
import kmp.core.mobile.notifications.INotificationManager
import app.shared.mobile.presentation.app.AppContract.Event
import app.shared.mobile.presentation.app.AppContract.State
import app.shared.mobile.presentation.app.AppContract.Effect


val Class = AppViewModel::class

class AppViewModel(
    private val notificationManager: INotificationManager,
    private val appLogger: AppLogger
) : AppBaseViewModel<State, Event, Effect>() {

    init {
        observeRemoteNotifications()
    }

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        is Event.Init -> init()
    }

    private fun init() {
        if (currentState.isInitialized) return

        // Init

        // State initialized
        setState { copy(isInitialized = true) }
    }

    private fun observeRemoteNotifications() {
        notificationManager.onReceiveDataNotification { payload ->
            appLogger.log("Remote push data received: $payload")
        }
    }
}