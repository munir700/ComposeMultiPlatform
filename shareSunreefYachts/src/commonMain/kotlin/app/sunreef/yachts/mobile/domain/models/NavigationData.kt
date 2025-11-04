package app.sunreef.yachts.mobile.domain.models

import kotlinx.serialization.Serializable

// ==================== Navigation System Models ====================

@Serializable
data class NavigationData(
    val latitude: Double,
    val longitude: Double,
    val heading: Float,
    val speed: Float,
    val depth: Float,
    val windSpeed: Float,
    val windDirection: Float,
    val courseOverGround: Float,
    val speedOverGround: Float,
    val timestamp: Long
)