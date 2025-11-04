# Sunreef Yachts Mobile Module

## Overview

The `shareSunreefYachts` module is a Kotlin Multiplatform (KMP) library that provides a comprehensive foundation for the Sunreef Yachts mobile application ecosystem. It's designed to support iOS and Android platforms with shared business logic, UI components, and utilities.

This module is specifically tailored for luxury yacht and catamaran mobile applications, supporting remote monitoring, control systems integration, and user experience optimization for maritime environments.

## Architecture

The module follows a clean architecture pattern organized into distinct layers:

### 1. **Domain Layer** (`domain/`)
- Business logic and use cases (organized by domain)
- Domain models and interfaces
- Repository contracts
- Independent of frameworks

**Note**: Use cases are now organized by domain (engine, navigation, security, etc.) in separate files and packages. See [ARCHITECTURE_REFACTORING.md](ARCHITECTURE_REFACTORING.md) for details.

### 2. **Data Layer** (`data/`)
- Repository implementations
- API clients and network management
- Local storage (database, preferences)
- Data source management
- Remote and local data handling

### 3. **Presentation Layer** (`presentation/`)
- UI components and screens
- ViewModel implementations (organized by domain)
- UI state management
- Compose-based UI declarations

**Note**: ViewModels are now organized by domain (engine, navigation, security, etc.) in separate files and packages. See [ARCHITECTURE_REFACTORING.md](ARCHITECTURE_REFACTORING.md) for details.

### 4. **Dependency Injection** (`di/`)
- Koin DI module configuration
- Platform-specific bindings (Android/iOS)
- Service locator setup

## Dependencies

The module depends on:
- **coreMobile** - Core multiplatform mobile utilities and libraries
  - Compose framework (UI)
  - Ktor (networking)
  - Koin (dependency injection)
  - Decompose (navigation)
  - And other essential KMP libraries

**Important:** This module does NOT depend on the `shared` module, making it independent and suitable for building isolated yacht-specific features.

## Project Structure

```
shareSunreefYachts/
├── build.gradle.kts                    # Gradle build configuration
├── src/
│   ├── commonMain/                     # Shared code for all platforms
│   │   ├── kotlin/app/sunreef/yachts/mobile/
│   │   │   ├── di/                     # DI configuration
│   │   │   ├── data/                   # Data layer
│   │   │   ├── domain/                 # Domain layer
│   │   │   ├── presentation/           # Presentation layer
│   │   │   └── App.kt                  # Main app entry point
│   │   └── composeResources/           # Compose resources (drawables, fonts, etc.)
│   │
│   ├── commonTest/                     # Common test code
│   │   └── kotlin/app/sunreef/yachts/mobile/
│   │
│   ├── androidMain/                    # Android-specific code
│   │   ├── kotlin/app/sunreef/yachts/mobile/
│   │   │   ├── di/                     # Android DI setup
│   │   │   ├── Platform.android.kt
│   │   │   └── ...
│   │   ├── AndroidManifest.xml
│   │   └── res/                        # Android resources
│   │
│   └── iosMain/                        # iOS-specific code
│       ├── kotlin/app/sunreef/yachts/mobile/
│       │   ├── di/                     # iOS DI setup
│       │   ├── Platform.ios.kt
│       │   └── Koin.ios.kt
│       └── ...
```

## Namespace

- **Package:** `app.sunreef.yachts.mobile`
- **Android Namespace:** `app.sunreef.yachts.mobile`
- **iOS Framework Name:** `SunreefYachts`

## Key Features & Integration Points

### 1. **Multiplatform Support**
- iOS (arm64, x64, simulatorArm64)
- Android (API 24+)
- Shared business logic
- Platform-specific implementations

### 2. **Yacht Integration Capabilities**
- System monitoring and data aggregation
- IoT device communication (NMEA 2000, Modbus, MQTT, OPC UA)
- Remote control systems
- Real-time status monitoring
- Push notification support
- Multimedia handling

### 3. **Networking & Data**
- Ktor HTTP client for REST APIs
- Serialization support (JSON, Protobuf)
- Local data caching with persistent storage
- Error handling and retry logic

### 4. **UI/UX**
- Jetpack Compose for modern UI
- Material 3 design system
- Responsive layouts
- Cross-platform consistency

### 5. **Security**
- Encrypted local storage (KVault)
- Secure API communication
- Firebase integration for backend services
- Authentication support

## Build Configuration

### Gradle Details
- **Kotlin Version:** 1.9+
- **Compose:** Latest Multiplatform Compose
- **JVM Target:** Java 17
- **Min Android SDK:** 24
- **Target Android SDK:** 36
- **iOS Deployment Target:** 16.0+

### Compose Resources
- Generates public resource class
- Package: `app.sunreef.yachts.mobile.resources`
- Supports drawables, fonts, and localized strings

## Getting Started

### Adding to Your Project

1. The module is included in `settings.gradle.kts`:
```kotlin
include(":shareSunreefYachts")
```

2. Depend on it in your app module:
```kotlin
dependencies {
    implementation(project(":shareSunreefYachts"))
}
```

### Basic Setup

1. **Common Main** - Implement core business logic
2. **Android Main** - Add Android-specific services
3. **iOS Main** - Add iOS-specific implementations

## Development Guidelines

### Code Organization
1. Keep platform-specific code in respective source sets
2. Use interfaces in common code for platform-specific implementations
3. Utilize Koin for dependency injection across platforms
4. Follow clean architecture principles

### Testing
- Add unit tests in `commonTest`
- Platform-specific tests in `androidMain`/`iosMain`

### Resource Management
- Place drawable resources in `composeResources/drawable`
- Font resources in `composeResources/font`
- String resources in `composeResources/values`

## Integration with Yacht Systems

This module is designed to facilitate integration with various yacht automation systems:

- **NMEA 2000** - Marine industry standard communication
- **Modbus/Modbus TCP** - Industrial control protocol
- **CAN Bus** - Controller Area Network communication
- **MQTT** - Lightweight publish-subscribe protocol
- **OPC UA** - Industrial interoperability standard
- **PLC Communication** - Programmable Logic Controller integration

## Performance Optimization

- Lazy loading of resources
- Efficient image handling with Coil
- Optimized Compose recomposition
- Memory-efficient data structures
- Battery-conscious updates

## Future Enhancements

- Offline-first architecture
- Advanced caching strategies
- Real-time data synchronization
- Enhanced security features
- Expanded IoT protocol support
- Advanced analytics integration

## License

This module is part of the CMPApp project. See the main project for licensing details.

## Support

For questions or issues related to this module, refer to the main project documentation or contact the development team.

