package learning.gof.androidDevelopmentSdk

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class  CoroutinesServices : Service() {
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate() {
        super.onCreate()
        // Called once when service is created
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val data = intent?.getStringExtra("data")
        
        // Launch coroutine for long-running task
        scope.launch(Dispatchers.IO) {
            performLongRunningTask(data)
            // Manually stop the service when done
            stopSelf(startId)
        }
        
        // Return flags to handle restarts
        return START_STICKY
    }

    private suspend fun performLongRunningTask(data: String?) {
        // Simulate long-running work
        delay(5000)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel() // Cancel all coroutines
    }

    override fun onBind(intent: Intent?): IBinder? = null
}