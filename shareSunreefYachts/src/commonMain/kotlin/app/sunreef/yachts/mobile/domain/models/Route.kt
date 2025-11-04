package app.sunreef.yachts.mobile.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Route(
    val id: String,
    val name: String,
    val waypoints: List<Waypoint>,
    val totalDistance: Float,
    val createdAt: Long,
    val updatedAt: Long
)
