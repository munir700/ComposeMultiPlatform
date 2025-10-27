package kmp.core.mobile.network

import io.ktor.client.plugins.HttpCallValidatorConfig
import io.ktor.http.isSuccess
import kmp.core.mobile.network.errors.UnauthorizedError

private const val UNAUTHORIZED_STATUS_CODE = 401
private const val BAD_REQUEST_STATUS_CODE = 400

internal fun defaultResponseInterceptor(): HttpCallValidatorConfig.() -> Unit = {
    validateResponse { response ->
        when {
            response.status.value == UNAUTHORIZED_STATUS_CODE -> {
                // Unauthorized,
                // Throw exception
                throw UnauthorizedError
            }

            // Bad/Invalid request
            response.status.value == BAD_REQUEST_STATUS_CODE || response.status.isSuccess().not() -> {
                // Error response,
                // throw NetworkResponseError with required data
//                throw NetworkResponseError(
//                    statusCode = response.status.value,
//                    statusDescription = response.status.description,
//                    bodyText = response.bodyAsText()
//                )
            }
        }
    }
}