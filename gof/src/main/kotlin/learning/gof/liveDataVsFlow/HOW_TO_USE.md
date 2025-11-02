# How to Use UserRepository Methods

## Overview
The `UserRepository` provides three different approaches to load user data:
1. **getUserWithExecutor()** - Traditional Executor-based approach
2. **fetchUserSuspend()** - Suspend function for coroutines
3. Manual approach with MutableLiveData

---

## Method 1: getUserWithExecutor() - Traditional Executor Approach

### What it does:
- Runs a long-running operation on a background thread using `Executor`
- Returns a `LiveData<User>` that can be observed
- Automatically handles threading and posting results to main thread

### How to use:

```kotlin
// In your Activity or Fragment
val repository = UserRepository()

// Call the function with a user ID
val userLiveData = repository.getUserWithExecutor("user123")

// Observe the LiveData
userLiveData.observe(this) { user ->
    // This block executes when data is ready
    textView.text = "User: ${user.name}\nEmail: ${user.email}"
}
```

### Complete Example in Activity:

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val userTextView = findViewById<TextView>(R.id.userTextView)
        val repository = UserRepository()
        
        // Load user with executor
        val userLiveData = repository.getUserWithExecutor("user123")
        
        // Observe changes
        userLiveData.observe(this) { user ->
            userTextView.text = "Name: ${user.name}\nEmail: ${user.email}"
        }
    }
}
```

### Advantages:
✅ Simple and straightforward  
✅ No coroutine dependency  
✅ Automatically posts to main thread  
✅ Good for simple background tasks  

### Disadvantages:
❌ Manual thread management  
❌ No built-in cancellation support  
❌ Creates new thread each time  

---

## Method 2: getUserWithCoroutines() - Coroutine Approach (Recommended)

### What it does:
- Uses Kotlin coroutines with LiveData builder
- More efficient than Executor
- Cleaner syntax

### How to use:

```kotlin
// In your Activity or Fragment
val repository = UserRepository()
val viewModel = UserViewModel(repository)

// Call the function
val userLiveData = viewModel.getUserWithCoroutines("user456")

// Observe the LiveData
userLiveData.observe(this) { user ->
    textView.text = "User: ${user.name}\nEmail: ${user.email}"
}
```

---

## Method 3: getUserWithLoadingState() - With Loading States

### What it does:
- Provides three states: Loading, Success, Error
- Better user experience with loading indicators
- Proper error handling

### How to use:

```kotlin
val repository = UserRepository()
val viewModel = UserViewModel(repository)

// Call the function
val resultLiveData = viewModel.getUserWithLoadingState("user789")

// Observe with state handling
resultLiveData.observe(this) { result ->
    when (result) {
        is Result.Loading -> {
            progressBar.visibility = View.VISIBLE
            textView.text = "Loading..."
        }
        is Result.Success -> {
            progressBar.visibility = View.GONE
            val user = result.data
            textView.text = "User: ${user.name}\nEmail: ${user.email}"
        }
        is Result.Error -> {
            progressBar.visibility = View.GONE
            textView.text = "Error: ${result.error}"
        }
    }
}
```

---

## Method 4: loadUserManually() - Manual ViewModel Approach

### What it does:
- Manual ViewModel management
- Uses viewModelScope for lifecycle awareness

### How to use:

```kotlin
val repository = UserRepository()
val viewModel = UserViewModel(repository)

// Call the function
viewModel.loadUserManually("user999")

// Observe the exposed LiveData
viewModel.user.observe(this) { user ->
    textView.text = "User: ${user.name}\nEmail: ${user.email}"
}
```

---

## Comparison Table

| Method | Threading | Error Handling | Loading State | Coroutines |
|--------|-----------|----------------|---------------|-----------|
| getUserWithExecutor() | Executor | No | No | No |
| getUserWithCoroutines() | Coroutines | Manual | No | Yes |
| getUserWithLoadingState() | Coroutines | Yes | Yes | Yes |
| loadUserManually() | Coroutines | No | No | Yes |

---

## Which One to Use?

1. **Use `getUserWithExecutor()`** if:
   - You want simple background execution
   - No coroutine dependency needed
   - Learning traditional Android approaches

2. **Use `getUserWithCoroutines()`** if:
   - You want cleaner code
   - You're comfortable with coroutines
   - Simple data loading without loading states

3. **Use `getUserWithLoadingState()`** if:
   - You want professional UI with loading indicators
   - You need error handling
   - You want to show progress to users (Recommended)

4. **Use `loadUserManually()`** if:
   - You need fine-grained control
   - You're managing multiple data sources
   - You need complex state management

---

## Key Points

- ✅ Always observe LiveData in UI components (Activity/Fragment)
- ✅ Use ViewModelProvider for proper ViewModel creation
- ✅ LiveData automatically handles lifecycle awareness
- ✅ Observers are only active when lifecycle is STARTED
- ✅ Use loading states for better UX
- ✅ Always handle errors gracefully

---

## Example with Fragment

```kotlin
class UserFragment : Fragment() {
    private lateinit var viewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val repository = UserRepository()
        viewModel = ViewModelProvider(
            this,
            UserViewModelFactory(repository)
        ).get(UserViewModel::class.java)

        val userTextView = view.findViewById<TextView>(R.id.userTextView)
        val userId = arguments?.getString("userId") ?: "user123"

        // Observe with loading state
        viewModel.getUserWithLoadingState(userId).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> userTextView.text = "Loading..."
                is Result.Success -> userTextView.text = result.data.name
                is Result.Error -> userTextView.text = "Error: ${result.error}"
            }
        }
    }
}
```

