# Quick Start Guide - shareSunreefYachts Module

## Overview

The `shareSunreefYachts` module is a Kotlin Multiplatform Module designed for building yacht and catamaran mobile applications for iOS and Android. It provides a solid foundation for integrating with yacht systems, IoT devices, and marine automation platforms.

## Module Details

- **Module Name**: `shareSunreefYachts`
- **Primary Package**: `app.sunreef.yachts.mobile`
- **Depends On**: `coreMobile` module (independent of `shared`)
- **Targets**: iOS (16.0+), Android (24+)
- **Build System**: Gradle with Kotlin Multiplatform Plugin

## Project Structure

```
src/
├── commonMain/          # Shared code for all platforms
├── commonTest/          # Shared test code
├── androidMain/         # Android-specific implementation
└── iosMain/             # iOS-specific implementation
```

## Getting Started

### 1. Understanding the Module

The module follows a **Clean Architecture** pattern:

```
Presentation Layer (UI, Navigation)
     ↓↓↓
Domain Layer (Business Logic, Use Cases)
     ↓↓↓
Data Layer (Repositories, APIs, Storage)
     ↓↓↓
External Services (APIs, Databases, Sensors)
```

### 2. Adding a New Feature

#### Step 1: Define the Domain Model

Create a data class in `src/commonMain/kotlin/app/sunreef/yachts/mobile/domain/models/`

```kotlin
// YachtEngine.kt
package app.sunreef.yachts.mobile.domain.models

data class YachtEngine(
    val id: String,
    val name: String,
    val rpmCurrent: Int,
    val rpmMax: Int,
    val temperature: Float,
    val fuelFlow: Float,
    val status: EngineStatus
)

enum class EngineStatus {
    IDLE, RUNNING, WARNING, CRITICAL
}
```

#### Step 2: Create Repository Interface

Create in `src/commonMain/kotlin/app/sunreef/yachts/mobile/domain/repositories/`

```kotlin
// IEngineRepository.kt
package app.sunreef.yachts.mobile.domain.repositories

interface IEngineRepository {
    suspend fun getEngineStatus(engineId: String): Result<YachtEngine>
    suspend fun monitorEngineRealtime(engineId: String): Flow<YachtEngine>
}
```

#### Step 3: Implement Data Layer

Create in `src/commonMain/kotlin/app/sunreef/yachts/mobile/data/`

```kotlin
// EngineRepository.kt
class EngineRepository(
    private val engineApi: EngineApi
) : IEngineRepository {
    override suspend fun getEngineStatus(engineId: String): Result<YachtEngine> {
        return try {
            val response = engineApi.getEngineStatus(engineId)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun monitorEngineRealtime(engineId: String): Flow<YachtEngine> {
        return engineApi.monitorEngine(engineId).map { it.toDomain() }
    }
}
```

#### Step 4: Create Use Case

Create in `src/commonMain/kotlin/app/sunreef/yachts/mobile/domain/usecases/engine/` (domain-specific organization)

```kotlin
// EngineUseCases.kt
class GetEngineStatusUseCase(
    private val engineRepository: IEngineRepository
) {
    suspend operator fun invoke(engineId: String): Result<YachtEngine> {
        return engineRepository.getEngineStatus(engineId)
    }
}
```

**Note**: Use cases are now organized by domain (engine, navigation, security, etc.) in separate subdirectories for better organization and maintainability. See `ARCHITECTURE_REFACTORING.md` for details.

#### Step 5: Create ViewModel

Create in `src/commonMain/kotlin/app/sunreef/yachts/mobile/presentation/viewmodels/engine/` (domain-specific organization)

```kotlin
// EngineMonitoringViewModel.kt
class EngineMonitoringViewModel(
    private val getEngineStatusUseCase: GetEngineStatusUseCase
) : ViewModel() {
    private val _engineState = MutableStateFlow<YachtEngine?>(null)
    val engineState: StateFlow<YachtEngine?> = _engineState.asStateFlow()

    fun loadEngineStatus(engineId: String) {
        viewModelScope.launch {
            getEngineStatusUseCase(engineId).onSuccess { engine ->
                _engineState.value = engine
            }
        }
    }
}
```

**Note**: ViewModels are now organized by domain (engine, navigation, security, etc.) in separate subdirectories. See `ARCHITECTURE_REFACTORING.md` for details.

#### Step 6: Create UI Screen

Create in `src/commonMain/kotlin/app/sunreef/yachts/mobile/presentation/screens/`

```kotlin
// EngineMonitorScreen.kt
@Composable
fun EngineMonitorScreen(
    viewModel: EngineMonitorViewModel = koinViewModel()
) {
    val engine by viewModel.engineState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.loadEngineStatus("engine-001")
    }

    engine?.let { engineData ->
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text("Engine Monitor", style = MaterialTheme.typography.headlineLarge)
            
            Text("RPM: ${engineData.rpmCurrent} / ${engineData.rpmMax}")
            Text("Temperature: ${engineData.temperature}°C")
            Text("Fuel Flow: ${engineData.fuelFlow} L/h")
            
            StatusIndicator(status = engineData.status)
        }
    }
}
```

#### Step 7: Register in DI

Update `src/commonMain/kotlin/app/sunreef/yachts/mobile/di/DiModule.kt`

