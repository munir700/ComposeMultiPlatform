package app.sunreef.yachts.mobile.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class ElectricalData(
    val batteryVoltage: Float,
    val batteryAmperage: Float,
    val batteryCapacity: Float,
    val alternatorVoltage: Float,
    val alternatorAmperage: Float,
    val inverterStatus: InverterStatus,
    val inverterOutputVoltage: Float,
    val solarPanelVoltage: Float,
    val solarPanelAmperage: Float,
    val timestamp: Long
)