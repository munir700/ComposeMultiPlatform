package app.sunreef.yachts.mobile.data.repositories

import app.sunreef.yachts.mobile.domain.models.CommandStatus
import app.sunreef.yachts.mobile.domain.models.RemoteControlCommand
import app.sunreef.yachts.mobile.domain.repositories.IRemoteControlRepository

class RemoteControlRepository : IRemoteControlRepository {
    override suspend fun sendCommand(command: RemoteControlCommand): Result<String> {
        return try {
            Result.success(command.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCommandStatus(commandId: String): Result<CommandStatus> {
        return try {
            Result.success(CommandStatus.COMPLETED)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCommandHistory(limit: Int): Result<List<RemoteControlCommand>> {
        return try {
            Result.success(emptyList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun cancelCommand(commandId: String): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
