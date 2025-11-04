package app.sunreef.yachts.mobile.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class CameraStatus(
    val cameraId: String,
    val name: String,
    val isActive: Boolean,
    val recordingStatus: RecordingStatus,
    val location: String
)