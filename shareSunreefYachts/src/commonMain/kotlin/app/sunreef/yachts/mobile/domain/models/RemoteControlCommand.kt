package app.sunreef.yachts.mobile.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class RemoteControlCommand(
    val id: String,
    val commandType: ControlCommandType,
    val targetSystem: SystemType,
    val parameters: Map<String, String> = emptyMap(),
    val priority: CommandPriority,
    val timestamp: Long,
    val executedAt: Long? = null,
    val status: CommandStatus
)
