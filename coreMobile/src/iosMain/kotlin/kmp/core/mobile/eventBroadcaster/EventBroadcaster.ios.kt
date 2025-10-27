package kmp.core.mobile.eventBroadcaster

actual object EventBroadcaster {
    actual fun <T : Any> notify(event: T) {
    }

    actual suspend fun <T : Any> subscribeToEvent(
        eventName: String,
        onReceived: (T) -> Unit
    ) {
    }
}