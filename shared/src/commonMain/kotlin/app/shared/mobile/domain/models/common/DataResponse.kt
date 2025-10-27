package app.shared.mobile.domain.models.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataResponse<T>(
    @SerialName("Body")
    val body: DataBody<T>? = null
)

@Serializable
data class DataBody<T>(
    @SerialName("Items")
    val body: List<T>? = emptyList(),

    @SerialName("MetaData")
    val metaData: MetaData? = null,
)