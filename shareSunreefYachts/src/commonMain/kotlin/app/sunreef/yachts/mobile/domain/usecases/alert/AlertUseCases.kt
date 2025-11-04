package app.sunreef.yachts.mobile.domain.usecases.alert

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*
import kotlinx.coroutines.flow.Flow

/**
 * Use cases for alert operations
 */

class GetActiveAlertsUseCase(
    private val alertRepository: IAlertRepository
) {
    suspend operator fun invoke(): Result<List<SystemAlert>> {
        return try {
            alertRepository.getActiveAlerts()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class StreamAlertsUseCase(
    private val alertRepository: IAlertRepository
) {
    operator fun invoke(): Flow<SystemAlert> {
        return alertRepository.streamAlerts()
    }
}

class AcknowledgeAlertUseCase(
    private val alertRepository: IAlertRepository
) {
    suspend operator fun invoke(alertId: String): Result<Boolean> {
        return try {
            alertRepository.acknowledgeAlert(alertId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

