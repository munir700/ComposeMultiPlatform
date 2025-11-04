# ShareSunreefYachts Module Structure

## Module Organization

This document provides a detailed overview of the `shareSunreefYachts` module structure and how to extend it for yacht-specific features.

## Directory Layout

```
shareSunreefYachts/
├── src/
│   ├── commonMain/
│   │   ├── kotlin/app/sunreef/yachts/mobile/
│   │   │   ├── App.kt                          # Main application entry point
│   │   │   ├── di/
│   │   │   │   └── DiModule.kt                 # Common DI setup
│   │   │   ├── data/
│   │   │   │   ├── DataModule.kt               # Data layer coordinator
│   │   │   │   ├── local/                      # Local storage
│   │   │   │   │   ├── preferences/            # SharedPreferences/UserDefaults
│   │   │   │   │   └── database/               # SQLite/Realm
│   │   │   │   ├── remote/                     # Remote data sources
│   │   │   │   │   ├── api/                    # API clients
│   │   │   │   │   ├── models/                 # API response models
│   │   │   │   │   └── websocket/              # WebSocket connections
│   │   │   │   └── repositories/               # Repository implementations
│   │   │   ├── domain/
│   │   │   │   ├── DomainModule.kt             # Domain layer coordinator
│   │   │   │   ├── models/                     # Domain entities
│   │   │   │   ├── repositories/               # Repository interfaces
│   │   │   │   ├── usecases/                   # Business logic use cases
│   │   │   │   │   ├── engine/                 # Engine operation use cases
│   │   │   │   │   │   └── EngineUseCases.kt
│   │   │   │   │   ├── navigation/             # Navigation use cases
│   │   │   │   │   │   └── NavigationUseCases.kt
│   │   │   │   │   ├── electrical/             # Electrical system use cases
│   │   │   │   │   │   └── ElectricalUseCases.kt
│   │   │   │   │   ├── water/                  # Water system use cases
│   │   │   │   │   │   └── WaterSystemUseCases.kt
│   │   │   │   │   ├── climate/                # Climate control use cases
│   │   │   │   │   │   └── ClimateUseCases.kt
│   │   │   │   │   ├── security/               # Security system use cases
│   │   │   │   │   │   └── SecurityUseCases.kt
│   │   │   │   │   ├── remotecontrol/          # Remote control use cases
│   │   │   │   │   │   └── RemoteControlUseCases.kt
│   │   │   │   │   ├── alert/                  # Alert management use cases
│   │   │   │   │   │   └── AlertUseCases.kt
│   │   │   │   │   ├── telemetry/              # Telemetry use cases
│   │   │   │   │   │   └── TelemetryUseCases.kt
│   │   │   │   │   ├── protocol/               # Protocol use cases (NMEA, Modbus, MQTT)
│   │   │   │   │   │   └── ProtocolUseCases.kt
│   │   │   │   │   ├── connectivity/           # Connectivity use cases
│   │   │   │   │   │   └── ConnectivityUseCases.kt
│   │   │   │   │   └── yacht/                  # General yacht system use cases
│   │   │   │   │       └── YachtSystemUseCases.kt
│   │   │   │   └── config/                     # Configuration models
│   │   │   ├── presentation/
│   │   │   │   ├── PresentationModule.kt       # Presentation layer coordinator
│   │   │   │   ├── app/                        # Main app composition
│   │   │   │   ├── screens/                    # Screen components
│   │   │   │   ├── components/                 # Reusable UI components
│   │   │   │   │   ├── containers/             # Layout containers
│   │   │   │   │   ├── fields/                 # Input fields
│   │   │   │   │   └── controls/               # UI controls
│   │   │   │   ├── viewmodels/                 # ViewModels & UI state
│   │   │   │   │   ├── engine/                 # Engine ViewModels
│   │   │   │   │   │   └── EngineMonitoringViewModel.kt
│   │   │   │   │   ├── navigation/             # Navigation ViewModels
│   │   │   │   │   │   └── NavigationViewModel.kt
│   │   │   │   │   ├── security/               # Security ViewModels
│   │   │   │   │   │   └── SecurityViewModel.kt
│   │   │   │   │   ├── electrical/             # Electrical ViewModels
│   │   │   │   │   │   └── ElectricalViewModel.kt
│   │   │   │   │   ├── climate/                # Climate ViewModels
│   │   │   │   │   │   └── ClimateViewModel.kt
│   │   │   │   │   ├── alerts/                 # Alert ViewModels
│   │   │   │   │   │   └── AlertsViewModel.kt
│   │   │   │   │   ├── remotecontrol/          # Remote Control ViewModels
│   │   │   │   │   │   └── RemoteControlViewModel.kt
│   │   │   │   │   └── dashboard/              # Dashboard ViewModels
│   │   │   │   │       └── DashboardViewModel.kt
│   │   │   │   ├── theme/                      # Compose theme and styles
│   │   │   │   └── navigation/                 # Navigation setup (Decompose)
��   │   │   ├── constants/                      # Application constants
│   │   │   ├── utils/                          # Utility functions
│   │   │   └── initializer/                    # App initialization logic
│   │   └── composeResources/
│   │       ├── drawable/                       # Drawables (XML, PNG, SVG)
│   │       ├── font/                           # Custom fonts
│   │       └── values/                         # String resources, colors
│   │
│   ├── commonTest/
│   │   └── kotlin/app/sunreef/yachts/mobile/
│   │       ├── data/
│   │       │   └── remote/                     # API mock tests
│   │       ├── domain/                         # Use case tests
│   │       └── presentation/                   # Compose preview & UI tests
│   │
│   ├── androidMain/
│   │   ├── kotlin/app/sunreef/yachts/mobile/
│   │   │   ├── di/
│   │   │   │   └── PlatformModule.kt           # Android-specific DI
│   │   │   ├── Platform.android.kt             # Android platform utils
│   │   │   ├── location/                       # Location services
│   │   │   ├── connectivity/                   # Network connectivity
│   │   │   ├── sensors/                        # Sensor integration
│   │   │   └── permissions/                    # Permission handling
│   │   ├── AndroidManifest.xml
│   │   └── res/
│   │       ├── values/                         # Strings, colors, dimensions
│   │       ├── drawable/                       # Android-specific drawables
│   │       ├── mipmap/                         # App icons
│   │       └── layout/                         # Alternative layouts
│   │
│   └── iosMain/
│       ├── kotlin/app/sunreef/yachts/mobile/
│       │   ├── di/
│       │   │   ├── PlatformModule.kt           # iOS-specific DI
│       │   │   └── Koin.ios.kt                 # iOS Koin setup
│       │   ├── Platform.ios.kt                 # iOS platform utils
│       │   ├── location/                       # iOS Location Manager
│       │   ├── connectivity/                   # iOS Network Monitor
│       │   ├── sensors/                        # iOS CoreMotion
│       │   └── permissions/                    # iOS permission handling
│       └── ...
└── build.gradle.kts
```

