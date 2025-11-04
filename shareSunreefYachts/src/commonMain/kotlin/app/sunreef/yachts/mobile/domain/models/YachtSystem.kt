package app.sunreef.yachts.mobile.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class YachtSystem(
    val id: String,
    val name: String,
    val type: SystemType,
    val status: SystemStatus,
    val lastUpdate: Long,
    val version: String
)