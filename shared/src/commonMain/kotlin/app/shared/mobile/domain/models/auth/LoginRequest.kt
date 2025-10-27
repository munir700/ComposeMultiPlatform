package app.shared.mobile.domain.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val grant_type: String = "password",
    val username: String,
    val password: String,
    val scope: String = "",
    val answer: String = ""
)