package app.sunreef.yachts.mobile.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class SystemAlert(
    val id: String,
    val type: AlertType,
    val severity: AlertSeverity,
    val title: String,
    val message: String,
    val systemType: SystemType,
    val timestamp: Long,
    val acknowledged: Boolean = false,
    val actionRequired: Boolean = false
)