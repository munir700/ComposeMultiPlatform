package app.sunreef.yachts.mobile.domain.usecases.navigation

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*
import kotlinx.coroutines.flow.Flow

/**
 * Use cases for navigation operations
 */

class GetCurrentPositionUseCase(
    private val navigationRepository: INavigationRepository
) {
    suspend operator fun invoke(): Result<NavigationData> {
        return try {
            navigationRepository.getCurrentPosition()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class StreamNavigationDataUseCase(
    private val navigationRepository: INavigationRepository
) {
    operator fun invoke(): Flow<NavigationData> {
        return navigationRepository.streamNavigationData()
    }
}

class SetDestinationUseCase(
    private val navigationRepository: INavigationRepository
) {
    suspend operator fun invoke(waypoint: Waypoint): Result<Boolean> {
        return try {
            navigationRepository.setDestination(waypoint)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class GetActiveRouteUseCase(
    private val navigationRepository: INavigationRepository
) {
    suspend operator fun invoke(): Result<Route?> {
        return try {
            navigationRepository.getActiveRoute()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class SaveRouteUseCase(
    private val navigationRepository: INavigationRepository
) {
    suspend operator fun invoke(route: Route): Result<String> {
        return try {
            navigationRepository.saveRoute(route)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

