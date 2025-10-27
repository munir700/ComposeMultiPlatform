package app.shared.mobile.domain.models.uploadImage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadImageResponse(
    @SerialName("Code")
    val code: Int? = null,

    @SerialName("UploadFileID")
    val uploadedFileId: Int? = null,

    @SerialName("message")
    val message: String? = null,
)