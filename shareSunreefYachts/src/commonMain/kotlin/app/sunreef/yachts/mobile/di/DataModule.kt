package app.sunreef.yachts.mobile.di
import app.sunreef.yachts.mobile.data.repositories.*
import app.sunreef.yachts.mobile.domain.repositories.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
/**
 * Data Layer DI Module
 * Registers all repository implementations
 * Each repository is focused on a single system responsibility
 */
val dataModule = module {
    // Yacht System Repository
    singleOf<IYachtSystemRepository> { YachtSystemRepositoryImpl() }
    // Engine Repository
    singleOf<IEngineRepository> { EngineRepositoryImpl() }
    // Navigation Repository
    singleOf<INavigationRepository> { NavigationRepositoryImpl() }
    // Electrical Repository
    singleOf<IElectricalRepository> { ElectricalRepositoryImpl() }
    // Water System Repository
    singleOf<IWaterSystemRepository> { WaterSystemRepositoryImpl() }
    // Climate Repository
    singleOf<IClimateRepository> { ClimateRepositoryImpl() }
    // Security Repository
    singleOf<ISecurityRepository> { SecurityRepositoryImpl() }
    // Remote Control Repository
    singleOf<IRemoteControlRepository> { RemoteControlRepositoryImpl() }
    // Alert Repository
    singleOf<IAlertRepository> { AlertRepositoryImpl() }
    // Telemetry Repository
    singleOf<ITelemetryRepository> { TelemetryRepositoryImpl() }
    // Protocol Repository
    singleOf<IProtocolRepository> { ProtocolRepositoryImpl() }
    // Connectivity Repository
    singleOf<IConnectivityRepository> { ConnectivityRepositoryImpl() }
}
