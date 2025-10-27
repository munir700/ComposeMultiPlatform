package learning.gof.creationalPattern.abstractFactoryPattern

class VolleyFactory : NetworkClientFactory() {
    override fun createClient(): NetworkClient = VolleyClientFactory()

}
