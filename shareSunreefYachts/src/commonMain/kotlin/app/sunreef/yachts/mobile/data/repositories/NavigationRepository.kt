package app.sunreef.yachts.mobile.data.repositories

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class NavigationRepository : INavigationRepository {
    @OptIn(ExperimentalTime::class)
    val currentTimeMillis = Clock.System.now().toEpochMilliseconds()
    override suspend fun getCurrentPosition(): Result<NavigationData> {
        return try {
            Result.success(
                NavigationData(
                    latitude = 40.7128,
                    longitude = -74.0060,
                    heading = 45f,
                    speed = 12.5f,
                    depth = 25.5f,
                    windSpeed = 8.2f,
                    windDirection = 90f,
                    courseOverGround = 45f,
                    speedOverGround = 12.5f,
                    timestamp = currentTimeMillis
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun streamNavigationData(): Flow<NavigationData> {
        return flow {
            while (true) {
                val data = getCurrentPosition().getOrNull()
                if (data != null) emit(data)
                kotlinx.coroutines.delay(1000)
            }
        }
    }

    override suspend fun setDestination(waypoint: Waypoint): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getActiveRoute(): Result<Route?> {
        return try {
            Result.success(null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveRoute(route: Route): Result<String> {
        return try {
            Result.success(route.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getWaypoints(): Result<List<Waypoint>> {
        return try {
            Result.success(emptyList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}