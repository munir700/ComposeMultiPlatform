package learning.gof.sharedFlowVsChannel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/** 2️⃣ SharedFlow (replay = 1) — replays latest value to new collectors **/
suspend fun sharedFlowReplayExample(coroutineScope: CoroutineScope) {
    val sharedFlow = MutableSharedFlow<String>(replay = 1)

    coroutineScope.launch {
        sharedFlow.collect { println("🟢 $it: Collector 1 received") }
    }

    delay(500)

    sharedFlow.emit("Hello SharedFlow (replay=1) ->")
    println("🔵 Emitting 'Hello SharedFlow (replay=1) '")

    delay(5000)

    coroutineScope.launch {
        sharedFlow.collect { println("🟣 $it: Collector 2 received") }
    }

    //delay(4000)
}