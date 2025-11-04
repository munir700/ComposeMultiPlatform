package app.shared.mobile.di

import app.shared.mobile.constants.AppEnvironment
import app.shared.mobile.constants.AppInfo
import app.shared.mobile.globalState.IAppGlobalState
import kmp.core.mobile.globalState.ICoreGlobalState
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Test suite for Koin DI module configuration
 * Verifies that IAppGlobalState is properly registered and can be injected
 */
class KoinModuleTest : KoinTest {

    @BeforeTest
    fun setup() {
        stopKoin()
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    /**
     * Test that appModule properly defines IAppGlobalState
     */
    @Test
    fun `test appModule defines IAppGlobalState`() {
        // Arrange
        val environment = AppEnvironment(
            title = "test",
            isDebuggable = true,
            baseUrl = "https://test.com"
        )
        val appInfo = AppInfo(
            version = "1.0.0",
            buildNumber = "1"
        )

        // Act
        val koinApp = startKoin {
            modules(
                appModule(environment, appInfo)
            )
        }

        // Assert
        val koin = koinApp.koin
        val appGlobalState = koin.getOrNull<IAppGlobalState>()
        assertNotNull(appGlobalState, "IAppGlobalState should be registered in appModule")
    }

    /**
     * Test that appModule properly overrides ICoreGlobalState with IAppGlobalState
     */
    @Test
    fun `test appModule overrides ICoreGlobalState with IAppGlobalState`() {
        // Arrange
        val environment = AppEnvironment(
            title = "test",
            isDebuggable = true,
            baseUrl = "https://test.com"
        )
        val appInfo = AppInfo(
            version = "1.0.0",
            buildNumber = "1"
        )

        // Act
        val koinApp = startKoin {
            modules(
                appModule(environment, appInfo)
            )
        }

        // Assert
        val koin = koinApp.koin
        val coreGlobalState = koin.getOrNull<ICoreGlobalState>()
        val appGlobalState = koin.getOrNull<IAppGlobalState>()
        
        assertNotNull(coreGlobalState, "ICoreGlobalState should be registered")
        assertNotNull(appGlobalState, "IAppGlobalState should be registered")
        
        // Verify that ICoreGlobalState is actually the IAppGlobalState instance
        assertTrue(
            coreGlobalState === appGlobalState,
            "ICoreGlobalState should be the same instance as IAppGlobalState"
        )
    }
}
