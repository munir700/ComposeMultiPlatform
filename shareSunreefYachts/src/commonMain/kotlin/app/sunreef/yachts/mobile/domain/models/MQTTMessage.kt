package app.sunreef.yachts.mobile.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class MQTTMessage(
    val topic: String,
    val payload: String,
    val qos: Int,
    val retained: Boolean,
    val timestamp: Long
)