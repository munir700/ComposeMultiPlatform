package app.shared.mobile.domain.config

import kotlinx.serialization.Serializable

@Serializable
data class LinkItem(
    val name: String,
    val value: String
)