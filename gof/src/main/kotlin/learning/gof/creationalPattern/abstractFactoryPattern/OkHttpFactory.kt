package learning.gof.creationalPattern.abstractFactoryPattern
class OkHttpFactory : NetworkClientFactory() {
    override fun createClient(): NetworkClient = OkHttpClient()
}