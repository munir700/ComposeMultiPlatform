package learning.gof.sharedFlowVsChannel

import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("=== SharedFlow Example (Broadcast) ===")
    sharedFlowBroadcastExample(this)

    println("\n=== Channel Example (One-to-One) ===")
    channelExample(this)
}