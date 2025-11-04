package app.sunreef.yachts.mobile.data.repositories

import app.sunreef.yachts.mobile.domain.models.DeviceConnection
import app.sunreef.yachts.mobile.domain.repositories.IConnectivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ConnectivityRepository :
    IConnectivityRepository {
    override suspend fun getConnectedDevices(): Result<List<DeviceConnection>> {
        return try {
            Result.success(emptyList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun streamConnectedDevices(): Flow<List<DeviceConnection>> {
        return flow {
            while (true) {
                val devices = getConnectedDevices().getOrNull() ?: emptyList()
                emit(devices)
                kotlinx.coroutines.delay(2000)
            }
        }
    }

    override suspend fun connectToDevice(deviceId: String): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun disconnectFromDevice(deviceId: String): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getConnectionStatistics(): Result<Map<String, Any>> {
        return try {
            Result.success(emptyMap())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}