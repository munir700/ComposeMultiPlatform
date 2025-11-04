package app.sunreef.yachts.mobile.data.repositories

import app.sunreef.yachts.mobile.domain.models.MQTTMessage
import app.sunreef.yachts.mobile.domain.models.ModbusDataType
import app.sunreef.yachts.mobile.domain.models.ModbusRegisterValue
import app.sunreef.yachts.mobile.domain.models.NMEA2000Frame
import app.sunreef.yachts.mobile.domain.repositories.IProtocolRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class ProtocolRepository : IProtocolRepository {

    @OptIn(ExperimentalTime::class)
    val currentTimeMillis = Clock.System.now().toEpochMilliseconds()
    override suspend fun processNMEA2000Frame(frame: NMEA2000Frame): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun streamNMEA2000Data(): Flow<NMEA2000Frame> {
        return flow { }
    }

    override suspend fun readModbusRegister(address: Int): Result<ModbusRegisterValue> {
        return try {
            Result.success(
                ModbusRegisterValue(
                    address = address,
                    value = 0,
                    dataType = ModbusDataType.HOLDING_REGISTER,
                    timestamp = currentTimeMillis
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun writeModbusRegister(address: Int, value: Int): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun publishMQTTMessage(message: MQTTMessage): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun subscribeMQTTTopic(topic: String): Flow<MQTTMessage> {
        return flow { }
    }
}