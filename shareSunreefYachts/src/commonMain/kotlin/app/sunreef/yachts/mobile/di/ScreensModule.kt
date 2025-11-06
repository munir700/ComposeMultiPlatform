package app.sunreef.yachts.mobile.di

import app.sunreef.yachts.mobile.presentation.dashboard.DashboardViewModel
import org.koin.dsl.module

/**
 * Presentation Layer DI Module
 * Provides all ViewModels and screen-related dependencies
 * Follows the shared module pattern with factory instead of viewModel DSL
 * (Using factory for Decompose-based ViewModels)
 */

val screensModule = module {
    // ==================== ViewModels ====================

    // Dashboard ViewModel
    factory { DashboardViewModel(get()) }
}

