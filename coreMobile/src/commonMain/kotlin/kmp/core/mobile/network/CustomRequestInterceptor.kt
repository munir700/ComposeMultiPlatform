package kmp.core.mobile.network

import io.ktor.client.call.HttpClientCall
import io.ktor.client.plugins.Sender
import io.ktor.client.request.HttpRequestBuilder

typealias CustomRequestInterceptor = suspend Sender.(HttpRequestBuilder) -> HttpClientCall