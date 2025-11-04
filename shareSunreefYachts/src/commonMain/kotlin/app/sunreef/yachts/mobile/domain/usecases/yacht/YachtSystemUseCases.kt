package app.sunreef.yachts.mobile.domain.usecases.yacht

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*

/**
 * Use cases for general yacht system operations
 */

class GetYachtSystemsUseCase(
    private val yachtSystemRepository: IYachtSystemRepository
) {
    suspend operator fun invoke(): Result<List<YachtSystem>> {
        return try {
            yachtSystemRepository.getYachtSystems()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

