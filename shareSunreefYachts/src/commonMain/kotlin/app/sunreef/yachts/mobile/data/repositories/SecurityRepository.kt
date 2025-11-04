package app.sunreef.yachts.mobile.data.repositories

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class SecurityRepository : ISecurityRepository {

    @OptIn(ExperimentalTime::class)
    val currentTimeMillis = Clock.System.now().toEpochMilliseconds()
    override suspend fun getSecurityData(): Result<SecurityData> {
        return try {
            Result.success(
                SecurityData(
                    alarmStatus = AlarmStatus.ARMED_AWAY,
                    doorsLocked = true,
                    windowsLocked = true,
                    motionDetected = false,
                    intrusionAlert = false,
                    fireAlarm = false,
                    timestamp = currentTimeMillis
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun streamSecurityData(): Flow<SecurityData> {
        return flow {
            while (true) {
                val data = getSecurityData().getOrNull()
                if (data != null) emit(data)
                kotlinx.coroutines.delay(500)
            }
        }
    }

    override suspend fun controlAlarm(command: String): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun controlDoors(locked: Boolean): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCameraStatus(): Result<List<CameraStatus>> {
        return try {
            Result.success(emptyList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}