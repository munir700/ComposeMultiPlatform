package app.shared.mobile.domain.config

import kotlinx.serialization.Serializable

@Serializable
data class VersionResponse(
    val version: String? = null,
    val links: List<LinkItem> = emptyList()
)