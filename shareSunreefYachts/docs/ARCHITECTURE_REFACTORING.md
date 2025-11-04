# Architecture Refactoring - Use Cases & ViewModels Split

## Overview

The `YachtUseCases.kt` and `YachtViewModels.kt` monolithic files have been refactored into separate, domain-organized classes to improve maintainability, testability, and code organization.

## Changes Made

### 1. Use Cases Refactoring

The monolithic `YachtUseCases.kt` has been split into 12 separate domain-specific files:

#### Engine Use Cases (`domain/usecases/engine/`)
- **File**: `EngineUseCases.kt`
- **Classes**:
  - `StreamEngineDataUseCase` - Stream engine data in real-time
  - `StartEngineUseCase` - Start engine operation
  - `StopEngineUseCase` - Stop engine operation
- **Package**: `app.sunreef.yachts.mobile.domain.usecases.engine`

#### Navigation Use Cases (`domain/usecases/navigation/`)
- **File**: `NavigationUseCases.kt`
- **Classes**:
  - `GetCurrentPositionUseCase` - Retrieve current position
  - `StreamNavigationDataUseCase` - Stream navigation data
  - `SetDestinationUseCase` - Set waypoint destination
  - `GetActiveRouteUseCase` - Get active navigation route
  - `SaveRouteUseCase` - Save route for later use
- **Package**: `app.sunreef.yachts.mobile.domain.usecases.navigation`

#### Electrical System Use Cases (`domain/usecases/electrical/`)
- **File**: `ElectricalUseCases.kt`
- **Classes**:
  - `GetElectricalDataUseCase` - Retrieve electrical system data
  - `StreamElectricalDataUseCase` - Stream electrical data in real-time
- **Package**: `app.sunreef.yachts.mobile.domain.usecases.electrical`

#### Water System Use Cases (`domain/usecases/water/`)
- **File**: `WaterSystemUseCases.kt`
- **Classes**:
  - `GetWaterSystemDataUseCase` - Retrieve water system data
  - `StreamWaterSystemDataUseCase` - Stream water system data
- **Package**: `app.sunreef.yachts.mobile.domain.usecases.water`

#### Climate Control Use Cases (`domain/usecases/climate/`)
- **File**: `ClimateUseCases.kt`
- **Classes**:
  - `GetClimateDataUseCase` - Retrieve climate data
  - `StreamClimateDataUseCase` - Stream climate data
  - `SetTemperatureUseCase` - Set target temperature (16-30°C)
- **Package**: `app.sunreef.yachts.mobile.domain.usecases.climate`

#### Security Use Cases (`domain/usecases/security/`)
- **File**: `SecurityUseCases.kt`
- **Classes**:
  - `GetSecurityDataUseCase` - Retrieve security system status
  - `StreamSecurityDataUseCase` - Stream security data
  - `ArmAlarmUseCase` - Arm alarm with specific mode
  - `LockDoorsUseCase` - Lock all doors
  - `UnlockDoorsUseCase` - Unlock all doors
- **Package**: `app.sunreef.yachts.mobile.domain.usecases.security`

#### Remote Control Use Cases (`domain/usecases/remotecontrol/`)
- **File**: `RemoteControlUseCases.kt`
- **Classes**:
  - `SendRemoteCommandUseCase` - Send remote control command
  - `GetCommandStatusUseCase` - Check command execution status
  - `GetCommandHistoryUseCase` - Retrieve command history (limit: 50 default)
- **Package**: `app.sunreef.yachts.mobile.domain.usecases.remotecontrol`

#### Alert Management Use Cases (`domain/usecases/alert/`)
- **File**: `AlertUseCases.kt`
- **Classes**:
  - `GetActiveAlertsUseCase` - Retrieve list of active alerts
  - `StreamAlertsUseCase` - Stream alerts in real-time
  - `AcknowledgeAlertUseCase` - Acknowledge and dismiss alert
- **Package**: `app.sunreef.yachts.mobile.domain.usecases.alert`

#### Telemetry Use Cases (`domain/usecases/telemetry/`)
- **File**: `TelemetryUseCases.kt`
- **Classes**:
  - `GetPerformanceMetricsUseCase` - Retrieve performance metrics
  - `StreamPerformanceMetricsUseCase` - Stream performance metrics
- **Package**: `app.sunreef.yachts.mobile.domain.usecases.telemetry`

#### Protocol Use Cases (`domain/usecases/protocol/`)
- **File**: `ProtocolUseCases.kt`
- **Classes**:
  - `ProcessNMEA2000FrameUseCase` - Process NMEA 2000 frames
  - `StreamNMEA2000DataUseCase` - Stream NMEA 2000 data
  - `ReadModbusRegisterUseCase` - Read Modbus register
  - `WriteModbusRegisterUseCase` - Write to Modbus register
  - `PublishMQTTMessageUseCase` - Publish MQTT message
