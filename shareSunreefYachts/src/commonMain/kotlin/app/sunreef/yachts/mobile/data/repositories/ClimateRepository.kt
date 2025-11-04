package app.sunreef.yachts.mobile.data.repositories

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class ClimateRepository : IClimateRepository {

    @OptIn(ExperimentalTime::class)
    val currentTimeMillis = Clock.System.now().toEpochMilliseconds()
    override suspend fun getClimateData(): Result<ClimateData> {
        return try {
            Result.success(
                ClimateData(
                    cabinTemperature = 22.5f,
                    targetTemperature = 23f,
                    acStatus = ClimateStatus.COOLING,
                    heatingStatus = ClimateStatus.OFF,
                    humidity = 55f,
                    airQuality = 90f,
                    ventilationStatus = VentilationStatus.AUTO,
                    timestamp = currentTimeMillis
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun streamClimateData(): Flow<ClimateData> {
        return flow {
            while (true) {
                val data = getClimateData().getOrNull()
                if (data != null) emit(data)
                kotlinx.coroutines.delay(2000)
            }
        }
    }

    override suspend fun setTargetTemperature(temperature: Float): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun setHVACMode(mode: String): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}