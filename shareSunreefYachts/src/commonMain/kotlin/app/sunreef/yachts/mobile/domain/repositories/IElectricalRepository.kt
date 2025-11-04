package app.sunreef.yachts.mobile.domain.repositories

import app.sunreef.yachts.mobile.domain.models.ElectricalData
import kotlinx.coroutines.flow.Flow

interface IElectricalRepository {
    /**
     * Get current electrical data
     */
    suspend fun getElectricalData(): Result<ElectricalData>

    /**
     * Stream real-time electrical data
     */
    fun streamElectricalData(): Flow<ElectricalData>

    /**
     * Get battery history
     */
    suspend fun getBatteryHistory(hours: Int): Result<List<ElectricalData>>

    /**
     * Control inverter
     */
    suspend fun controlInverter(command: String): Result<Boolean>
}
