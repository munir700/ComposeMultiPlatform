package learning.gof.androidDevelopmentSdk

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

class BestPracticeLifecycleAwarenessService : Service() {
    private val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main.immediate
    )

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val data = intent?.getStringExtra("data")

        scope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    performWork(data)
                }
                // Handle result
            } catch (e: CancellationException) {
                // Handle service cancellation
            } finally {
                stopSelf(startId)
            }
        }

        return START_STICKY // Restart if killed
    }

    private suspend fun performWork(data: String?) {
        // Long-running operation
        delay(5000L)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}