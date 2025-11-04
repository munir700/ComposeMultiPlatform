package app.sunreef.yachts.mobile.data.repositories

import app.sunreef.yachts.mobile.domain.models.PerformanceMetrics
import app.sunreef.yachts.mobile.domain.models.SystemTelemetry
import app.sunreef.yachts.mobile.domain.models.SystemType
import app.sunreef.yachts.mobile.domain.repositories.ITelemetryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class TelemetryRepository : ITelemetryRepository {

    @OptIn(ExperimentalTime::class)
    val currentTimeMillis = Clock.System.now().toEpochMilliseconds()
    override suspend fun getTelemetry(systemId: String): Result<SystemTelemetry> {
        return try {
            Result.success(
                SystemTelemetry(
                    systemId = systemId,
                    systemType = SystemType.OTHER,
                    metrics = emptyMap(),
                    timestamp = currentTimeMillis,
                    interval = 1000
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun streamTelemetry(systemId: String): Flow<SystemTelemetry> {
        return flow {
            while (true) {
                val data = getTelemetry(systemId).getOrNull()
                if (data != null) emit(data)
                kotlinx.coroutines.delay(1000)
            }
        }
    }

    override suspend fun getPerformanceMetrics(): Result<PerformanceMetrics> {
        return try {
            Result.success(
                PerformanceMetrics(
                    cpuUsage = 45f,
                    memoryUsage = 60f,
                    networkLatency = 50f,
                    batteryHealth = 95f,
                    signalStrength = 80f,
                    timestamp = currentTimeMillis
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun streamPerformanceMetrics(): Flow<PerformanceMetrics> {
        return flow {
            while (true) {
                val data = getPerformanceMetrics().getOrNull()
                if (data != null) emit(data)
                kotlinx.coroutines.delay(2000)
            }
        }
    }
}