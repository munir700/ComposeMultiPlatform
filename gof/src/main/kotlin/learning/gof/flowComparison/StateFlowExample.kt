package learning.gof.flowComparison

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

// 3️⃣ StateFlow — always holds latest value and ignores duplicates
suspend fun stateFlowExample(coroutinesScope: CoroutineScope) {
    val stateFlow = MutableStateFlow<List<String>>(emptyList())

    coroutinesScope.launch {
        delay(500)
        val users = listOf("Ali", "Sara", "John")
        println("🔵 [StateFlow] API emitted: $users")
        stateFlow.value = users

        delay(1000)
        println("🔵 [StateFlow] API emitted same data again")
        stateFlow.value = users // same value — won't emit again
    }

    delay(1500)
    stateFlow.collect {
        println("🟢 [StateFlow] Collector received: $it")
    }
}