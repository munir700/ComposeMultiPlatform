package learning.gof.creationalPattern.abstractFactoryPattern

val factoryRegistry: Map<ClientType, NetworkClientFactory> = mapOf(
    ClientType.RETROFIT to RetrofitFactory(),
    ClientType.OKHTTP to OkHttpFactory(),
    ClientType.VOLLEY to VolleyFactory() // new client added easily
)/**/

