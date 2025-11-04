package app.sunreef.yachts.mobile.data.repositories

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class WaterSystemRepository : IWaterSystemRepository {

    @OptIn(ExperimentalTime::class)
    val currentTimeMillis = Clock.System.now().toEpochMilliseconds()
    override suspend fun getWaterSystemData(): Result<WaterSystemData> {
        return try {
            Result.success(
                WaterSystemData(
                    freshWaterLevel = 80f,
                    greyWaterLevel = 40f,
                    blackWaterLevel = 30f,
                    bilgeWaterLevel = 5f,
                    waterPumpStatus = PumpStatus.RUNNING,
                    desalinationStatus = DesalinationStatus.RUNNING,
                    timestamp = currentTimeMillis
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun streamWaterSystemData(): Flow<WaterSystemData> {
        return flow {
            while (true) {
                val data = getWaterSystemData().getOrNull()
                if (data != null) emit(data)
                kotlinx.coroutines.delay(1000)
            }
        }
    }

    override suspend fun controlPump(pumpType: String, command: String): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun controlDesalination(command: String): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}