package app.shared.mobile.domain.models.uploadImage.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressInputs(

    @SerialName("Longitude")
    val longitude: String = "",

    @SerialName("Latitude")
    val latitude: String = "",
)