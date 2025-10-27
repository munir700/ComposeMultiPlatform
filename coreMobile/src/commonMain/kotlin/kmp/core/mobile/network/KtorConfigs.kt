package kmp.core.mobile.network

data class KtorConfigs(
    val baseUrl: String,
    val extraHeaders: Map<String, Any> = emptyMap(),
    val extraQueries: Map<String, Any> = emptyMap(),
    val customRequestInterceptors: List<CustomRequestInterceptor> = emptyList()
)
