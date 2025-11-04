package app.sunreef.yachts.mobile.domain.repositories

import app.sunreef.yachts.mobile.domain.models.YachtSystem
import kotlinx.coroutines.flow.Flow

interface IYachtSystemRepository {
    /**
     * Get all yacht systems
     */
    suspend fun getYachtSystems(): Result<List<YachtSystem>>

    /**
     * Get specific yacht system by ID
     */
    suspend fun getYachtSystem(systemId: String): Result<YachtSystem>

    /**
     * Monitor yacht systems in real-time
     */
    fun monitorYachtSystems(): Flow<List<YachtSystem>>
}
