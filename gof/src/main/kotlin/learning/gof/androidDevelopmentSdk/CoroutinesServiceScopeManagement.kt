package learning.gof.androidDevelopmentSdk

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutinesServiceScopeManagement : Service() {
    private val serviceJob = Job()
    private val scope = CoroutineScope(Dispatchers.Main + serviceJob)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch(Dispatchers.IO) {
            try {
                performWork()
            } finally {
                stopSelf(startId)
            }
        }
        return START_STICKY
    }

    private suspend fun performWork() {
        // Long-running operation
        delay(5000L)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel() // Cancels all child coroutines
    }

    override fun onBind(intent: Intent?): IBinder? = null
}