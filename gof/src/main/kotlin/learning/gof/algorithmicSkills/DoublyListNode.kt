package learning.gof.algorithmicSkills

// Doubly LinkedList Node definition with both next and previous pointers
data class DoublyListNode(
    var value: Int,
    var next: DoublyListNode? = null,
    var prev: DoublyListNode? = null
)

class DoublyLinkedList {

    // Insert node at the beginning
    // Time complexity: O(1)
    // Space complexity: O(1)
    fun insertAtBeginning(head: DoublyListNode?, value: Int): DoublyListNode {
        val newNode = DoublyListNode(value)
        if (head != null) {
            newNode.next = head
            head.prev = newNode
        }
        return newNode
    }

    // Insert node at the end
    // Time complexity: O(n)
    // Space complexity: O(1)
    fun insertAtEnd(head: DoublyListNode?, value: Int): DoublyListNode? {
        val newNode = DoublyListNode(value)
        
        if (head == null) return newNode
        
        var current = head
        while (current?.next != null) {
            current = current.next!!
        }
        
        current.next = newNode
        newNode.prev = current
        return head
    }

    // Insert node at a specific position
    // Time complexity: O(n)
    // Space complexity: O(1)
    fun insertAtPosition(head: DoublyListNode?, value: Int, position: Int): DoublyListNode? {
        if (position == 0) return insertAtBeginning(head, value)
        
        var current = head
        var index = 0
        
        while (current != null && index < position - 1) {
            current = current.next
            index++
        }
        
        if (current == null) return head
        
        val newNode = DoublyListNode(value)
        newNode.next = current.next
        newNode.prev = current
        
        if (current.next != null) {
            current.next!!.prev = newNode
        }
        current.next = newNode
        
        return head
    }

    // Delete node with specified value
    // Time complexity: O(n)
    // Space complexity: O(1)
    fun deleteNode(head: DoublyListNode?, valueToDelete: Int): DoublyListNode? {
        var current = head
        
        // If head node needs to be deleted
        if (current?.value == valueToDelete) {
            println("Deleting head node with value: $valueToDelete")
            if (current.next != null) {
                current.next!!.prev = null
            }
            return current.next
        }
        
        // Traverse the list to find the node
        while (current != null) {
            if (current.value == valueToDelete) {
                println("Found node with value: $valueToDelete, removing it")
                if (current.next != null) {
                    current.next!!.prev = current.prev
                }
                if (current.prev != null) {
                    current.prev!!.next = current.next
                }
                return head
            }
            current = current.next
        }
        
        println("Node with value $valueToDelete not found")
        return head
    }

    // Search for a node with specified value
    // Time complexity: O(n)
    // Space complexity: O(1)
    fun hasNode(head: DoublyListNode?, findValue: Int): Boolean {
        var current = head
        while (current != null) {
            if (current.value == findValue) return true
            current = current.next
        }
        return false
    }

    // Traverse forward and print
    // Time complexity: O(n)
    // Space complexity: O(1)
    fun printForward(head: DoublyListNode?) {
        var current = head
        print("Forward: ")
        while (current != null) {
            print("${current.value} <-> ")
            current = current.next
        }
        println("null")
    }

    // Traverse backward and print
    // Time complexity: O(n)
    // Space complexity: O(1)
    fun printBackward(tail: DoublyListNode?) {
        var current = tail
        print("Backward: ")
        while (current != null) {
            print("${current.value} <-> ")
            current = current.prev
        }
        println("null")
    }

    // Get the tail node (last node)
    // Time complexity: O(n)
    // Space complexity: O(1)
    fun getTail(head: DoublyListNode?): DoublyListNode? {
        var current = head
        while (current?.next != null) {
            current = current.next
        }
        return current
    }

    // Reverse the doubly linked list
    // Time complexity: O(n)
    // Space complexity: O(1)
    fun reverse(head: DoublyListNode?): DoublyListNode? {
        var current = head
        var newHead = head
        
        while (current != null) {
            val temp = current.next
            current.next = current.prev
            current.prev = temp
            newHead = current
            current = temp
        }
        
        return newHead
    }
}

// Example usage
fun main() {
    val dll = DoublyLinkedList()
    var head: DoublyListNode? = null
    
    // Build list: 1 <-> 2 <-> 3 <-> 4
    head = dll.insertAtEnd(head, 1)
    head = dll.insertAtEnd(head, 2)
    head = dll.insertAtEnd(head, 3)
    head = dll.insertAtEnd(head, 4)
    
    println("=== Original List ===")
    dll.printForward(head)
    val tail = dll.getTail(head)
    dll.printBackward(tail)
    
    println("\n=== Insert at Position ===")
    head = dll.insertAtPosition(head, 2, 2)
    dll.printForward(head)
    
    println("\n=== Search Operations ===")
    println("Has node 3: ${dll.hasNode(head, 3)}")
    println("Has node 10: ${dll.hasNode(head, 10)}")
    
    println("\n=== Delete Node ===")
    head = dll.deleteNode(head, 2)
    dll.printForward(head)
    
    println("\n=== Reverse List ===")
    head = dll.reverse(head)
    dll.printForward(head)
    val newTail = dll.getTail(head)
    dll.printBackward(newTail)
    
    println("\n=== Insert at Beginning ===")
    head = dll.insertAtBeginning(head, 0)
    dll.printForward(head)
}