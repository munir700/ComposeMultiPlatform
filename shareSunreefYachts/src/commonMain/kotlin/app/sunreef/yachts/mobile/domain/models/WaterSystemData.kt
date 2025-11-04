package app.sunreef.yachts.mobile.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class WaterSystemData(
    val freshWaterLevel: Float,
    val greyWaterLevel: Float,
    val blackWaterLevel: Float,
    val bilgeWaterLevel: Float,
    val waterPumpStatus: PumpStatus,
    val desalinationStatus: DesalinationStatus,
    val timestamp: Long
)