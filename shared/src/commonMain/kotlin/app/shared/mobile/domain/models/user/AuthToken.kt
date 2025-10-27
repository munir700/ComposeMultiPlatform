package app.shared.mobile.domain.models.user


import app.shared.mobile.domain.parsers.ClaimsSerializer
import io.ktor.util.decodeBase64String
import kmp.core.mobile.utils.extensions.orFalse
import kmp.core.mobile.utils.extensions.secondOrNull
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class AuthToken(
    @SerialName("access_token")
    val accessToken: String? = null,
    @SerialName("expires_in")
    val expiresIn: Int? = null,
    @SerialName("refresh_token")
    val refreshToken: String? = null,
    @SerialName("token_type")
    val tokenType: String? = null,
) {
    var claims: Claims? = null
        private set

    init {
        claims = setClaims()
    }

    // Extract claims from access token
    private fun setClaims(): Claims? {
        if (accessToken.isNullOrBlank())
            return null
        val tokenParts = accessToken.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
        return tokenParts.secondOrNull()?.let { encodedClaims ->
            createClaims(encodedClaims.decodeBase64String())
        }
    }

    private fun createClaims(decodedClaims: String): Claims {
        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString(ClaimsSerializer, decodedClaims)
    }


    fun isSessionExpired(securityStamp: String?): Boolean {
        return claims?.isSessionExpired(securityStamp).orFalse()
    }

}