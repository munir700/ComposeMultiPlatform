package app.sunreef.yachts.mobile.domain.repositories

import app.sunreef.yachts.mobile.domain.models.WaterSystemData
import kotlinx.coroutines.flow.Flow

interface IWaterSystemRepository {
    /**
     * Get current water system data
     */
    suspend fun getWaterSystemData(): Result<WaterSystemData>

    /**
     * Stream real-time water system data
     */
    fun streamWaterSystemData(): Flow<WaterSystemData>

    /**
     * Control water pump
     */
    suspend fun controlPump(pumpType: String, command: String): Result<Boolean>

    /**
     * Start/stop desalination
     */
    suspend fun controlDesalination(command: String): Result<Boolean>
}
