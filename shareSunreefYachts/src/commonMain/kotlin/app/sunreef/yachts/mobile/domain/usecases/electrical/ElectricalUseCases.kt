package app.sunreef.yachts.mobile.domain.usecases.electrical

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*
import kotlinx.coroutines.flow.Flow

/**
 * Use cases for electrical system operations
 */

class GetElectricalDataUseCase(
    private val electricalRepository: IElectricalRepository
) {
    suspend operator fun invoke(): Result<ElectricalData> {
        return try {
            electricalRepository.getElectricalData()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class StreamElectricalDataUseCase(
    private val electricalRepository: IElectricalRepository
) {
    operator fun invoke(): Flow<ElectricalData> {
        return electricalRepository.streamElectricalData()
    }
}

