package app.sunreef.yachts.mobile.di


import app.sunreef.yachts.mobile.navigation.KoinScreenProvider
import kmp.core.mobile.navigations.DIScreenFactory
import kmp.core.mobile.navigations.ScreenProviderRegistry
import org.koin.dsl.module

val navigationModule = module {
    single { ScreenProviderRegistry() }
    single { KoinScreenProvider() }
    single { DIScreenFactory(get()) }
}