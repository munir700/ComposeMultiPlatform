package app.sunreef.yachts.mobile.domain.repositories

import app.sunreef.yachts.mobile.domain.models.ClimateData
import kotlinx.coroutines.flow.Flow

interface IClimateRepository {
    /**
     * Get current climate data
     */
    suspend fun getClimateData(): Result<ClimateData>

    /**
     * Stream real-time climate data
     */
    fun streamClimateData(): Flow<ClimateData>

    /**
     * Set target temperature
     */
    suspend fun setTargetTemperature(temperature: Float): Result<Boolean>

    /**
     * Set HVAC mode
     */
    suspend fun setHVACMode(mode: String): Result<Boolean>
}
