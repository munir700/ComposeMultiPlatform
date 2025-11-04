package app.sunreef.yachts.mobile.domain.repositories

import app.sunreef.yachts.mobile.domain.models.CameraStatus
import app.sunreef.yachts.mobile.domain.models.SecurityData
import kotlinx.coroutines.flow.Flow

interface ISecurityRepository {
    /**
     * Get security status
     */
    suspend fun getSecurityData(): Result<SecurityData>

    /**
     * Stream real-time security data
     */
    fun streamSecurityData(): Flow<SecurityData>

    /**
     * Arm/disarm alarm
     */
    suspend fun controlAlarm(command: String): Result<Boolean>

    /**
     * Lock/unlock doors
     */
    suspend fun controlDoors(locked: Boolean): Result<Boolean>

    /**
     * Get camera feed status
     */
    suspend fun getCameraStatus(): Result<List<CameraStatus>>
}
