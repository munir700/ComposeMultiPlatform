package app.shared.mobile.domain.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("access_token")
    val accessToken: String? = null,
    @SerialName("refresh_token")
    val refreshToken: String? = null,
    @SerialName("expires_in")
    val expiresIn: Int = 0,
    @SerialName("token_type")
    val tokenType: String? = null,
    val scope: String? = null,
    @SerialName("id_token")
    val idToken: String? = null,
    val errors: String? = null,
    @SerialName("error_description")
    val errorDescription: String? = null
)

@Serializable
data class RefreshTokenRequest(
    val grant_type: String = "refresh_token",
    val refresh_token: String
)