package app.sunreef.yachts.mobile.di

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named

/**
 * DI Qualifiers for Sunreef Yachts module
 * Used to distinguish between different implementations of the same interface
 * Follows the shared module pattern
 */

object SunreefYachtsDiQualifiers {
    // Yacht System Qualifiers
    val YACHT_SYSTEM_MAIN: Qualifier = named("yacht_system_main")
    val YACHT_SYSTEM_SECONDARY: Qualifier = named("yacht_system_secondary")

    // Engine Qualifiers
    val ENGINE_PRIMARY: Qualifier = named("engine_primary")
    val ENGINE_BACKUP: Qualifier = named("engine_backup")

    // Navigation Qualifiers
    val NAVIGATION_GPS: Qualifier = named("navigation_gps")
    val NAVIGATION_AUTOPILOT: Qualifier = named("navigation_autopilot")

    // Connectivity Qualifiers
    val CONNECTIVITY_WIFI: Qualifier = named("connectivity_wifi")
    val CONNECTIVITY_CELLULAR: Qualifier = named("connectivity_cellular")
    val CONNECTIVITY_BOAT_NETWORK: Qualifier = named("connectivity_boat_network")
}

