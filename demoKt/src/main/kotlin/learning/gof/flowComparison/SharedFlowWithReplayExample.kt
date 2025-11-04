package learning.gof.flowComparison

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

// 2️⃣ SharedFlow with replay = 1 — remembers last emission
suspend fun sharedFlowWithReplayExample(coroutinesScope: CoroutineScope) {
    val sharedFlow = MutableSharedFlow<List<String>>(replay = 1)

    GlobalScope.launch {
        delay(500)
        val users = listOf("Ali", "Sara", "John")
        println("🔵 [SharedFlow 1] API emitted: $users")
        sharedFlow.emit(users)
    }

    // Collector joins late but still receives last emission
    delay(1500)
    sharedFlow.collect {
        println("🟢 [SharedFlow 1] Collector received: $it")
    }
}

