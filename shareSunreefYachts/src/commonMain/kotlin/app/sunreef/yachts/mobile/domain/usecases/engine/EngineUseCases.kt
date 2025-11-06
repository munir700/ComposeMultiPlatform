package app.sunreef.yachts.mobile.domain.usecases.engine

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*
import kotlinx.coroutines.flow.Flow

/**
 * Use cases for engine operations
 */

class GetEngineDataUseCase(
    private val engineRepository: IEngineRepository
) {
    suspend operator fun invoke(engineId: String): Result<EngineData> {
        return try {
            engineRepository.getEngineData(engineId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class StreamEngineDataUseCase(
    private val engineRepository: IEngineRepository
) {
    operator fun invoke(engineId: String): Flow<EngineData> {
        return engineRepository.streamEngineData(engineId)
    }
}

class StartEngineUseCase(
    private val engineRepository: IEngineRepository
) {
    suspend operator fun invoke(engineId: String): Result<Boolean> {
        return try {
            engineRepository.controlEngine(engineId, "START")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class StopEngineUseCase(
    private val engineRepository: IEngineRepository
) {
    suspend operator fun invoke(engineId: String): Result<Boolean> {
        return try {
            engineRepository.controlEngine(engineId, "STOP")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
