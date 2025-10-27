package learning.gof.creationalPattern.abstractFactoryPattern

class OkHttpClient : NetworkClient {
    override fun makeRequest(url: String) {
        println("Making request using OkHttp: $url")
    }
}