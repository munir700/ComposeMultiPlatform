package learning.gof.creationalPattern.abstractFactoryPattern


/**
 * ✅ Benefits:
 *
 * Adding a new client requires only adding it to the map.
 *
 * Client code does not need to change.
 *
 * Fully adheres to Open/Closed Principle.
 *
 * 🔹 Another Option — Reflection / Dependency Injection
 *
 * With DI frameworks like Dagger or Hilt, you can inject the right factory at runtime based on configuration or qualifiers, avoiding any conditional logic entirely.
 */
fun main() {
    val useRetrofit = false  // Could be based on config or build variant

    val factory: NetworkClientFactory = if (useRetrofit) {
        RetrofitFactory()
    } else {
        OkHttpFactory()
    }

    factory.performRequest("https://example.com/data")


    // Choose factory dynamically
    val selectedType = ClientType.RETROFIT
    val factory1 = factoryRegistry[selectedType] ?: throw IllegalArgumentException("Unknown client")
    factory1.performRequest("https://example.com/data")
}
