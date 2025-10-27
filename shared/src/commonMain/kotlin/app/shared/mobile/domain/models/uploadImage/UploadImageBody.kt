package app.shared.mobile.domain.models.uploadImage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UploadImageBody(
    @SerialName("imageType")
    val imageType: Int,

    @SerialName("Base64File")
    val base64File: String,

    @SerialName("TaskID")
    val taskId: Int,

    @SerialName("Latitude")
    val latitude: Double,

    @SerialName("Longitude")
    val longitude: Double,

    @SerialName("UploadedBy")
    val uploadedBy: String
)
