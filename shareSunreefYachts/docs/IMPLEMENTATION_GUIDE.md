# shareSunreefYachts - Implementation Guide

## Quick Implementation Guide

This guide shows developers how to use the implemented features in the shareSunreefYachts module.

---

## 1. Engine Control Example

### ViewModel Setup

```kotlin
class EngineControlScreen(
    private val engineViewModel: EngineMonitoringViewModel = koinViewModel()
) {
    // Setup
    LaunchedEffect(Unit) {
        engineViewModel.streamEngineData("engine-001")
    }
    
    // Get state
    val engineState by engineViewModel.engineState.collectAsState()
    
    // Display
    engineState?.let { engine ->
        Text("RPM: ${engine.rpmCurrent}/${engine.rpmMax}")
        Text("Temperature: ${engine.temperature}°C")
        Text("Status: ${engine.status}")
    }
}
```

### Start Engine

```kotlin
viewModelScope.launch {
    val command = RemoteControlCommand(
        id = UUID.randomUUID().toString(),
        commandType = ControlCommandType.START_ENGINE,
        targetSystem = SystemType.ENGINE,
        priority = CommandPriority.HIGH,
        timestamp = System.currentTimeMillis(),
        status = CommandStatus.PENDING,
        parameters = mapOf("engineId" to "engine-001")
    )
    
    val result = sendRemoteCommandUseCase(command)
    result.onSuccess { commandId ->
        println("Engine start command sent: $commandId")
    }
}
```

---

## 2. Navigation Control Example

### Set Destination

```kotlin
val destination = Waypoint(
    id = UUID.randomUUID().toString(),
    latitude = 40.7489,
    longitude = -73.9680,
    name = "New York Harbor",
    description = "Harbor entrance",
    timestamp = System.currentTimeMillis()
)

viewModelScope.launch {
    val result = setDestinationUseCase(destination)
    result.onSuccess {
        println("Destination set successfully")
    }
}
```

### Get Current Position

```kotlin
viewModelScope.launch {
    val position = getCurrentPositionUseCase()
    position.onSuccess { navData ->
        println("Position: ${navData.latitude}, ${navData.longitude}")
        println("Heading: ${navData.heading}°")
        println("Speed: ${navData.speed} knots")
    }
}
```

### Monitor Navigation Real-time

```kotlin
LaunchedEffect(Unit) {
    streamNavigationDataUseCase()
        .collect { navData ->
            updateMap(navData.latitude, navData.longitude)
            updateHeading(navData.heading)
        }
}
```

---

## 3. Security Control Example

### Lock All Doors

```kotlin
viewModelScope.launch {
    lockDoorsUseCase()
        .onSuccess {
            showMessage("All doors locked")
        }
        .onFailure { error ->
            showError("Failed to lock doors: ${error.message}")
        }
}
```

### Arm Alarm

```kotlin
viewModelScope.launch {
    armAlarmUseCase("ARMED_AWAY")
        .onSuccess {
            updateSecurityUI("Armed Away")
        }
}
```

### Monitor Security Real-time

```kotlin
LaunchedEffect(Unit) {
    securityViewModel.streamSecurityData()
        .collect { security ->
            updateSecurityStatus(security)
            if (security.intrusionAlert) {
                triggerAlert("INTRUSION DETECTED!")
            }
        }
}
```

---

## 4. Climate Control Example

### Set Temperature

```kotlin
viewModelScope.launch {
    setTemperatureUseCase(23.5f)
        .onSuccess {
            println("Temperature set to 23.5°C")
        }
        .onFailure { error ->
            println("Temperature range must be 16°C to 30°C")
        }
}
```

### Monitor Climate

```kotlin
LaunchedEffect(Unit) {
    streamClimateDataUseCase()
        .collect { climate ->
            updateTemperatureDisplay(climate.cabinTemperature)
            updateHumidityDisplay(climate.humidity)
            updateACStatus(climate.acStatus)
        }
}
```

---

## 5. Alert Management Example

### Get Active Alerts

```kotlin
viewModelScope.launch {
    getActiveAlertsUseCase()
        .onSuccess { alerts ->
            alerts.forEach { alert ->
                println("${alert.severity}: ${alert.message}")
            }
        }
}
```

### Stream Alerts Real-time

```kotlin
LaunchedEffect(Unit) {
    streamAlertsUseCase()
        .collect { alert ->
            when (alert.severity) {
                AlertSeverity.CRITICAL -> showCriticalAlert(alert)
                AlertSeverity.HIGH -> showHighAlert(alert)
                else -> logAlert(alert)
            }
        }
}
```

### Acknowledge Alert

```kotlin
viewModelScope.launch {
    acknowledgeAlertUseCase(alertId)
        .onSuccess {
            removeAlertFromUI(alertId)
        }
}
```

---

## 6. Electrical System Example

### Monitor Battery

```kotlin
LaunchedEffect(Unit) {
    streamElectricalDataUseCase()
        .collect { electrical ->
            updateBatteryVoltage(electrical.batteryVoltage)
            updateBatteryCapacity(electrical.batteryCapacity)
            
            if (electrical.batteryCapacity < 20f) {
                showWarning("Battery low!")
            }
        }
}
```

---

## 7. Protocol Integration Example

### Handle NMEA 2000 Data

```kotlin
viewModelScope.launch {
    streamNMEA2000DataUseCase()
        .collect { frame ->
            processNMEAFrame(frame)
            updateDeviceFromNMEA(frame)
        }
}
```

