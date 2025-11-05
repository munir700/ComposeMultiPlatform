package app.sunreef.yachts.mobile.domain.repositories

import app.sunreef.yachts.mobile.domain.models.MQTTMessage
import app.sunreef.yachts.mobile.domain.models.ModbusRegisterValue
import kotlinx.coroutines.flow.Flow

interface IProtocolRepository {
    /**
     * Process NMEA 2000 frame
     */
    suspend fun processNMEA2000Frame(frame: NMEA2000Frame): Result<Boolean>

    /**
     * Stream NMEA 2000 data
     */
    fun streamNMEA2000Data(): Flow<NMEA2000Frame>

    /**
     * Read Modbus register
     */
    suspend fun readModbusRegister(address: Int): Result<ModbusRegisterValue>

    /**
     * Write Modbus register
     */
    suspend fun writeModbusRegister(address: Int, value: Int): Result<Boolean>

    /**
     * Publish MQTT message
     */
    suspend fun publishMQTTMessage(message: MQTTMessage): Result<Boolean>

    /**
     * Subscribe to MQTT topic
     */
    fun subscribeMQTTTopic(topic: String): Flow<MQTTMessage>
}
