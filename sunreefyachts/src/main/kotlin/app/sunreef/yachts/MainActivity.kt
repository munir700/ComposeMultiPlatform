package app.sunreef.yachts

import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.sunreef.yachts.di.androidAppModule
import app.sunreef.yachts.mobile.constants.AppEnvironment
import app.sunreef.yachts.mobile.constants.AppInfo
import app.sunreef.yachts.mobile.di.initKoin
import app.sunreef.yachts.mobile.presentation.MainView
import app.sunreef.yachts.mobile.presentation.app.registerScreenProviders
import com.arkivanov.decompose.defaultComponentContext
import kmp.core.mobile.AppLogger
import kmp.core.mobile.navigations.NavManager
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class MainActivity : ComponentActivity() {

    val navManager by lazy { inject<NavManager>().value }
    val logger by lazy { inject<AppLogger>().value }

    // Double back press tracking
    private var lastBackPressTime = 0L
    private val backPressResetDelay = 2000L  // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setUpKoin()
        // Register screen providers
        registerScreenProviders()

        // Register back button handler using OnBackPressedDispatcher
        setupBackButtonHandler()

        // Then just use setContent normally
        setContent {
            MainView()
        }
    }

    /**
     * Handle device back button press using OnBackPressedDispatcher.
     * Routes it through the navigator to allow screens to handle back events.
     * Shows exit confirmation on double back press.
     */
    private fun setupBackButtonHandler() {
        onBackPressedDispatcher.addCallback(
            owner = this,  // LifecycleOwner
            onBackPressedCallback = deviceCallbackPressed
        )
    }

    /**
     * OnBackPressedCallback object with double press detection
     */
    private val deviceCallbackPressed = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            try {
                logger.log("Device back button pressed")
                handleAppExit(this)
            } catch (e: Exception) {
                logger.log("Error handling back press: ${e.message}")
                isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun handleAppExit(onBackPressedCallback: OnBackPressedCallback) {
        if (!navManager.goBack()) {
            // Navigation stack is empty - handle double back press
            val currentTime = SystemClock.uptimeMillis()
            val timeSinceLastPress = currentTime - lastBackPressTime

            logger.log("Navigation stack empty. Time since last press: $timeSinceLastPress ms, lastBackPressTime: $lastBackPressTime")

            if (lastBackPressTime == 0L || timeSinceLastPress > backPressResetDelay) {
                // FIRST BACK PRESS (or timeout occurred - lastBackPressTime is 0 or too old)
                logger.log("First back press - showing toast")
                lastBackPressTime = currentTime
                Toast.makeText(
                    this@MainActivity,
                    "Press back again to exit",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // SECOND BACK PRESS within timeout
                logger.log("Second back press - exiting app")
                lastBackPressTime = 0L  // Reset for next time

                // Exit the app
                onBackPressedCallback.isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun setUpKoin() {
        // Create ComponentContext here and add it to Koin
        val rootComponentContext = defaultComponentContext()

        // Prepare the environment
        val environment = when {
            BuildConfig.DEBUG -> AppEnvironment.dev
            else -> AppEnvironment.prod
        }


        // Init koin
        initKoin(
            appInfo = AppInfo(
                appVersion = BuildConfig.VERSION_CODE.toString()
            ),
            environment = environment,
            componentContext = rootComponentContext
        ) {
            androidContext(this@MainActivity)
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.INFO)
            modules(androidAppModule)
        }
    }
}


@Preview
@Composable
fun AppAndroidPreview() {
    MainView()
}