 Explain the complete Activity lifecycle with all callback methods.
Answer:
The Activity lifecycle consists of seven primary states with corresponding callbacks:

onCreate(): Called when the activity is first created. Initialize variables, set layouts, bind data to lists here.
onStart(): Called when the activity becomes visible to the user. The activity is moving toward the foreground.
onResume(): Called when the activity gains focus and starts interacting with the user. The activity is at the top of the stack.
onPause(): Called when the system is about to resume another activity. Save data here. This method completes very quickly.
onStop(): Called when the activity is no longer visible. This occurs when another activity comes to the foreground or the activity is being destroyed.
onRestart(): Called after onStop() when the activity is being restarted.
onDestroy(): Called before the activity is destroyed. This is the final callback in the lifecycle.

Key Pattern: onCreate → onStart → onResume → [activity running] → onPause → onStop → onDestroy

What happens to an Activity when the screen rotates?
Answer:
By default, Android destroys and recreates the activity during screen rotation:

The system calls onPause(), onStop(), and onDestroy() on the current instance
A new Activity instance is created with onCreate(), onStart(), and onResume()
Android automatically restores view states using onSaveInstanceState() if views have unique IDs

Best Practices to Preserve State:

Implement onSaveInstanceState() to save non-persistent state to a Bundle
Implement onRestoreInstanceState() to restore state from the Bundle
Use ViewModel to retain data across configuration changes
Use the configChanges attribute in AndroidManifest.xml to handle rotation yourself (not recommended)

What is the difference between onPause() and onStop()?
Answer:

onPause(): Called when another activity starts to come into the foreground but the current activity is still partially visible. Save critical data here but keep it brief as the next activity won't start until this returns.
onStop(): Called when the activity is no longer visible at all. Another activity has taken full focus. Safe to perform more intensive shutdown operations.


What are the best practices for managing component lifecycles?

Answer:

Initialize in onCreate(): Setup only essential resources
Start operations in onStart()/onResume(): Register listeners, start animations
Pause in onPause(): Save data, stop animations, unregister listeners
Clean up in onStop(): Release resources, stop background operations
Handle configuration changes properly: Use ViewModels and onSaveInstanceState()
Use appropriate threading: Keep main thread responsive
Implement proper error handling: Prevent ANR issues
Test lifecycle transitions: Especially for configuration changes and background/foreground transitions


How do you handle long-running operations in different components?
Answer:

Activity: Use Coroutines with lifecycleScope or ViewModel for long operations
Fragment: Use viewLifecycleOwner with Coroutines to respect Fragment lifecycle
Service: Started service for independent operations; Bound service for client communication
Broadcast Receiver: Never perform long operations in onReceive(); start a Service instead

Avoid: AsyncTask (deprecated), Thread directly on UI thread, blocking operations on main thread