package kmp.core.mobile.network.errors

import kotlinx.serialization.json.Json

val networkErrorJson = Json {
    ignoreUnknownKeys = true
}


open class NetworkResponseError(
    open val bodyText: String,
    open val statusCode: Int,
    open val statusDescription: String = ""
) : Throwable() {

    inline fun <reified T> body(): T {
        return networkErrorJson.decodeFromString(bodyText)
    }
}