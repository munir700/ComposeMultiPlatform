package app.sunreef.yachts.mobile.domain.usecases.telemetry

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*
import kotlinx.coroutines.flow.Flow

/**
 * Use cases for telemetry operations
 */

class GetPerformanceMetricsUseCase(
    private val telemetryRepository: ITelemetryRepository
) {
    suspend operator fun invoke(): Result<PerformanceMetrics> {
        return try {
            telemetryRepository.getPerformanceMetrics()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class StreamPerformanceMetricsUseCase(
    private val telemetryRepository: ITelemetryRepository
) {
    operator fun invoke(): Flow<PerformanceMetrics> {
        return telemetryRepository.streamPerformanceMetrics()
    }
}

