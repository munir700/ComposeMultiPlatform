package app.sunreef.yachts.mobile.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class ClimateData(
    val cabinTemperature: Float,
    val targetTemperature: Float,
    val acStatus: ClimateStatus,
    val heatingStatus: ClimateStatus,
    val humidity: Float,
    val airQuality: Float,
    val ventilationStatus: VentilationStatus,
    val timestamp: Long
)