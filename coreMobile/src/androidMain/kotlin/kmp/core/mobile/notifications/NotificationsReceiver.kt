package kmp.core.mobile.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kmp.core.mobile.utils.extensions.getParcelable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationsReceiver : BroadcastReceiver(), KoinComponent {
    private val notificationManager by inject<INotificationManager>()

    override fun onReceive(context: Context, intentData: Intent?) {
        intentData?.getParcelable<Notification>(KEY_NOTIFICATION)?.let {
            notificationManager.show(it)
        }
    }

    companion object {
        const val KEY_NOTIFICATION = "notification"
    }
}