- **Package**: `app.sunreef.yachts.mobile.domain.usecases.protocol`

#### Connectivity Use Cases (`domain/usecases/connectivity/`)
- **File**: `ConnectivityUseCases.kt`
- **Classes**:
  - `GetConnectedDevicesUseCase` - Get list of connected devices
  - `StreamConnectedDevicesUseCase` - Stream connected devices updates
- **Package**: `app.sunreef.yachts.mobile.domain.usecases.connectivity`

#### Yacht System Use Cases (`domain/usecases/yacht/`)
- **File**: `YachtSystemUseCases.kt`
- **Classes**:
  - `GetYachtSystemsUseCase` - Get all yacht systems
- **Package**: `app.sunreef.yachts.mobile.domain.usecases.yacht`

### 2. ViewModels Refactoring

The monolithic `YachtViewModels.kt` has been split into 8 separate domain-specific ViewModels:

#### Engine Monitoring ViewModel (`presentation/viewmodels/engine/`)
- **File**: `EngineMonitoringViewModel.kt`
- **Class**: `EngineMonitoringViewModel`
- **State Management**:
  - `engineState: StateFlow<EngineData?>` - Current engine data
  - `isLoading: StateFlow<Boolean>` - Loading state
  - `error: StateFlow<String?>` - Error messages
- **Methods**:
  - `streamEngineData(engineId: String)` - Start streaming engine data
- **Package**: `app.sunreef.yachts.mobile.presentation.viewmodels.engine`

#### Navigation ViewModel (`presentation/viewmodels/navigation/`)
- **File**: `NavigationViewModel.kt`
- **Class**: `NavigationViewModel`
- **State Management**:
  - `navigationState: StateFlow<NavigationData?>` - Current navigation data
  - `isLoading: StateFlow<Boolean>` - Loading state
  - `error: StateFlow<String?>` - Error messages
- **Methods**:
  - `loadPosition()` - Load current position
  - `streamNavigation()` - Stream navigation data
  - `setDestination(waypoint: Waypoint)` - Set destination
- **Package**: `app.sunreef.yachts.mobile.presentation.viewmodels.navigation`

#### Security ViewModel (`presentation/viewmodels/security/`)
- **File**: `SecurityViewModel.kt`
- **Class**: `SecurityViewModel`
- **State Management**:
  - `securityState: StateFlow<SecurityData?>` - Current security data
  - `isProcessing: StateFlow<Boolean>` - Processing state
  - `error: StateFlow<String?>` - Error messages
- **Methods**:
  - `loadSecurityData()` - Load security data
  - `armAlarm(mode: String)` - Arm alarm
  - `lockDoors()` - Lock doors
  - `unlockDoors()` - Unlock doors
- **Package**: `app.sunreef.yachts.mobile.presentation.viewmodels.security`

#### Electrical ViewModel (`presentation/viewmodels/electrical/`)
- **File**: `ElectricalViewModel.kt`
- **Class**: `ElectricalViewModel`
- **State Management**:
  - `electricalState: StateFlow<ElectricalData?>` - Current electrical data
  - `isLoading: StateFlow<Boolean>` - Loading state
- **Methods**:
  - `loadElectricalData()` - Load electrical data
  - `streamElectricalData()` - Stream electrical data
- **Package**: `app.sunreef.yachts.mobile.presentation.viewmodels.electrical`

#### Climate ViewModel (`presentation/viewmodels/climate/`)
- **File**: `ClimateViewModel.kt`
- **Class**: `ClimateViewModel`
- **State Management**:
  - `climateState: StateFlow<ClimateData?>` - Current climate data
  - `isProcessing: StateFlow<Boolean>` - Processing state
  - `error: StateFlow<String?>` - Error messages
- **Methods**:
  - `loadClimateData()` - Load climate data
  - `setTemperature(temperature: Float)` - Set temperature
- **Package**: `app.sunreef.yachts.mobile.presentation.viewmodels.climate`

#### Alerts ViewModel (`presentation/viewmodels/alerts/`)
- **File**: `AlertsViewModel.kt`
- **Class**: `AlertsViewModel`
- **State Management**:
  - `alertsState: StateFlow<List<SystemAlert>>` - List of active alerts
  - `isLoading: StateFlow<Boolean>` - Loading state
- **Methods**:
  - `loadActiveAlerts()` - Load active alerts
  - `streamAlerts()` - Stream alerts
  - `acknowledgeAlert(alertId: String)` - Acknowledge alert
- **Package**: `app.sunreef.yachts.mobile.presentation.viewmodels.alerts`

