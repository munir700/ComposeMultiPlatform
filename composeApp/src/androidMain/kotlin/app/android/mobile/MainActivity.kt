package app.android.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.android.mobile.di.androidAppModule
import app.shared.mobile.constants.AppEnvironment
import app.shared.mobile.constants.AppInfo
import app.shared.mobile.di.initKoin
import app.shared.mobile.presentation.MainView
import app.shared.mobile.presentation.app.registerScreenProviders
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
     */
    private fun setupBackButtonHandler() {

        onBackPressedDispatcher.addCallback(
            owner = this,  // LifecycleOwner
            onBackPressedCallback = deviceCallbackPressed
        )
    }

    /**
     * OnBackPressedCallback object
     */
    private val deviceCallbackPressed = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            try {
                logger.log("Device back button pressed")

                // Attempt to pop/go back
                if (!navManager.goBack()) {
                    // If navigation stack is empty, exit app
                    logger.log("Navigation stack empty, exiting app")
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            } catch (e: Exception) {
                logger.log("Error handling back press: ${e.message}")
                isEnabled = false
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