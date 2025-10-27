Explain the relationship between Activities, Fragments, and Services.
Answer:

Activity: Is the entry point for user interaction. Manages the app's UI and user interactions.
Fragment: Lives inside an Activity. Represents a reusable piece of UI that can be combined with other fragments in a single Activity or reused across multiple Activities.
Service: Runs independently of any UI. Performs background tasks. Not associated with the user interface directly.

Relationship Flow:
An Activity can contain one or more Fragments and can start/bind to one or more Services. Fragments communicate with their host Activity but not directly with Services.

How do you preserve state across lifecycle events?
Answer:
Several approaches depending on the scope:

onSaveInstanceState() / onRestoreInstanceState(): For temporary data during configuration changes (Activity/Fragment level)
ViewModel: Survives configuration changes and retains data for the scope of an Activity/Fragment
SharedPreferences: For persistent data that survives app restarts
Room Database: For larger datasets requiring permanent storage
Bundle: Pass data between Activities/Fragments during navigation
Static Variables: Not recommended; can cause memory leaks


What is ANR (Application Not Responding) and how do you prevent it?
Answer:
ANR Definition: The system kills an app if the main thread is blocked for more than 5 seconds without responding to user input.
Causes:

Long-running operations on the main thread (network calls, database queries, file I/O)
Infinite loops or deadlocks

Prevention:

Move long-running tasks to background threads using Thread, AsyncTask, Coroutines, or RxJava
Use Services for long-running background operations
Implement proper exception handling to prevent crashes that freeze the main thread
Use LiveData and Coroutines with proper threading models