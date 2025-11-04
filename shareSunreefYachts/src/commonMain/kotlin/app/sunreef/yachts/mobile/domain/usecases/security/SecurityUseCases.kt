package app.sunreef.yachts.mobile.domain.usecases.security

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*
import kotlinx.coroutines.flow.Flow

/**
 * Use cases for security system operations
 */

class GetSecurityDataUseCase(
    private val securityRepository: ISecurityRepository
) {
    suspend operator fun invoke(): Result<SecurityData> {
        return try {
            securityRepository.getSecurityData()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class StreamSecurityDataUseCase(
    private val securityRepository: ISecurityRepository
) {
    operator fun invoke(): Flow<SecurityData> {
        return securityRepository.streamSecurityData()
    }
}

class ArmAlarmUseCase(
    private val securityRepository: ISecurityRepository
) {
    suspend operator fun invoke(mode: String): Result<Boolean> {
        return try {
            securityRepository.controlAlarm(mode)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class LockDoorsUseCase(
    private val securityRepository: ISecurityRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        return try {
            securityRepository.controlDoors(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class UnlockDoorsUseCase(
    private val securityRepository: ISecurityRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        return try {
            securityRepository.controlDoors(false)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

