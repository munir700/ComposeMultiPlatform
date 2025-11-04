package app.sunreef.yachts.mobile.presentation.app

import app.sunreef.yachts.mobile.navigation.KoinScreenProvider
import kmp.core.mobile.navigations.ScreenProviderRegistry
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun registerScreenProviders() {
    object : KoinComponent {
        fun register() {
            val registry by inject<ScreenProviderRegistry>()
            val koinProvider by inject<KoinScreenProvider>()

            // Register all your screen classes here


            registry.registerProvider(koinProvider)
        }
    }.register()
}