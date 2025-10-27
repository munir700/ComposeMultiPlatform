package app.shared.mobile.location

import kmp.core.mobile.utils.CommonParcelable
import kmp.core.mobile.utils.CommonParcelize
import kmp.core.mobile.utils.CommonSerializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@CommonParcelize
@Serializable
data class Location(
    @SerialName("Latitude")
    val latitude: Double? = 0.0,
    @SerialName("Longitude")
    val longitude: Double? = 0.0
) : CommonParcelable, CommonSerializable