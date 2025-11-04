package app.sunreef.yachts.mobile.domain.repositories

import app.sunreef.yachts.mobile.domain.models.EngineData
import kotlinx.coroutines.flow.Flow

interface IEngineRepository {
    /**
     * Get current engine data
     */
    suspend fun getEngineData(engineId: String): Result<EngineData>

    /**
     * Stream real-time engine data
     */
    fun streamEngineData(engineId: String): Flow<EngineData>

    /**
     * Get engine history
     */
    suspend fun getEngineHistory(engineId: String, hours: Int): Result<List<EngineData>>

    /**
     * Start/stop engine (remote control)
     */
    suspend fun controlEngine(engineId: String, command: String): Result<Boolean>
}
