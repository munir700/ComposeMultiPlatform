package app.sunreef.yachts.mobile.di

import app.sunreef.yachts.mobile.domain.usecases.*
import org.koin.core.module.dsl.factory
import org.koin.dsl.module

/**
 * Domain Layer DI Module
 * Provides all use cases and business logic
 * Follows the shared module pattern
 */

val domainsModule = module {
    // ==================== Use Cases - Yacht System ====================
    factory { GetYachtSystemsUseCase(get()) }

    // ==================== Use Cases - Engine ====================
    factory { GetEngineDataUseCase(get()) }
    factory { StreamEngineDataUseCase(get()) }
    factory { StartEngineUseCase(get()) }
    factory { StopEngineUseCase(get()) }

    // ==================== Use Cases - Navigation ====================
    factory { GetCurrentPositionUseCase(get()) }
    factory { StreamNavigationDataUseCase(get()) }
    factory { SetDestinationUseCase(get()) }
    factory { GetActiveRouteUseCase(get()) }
    factory { SaveRouteUseCase(get()) }

    // ==================== Use Cases - Electrical ====================
    factory { GetElectricalDataUseCase(get()) }
    factory { StreamElectricalDataUseCase(get()) }

    // ==================== Use Cases - Water ====================
    factory { GetWaterSystemDataUseCase(get()) }
    factory { StreamWaterSystemDataUseCase(get()) }

    // ==================== Use Cases - Climate ====================
    factory { GetClimateDataUseCase(get()) }
    factory { StreamClimateDataUseCase(get()) }
    factory { SetTemperatureUseCase(get()) }

    // ==================== Use Cases - Security ====================
    factory { GetSecurityDataUseCase(get()) }
    factory { StreamSecurityDataUseCase(get()) }
    factory { ArmAlarmUseCase(get()) }
    factory { LockDoorsUseCase(get()) }
    factory { UnlockDoorsUseCase(get()) }

    // ==================== Use Cases - Remote Control ====================
    factory { SendRemoteCommandUseCase(get()) }
    factory { GetCommandStatusUseCase(get()) }
    factory { GetCommandHistoryUseCase(get()) }

    // ==================== Use Cases - Alerts ====================
    factory { GetActiveAlertsUseCase(get()) }
    factory { StreamAlertsUseCase(get()) }
    factory { AcknowledgeAlertUseCase(get()) }

    // ==================== Use Cases - Telemetry ====================
    factory { GetPerformanceMetricsUseCase(get()) }
    factory { StreamPerformanceMetricsUseCase(get()) }

    // ==================== Use Cases - Protocols ====================
    factory { ProcessNMEA2000FrameUseCase(get()) }
    factory { StreamNMEA2000DataUseCase(get()) }
    factory { ReadModbusRegisterUseCase(get()) }
    factory { WriteModbusRegisterUseCase(get()) }
    factory { PublishMQTTMessageUseCase(get()) }

    // ==================== Use Cases - Connectivity ====================
    factory { GetConnectedDevicesUseCase(get()) }
    factory { StreamConnectedDevicesUseCase(get()) }
}

