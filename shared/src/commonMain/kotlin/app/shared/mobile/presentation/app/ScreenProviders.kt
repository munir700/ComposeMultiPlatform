package app.shared.mobile.presentation.app

import app.shared.mobile.navigation.KoinScreenProvider
import app.shared.mobile.presentation.login.LoginScreen
import app.shared.mobile.presentation.main.MainScreen
import app.shared.mobile.presentation.splash.SplashScreen
import kmp.core.mobile.navigations.ScreenProviderRegistry
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun registerScreenProviders() {
    object : KoinComponent {
        fun register() {
            val registry by inject<ScreenProviderRegistry>()
            val koinProvider by inject<KoinScreenProvider>()

            // Register all your screen classes here
            koinProvider.registerScreen(SplashScreen::class)

            // Add more screens as needed:
             koinProvider.registerScreen(LoginScreen::class)
             koinProvider.registerScreen(MainScreen::class)

            registry.registerProvider(koinProvider)
        }
    }.register()
}