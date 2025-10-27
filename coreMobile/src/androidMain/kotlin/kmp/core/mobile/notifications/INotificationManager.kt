package kmp.core.mobile.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import kotlinx.datetime.LocalDateTime

actual interface INotificationManager {

    actual fun preloadPushNotificationToken()

    fun show(notification: Notification)

    fun createNotificationsChannel(
        id: String,
        name: String,
        priority: Int = NotificationManager.IMPORTANCE_HIGH
    ): NotificationChannel?

    fun onCreateOrOnNewIntent(intent: Intent?)

    actual fun schedule(notification: Notification, dateTime: LocalDateTime)

    actual fun scheduleRepeating(notification: Notification, hourOfDay: Int, minute: Int)

    actual fun scheduleRepeating(notification: Notification, intervalMinutes: Int)

    actual fun cancelScheduled(id: Int)

    actual fun removeDelivered(id: Int)

    actual fun removeAllDelivered()

    actual fun clearBadgeCount()

    actual suspend fun getPushNotificationToken(): String?

    actual suspend fun deletePushNotificationToken()

    actual suspend fun subscribeToTopic(topicName: String)

    actual suspend fun unSubscribeFromTopic(topicName: String)

    actual fun onNewTokenListener(listener: (String) -> Unit)

    actual fun onReceiveMessageNotification(listener: (title: String?, body: String?) -> Unit)

    actual fun onReceiveDataNotification(listener: (payload: NotificationPayload) -> Unit)

    actual fun onNotificationClicked(listener: (payload: NotificationPayload) -> Unit)

    companion object {
        const val APP_CHANNEL_ID = "AppNotifications"
        const val APP_CHANNEL_NAME = "App Notifications"
    }
}