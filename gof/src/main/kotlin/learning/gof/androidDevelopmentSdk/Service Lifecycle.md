Explain the Service lifecycle and differentiate between Started and Bound services.
Answer:
Services have their own lifecycle with three main callbacks:

onCreate(): Called when the service is first created
onStartCommand(): Called each time the service is started via startService()
onDestroy(): Called when the service is destroyed

Started Service (Unbounded):

Started by calling startService() from an Activity or component
Runs in the background independently
Lifecycle: onCreate() → onStartCommand() → [running] → onDestroy()
Must be stopped explicitly with stopService() or stopSelf()

Bound Service:

Started by calling bindService() from an Activity
Provides a client-server interface
Lifecycle: onCreate() → onBind() → [clients connected] → onUnbind() → onDestroy()
Automatically destroyed when all clients unbind

Lifecycle Flows:

Started Only: onCreate() → onStartCommand() → onDestroy()
Bound Only: onCreate() → onBind() → onUnbind() → onDestroy()
Both: onCreate() → onStartCommand() → onBind() → onUnbind() → onDestroy()

When should you use a Service vs. an AsyncTask?
Answer:

Service: Use when you need long-running background operations that should continue even if the Activity is destroyed (downloading files, playing music, syncing data)
AsyncTask: Use for short operations that run in the background and report results to the UI thread (loading data for a screen, processing images)

Key difference: Services survive Activity destruction; AsyncTasks are tied to the Activity lifecycle.