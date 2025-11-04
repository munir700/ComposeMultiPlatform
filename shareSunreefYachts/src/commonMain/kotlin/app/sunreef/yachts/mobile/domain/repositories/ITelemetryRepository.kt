package app.sunreef.yachts.mobile.domain.repositories

import app.sunreef.yachts.mobile.domain.models.PerformanceMetrics
import app.sunreef.yachts.mobile.domain.models.SystemTelemetry
import kotlinx.coroutines.flow.Flow

interface ITelemetryRepository {
    /**
     * Get system telemetry
     */
    suspend fun getTelemetry(systemId: String): Result<SystemTelemetry>

    /**
     * Stream telemetry data
     */
    fun streamTelemetry(systemId: String): Flow<SystemTelemetry>

    /**
     * Get performance metrics
     */
    suspend fun getPerformanceMetrics(): Result<PerformanceMetrics>

    /**
     * Stream performance metrics
     */
    fun streamPerformanceMetrics(): Flow<PerformanceMetrics>
}
