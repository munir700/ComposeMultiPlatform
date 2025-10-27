package learning.gof.creationalPattern.abstractFactoryPattern

class RetrofitFactory : NetworkClientFactory() {
    override fun createClient(): NetworkClient = RetrofitClient()
}
