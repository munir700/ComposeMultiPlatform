package kmp.core.mobile.notifications

import kotlinx.datetime.LocalDateTime

val Class = INotificationManager::class

actual interface INotificationManager {
    actual fun preloadPushNotificationToken()

    fun onApplicationDidReceiveRemoteNotification(userInfo: Map<Any?, *>)
    actual fun schedule(
        notification: Notification,
        dateTime: LocalDateTime
    )

    actual fun scheduleRepeating(
        notification: Notification,
        hourOfDay: Int,
        minute: Int
    )

    actual fun scheduleRepeating(
        notification: Notification,
        intervalMinutes: Int
    )

    actual fun cancelScheduled(id: Int)
    actual fun removeDelivered(id: Int)
    actual fun removeAllDelivered()
    actual fun clearBadgeCount()
    actual suspend fun getPushNotificationToken(): String?
    actual suspend fun deletePushNotificationToken()
    actual suspend fun subscribeToTopic(topicName: String)
    actual suspend fun unSubscribeFromTopic(topicName: String)
    actual fun onNewTokenListener(listener: (String) -> Unit)
    actual fun onReceiveMessageNotification(listener: (String?, String?) -> Unit)
    actual fun onReceiveDataNotification(listener: (NotificationPayload) -> Unit)
    actual fun onNotificationClicked(listener: (NotificationPayload) -> Unit)
}