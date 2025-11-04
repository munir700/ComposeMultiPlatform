package app.sunreef.yachts.mobile.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class SystemTelemetry(
    val systemId: String,
    val systemType: SystemType,
    val metrics: Map<String, Float>,
    val timestamp: Long,
    val interval: Long
)