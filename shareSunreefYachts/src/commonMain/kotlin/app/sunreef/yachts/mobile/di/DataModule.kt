package app.sunreef.yachts.mobile.di
import app.sunreef.yachts.mobile.data.repositories.*
import app.sunreef.yachts.mobile.domain.repositories.*
import org.koin.dsl.module
/**
 * Data Layer DI Module
 * Registers all repository implementations
 * Each repository is focused on a single system responsibility
 */
val dataModule = module {
    // Yacht System Repository
    single <IYachtSystemRepository> { YachtSystemRepositoryImpl() }
    // Engine Repository
    single  <IEngineRepository> { EngineRepositoryImpl() }
    // Navigation Repository
    single  <INavigationRepository> { NavigationRepositoryImpl() }
    // Electrical Repository
    single  <IElectricalRepository> { ElectricalRepositoryImpl() }
    // Water System Repository
    single  <IWaterSystemRepository> { WaterSystemRepositoryImpl() }
    // Climate Repository
    single  <IClimateRepository> { ClimateRepositoryImpl() }
    // Security Repository
    single  <ISecurityRepository> { SecurityRepositoryImpl() }
    // Remote Control Repository
    single  <IRemoteControlRepository> { RemoteControlRepositoryImpl() }
    // Alert Repository
    single  <IAlertRepository> { AlertRepositoryImpl() }
    // Telemetry Repository
    single  <ITelemetryRepository> { TelemetryRepositoryImpl() }
    // Protocol Repository
    single  <IProtocolRepository> { ProtocolRepositoryImpl() }
    // Connectivity Repository
    single  <IConnectivityRepository> { ConnectivityRepositoryImpl() }
}
