package learning.gof.liveDataVsFlow

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import learning.gof.R
import learning.gof.liveDataVsFlow.models.Result
import learning.gof.liveDataVsFlow.repository.UserRepository
import learning.gof.liveDataVsFlow.viewModels.GenericViewModelFactory
import learning.gof.liveDataVsFlow.viewModels.UserViewModel

/**
 * Example Activity demonstrating how to use UserRepository and UserViewModel
 */
class UserActivity : ComponentActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var userTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        // Initialize ViewModel using generic factory
        val repository = UserRepository()
        userViewModel = ViewModelProvider(
            this,
            GenericViewModelFactory(repository) { repo ->
                UserViewModel(repo)
            }
        )[UserViewModel::class.java]

        // Get reference to TextView
        userTextView = findViewById(R.id.userTextView)

        // Example 1: Using getUserWithExecutor()
        exampleGetUserWithExecutor()

        // Example 2: Using getUserWithCoroutines()
        exampleGetUserWithCoroutines()

        // Example 3: Using getUserWithLoadingState()
        exampleGetUserWithLoadingState()

        // Example 4: Using loadUserManually()
        exampleLoadUserManually()
    }

    /**
     * Example 1: How to use getUserWithExecutor()
     * This method loads user data using traditional Executor approach
     */
    private fun exampleGetUserWithExecutor() {
        val repository = UserRepository()

        // Call getUserWithExecutor with a user ID
        val userLiveData = repository.getUserWithExecutor("user123")

        // Observe the LiveData
        userLiveData.observe(this) { user ->
            // This callback is called when data is ready
            userTextView.text = "User: ${user.name}\nEmail: ${user.email}"
        }
    }

    /**
     * Example 2: How to use getUserWithCoroutines()
     * This method loads user data using coroutines with LiveData builder
     */
    private fun exampleGetUserWithCoroutines() {
        // Call getUserWithCoroutines with a user ID
        val userLiveData = userViewModel.getUserWithCoroutines("user456")

        // Observe the LiveData
        userLiveData.observe(this) { user ->
            userTextView.text = "User: ${user.name}\nEmail: ${user.email}"
        }
    }

    /**
     * Example 3: How to use getUserWithLoadingState()
     * This method loads user data with loading states (Loading, Success, Error)
     */
    private fun exampleGetUserWithLoadingState() {
        // Call getUserWithLoadingState with a user ID
        val resultLiveData = userViewModel.getUserWithLoadingState("user789")

        // Observe the LiveData with Result states
        resultLiveData.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    userTextView.text = "Loading..."
                }

                is Result.Success -> {
                    val user = result.data
                    userTextView.text = "User: ${user.name}\nEmail: ${user.email}"
                }

                is Result.Error -> {
                    userTextView.text = "Error: ${result.error}"
                }
            }
        }
    }

    /**
     * Example 4: How to use loadUserManually()
     * This method loads user data manually with MutableLiveData
     */
    private fun exampleLoadUserManually() {
        // Call loadUserManually with a user ID
        userViewModel.loadUserManually("user999")

        // Observe the LiveData
        userViewModel.user.observe(this) { user ->
            userTextView.text = "User: ${user.name}\nEmail: ${user.email}"
        }
    }
}


