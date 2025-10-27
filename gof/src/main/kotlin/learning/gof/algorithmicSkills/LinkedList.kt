package learning.gof.algorithmicSkills

//TODO Explain time and space complexity of your solution to detect a loop in a linked list.

//Use Floyd’s Cycle Detection Algorithm (Tortoise and Hare).
//Time complexity: O(n)
//Space complexity: O(1)
class LinkedList {

    fun hasCycle(head: ListNode?): Boolean {
        var slow = head
        var fast = head
        while (fast?.next != null) {
            slow = slow?.next
            fast = fast.next?.next
            if (slow == fast) return true
        }
        return false
    }

    fun hasNode(head: ListNode?, findNode: Int): Boolean {
        var tempNode = head
        while (tempNode?.next != null) {
            if (tempNode.value == findNode)
                return true
            tempNode = tempNode.next
        }
        return false
    }

    // Delete node with specified value from linked list
    // Returns: true if node was found and deleted, false otherwise
    // Time complexity: O(n)
    // Space complexity: O(1)
    fun deleteNode(head: ListNode?, valueToDelete: Int): ListNode? {
        var currentNode = head

        // If head node needs to be deleted
        if (currentNode?.value == valueToDelete) {
            println("Deleting head node with value: $valueToDelete")
            return currentNode.next
        }

        // Traverse the list to find the node to delete
        while (currentNode?.next != null) {
            val nextNode = currentNode.next
            if (nextNode?.value == valueToDelete) {
                println("Found node with value: $valueToDelete, removing it")
                currentNode.next = nextNode.next
                return head
            }
            currentNode = nextNode
        }

        println("Node with value $valueToDelete not found")
        return head
    }

    // Optional: Find the start of the cycle
    fun detectCycleStart(head: ListNode?): ListNode? {
        var slow = head
        var fast = head

        // Detect cycle
        while (fast?.next != null) {
            slow = slow?.next
            fast = fast.next?.next
            if (slow == fast) break
        }

        // No cycle found
        if (fast?.next == null) return null

        // Find cycle start
        var fromHead = head
        var fromCycleNode = slow
        while (fromHead != fromCycleNode) {
            fromHead = fromHead?.next
            fromCycleNode = fromCycleNode?.next
        }

        return fromHead
    }

    fun reverseIterative(head: ListNode?): ListNode? {
        var prev: ListNode? = null
        var curr = head
        while (curr != null) {
            val nextTemp = curr.next
            curr.next = prev
            prev = curr
            curr = nextTemp
        }
        return prev
    }

    fun printNode(head: ListNode?) {
        var curr = head
        while (curr != null) {
            println("Data ${curr.value}, ref= ${curr.next}")
            curr = curr.next
        }
    }
}


// ListNode definition
data class ListNode(
    var value: Int,
    var next: ListNode? = null
)

// Example usage
fun main() {
    // Create a linked list: 1 -> 2 -> 3 -> 4 -> 2 (cycle)
    val node1 = ListNode(1)
    val node2 = ListNode(2)
    val node3 = ListNode(3)
    val node4 = ListNode(4)

    node1.next = node2
    node2.next = node3
    node3.next = node4
    node4.next = node2  // Creates cycle

    val linkedList = LinkedList()
    println("Has cycle: ${linkedList.hasCycle(node1)}")  // Output: true
    println("Cycle starts at: ${linkedList.detectCycleStart(node1)?.value}")  // Output: 2
    println("Has node: ${linkedList.hasNode(node1, 3)}")  // Output: 2

    // Create a linked list without cycle: 1 -> 2 -> 3 -> null
    val node5 = ListNode(5)
    val node6 = ListNode(6)
    val node7 = ListNode(7)
    node4.next = node5
    node5.next = node6
    node6.next = node7

    println("Has cycle: ${linkedList.hasCycle(node5)}")  // Output: false

    /*println("before verse $node1")
    linkedList.printNode(node1)
    val reverseNode = linkedList.reverseIterative(node1)
    println("after verse $reverseNode")
    linkedList.printNode(reverseNode)*/
}

