package kmp.core.mobile.eventBroadcaster

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

actual object EventBroadcaster {
    private val _events = MutableSharedFlow<Any>()
    private val events = _events.asSharedFlow()

    @OptIn(DelicateCoroutinesApi::class)
    actual fun <T : Any> notify(event: T) {
        GlobalScope.launch {
            _events.emit(event)
        }
    }

    @Suppress("UNCHECKED_CAST")
    actual suspend fun <T : Any> subscribeToEvent(
        eventName: String,
        onReceived: (T) -> Unit
    ) {
        events
            .filter { appEvent -> appEvent::class.simpleName == eventName }
            .collectLatest { onReceived.invoke(it as T) }
    }
}