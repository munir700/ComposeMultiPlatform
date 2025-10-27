package learning.gof.androidDevelopmentSdk

import android.app.IntentService
import android.content.Intent

class MyIntentService : IntentService("MyIntentService") {
    override fun onHandleIntent(intent: Intent?) {
        // Long-running operation (runs on background thread automatically)
        val data = intent?.getStringExtra("data")
        // TODO performLongRunningTask(data)
        // Service automatically stops after this completes
    }
}

