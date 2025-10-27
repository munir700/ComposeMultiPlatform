Explain the Broadcast Receiver lifecycle and how it works.
Answer:
Broadcast Receivers have a minimal lifecycle:

onReceive(): Called when a broadcast is received. This is the only callback method.

Lifecycle Flow:

Receiver is created when the broadcast is sent
onReceive() is called
Receiver is destroyed after onReceive() completes

Key Characteristics:

onReceive() must complete within 5-10 seconds or Android will consider it unresponsive
Never perform long-running tasks in onReceive()
If you need long operations, start a Service from onReceive()
Receivers are created and destroyed for each broadcast

Registration Types:

Static Registration (in AndroidManifest.xml): Receiver is active even when the app is not running
Dynamic Registration (in code): Receiver is only active when registered, typically during onCreate() and unregistered in onDestroy()

What's the difference between static and dynamic Broadcast Receiver registration?
Answer:

Static Registration: Defined in AndroidManifest.xml. Receiver exists even if the app isn't running. Useful for system broadcasts like boot completion or SMS received.
Dynamic Registration: Registered programmatically in code using registerReceiver(). Receiver only exists while registered. More control and flexibility.

Modern Android versions (API 26+) restrict static receivers due to battery and performance concerns. Check if your broadcast requires static or dynamic registration based on Android version and use case.