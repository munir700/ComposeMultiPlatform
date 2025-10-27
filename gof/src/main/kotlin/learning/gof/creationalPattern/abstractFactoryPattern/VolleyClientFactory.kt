package learning.gof.creationalPattern.abstractFactoryPattern

class VolleyClientFactory : NetworkClient {
    override fun makeRequest(url: String) {
        println("Making request using Volley: $url")
    }
}