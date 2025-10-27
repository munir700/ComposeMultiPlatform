package app.shared.mobile.domain.models.uploadImage

import kmp.core.mobile.utils.CommonParcelable
import kmp.core.mobile.utils.CommonParcelize
import kmp.core.mobile.utils.CommonSerializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@CommonParcelize
@Serializable
data class UploadPhoto(

    @SerialName("UploadFileID")
    val uploadFileId: Int = 0,

    @SerialName("FileTypeID")
    val fileTypeId: Int = 0,

    val imageBase64: String = "",
) : CommonSerializable, CommonParcelable