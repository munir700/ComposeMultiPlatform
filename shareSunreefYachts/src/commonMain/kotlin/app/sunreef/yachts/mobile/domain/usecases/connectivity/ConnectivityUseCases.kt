package app.sunreef.yachts.mobile.domain.usecases.connectivity

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*
import kotlinx.coroutines.flow.Flow

/**
 * Use cases for connectivity operations
 */

class GetConnectedDevicesUseCase(
    private val connectivityRepository: IConnectivityRepository
) {
    suspend operator fun invoke(): Result<List<DeviceConnection>> {
        return try {
            connectivityRepository.getConnectedDevices()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class StreamConnectedDevicesUseCase(
    private val connectivityRepository: IConnectivityRepository
) {
    operator fun invoke(): Flow<List<DeviceConnection>> {
        return connectivityRepository.streamConnectedDevices()
    }
}

