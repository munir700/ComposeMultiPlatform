package app.sunreef.yachts.mobile.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class SecurityData(
    val alarmStatus: AlarmStatus,
    val doorsLocked: Boolean,
    val windowsLocked: Boolean,
    val cameraStatus: List<CameraStatus> = emptyList(),
    val motionDetected: Boolean,
    val intrusionAlert: Boolean,
    val fireAlarm: Boolean,
    val timestamp: Long
)