package learning.gof.creationalPattern.abstractFactoryPattern

abstract class NetworkClientFactory {
    abstract fun createClient(): NetworkClient

    fun performRequest(url: String) {
        val client = createClient()
        client.makeRequest(url)
    }
}
