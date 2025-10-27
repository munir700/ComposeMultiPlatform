package learning.gof.creationalPattern.protoType

// Step 4: Usage
fun main() {
    val original = Person("Munir", 30, Address("Karachi", "Pakistan"))

    // Create new object using prototype (copy)
    val clone = original.clone()

    println("Original: $original")
    println("Clone:    $clone")

    // Modify clone without affecting original
    val modifiedClone = clone.copy(address = clone.address.copy(city = "Lahore"), name = "Ahmad")
    println("Modified Clone: $modifiedClone")
    println("Original: $original")
    println("Clone:    $clone")
}