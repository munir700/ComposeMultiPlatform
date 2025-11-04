package app.sunreef.yachts.mobile.domain.usecases.protocol

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*
import kotlinx.coroutines.flow.Flow

/**
 * Use cases for protocol operations (NMEA2000, Modbus, MQTT)
 */

class ProcessNMEA2000FrameUseCase(
    private val protocolRepository: IProtocolRepository
) {
    suspend operator fun invoke(frame: NMEA2000Frame): Result<Boolean> {
        return try {
            protocolRepository.processNMEA2000Frame(frame)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class StreamNMEA2000DataUseCase(
    private val protocolRepository: IProtocolRepository
) {
    operator fun invoke(): Flow<NMEA2000Frame> {
        return protocolRepository.streamNMEA2000Data()
    }
}

class ReadModbusRegisterUseCase(
    private val protocolRepository: IProtocolRepository
) {
    suspend operator fun invoke(address: Int): Result<ModbusRegisterValue> {
        return try {
            protocolRepository.readModbusRegister(address)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class WriteModbusRegisterUseCase(
    private val protocolRepository: IProtocolRepository
) {
    suspend operator fun invoke(address: Int, value: Int): Result<Boolean> {
        return try {
            protocolRepository.writeModbusRegister(address, value)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class PublishMQTTMessageUseCase(
    private val protocolRepository: IProtocolRepository
) {
    suspend operator fun invoke(message: MQTTMessage): Result<Boolean> {
        return try {
            protocolRepository.publishMQTTMessage(message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

