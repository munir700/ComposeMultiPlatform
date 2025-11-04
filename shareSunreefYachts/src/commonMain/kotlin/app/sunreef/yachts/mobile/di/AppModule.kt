package app.sunreef.yachts.mobile.di

import app.sunreef.yachts.mobile.globalState.AppGlobalState
import app.sunreef.yachts.mobile.globalState.IAppGlobalState
import org.koin.dsl.module

/**
 * App Module for Sunreef Yachts
 * Registers app-level configurations and global instances
 * Follows the same pattern as AppModule in shared module
 */

fun sunreefYachtsAppModule() = module {
    // Global State
    single<IAppGlobalState> { AppGlobalState() }
}

