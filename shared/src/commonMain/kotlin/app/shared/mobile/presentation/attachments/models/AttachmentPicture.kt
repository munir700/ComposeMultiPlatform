package app.shared.mobile.presentation.attachments.models

import kmp.core.mobile.utils.CommonParcelable
import kmp.core.mobile.utils.CommonParcelize
import kmp.core.mobile.utils.CommonSerializable

@CommonParcelize
data class AttachmentPicture(
    val isPdf: Boolean = false,
    val name: String?,
    val uploadFileId: Int? = null,
    val filePath: String? = null,
    val fileBytes: ByteArray? = null
) : CommonSerializable, CommonParcelable