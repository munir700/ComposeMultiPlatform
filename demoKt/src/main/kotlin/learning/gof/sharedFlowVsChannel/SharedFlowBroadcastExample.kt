package learning.gof.sharedFlowVsChannel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/** 1️⃣ SharedFlow — broadcasts to all collectors **/
suspend fun sharedFlowBroadcastExample(coroutineScope: CoroutineScope) {
    val sharedFlow = MutableSharedFlow<String>(replay = 1)

    // Launch two collectors
    coroutineScope.launch {
        sharedFlow.collect { println("🟢 $it: Collector 1 received") }
    }

    delay(500)

    coroutineScope.launch {
        sharedFlow.collect { println("🟣 $it: Collector 2 received") }
    }


    println("🔵 Emitting 'Hello SharedFlow'")
    sharedFlow.emit("Hello SharedFlow ->")

    delay(500)
}

