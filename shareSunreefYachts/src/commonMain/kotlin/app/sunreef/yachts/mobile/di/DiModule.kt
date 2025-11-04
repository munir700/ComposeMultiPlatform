package app.sunreef.yachts.mobile.di
import org.koin.dsl.module
/**
 * Dependency Injection configuration for Sunreef Yachts
 * Follows the same modular pattern as the shared module
 * Each concern has its own module for better organization
 */
// Main module that includes all sub-modules
val sunreefYachtsModule = module {
    includes(dataModule, domainsModule, screensModule, navigationModule, platformModule)
}
