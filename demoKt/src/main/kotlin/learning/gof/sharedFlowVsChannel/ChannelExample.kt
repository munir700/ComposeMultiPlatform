package learning.gof.sharedFlowVsChannel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/** 2️⃣ Channel — sends data to only one receiver **/
suspend fun channelExample(coroutineScope: CoroutineScope) {
    val channel = Channel<String>()

    // Launch two receivers — but each message goes to only ONE
    coroutineScope.launch {
        for (msg in channel) {
            println("🟢 Receiver 1 got: $msg")
        }
    }

    coroutineScope.launch {
        for (msg in channel) {
            println("🟣 Receiver 2 got: $msg")
        }
    }

    delay(500)
    println("🔵 Sending 'Hello Channel'")
    channel.send("Hello Channel")

    delay(500)
    channel.close()
}

