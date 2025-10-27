package kmp.core.mobile.network

import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.request.header

internal fun defaultRequestInterceptor(
    configs: KtorConfigs
): DefaultRequest.DefaultRequestBuilder.() -> Unit = {
    configs.extraHeaders.forEach { (key, value) ->
        if (headers.contains(key).not()) {
            header(key, value.toString())
        }
    }

    configs.extraQueries.forEach { (key, value) ->
        if (url.parameters.contains(key).not()) {
            url.parameters.append(key, value.toString())
        }
    }
}
