package app.shared.mobile.data.remote.api


import app.shared.mobile.constants.AppConstants
import app.shared.mobile.domain.DynamicUrlManager
import app.shared.mobile.domain.models.auth.LoginRequest
import app.shared.mobile.domain.models.auth.LoginResponse
import app.shared.mobile.domain.models.auth.RefreshTokenRequest
import app.shared.mobile.domain.models.auth.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.parameters
import kmp.core.mobile.language.ILanguageManager
import kmp.core.mobile.remoteConfigs.IRemoteConfigs
import kmp.core.mobile.utils.extensions.toMapOfString

class AuthApiService(
    private val httpClient: HttpClient,
    private val dynamicUrlManager: DynamicUrlManager,
    private val remoteConfigs: IRemoteConfigs,
    private val languageManager: ILanguageManager
) {

    companion object {
        // Default endpoints
        private const val LOGIN_ENDPOINT = "connect/token"
        private const val USER_PROFILE_ENDPOINT = "api/profile"
    }

    /**
     * Get mobile client auth from remote configs
     * This provides security by retrieving client auth from remote config only
     */
    private fun getMobileClientAuth(): String {
        val clientAuth = remoteConfigs.getString(AppConstants.RemoteConfigs.MOBILE_CLIENT_AUTH)
        return clientAuth
            ?: throw IllegalStateException("MOBILE_CLIENT_AUTH not configured in remote config")
    }

    /**
     * Authenticate user with form-encoded request
     */
    suspend fun login(request: LoginRequest): LoginResponse {
        val baseUrl = getCurrentSsoUrl()
        val formParams = toMapOfString(request)

        return httpClient.submitForm(
            url = buildUrl(baseUrl, LOGIN_ENDPOINT),
            formParameters = parameters {
                formParams.forEach { (key, value) ->
                    append(key, value)
                }
            }
        ) {
            headers {
                append(HttpHeaders.Authorization, getMobileClientAuth())
                append(HttpHeaders.ContentType, "application/x-www-form-urlencoded")
                append(HttpHeaders.AcceptLanguage, languageManager.getCurrentLanguage().code)
            }
        }.body()
    }

    /**
     * Refresh access token
     */
    suspend fun refreshToken(request: RefreshTokenRequest): LoginResponse {
        val baseUrl = getCurrentSsoUrl()
        val formParams = toMapOfString(request)

        return httpClient.submitForm(
            url = buildUrl(baseUrl, LOGIN_ENDPOINT),
            formParameters = parameters {
                formParams.forEach { (key, value) ->
                    append(key, value)
                }
            }
        ) {
            headers {
                append(HttpHeaders.Authorization, getMobileClientAuth())
                append(HttpHeaders.ContentType, "application/x-www-form-urlencoded")
                append(HttpHeaders.AcceptLanguage, languageManager.getCurrentLanguage().code)
            }
        }.body()
    }

    /**
     * Get user profile information
     */
    suspend fun getUserProfile(accessToken: String): User {
        val baseUrl = getCurrentSsoUrl()
        return httpClient.get(buildUrl(baseUrl, USER_PROFILE_ENDPOINT)) {
            header(HttpHeaders.Authorization, "Bearer $accessToken")
            header(HttpHeaders.AcceptLanguage, languageManager.getCurrentLanguage().code)
        }.body()
    }

    private suspend fun getCurrentSsoUrl(): String {
        val dynamicUrl = dynamicUrlManager.getServerUrl()
        return dynamicUrl ?: dynamicUrlManager.getFallbackSsoUrl()
    }

    private fun buildUrl(baseUrl: String, endpoint: String): String {
        return if (baseUrl.endsWith("/")) {
            baseUrl + endpoint
        } else {
            "$baseUrl/$endpoint"
        }
    }
}