package learning.gof.sharedFlowVsChannel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/** 3️⃣ StateFlow — always holds latest value **/
suspend fun stateFlowExample(coroutineScope: CoroutineScope) {
    val stateFlow = MutableStateFlow("Initial Value")

    coroutineScope.launch {
        stateFlow.collect { println("🟢 $it: Collector 1 received") }
    }

    delay(500)

    println("🔵 Updating StateFlow to 'Hello StateFlow'")
    stateFlow.value = "Hello StateFlow ->"

    delay(500)

    coroutineScope.launch {
        stateFlow.collect { println("🟣 $it: Collector 2 received") }
    }

    delay(500)
}

