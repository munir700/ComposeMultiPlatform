package app.sunreef.yachts.mobile.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class PerformanceMetrics(
    val cpuUsage: Float,
    val memoryUsage: Float,
    val networkLatency: Float,
    val batteryHealth: Float,
    val signalStrength: Float,
    val timestamp: Long
)