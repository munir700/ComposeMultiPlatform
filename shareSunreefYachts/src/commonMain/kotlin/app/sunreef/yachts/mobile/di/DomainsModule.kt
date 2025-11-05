package app.sunreef.yachts.mobile.di

import app.sunreef.yachts.mobile.domain.usecases.alert.AcknowledgeAlertUseCase
import app.sunreef.yachts.mobile.domain.usecases.alert.GetActiveAlertsUseCase
import app.sunreef.yachts.mobile.domain.usecases.alert.StreamAlertsUseCase
import app.sunreef.yachts.mobile.domain.usecases.climate.GetClimateDataUseCase
import app.sunreef.yachts.mobile.domain.usecases.climate.SetTemperatureUseCase
import app.sunreef.yachts.mobile.domain.usecases.climate.StreamClimateDataUseCase
import app.sunreef.yachts.mobile.domain.usecases.connectivity.GetConnectedDevicesUseCase
import app.sunreef.yachts.mobile.domain.usecases.connectivity.StreamConnectedDevicesUseCase
import app.sunreef.yachts.mobile.domain.usecases.electrical.GetElectricalDataUseCase
import app.sunreef.yachts.mobile.domain.usecases.electrical.StreamElectricalDataUseCase
import app.sunreef.yachts.mobile.domain.usecases.engine.GetEngineDataUseCase
import app.sunreef.yachts.mobile.domain.usecases.engine.StartEngineUseCase
import app.sunreef.yachts.mobile.domain.usecases.engine.StopEngineUseCase
import app.sunreef.yachts.mobile.domain.usecases.engine.StreamEngineDataUseCase
import app.sunreef.yachts.mobile.domain.usecases.navigation.GetActiveRouteUseCase
import app.sunreef.yachts.mobile.domain.usecases.navigation.GetCurrentPositionUseCase
import app.sunreef.yachts.mobile.domain.usecases.navigation.SaveRouteUseCase
import app.sunreef.yachts.mobile.domain.usecases.navigation.SetDestinationUseCase
import app.sunreef.yachts.mobile.domain.usecases.navigation.StreamNavigationDataUseCase
import app.sunreef.yachts.mobile.domain.usecases.protocol.ProcessNMEA2000FrameUseCase
import app.sunreef.yachts.mobile.domain.usecases.protocol.PublishMQTTMessageUseCase
import app.sunreef.yachts.mobile.domain.usecases.protocol.ReadModbusRegisterUseCase
import app.sunreef.yachts.mobile.domain.usecases.protocol.StreamNMEA2000DataUseCase
import app.sunreef.yachts.mobile.domain.usecases.protocol.WriteModbusRegisterUseCase
import app.sunreef.yachts.mobile.domain.usecases.remotecontrol.GetCommandHistoryUseCase
import app.sunreef.yachts.mobile.domain.usecases.remotecontrol.GetCommandStatusUseCase
import app.sunreef.yachts.mobile.domain.usecases.remotecontrol.SendRemoteCommandUseCase
import app.sunreef.yachts.mobile.domain.usecases.security.ArmAlarmUseCase
import app.sunreef.yachts.mobile.domain.usecases.security.GetSecurityDataUseCase
import app.sunreef.yachts.mobile.domain.usecases.security.LockDoorsUseCase
import app.sunreef.yachts.mobile.domain.usecases.security.StreamSecurityDataUseCase
import app.sunreef.yachts.mobile.domain.usecases.security.UnlockDoorsUseCase
import app.sunreef.yachts.mobile.domain.usecases.telemetry.GetPerformanceMetricsUseCase
import app.sunreef.yachts.mobile.domain.usecases.telemetry.StreamPerformanceMetricsUseCase
import app.sunreef.yachts.mobile.domain.usecases.water.GetWaterSystemDataUseCase
import app.sunreef.yachts.mobile.domain.usecases.water.StreamWaterSystemDataUseCase
import app.sunreef.yachts.mobile.domain.usecases.yacht.GetYachtSystemsUseCase
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

