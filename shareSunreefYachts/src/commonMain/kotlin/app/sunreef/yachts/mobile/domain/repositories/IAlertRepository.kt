package app.sunreef.yachts.mobile.domain.repositories

import app.sunreef.yachts.mobile.domain.models.SystemAlert
import kotlinx.coroutines.flow.Flow

interface IAlertRepository {
    /**
     * Get active alerts
     */
    suspend fun getActiveAlerts(): Result<List<SystemAlert>>

    /**
     * Stream real-time alerts
     */
    fun streamAlerts(): Flow<SystemAlert>

    /**
     * Acknowledge alert
     */
    suspend fun acknowledgeAlert(alertId: String): Result<Boolean>

    /**
     * Get alert history
     */
    suspend fun getAlertHistory(days: Int = 7): Result<List<SystemAlert>>
}
