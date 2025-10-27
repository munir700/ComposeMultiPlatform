package learning.gof.androidDevelopmentSdk

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService

class MyJobIntentService : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        // Long-running operation (runs on background thread)
        val data = intent.getStringExtra("data")
        // TODO performLongRunningTask(data)
        // Service automatically stops after this completes
    }

    companion object {
        private const val JOB_ID = 1000

        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, MyJobIntentService::class.java, JOB_ID, work)
        }
    }
}