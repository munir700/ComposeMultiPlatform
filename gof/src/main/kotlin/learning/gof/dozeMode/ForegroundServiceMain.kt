package learning.gof.dozeMode

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

/**
 * Example Activity demonstrating how to start a ForegroundService
 * ForegroundServices are used to perform long-running tasks that must keep running
 * even when the app is in the background or device enters Doze mode.
 */
class ForegroundServiceMainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ForegroundServiceHelper.startService(
            this@ForegroundServiceMainActivity,
            "Service started from Activity"
        )
    }

    override fun onStop() {
        super.onStop()
        ForegroundServiceHelper.stopService(this@ForegroundServiceMainActivity)
    }
}

/**
 * Utility object with helper functions for managing ForegroundService
 */
object ForegroundServiceHelper {

    /**
     * Start foreground service from any Android context
     */
    fun startService(context: android.content.Context, data: String = "") {
        val intent = Intent(context, ForegroundService::class.java).apply {
            putExtra("data", data)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context, intent)
        } else {
            context.startService(intent)
        }
    }

    /**
     * Stop the foreground service
     */
    fun stopService(context: android.content.Context) {
        val intent = Intent(context, ForegroundService::class.java)
        context.stopService(intent)
    }
}