#### Remote Control ViewModel (`presentation/viewmodels/remotecontrol/`)
- **File**: `RemoteControlViewModel.kt`
- **Class**: `RemoteControlViewModel`
- **State Management**:
  - `commandHistory: StateFlow<List<RemoteControlCommand>>` - Command history
  - `isProcessing: StateFlow<Boolean>` - Processing state
  - `error: StateFlow<String?>` - Error messages
- **Methods**:
  - `sendCommand(command: RemoteControlCommand)` - Send remote command
  - `loadCommandHistory()` - Load command history
  - `checkCommandStatus(commandId: String)` - Check command status
- **Package**: `app.sunreef.yachts.mobile.presentation.viewmodels.remotecontrol`

#### Dashboard ViewModel (`presentation/viewmodels/dashboard/`)
- **File**: `DashboardViewModel.kt`
- **Class**: `DashboardViewModel`
- **State Management**:
  - `systems: StateFlow<List<YachtSystem>>` - List of yacht systems
  - `metrics: StateFlow<PerformanceMetrics?>` - Performance metrics
  - `isLoading: StateFlow<Boolean>` - Loading state
- **Methods**:
  - `loadDashboard()` - Load all dashboard data
- **Package**: `app.sunreef.yachts.mobile.presentation.viewmodels.dashboard`

## Benefits

### 1. **Improved Maintainability**
- Each domain has its own dedicated file
- Changes to one system don't affect others
- Easier to locate and modify code

### 2. **Better Testability**
- Smaller, focused classes are easier to test
- Each use case/ViewModel can be tested independently
- Reduced test complexity

### 3. **Enhanced Scalability**
- Easy to add new use cases for existing systems
- Clear structure for adding new systems
- Prevents monolithic file growth

### 4. **Clearer Organization**
- Domain-driven structure is intuitive
- Package names reflect functionality
- Self-documenting code

### 5. **Reduced Merge Conflicts**
- Multiple developers can work on different systems
- Changes are localized to specific files
- Fewer conflicts during version control

## Import Updates Required

### Previous Pattern (Monolithic)
```kotlin
import app.sunreef.yachts.mobile.domain.usecases.YachtUseCases.*
import app.sunreef.yachts.mobile.presentation.viewmodels.YachtViewModels.*
```

### New Pattern (Domain-Specific)
```kotlin
// Import specific use cases
import app.sunreef.yachts.mobile.domain.usecases.engine.StreamEngineDataUseCase
import app.sunreef.yachts.mobile.domain.usecases.navigation.NavigationUseCases

// Import specific ViewModels
import app.sunreef.yachts.mobile.presentation.viewmodels.engine.EngineMonitoringViewModel
import app.sunreef.yachts.mobile.presentation.viewmodels.security.SecurityViewModel
```

## Directory Structure

```
domain/
└── usecases/
    ├── engine/
    ├── navigation/
    ├── electrical/
    ├── water/
    ├── climate/
    ├── security/
    ├── remotecontrol/
    ├── alert/
    ├── telemetry/
    ├── protocol/
    ├── connectivity/
    └── yacht/

presentation/
└── viewmodels/
    ├── engine/
    ├── navigation/
    ├── security/
    ├── electrical/
    ├── climate/
    ├── alerts/
    ├── remotecontrol/
    └── dashboard/
```

## Migration Checklist

- [x] Split engine use cases into separate file
- [x] Split navigation use cases into separate file
- [x] Split electrical use cases into separate file
- [x] Split water system use cases into separate file
- [x] Split climate use cases into separate file
- [x] Split security use cases into separate file
- [x] Split remote control use cases into separate file
- [x] Split alert use cases into separate file
- [x] Split telemetry use cases into separate file
- [x] Split protocol use cases into separate file
- [x] Split connectivity use cases into separate file
- [x] Split yacht system use cases into separate file
- [x] Split engine ViewModels into separate file
- [x] Split navigation ViewModels into separate file
- [x] Split security ViewModels into separate file
- [x] Split electrical ViewModels into separate file
- [x] Split climate ViewModels into separate file
- [x] Split alerts ViewModels into separate file
- [x] Split remote control ViewModels into separate file
- [x] Split dashboard ViewModels into separate file
- [x] Update STRUCTURE.md
- [x] Create ARCHITECTURE_REFACTORING.md

## Next Steps

1. Update all import statements in existing code
2. Update DI module configurations to reference new packages
3. Run build and tests to verify all imports work
4. Update any documentation that references the old files
5. Consider deleting the old monolithic files after verification

## Related Files

- `STRUCTURE.md` - Updated with new directory structure
- Original files (if kept for reference):
  - `YachtUseCases.kt` (deprecated)
  - `YachtViewModels.kt` (deprecated)

---

**Last Updated**: 2025-11-04
**Status**: ✅ Refactoring Complete

