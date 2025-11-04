package app.sunreef.yachts.mobile.domain.usecases.remotecontrol

import app.sunreef.yachts.mobile.domain.models.*
import app.sunreef.yachts.mobile.domain.repositories.*

/**
 * Use cases for remote control operations
 */

class SendRemoteCommandUseCase(
    private val remoteControlRepository: IRemoteControlRepository
) {
    suspend operator fun invoke(command: RemoteControlCommand): Result<String> {
        return try {
            remoteControlRepository.sendCommand(command)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class GetCommandStatusUseCase(
    private val remoteControlRepository: IRemoteControlRepository
) {
    suspend operator fun invoke(commandId: String): Result<CommandStatus> {
        return try {
            remoteControlRepository.getCommandStatus(commandId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class GetCommandHistoryUseCase(
    private val remoteControlRepository: IRemoteControlRepository
) {
    suspend operator fun invoke(limit: Int = 50): Result<List<RemoteControlCommand>> {
        return try {
            remoteControlRepository.getCommandHistory(limit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

