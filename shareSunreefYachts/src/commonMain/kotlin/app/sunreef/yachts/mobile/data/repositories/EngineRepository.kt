package app.sunreef.yachts.mobile.data.repositories

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class EngineRepository : IEngineRepository {
    @OptIn(ExperimentalTime::class)
    val currentTimeMillis = Clock.System.now().toEpochMilliseconds()
    override suspend fun getEngineData(engineId: String): Result<EngineData> {
        return try {
            Result.success(
                EngineData(
                    engineId = engineId,
                    engineName = "Main Engine",
                    rpmCurrent = 1200,
                    rpmMax = 3000,
                    temperature = 85f,
                    oilPressure = 4.5f,
                    fuelFlow = 25.5f,
                    fuelLevel = 85f,
                    runningHours = 5000L,
                    status = EngineStatus.RUNNING,
                    timestamp = currentTimeMillis
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun streamEngineData(engineId: String): Flow<EngineData> {
        return flow {
            while (true) {
                val data = getEngineData(engineId).getOrNull()
                if (data != null) emit(data)
                kotlinx.coroutines.delay(500)
            }
        }
    }

    override suspend fun getEngineHistory(engineId: String, hours: Int): Result<List<EngineData>> {
        return try {
            Result.success(emptyList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun controlEngine(engineId: String, command: String): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
