package app.sunreef.yachts.mobile.domain.usecases.climate

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*
import kotlinx.coroutines.flow.Flow

/**
 * Use cases for climate control operations
 */

class GetClimateDataUseCase(
    private val climateRepository: IClimateRepository
) {
    suspend operator fun invoke(): Result<ClimateData> {
        return try {
            climateRepository.getClimateData()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class StreamClimateDataUseCase(
    private val climateRepository: IClimateRepository
) {
    operator fun invoke(): Flow<ClimateData> {
        return climateRepository.streamClimateData()
    }
}

class SetTemperatureUseCase(
    private val climateRepository: IClimateRepository
) {
    suspend operator fun invoke(temperature: Float): Result<Boolean> {
        return try {
            if (temperature < 16f || temperature > 30f) {
                return Result.failure(IllegalArgumentException("Temperature must be between 16°C and 30°C"))
            }
            climateRepository.setTargetTemperature(temperature)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

