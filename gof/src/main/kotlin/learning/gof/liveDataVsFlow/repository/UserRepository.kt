package learning.gof.liveDataVsFlow.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.delay
import learning.gof.liveDataVsFlow.models.User
import java.util.concurrent.Executors

class UserRepository : BaseRepository(){
    private val executor = Executors.newSingleThreadExecutor()

    fun getUserWithExecutor(userId: String): LiveData<User> {
        val liveData = MutableLiveData<User>()

        executor.execute {
            // Simulate network call or database query
            Thread.sleep(2000) // Simulating delay
            val user = fetchUserFromApi(userId) // Long-running operation

            // Post result to main thread
            liveData.postValue(user)
        }

        return liveData
    }

    suspend fun fetchUserSuspend(userId: String): User {
        // Simulate network delay with coroutine
        delay(2000)
        return fetchUserFromApi(userId)
    }

    fun fetchUserFromApi(userId: String): User {
        // Simulate API call
        return User(userId, "John Doe", "john@example.com")
    }
}