package learning.gof.sharedFlowVsChannel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/** 1️⃣ SharedFlow — broadcasts to all collectors **/
suspend fun sharedFlowBroadcastExample(coroutineScope: CoroutineScope) {
    val sharedFlow = MutableSharedFlow<String>()

    // Launch two collectors
    coroutineScope.launch {
        sharedFlow.collect { println("🟢 Collector 1 received: $it") }
    }

    delay(500)

    coroutineScope.launch {
        sharedFlow.collect { println("🟣 Collector 2 received: $it") }
    }


    println("🔵 Emitting 'Hello SharedFlow'")
    sharedFlow.emit("Hello SharedFlow ->")

    delay(500)
}