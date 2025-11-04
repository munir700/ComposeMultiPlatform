package app.sunreef.yachts.mobile.domain.repositories

import app.sunreef.yachts.mobile.domain.models.CommandStatus
import app.sunreef.yachts.mobile.domain.models.RemoteControlCommand

interface IRemoteControlRepository {
    /**
     * Send remote control command
     */
    suspend fun sendCommand(command: RemoteControlCommand): Result<String>

    /**
     * Get command status
     */
    suspend fun getCommandStatus(commandId: String): Result<CommandStatus>

    /**
     * Get command history
     */
    suspend fun getCommandHistory(limit: Int = 50): Result<List<RemoteControlCommand>>

    /**
     * Cancel pending command
     */
    suspend fun cancelCommand(commandId: String): Result<Boolean>
}
