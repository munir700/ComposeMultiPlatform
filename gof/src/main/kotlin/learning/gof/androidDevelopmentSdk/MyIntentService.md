Characteristics

Automatically creates a background worker thread (HandlerThread)
Processes intents sequentially from a queue (FIFO order)
onHandleIntent() is called on the background thread
Automatically stops itself after all intents are processed
Simple boilerplate, but rigid behavior

Lifecycle
startService(intent)
↓
onCreate() [thread created]
↓
onStartCommand() [adds intent to queue]
↓
onHandleIntent() [processes on background thread]
↓
onDestroy() [when queue empty]

Problems

Blocked by garbage collection; not optimized for Doze mode
Cannot restart automatically if device reboots
Limited control over threading behavior
Deprecated and no longer recommended

When to Use
Only in legacy apps still targeting old API levels. For new code, use alternatives.