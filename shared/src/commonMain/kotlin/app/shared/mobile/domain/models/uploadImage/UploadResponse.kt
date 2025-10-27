package app.shared.mobile.domain.models.uploadImage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadResponse<T>(
    @SerialName("Code")
    val code: Int = 0,

    @SerialName("Message")
    val message: String = "",

    @SerialName("Photo")
    val photo: T? = null,

)
