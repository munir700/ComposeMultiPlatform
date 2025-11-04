package app.sunreef.yachts.mobile.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class ModbusRegisterValue(
    val address: Int,
    val value: Int,
    val dataType: ModbusDataType,
    val timestamp: Long
)