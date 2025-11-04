package app.sunreef.yachts.mobile.domain.models

import kotlinx.serialization.Serializable

// ==================== Engine System Models ====================

@Serializable
data class EngineData(
    val engineId: String,
    val engineName: String,
    val rpmCurrent: Int,
    val rpmMax: Int,
    val temperature: Float,
    val oilPressure: Float,
    val fuelFlow: Float,
    val fuelLevel: Float,
    val runningHours: Long,
    val status: EngineStatus,
    val alarms: List<String> = emptyList(),
    val timestamp: Long
)
