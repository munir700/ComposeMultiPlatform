package app.sunreef.yachts.mobile.domain.usecases

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*
import kotlinx.coroutines.flow.Flow

/**
 * Use cases for yacht system operations
 * Orchestrates business logic across repositories
 */

// ==================== Engine Use Cases ====================



class StreamEngineDataUseCase(
    private val engineRepository: IEngineRepository
) {
    operator fun invoke(engineId: String): Flow<EngineData> {
        return engineRepository.streamEngineData(engineId)
    }
}

class StartEngineUseCase(
    private val engineRepository: IEngineRepository
) {
    suspend operator fun invoke(engineId: String): Result<Boolean> {
        return try {
            engineRepository.controlEngine(engineId, "START")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class StopEngineUseCase(
    private val engineRepository: IEngineRepository
) {
    suspend operator fun invoke(engineId: String): Result<Boolean> {
        return try {
            engineRepository.controlEngine(engineId, "STOP")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// ==================== Navigation Use Cases ====================

class GetCurrentPositionUseCase(
    private val navigationRepository: INavigationRepository
) {
    suspend operator fun invoke(): Result<NavigationData> {
        return try {
            navigationRepository.getCurrentPosition()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class StreamNavigationDataUseCase(
    private val navigationRepository: INavigationRepository
) {
    operator fun invoke(): Flow<NavigationData> {
        return navigationRepository.streamNavigationData()
    }
}

class SetDestinationUseCase(
    private val navigationRepository: INavigationRepository
) {
    suspend operator fun invoke(waypoint: Waypoint): Result<Boolean> {
        return try {
            navigationRepository.setDestination(waypoint)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class GetActiveRouteUseCase(
    private val navigationRepository: INavigationRepository
) {
    suspend operator fun invoke(): Result<Route?> {
        return try {
            navigationRepository.getActiveRoute()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class SaveRouteUseCase(
    private val navigationRepository: INavigationRepository
) {
    suspend operator fun invoke(route: Route): Result<String> {
        return try {
            navigationRepository.saveRoute(route)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// ==================== Electrical System Use Cases ====================

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

// ==================== Water System Use Cases ====================

class GetWaterSystemDataUseCase(
    private val waterSystemRepository: IWaterSystemRepository
) {
    suspend operator fun invoke(): Result<WaterSystemData> {
        return try {
            waterSystemRepository.getWaterSystemData()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class StreamWaterSystemDataUseCase(
    private val waterSystemRepository: IWaterSystemRepository
) {
    operator fun invoke(): Flow<WaterSystemData> {
        return waterSystemRepository.streamWaterSystemData()
    }
}

// ==================== Climate Control Use Cases ====================

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

// ==================== Security Use Cases ====================

class GetSecurityDataUseCase(
    private val securityRepository: ISecurityRepository
) {
    suspend operator fun invoke(): Result<SecurityData> {
        return try {
            securityRepository.getSecurityData()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class StreamSecurityDataUseCase(
    private val securityRepository: ISecurityRepository
) {
    operator fun invoke(): Flow<SecurityData> {
        return securityRepository.streamSecurityData()
    }
}

class ArmAlarmUseCase(
    private val securityRepository: ISecurityRepository
) {
    suspend operator fun invoke(mode: String): Result<Boolean> {
        return try {
            securityRepository.controlAlarm(mode)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class LockDoorsUseCase(
    private val securityRepository: ISecurityRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        return try {
            securityRepository.controlDoors(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class UnlockDoorsUseCase(
    private val securityRepository: ISecurityRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        return try {
            securityRepository.controlDoors(false)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// ==================== Remote Control Use Cases ====================

class SendRemoteCommandUseCase(
    private val remoteControlRepository: IRemoteControlRepository
) {
    suspend operator fun invoke(command: RemoteControlCommand): Result<String> {
        return try {
            remoteControlRepository.sendCommand(command)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class GetCommandStatusUseCase(
    private val remoteControlRepository: IRemoteControlRepository
) {
    suspend operator fun invoke(commandId: String): Result<CommandStatus> {
        return try {
            remoteControlRepository.getCommandStatus(commandId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class GetCommandHistoryUseCase(
    private val remoteControlRepository: IRemoteControlRepository
) {
    suspend operator fun invoke(limit: Int = 50): Result<List<RemoteControlCommand>> {
        return try {
            remoteControlRepository.getCommandHistory(limit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// ==================== Alert Use Cases ====================

class GetActiveAlertsUseCase(
    private val alertRepository: IAlertRepository
) {
    suspend operator fun invoke(): Result<List<SystemAlert>> {
        return try {
            alertRepository.getActiveAlerts()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class StreamAlertsUseCase(
    private val alertRepository: IAlertRepository
) {
    operator fun invoke(): Flow<SystemAlert> {
        return alertRepository.streamAlerts()
    }
}

class AcknowledgeAlertUseCase(
    private val alertRepository: IAlertRepository
) {
    suspend operator fun invoke(alertId: String): Result<Boolean> {
        return try {
            alertRepository.acknowledgeAlert(alertId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// ==================== Telemetry Use Cases ====================

class GetPerformanceMetricsUseCase(
    private val telemetryRepository: ITelemetryRepository
) {
    suspend operator fun invoke(): Result<PerformanceMetrics> {
        return try {
            telemetryRepository.getPerformanceMetrics()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class StreamPerformanceMetricsUseCase(
    private val telemetryRepository: ITelemetryRepository
) {
    operator fun invoke(): Flow<PerformanceMetrics> {
        return telemetryRepository.streamPerformanceMetrics()
    }
}

// ==================== Protocol Use Cases ====================

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

// ==================== Connectivity Use Cases ====================

class GetConnectedDevicesUseCase(
    private val connectivityRepository: IConnectivityRepository
) {
    suspend operator fun invoke(): Result<List<DeviceConnection>> {
        return try {
            connectivityRepository.getConnectedDevices()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class StreamConnectedDevicesUseCase(
    private val connectivityRepository: IConnectivityRepository
) {
    operator fun invoke(): Flow<List<DeviceConnection>> {
        return connectivityRepository.streamConnectedDevices()
    }
}

// ==================== Yacht System Use Cases ====================

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
