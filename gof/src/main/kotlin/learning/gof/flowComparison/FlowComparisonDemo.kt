package learning.gof.flowComparison

import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("=== SharedFlow (replay = 0) Example ===")
    sharedFlowNoReplayExample(this)

    println("\n=== SharedFlow (replay = 1) Example ===")
    sharedFlowWithReplayExample(this)

    println("\n=== StateFlow Example ===")
    stateFlowExample(this)
}