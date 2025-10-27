package app.android.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
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
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setUpKoin()
        // Register screen providers
        registerScreenProviders()

        // Then just use setContent normally
        setContent {
            MainView()
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