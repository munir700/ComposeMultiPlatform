package learning.gof.workManager

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit

private const val MIN_BACKOFF_MILLIS: Long = 1000L

class SchedulingTask {
    fun schedulingTask(context: Context) {
        // One-time task
        val backupRequest = OneTimeWorkRequestBuilder<DeferredTasksWorker>()
            .setInputData(
                workDataOf("data" to "sync_data")
            )
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "my_task",
            ExistingWorkPolicy.KEEP,
            backupRequest
        )

        // Periodic task
        val periodicRequest = PeriodicWorkRequestBuilder<DeferredTasksWorker>(
            15, TimeUnit.MINUTES
        )
            .setInputData(workDataOf("data" to "periodic_sync"))
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "periodic_task",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRequest
        )
    }
}