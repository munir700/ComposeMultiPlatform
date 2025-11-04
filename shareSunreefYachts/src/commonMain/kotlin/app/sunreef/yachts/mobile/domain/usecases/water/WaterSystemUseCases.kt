package app.sunreef.yachts.mobile.domain.usecases.water

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*
import kotlinx.coroutines.flow.Flow

/**
 * Use cases for water system operations
 */

class GetWaterSystemDataUseCase(
    private val waterSystemRepository: IWaterSystemRepository
) {
    suspend operator fun invoke(): Result<WaterSystemData> {
        return try {
            waterSystemRepository.getWaterSystemData()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class StreamWaterSystemDataUseCase(
    private val waterSystemRepository: IWaterSystemRepository
) {
    operator fun invoke(): Flow<WaterSystemData> {
        return waterSystemRepository.streamWaterSystemData()
    }
}

