package learning.gof.algorithmicSkills

import java.util.PriorityQueue


// TODO How would you find the kth largest element in a stream of numbers?
//A:
//Maintain a min-heap of size k.
//When heap size exceeds k, pop the smallest.
//Time complexity: O(n log k)

// Key concept:
//
//Maintains a min-heap of size k containing the k largest elements seen so far
//The root of the min-heap is always the kth largest element
//When a new number arrives, add it to the heap
//If heap size exceeds k, remove the smallest element (maintaining k elements)
//
//Example walkthrough with k=3:
//
//Add 4: heap = [4], 3rd largest = 4
//Add 13: heap = [4, 13], 3rd largest = 4
//Add 7: heap = [4, 7, 13], 3rd largest = 4
//Add 2: heap = [4, 7, 13, 2] → remove 2 → heap = [4, 7, 13], 3rd largest = 4
//Add 8: heap = [4, 7, 8, 13] → remove 4 → heap = [7, 8, 13], 3rd largest = 7
//
//Why this approach?
//
//Efficient for continuous streams (O(log k) per insertion)
//Only stores k elements in memory
//Instantly returns the kth largest without re-sorting

class KthLargestElementStreamNumbers(val k: Int) {

    private val minHeap = PriorityQueue<Int>()

    fun add(number: Int): Int? {
        // Add the number to the min-heap
        minHeap.offer(number)

        // If heap size exceeds k, remove the smallest element
        if (minHeap.size > k) {
            minHeap.poll()
        }

        println("Added: $number, Heap: ${minHeap.sorted()}, Kth Largest: ${minHeap.peek()}")

        // Return the kth largest element (top of min-heap)
        return minHeap.peek()
    }

    private val elements = mutableListOf<Int>()

    fun addElements(number: Int): Int {
        // Insert number in sorted position
        var insertIndex = 0
        for (index in elements.indices) {
            if (elements[index] < number) {
                insertIndex = index + 1
            } else {
                break
            }
        }

        elements.add(insertIndex, number)

        // If list size exceeds k, remove the smallest element
        if (elements.size > k) {
            elements.removeAt(0)
        }

        println("Added: $number, Elements: $elements, Kth Largest: ${elements.firstOrNull()}")

        // Return the kth largest element (smallest in our sorted list)
        return elements.firstOrNull() ?: -1
    }
}

// Example usage
fun main() {
    val kthLargest = KthLargestElementStreamNumbers(k = 3)

    println("Finding 3rd largest element in stream:")
    kthLargest.add(4)
    kthLargest.add(13)
    kthLargest.add(7)
    kthLargest.add(2)
    kthLargest.add(8)
    kthLargest.add(5)
    kthLargest.add(10)

    // Output: 3rd largest is 10

    val twoLargest = KthLargestElementStreamNumbers(k = 2)
    println("Finding 2rd largest element in stream:")
    twoLargest.addElements(4)
    twoLargest.addElements(13)
    twoLargest.addElements(7)
    twoLargest.addElements(2)
    twoLargest.addElements(8)
    twoLargest.addElements(5)
    twoLargest.addElements(10)
}