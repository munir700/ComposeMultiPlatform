package app.sunreef.yachts.mobile.domain.repositories

import app.sunreef.yachts.mobile.domain.models.DeviceConnection
import kotlinx.coroutines.flow.Flow

interface IConnectivityRepository {
    /**
     * Get connected devices
     */
    suspend fun getConnectedDevices(): Result<List<DeviceConnection>>

    /**
     * Stream device connections
     */
    fun streamConnectedDevices(): Flow<List<DeviceConnection>>

    /**
     * Connect to device
     */
    suspend fun connectToDevice(deviceId: String): Result<Boolean>

    /**
     * Disconnect from device
     */
    suspend fun disconnectFromDevice(deviceId: String): Result<Boolean>

    /**
     * Get connection statistics
     */
    suspend fun getConnectionStatistics(): Result<Map<String, Any>>
}