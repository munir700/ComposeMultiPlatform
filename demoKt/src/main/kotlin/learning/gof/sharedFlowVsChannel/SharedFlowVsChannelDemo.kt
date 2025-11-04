import kotlinx.coroutines.runBlocking
import learning.gof.sharedFlowVsChannel.channelExample
import learning.gof.sharedFlowVsChannel.sharedFlowBroadcastExample
import learning.gof.sharedFlowVsChannel.sharedFlowReplayExample
import learning.gof.sharedFlowVsChannel.stateFlowExample

fun main() = runBlocking {
    println("=== SharedFlow Example (Broadcast) ===")
    sharedFlowBroadcastExample(this)

    println("\n=== Channel Example (One-to-One) ===")
    channelExample(this)


    println("\n=== StateFlow Example  ===")
    stateFlowExample(this)

    sharedFlowReplayExample(this)
}