package app.sunreef.yachts.mobile.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class DeviceConnection(
    val deviceId: String,
    val deviceName: String,
    val connectionType: ConnectionType,
    val isConnected: Boolean,
    val signalStrength: Int,
    val lastConnectedAt: Long,
    val firmwareVersion: String
)
