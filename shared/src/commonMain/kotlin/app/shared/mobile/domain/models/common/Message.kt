package app.shared.mobile.domain.models.common

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val success: Boolean = false,
    val message: String? = null
)