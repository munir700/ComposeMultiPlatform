package app.sunreef.yachts.mobile.data.repositories

import app.sunreef.yachts.mobile.domain.models.SystemAlert
import app.sunreef.yachts.mobile.domain.repositories.IAlertRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AlertRepository : IAlertRepository {
    override suspend fun getActiveAlerts(): Result<List<SystemAlert>> {
        return try {
            Result.success(emptyList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun streamAlerts(): Flow<SystemAlert> {
        return flow {
            kotlinx.coroutines.delay(Long.MAX_VALUE)
        }
    }

    override suspend fun acknowledgeAlert(alertId: String): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAlertHistory(days: Int): Result<List<SystemAlert>> {
        return try {
            Result.success(emptyList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}