```kotlin
val sunreefYachtsModule = module {
    // API
    single { EngineApi(get()) }
    
    // Repository
    single<IEngineRepository> { EngineRepository(get()) }
    
    // Use Cases
    factory { GetEngineStatusUseCase(get()) }
    
    // ViewModel
    viewModelOf(::EngineMonitorViewModel)
}
```

### 3. Platform-Specific Implementation

#### Android-Specific (src/androidMain/)

```kotlin
// AndroidPlatformModule.kt
actual class PlatformSpecificEngine {
    // Android-specific sensor reading
    fun readEngineData(): Flow<EngineData> {
        return flow {
            // Read from CAN bus, Bluetooth, or direct hardware
        }
    }
}
```

#### iOS-Specific (src/iosMain/)

```kotlin
// iOSPlatformModule.kt
actual class PlatformSpecificEngine {
    // iOS-specific sensor reading
    fun readEngineData(): Flow<EngineData> {
        return flow {
            // Read from NMEA 2000, Bluetooth, or direct hardware
        }
    }
}
```

## Key Concepts

### Koin Dependency Injection

The module uses Koin for dependency injection. Register your components:

```kotlin
val module = module {
    single { MyRepository() }
    factory { MyUseCase(get()) }
    viewModelOf(::MyViewModel)
}
```

### Compose UI

Build UI using Jetpack Compose:

```kotlin
@Composable
fun MyScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Hello Sunreef")
    }
}
```

### State Management

Use Compose's `remember` and `mutableStateOf` for UI state:

```kotlin
val (count, setCount) = remember { mutableStateOf(0) }
```

Or use ViewModel with StateFlow:

```kotlin
private val _state = MutableStateFlow(initialValue)
val state: StateFlow<State> = _state.asStateFlow()
```

## Working with Yacht Systems

### NMEA 2000 Integration

```kotlin
class NMEA2000DataSource : IYachtDataSource {
    override suspend fun readData(): Flow<YachtData> {
        // Parse NMEA 2000 datagrams
    }
}
```

### Modbus Integration

```kotlin
class ModbusDataSource : IYachtDataSource {
    override suspend fun readData(): Flow<YachtData> {
        // Read from Modbus master/slave
    }
}
```

### MQTT Integration

```kotlin
class MQTTDataSource : IYachtDataSource {
    override suspend fun readData(): Flow<YachtData> {
        // Subscribe to MQTT topics
    }
}
```

## Testing

Add tests in `src/commonTest/`:

```kotlin
// EngineRepositoryTest.kt
class EngineRepositoryTest {
    @Test
    fun testGetEngineStatus() = runTest {
        val repository = EngineRepository(mockApi)
        val result = repository.getEngineStatus("engine-001")
        assertTrue(result.isSuccess)
    }
}
```

## Resources

### Compose Resources

Place in `src/commonMain/composeResources/`:

- **Drawables**: SVG icons, PNG images
- **Fonts**: Custom fonts
- **Strings**: Localized strings (values/)

```kotlin
// Using resources
Icon(painter = painterResource("drawable/ic_engine.xml"), ...)
Text(stringResource("strings/engine_status"))
```

## Building

### Build for Android
```bash
./gradlew assembleDebug
```

### Build for iOS
```bash
./gradlew iosX64Binaries
```

### Run Tests
```bash
./gradlew commonTestClasses
```

## Best Practices

1. **Always use interfaces** in the domain layer for repositories and use cases
2. **Keep business logic in domain** - don't mix UI concerns
3. **Use Flow for streams** - reactive data handling
4. **Implement proper error handling** - use Result<T> or Try<T>
5. **Separate concerns** - keep platform-specific code isolated
6. **Add KDoc comments** - document public APIs
7. **Test thoroughly** - unit test use cases and repositories
8. **Use StateFlow** for ViewModel state - predictable updates

## Common Patterns

### Loading State Pattern

```kotlin
sealed class UiState<T> {
    class Loading<T> : UiState<T>()
    class Success<T>(val data: T) : UiState<T>()
    class Error<T>(val exception: Exception) : UiState<T>()
}
```

### Repository Pattern

```kotlin
interface Repository {
    suspend fun getData(id: String): Result<Data>
}
```

### ViewModel Pattern

```kotlin
class MyViewModel(useCase: MyUseCase) : ViewModel() {
    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state.asStateFlow()
}
```

## Troubleshooting

### Build Issues
- Clear build cache: `./gradlew clean`
- Invalidate IDE cache
- Verify Gradle version compatibility

### Runtime Issues
- Check logcat for Android errors
- Check iOS console for crashes
- Verify dependency versions in `libs.versions.toml`

## Next Steps

1. Study the `coreMobile` module for available utilities
2. Review `STRUCTURE.md` for detailed architecture
3. Check `README.md` for comprehensive documentation
4. Start implementing features following the patterns above
5. Add tests for all business logic
6. Integrate with actual yacht systems

## Resources

- [Kotlin Multiplatform Documentation](https://kotlinlang.org/docs/multiplatform.html)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Koin Documentation](https://insert-koin.io/)
- [Ktor Client Documentation](https://ktor.io/docs/client.html)
- [NMEA 2000 Specification](https://www.nmea.org/nmea-0183.html)

## Support

For issues or questions:
1. Check the module documentation
2. Review similar implementations in the codebase
3. Check for related issues or PRs
4. Contact the development team

Happy coding! 🛥️

