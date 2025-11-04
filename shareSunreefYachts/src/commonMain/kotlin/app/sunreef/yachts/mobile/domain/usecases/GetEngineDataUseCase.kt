package app.sunreef.yachts.mobile.domain.usecases

import app.sunreef.yachts.mobile.domain.models.EngineData
import app.sunreef.yachts.mobile.domain.repositories.IEngineRepository

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