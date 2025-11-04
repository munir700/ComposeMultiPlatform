package app.sunreef.yachts.mobile.data.repositories

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class ElectricalRepository : IElectricalRepository {

    @OptIn(ExperimentalTime::class)
    val currentTimeMillis = Clock.System.now().toEpochMilliseconds()
    override suspend fun getElectricalData(): Result<ElectricalData> {
        return try {
            Result.success(
                ElectricalData(
                    batteryVoltage = 24.5f,
                    batteryAmperage = 50f,
                    batteryCapacity = 85f,
                    alternatorVoltage = 28f,
                    alternatorAmperage = 100f,
                    inverterStatus = InverterStatus.INVERTING,
                    inverterOutputVoltage = 230f,
                    solarPanelVoltage = 48f,
                    solarPanelAmperage = 20f,
                    timestamp = currentTimeMillis
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun streamElectricalData(): Flow<ElectricalData> {
        return flow {
            while (true) {
                val data = getElectricalData().getOrNull()
                if (data != null) emit(data)
                kotlinx.coroutines.delay(500)
            }
        }
    }

    override suspend fun getBatteryHistory(hours: Int): Result<List<ElectricalData>> {
        return try {
            Result.success(emptyList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun controlInverter(command: String): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
