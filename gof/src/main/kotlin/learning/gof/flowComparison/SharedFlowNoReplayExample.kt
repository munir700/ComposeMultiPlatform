package learning.gof.flowComparison

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

// 1️⃣ SharedFlow without replay — will lose emission if collected late
suspend fun sharedFlowNoReplayExample(coroutinesScope: CoroutineScope) {
    val sharedFlow = MutableSharedFlow<List<String>>(replay = 0)

    // Emit data before collector starts
    coroutinesScope.launch {
        delay(500)
        val users = listOf("Ali", "Sara", "John")
        println("🔵 [SharedFlow 0] API emitted: $users")
        sharedFlow.emit(users)
    }

    // Start collector after some delay (too late!)
    delay(1000)
    sharedFlow.collect {
        println("🟢 [SharedFlow 0] Collector received: $it")
    }
}