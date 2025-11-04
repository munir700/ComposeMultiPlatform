package app.sunreef.yachts.mobile.di

import org.koin.dsl.module

/**
 * iOS-specific DI configuration for Sunreef Yachts
 * Platform-specific services and implementations
 */

actual val platformModule = module {
    // iOS-specific implementations here
    // - Core Location
    // - Core Motion
    // - HealthKit
    // - HomeKit integration
}
