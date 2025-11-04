package app.sunreef.yachts.mobile.di

import org.koin.dsl.module

/**
 * Common platform module declaration
 * Platform-specific implementations provided by Android/iOS
 */

expect val platformModule: org.koin.core.module.Module

