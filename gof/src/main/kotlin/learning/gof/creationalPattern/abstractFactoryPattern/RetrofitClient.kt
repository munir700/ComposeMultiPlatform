package learning.gof.creationalPattern.abstractFactoryPattern

class RetrofitClient : NetworkClient {
    override fun makeRequest(url: String) {
        println("Making REST request using Retrofit: $url")
    }
}

