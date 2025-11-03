# ForegroundService Implementation Guide

## Overview
A ForegroundService is an Android service that performs long-running tasks while displaying a persistent notification to the user. It keeps running even when the app is in the background or the device enters Doze mode.

## Key Features
- ✅ Continues running even when app is backgrounded
- ✅ Survives Doze mode restrictions
- ✅ Shows persistent notification
- ✅ Proper cleanup with coroutines
- ✅ Works on Android 8.0 (API 26) and above

## Usage

### 1. Start the ForegroundService from Activity

```kotlin
class MyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Start the service
        ForegroundServiceHelper.startService(
            context = this,
            data = "My important task data"
        )
    }
}
```

### 2. Stop the ForegroundService

```kotlin
// Stop the service
ForegroundServiceHelper.stopService(this)
```

### 3. Direct Usage from Activity

```kotlin
val activity = ForegroundServiceMainActivity()
activity.startForegroundService()
// ... later ...
activity.stopForegroundService()
```

## AndroidManifest.xml Permissions Required

```xml
<manifest>
    <!-- Required permissions -->
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" /> <!-- Android 13+ -->
    
    <!-- Register the service -->
    <application>
        <service
            android:name="learning.gof.dozeMode.ForegroundService"
            android:enabled="true"
            android:exported="false" />
    </application>
</manifest>
```

## How It Works

### Service Lifecycle
1. **onCreate()** - Creates notification channel for Android 8.0+
2. **onStartCommand()** - Starts as foreground service with notification
3. **performLongRunningTask()** - Executes the background work
4. **onDestroy()** - Cleans up coroutines

### Notification
- **Channel ID**: "my_service_channel"
- **Notification ID**: 1
- **Importance**: LOW (no sound/vibration)
- **Ongoing**: True (can't be dismissed)

## Example Scenarios

### Background Download
```kotlin
ForegroundServiceHelper.startService(
    context = this,
    data = "https://example.com/large-file.zip"
)
```

### Real-time Location Tracking
```kotlin
ForegroundServiceHelper.startService(
    context = this,
    data = "location-tracking-enabled"
)
```

### Periodic Sync
```kotlin
ForegroundServiceHelper.startService(
    context = this,
    data = "sync-all-data"
)
```

## Important Notes

### Doze Mode Compatibility
- ForegroundService is exempt from Doze mode restrictions
- The persistent notification informs users about background activity
- This is the recommended approach for long-running background tasks

### Coroutine Management
- Uses `SupervisorJob()` for proper scope management
- Handles `CancellationException` gracefully
- Cleans up resources in `onDestroy()`

### Error Handling
```kotlin
override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    startForeground(NOTIFICATION_ID, createNotification())

    scope.launch {
        try {
            val result = performLongRunningTask(data)
            updateNotification("Success: $result")
        } catch (e: CancellationException) {
            Log.e("Service", "Task cancelled", e)
        } finally {
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf(startId)
        }
    }
    
    return START_STICKY
}
```

## Comparison: Services vs Alternatives

| Approach | Background Time | Doze Mode | Notification | Use Case |
|----------|-----------------|-----------|--------------|----------|
| **ForegroundService** | Long (indefinite) | ✅ Exempt | ✅ Required | Long-running tasks |
| WorkManager | Short to long | ✅ Handles | ❌ Optional | Scheduled tasks |
| IntentService | Short | ❌ Affected | ❌ Optional | One-time tasks |
| Background Service | Short | ❌ Affected | ❌ Optional | Light tasks |

## Best Practices

1. ✅ Always show a meaningful notification
2. ✅ Allow users to stop the service easily
3. ✅ Use appropriate channel importance
4. ✅ Handle cancellation gracefully
5. ✅ Clean up resources in onDestroy()
6. ✅ Check permissions before starting
7. ✅ Use START_STICKY for automatic restart

## Troubleshooting

### Service Stops Unexpectedly
- Check if notification channel is created
- Verify permissions in AndroidManifest.xml
- Ensure coroutine scope is not cancelled

### Notification Not Showing
- Check notification channel importance level
- Verify permission `android.permission.POST_NOTIFICATIONS`
- Check if channel was created before calling startForeground()

### Task Not Completing
- Increase delay time in `performLongRunningTask()`
- Check if service is being stopped externally
- Monitor logcat for exceptions

## API Requirements

- **Minimum SDK**: API 21 (Android 5.0)
- **Target SDK**: API 33+ (Android 13+)
- **ForegroundService API**: API 26+ (Android 8.0+)
- **POST_NOTIFICATIONS Permission**: API 33+ (Android 13+)

