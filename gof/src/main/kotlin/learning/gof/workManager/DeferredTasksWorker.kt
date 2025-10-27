package learning.gof.workManager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class DeferredTasksWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            val data = inputData.getString("data") ?: "default"
            val result = performLongRunningTask(data)
            
            Result.success(
                workDataOf("result" to result)
            )
        } catch (e: Exception) {
            Log.e("MyBackgroundTask", "Task failed", e)
            Result.retry() // Retry on failure
        }
    }

    private suspend fun performLongRunningTask(data: String): String {
        delay(5000) // Simulate work
        return "Completed: $data"
    }
}