## Layer Responsibilities

### App Layer
- Entry point for the application
- Initialization and setup
- Platform-specific app lifecycle

### Domain Layer
- Business logic implementation
- Use cases (orchestration of operations)
- Repository interfaces (contracts)
- Domain models (immutable entities)
- Independent of frameworks and UI

### Data Layer
- Implements repository interfaces
- Manages data sources (API, local storage)
- Handles caching and synchronization
- Network communication (Ktor)
- Database operations

### Presentation Layer
- Compose UI components
- ViewModels (state management)
- Screen navigation (Decompose)
- User interaction handling
- UI theming and styling

### DI Layer
- Koin module definitions
- Service registration
- Singleton/scoped instance management
- Platform-specific implementations

## Adding New Features

### 1. Adding a New Domain Feature

```
shareSunreefYachts/src/commonMain/kotlin/app/sunreef/yachts/mobile/
├── domain/
│   ├── models/
│   │   └── YachtSystemStatus.kt          # Domain model
│   ├── repositories/
│   │   └── IYachtSystemRepository.kt     # Repository interface
│   └── useCases/
│       ├── GetYachtStatusUseCase.kt      # Get status use case
│       └── MonitorYachtSystemUseCase.kt  # Real-time monitoring
```

### 2. Implementing Data Sources

