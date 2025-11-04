package app.sunreef.yachts.mobile.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Waypoint(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val description: String = "",
    val timestamp: Long
)