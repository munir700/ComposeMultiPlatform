package learning.gof.liveDataVsFlow.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import learning.gof.liveDataVsFlow.repository.UserRepository
import learning.gof.liveDataVsFlow.models.Result
import learning.gof.liveDataVsFlow.models.User

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    // Using liveData builder (recommended)
    fun getUserWithCoroutines(userId: String): LiveData<User> = liveData {
        // This runs in a coroutine
        val user = repository.fetchUserSuspend(userId)
        emit(user) // Emit value to LiveData
    }

    // Using liveData builder with loading states
    fun getUserWithLoadingState(userId: String): LiveData<Result<User>> = liveData {
        emit(Result.Loading<User>())
        try {
            val user = repository.fetchUserSuspend(userId)
            emit(Result.Success<User>(user))
        } catch (e: Exception) {
            emit(Result.Error<User>(e.message ?: "Unknown error"))
        }
    }

    // Manual approach with MutableLiveData
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun loadUserManually(userId: String) {
        viewModelScope.launch {
            val user = repository.fetchUserSuspend(userId)
            _user.value = user // or _user.postValue(user) from background
        }
    }
}