### Read Modbus Register

```kotlin
viewModelScope.launch {
    readModbusRegisterUseCase(0x100)
        .onSuccess { register ->
            println("Register value: ${register.value}")
            updateUIFromModbus(register)
        }
}
```

### Publish MQTT Message

```kotlin
viewModelScope.launch {
    val message = MQTTMessage(
        topic = "sunreef/yacht/engine/status",
        payload = """{"rpm": 1200, "status": "running"}""",
        qos = 1,
        retained = false,
        timestamp = System.currentTimeMillis()
    )
    
    publishMQTTMessageUseCase(message)
        .onSuccess { println("Message published") }
}
```

---

## 8. Remote Control Example

### Send Generic Command

```kotlin
viewModelScope.launch {
    val command = RemoteControlCommand(
        id = UUID.randomUUID().toString(),
        commandType = ControlCommandType.SET_HEADING,
        targetSystem = SystemType.NAVIGATION,
        priority = CommandPriority.NORMAL,
        timestamp = System.currentTimeMillis(),
        status = CommandStatus.PENDING,
        parameters = mapOf(
            "heading" to 45,
            "autopilot" to true
        )
    )
    
    sendRemoteCommandUseCase(command)
        .onSuccess { commandId ->
            monitorCommandStatus(commandId)
        }
}
```

### Monitor Command Status

```kotlin
private fun monitorCommandStatus(commandId: String) {
    viewModelScope.launch {
        while (true) {
            getCommandStatusUseCase(commandId)
                .onSuccess { status ->
                    when (status) {
                        CommandStatus.COMPLETED -> {
                            println("Command completed")
                            break
                        }
                        CommandStatus.FAILED -> {
                            println("Command failed")
                            break
                        }
                        CommandStatus.EXECUTING -> println("Executing...")
                        else -> {} // Wait
                    }
                }
            delay(500)
        }
    }
}
```

---

## 9. Dashboard Integration Example

### Load Dashboard Data

```kotlin
class MainScreen(
    private val dashboardVM: DashboardViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        dashboardVM.loadDashboard()
    }
    
    val systems by dashboardVM.systems.collectAsState()
    val metrics by dashboardVM.metrics.collectAsState()
    val isLoading by dashboardVM.isLoading.collectAsState()
    
    if (isLoading) {
        CircularProgressIndicator()
    } else {
        systems.forEach { system ->
            SystemStatusCard(
                title = system.name,
                status = system.status.name,
                statusColor = getStatusColor(system.status)
            )
        }
    }
}
```

---

## 10. Best Practices

### Error Handling

```kotlin
getEngineDataUseCase(engineId)
    .onSuccess { engine ->
        updateUI(engine)
    }
    .onFailure { error ->
        when (error) {
            is IOException -> showNetworkError()
            is TimeoutException -> showTimeoutError()
            else -> showGenericError(error.message)
        }
    }
```

### State Management

```kotlin
// Always use StateFlow for UI state
private val _engineState = MutableStateFlow<EngineData?>(null)
val engineState: StateFlow<EngineData?> = _engineState.asStateFlow()

// Collect safely
val engine by engineState.collectAsState()
```

### Memory Leak Prevention

```kotlin
// Flow operations automatically cancel with viewModelScope
viewModelScope.launch {
    streamEngineData()
        .collect { data ->
            _state.value = data
        } // Automatically cancelled when ViewModel is destroyed
}
```

### Real-time Data Streaming

```kotlin
// Stream for real-time updates
fun monitorSystem() {
    viewModelScope.launch {
        streamSystemData()
            .collect { data ->
                _systemState.value = data
                updateUI()
            }
    }
}

// Load for one-time data
fun loadSystemData() {
    viewModelScope.launch {
        getSystemData()
            .onSuccess { data ->
                _systemState.value = data
            }
    }
}
```

---

## 11. Testing Example

### Mock Repository Setup

```kotlin
class MockEngineRepository : IEngineRepository {
    override suspend fun getEngineData(engineId: String): Result<EngineData> {
        return Result.success(
            EngineData(
                engineId = engineId,
                engineName = "Test Engine",
                rpmCurrent = 1000,
                rpmMax = 3000,
                temperature = 80f,
                oilPressure = 4.0f,
                fuelFlow = 20f,
                fuelLevel = 90f,
                runningHours = 1000,
                status = EngineStatus.RUNNING,
                timestamp = System.currentTimeMillis()
            )
        )
    }
    
    // ... implement other methods
}
```

### ViewModel Testing

```kotlin
@Test
fun testEngineMonitoring() = runTest {
    val useCase = GetEngineDataUseCase(mockRepository)
    val viewModel = EngineMonitoringViewModel(useCase, mockStreamUseCase)
    
    viewModel.loadEngineData("engine-001")
    
    assert(viewModel.engineState.value != null)
    assert(viewModel.engineState.value?.rpmCurrent == 1000)
}
```

---

## 12. Integration Checklist

When implementing a new feature:

- [ ] Create domain model
- [ ] Define repository interface
- [ ] Implement repository with mock data
- [ ] Create use case
- [ ] Register in DI module
- [ ] Create ViewModel
- [ ] Create Composable UI
- [ ] Test with mock data
- [ ] Connect to real API
- [ ] Handle errors properly
- [ ] Test on device

---

**All features are ready to use! Start integrating them into your yacht automation application.**