```
shareSunreefYachts/src/commonMain/kotlin/app/sunreef/yachts/mobile/
├── data/
│   ├── remote/
│   │   ├── api/
│   │   │   └── YachtSystemApi.kt         # API endpoints
│   │   └── models/
│   │       └── YachtStatusResponse.kt    # API response models
│   └── repositories/
│       └── YachtSystemRepository.kt      # Implementation
```

### 3. Creating UI Screens

```
shareSunreefYachts/src/commonMain/kotlin/app/sunreef/yachts/mobile/
├── presentation/
│   ├── screens/
│   │   └── yachtStatus/
│   │       ├── YachtStatusScreen.kt      # Main screen
│   │       ├── YachtStatusViewModel.kt   # State management
│   │       └── components/
│   │           ├── StatusIndicator.kt
│   │           ├── SystemMonitor.kt
│   │           └── ControlPanel.kt
│   └── navigation/
│       └── YachtStatusDestination.kt     # Navigation definition
```

## Platform-Specific Extensions

### Android Specific
- Location services (Google Play Services)
- Sensors integration
- Background tasks
- Push notifications
- Bluetooth connectivity

### iOS Specific
- Core Location
- Core Motion
- HealthKit
- HomeKit integration
- CallKit support

## Resource Management

### Compose Resources
Place in `src/commonMain/composeResources/`

- **Drawables**: SVG, PNG, XML vectors
- **Fonts**: TTF, OTF files
- **Strings**: Locale-specific strings (values/)
- **Colors**: Color definitions

### Android Resources
Place in `src/androidMain/res/`

- **values/**: strings.xml, colors.xml, dimens.xml
- **drawable/**: Android-specific drawables
- **mipmap/**: App icons

## Build Configuration

The module is configured as a Kotlin Multiplatform Library:

- **Targets**: Android (minSdk 24), iOS (16.0+)
- **Compose**: Full Multiplatform Compose support
- **Serialization**: Kotlinx Serialization
- **Framework**: XCFramework for iOS

## Dependencies

See `build.gradle.kts` for complete dependency list. Key dependencies:

- `coreMobile` - Foundation library
- Compose (Runtime, Foundation, Material3, UI)
- Ktor (HTTP client)
- Koin (Dependency injection)
- Decompose (Navigation)
- Kotlinx DateTime
- Lottie (Animations)
- Coil (Image loading)

## Testing Strategy

### Common Tests
- Unit tests for use cases
- Repository mock tests
- API model serialization tests

### Android Tests
- Integration tests
- Permission tests
- Location service tests

### iOS Tests
- Platform-specific permission tests
- Core Location integration

## Performance Considerations

1. **Memory Management**
   - Avoid large object allocations in Compose
   - Use remember {} for expensive computations
   - Implement proper resource cleanup

2. **Network Optimization**
   - Cache API responses appropriately
   - Use websockets for real-time data
   - Implement exponential backoff for retries

3. **UI Performance**
   - Minimize recompositions
   - Use LazyColumn/LazyRow for long lists
   - Implement efficient image loading with Coil

4. **Battery Optimization**
   - Batch location updates
   - Use appropriate sensor update rates
   - Implement efficient connectivity monitoring

## Documentation Standards

- KDoc comments for all public APIs
- Package-level documentation
- Example usage in function KDoc
- Architecture decision records (ADRs) for complex features

## Integration with Yacht Systems

This module is designed to integrate with:

- **NMEA 2000**: Marine communication standard
- **Modbus**: Industrial protocol for PLC communication
- **CAN Bus**: Vehicle/yacht communication network
- **MQTT**: IoT messaging protocol
- **OPC UA**: Industrial automation standard

Each integration should be implemented as a separate data source in the data layer.

