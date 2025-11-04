package app.sunreef.yachts.mobile.di

import org.koin.dsl.module

/**
 * Android-specific DI configuration for Sunreef Yachts
 * Platform-specific services and implementations
 */

actual val platformModule = module {
    // Android-specific implementations here
    // - Location services
    // - Sensors
    // - Bluetooth connectivity
    // - Permissions
}
