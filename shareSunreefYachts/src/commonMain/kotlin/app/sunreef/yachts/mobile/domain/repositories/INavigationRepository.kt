package app.sunreef.yachts.mobile.domain.repositories

import app.sunreef.yachts.mobile.domain.models.NavigationData
import app.sunreef.yachts.mobile.domain.models.Route
import app.sunreef.yachts.mobile.domain.models.Waypoint
import kotlinx.coroutines.flow.Flow

interface INavigationRepository {
    /**
     * Get current navigation data
     */
    suspend fun getCurrentPosition(): Result<NavigationData>

    /**
     * Stream real-time navigation data
     */
    fun streamNavigationData(): Flow<NavigationData>

    /**
     * Set destination
     */
    suspend fun setDestination(waypoint: Waypoint): Result<Boolean>

    /**
     * Get active route
     */
    suspend fun getActiveRoute(): Result<Route?>

    /**
     * Save route
     */
    suspend fun saveRoute(route: Route): Result<String>

    /**
     * Get waypoints
     */
    suspend fun getWaypoints(): Result<List<Waypoint>>
}
