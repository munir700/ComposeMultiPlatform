package app.shared.mobile.domain.models.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MetaData(
    @SerialName("TotalRecord")
    val totalRecord: Double? = null
)
