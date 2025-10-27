package learning.gof.algorithmicSkills
// TODO Find the first non-repeating character in a string.
//A:
//Use a hash map to count occurrences, then iterate to find the first with count = 1.
//Time complexity: O(n)
//Space complexity: O(1) (since alphabet size is bounded)

class NonRepeatingCharacterString {
    fun firstUniqueChar(input: String): Char? {
        val characterFrequency = IntArray(26)

        // Count occurrences of each character
        for (character in input) {
            val charIndex = character - 'a'
            characterFrequency[charIndex]++
            // println("Character: '$character', Index: $charIndex, Frequency: ${characterFrequency[charIndex]}")
        }

        /*   // Find the first character with frequency = 1
           for (character in input) {
               if (characterFrequency[character - 'a'] == 1) {
                   return character
               }
           }*/

        // Find the first character with frequency = 1
        var count = 0
        for (character in input) {
            val charIndex = character - 'a'
            if (characterFrequency[charIndex] == 1) {
                count++
                println("Found unique character #$count: '$character' at index $charIndex")
                if (count == 5) {
                    println("5th unique character found: '$character'")
                    return character
                }
            }
        }


        return null
    }
}

// Example usage
fun main() {
    val solution = NonRepeatingCharacterString()

    println("leetcode ${solution.firstUniqueChar("leetcode")}")      // Output: 'l'
    println(solution.firstUniqueChar("loveleetcode"))  // Output: 'v'
    println(solution.firstUniqueChar("aabb"))          // Output: null
    println(solution.firstUniqueChar("a"))             // Output: 'a'
}