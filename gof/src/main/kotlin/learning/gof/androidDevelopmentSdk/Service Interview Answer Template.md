Interview Answer Template
"IntentService was the old way to handle background tasks sequentially, but it's deprecated because it didn't respect Doze mode and was inflexible.
JobIntentService is a better-maintained alternative that respects battery optimization using JobScheduler under the hood.
It's good for backward compatibility but still not the modern approach.
Coroutines with Services is the current recommended way. 
It gives you full control over threading using lightweight coroutines, integrates well with modern architecture components like LiveData and ViewModel, and follows Kotlin idioms.
However, you must manually manage the service lifecycle and handle device Doze mode yourself. 
For guaranteed background execution across device reboots, use WorkManager instead."