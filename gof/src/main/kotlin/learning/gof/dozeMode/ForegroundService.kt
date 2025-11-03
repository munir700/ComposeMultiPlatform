package learning.gof.dozeMode

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForegroundService : Service() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val NOTIFICATION_ID = 1
    private val CHANNEL_ID = "my_service_channel"
    private val ACTION_STOP = "STOP_SERVICE"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Check if this is a stop action from notification
        if (intent?.action == ACTION_STOP) {
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
            return START_NOT_STICKY
        }

        // Start as foreground service
        startForeground(NOTIFICATION_ID, createNotification())

        val data = intent?.getStringExtra("data")

        scope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    performLongRunningTask(data)
                }
                // Update notification with completion status - keeps it visible
                updateNotification("✓ Task completed: $result")
            } catch (e: CancellationException) {
                Log.e("MyService", "Task cancelled", e)
                updateNotification("✗ Task cancelled")
            } catch (e: Exception) {
                Log.e("MyService", "Task failed", e)
                updateNotification("✗ Task failed: ${e.message}")
            }
        }

        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Background Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Handles background tasks"
                enableVibration(false)  // No vibration
                enableLights(false)     // No lights
                setSound(null, null)    // No sound
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    private fun createNotification() = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("Background Task Running")
        .setContentText("Processing...")
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setOngoing(true)
        .setOnlyAlertOnce(true)  // Only alert once, not repeatedly
        .addAction(createStopAction())  // Add stop button
        .build()

    private fun updateNotification(message: String) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Task Status")
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setOngoing(true)  // Keep it persistent
            .setOnlyAlertOnce(true)  // Don't re-alert
            .addAction(createStopAction())  // Add stop button
            .build()

        val manager = getSystemService(NotificationManager::class.java)
        manager?.notify(NOTIFICATION_ID, notification)
    }

    private fun createStopAction(): NotificationCompat.Action {
        val stopIntent = Intent(this, ForegroundService::class.java).apply {
            action = ACTION_STOP
        }
        val stopPendingIntent = PendingIntent.getService(
            this,
            0,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Action(
            android.R.drawable.ic_menu_close_clear_cancel,
            "Stop",
            stopPendingIntent
        )
    }

    private suspend fun performLongRunningTask(data: String?): String {
        delay(5000) // Simulate work
        return "Completed: $data"
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    override fun onBind(intent: Intent?) = null
}