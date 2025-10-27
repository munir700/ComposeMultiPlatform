package app.shared.mobile.presentation.attachments.chooser.models

import app.shared.mobile.domain.models.attachments.Attachment
import app.shared.mobile.presentation.attachments.models.AttachmentPicture
import app.shared.mobile.domain.models.attachments.convertToDocTypes
import kmp.core.mobile.imagePicker.models.DocType
import kmp.core.mobile.utils.CommonParcelable
import kmp.core.mobile.utils.CommonParcelize
import kmp.core.mobile.utils.CommonSerializable
import kmp.core.mobile.utils.extensions.orZero


@CommonParcelize
data class AttachmentChooserUIModel(
    val isMandatory: Boolean,
    val title: String,
    val description: String,
    val minPage: Int,
    val maxPage: Int,
    val docTypes: List<DocType>?,
    val attachedPics: List<AttachmentPicture>?
) : CommonSerializable, CommonParcelable {
    fun isPdfAllow(): Boolean {
        return docTypes?.contains(DocType.PDF) == true
    }
}

fun Attachment.toPicAttachmentUIModel(attachedPics: List<AttachmentPicture>?) =
    AttachmentChooserUIModel(
        isMandatory = (mandatory.orZero() == 1),
        title = buttonText.orEmpty(),
        description = scanDescription.orEmpty(),
        minPage = minPage.orZero(),
        maxPage = maxPage.orZero(),
        docTypes = docType?.convertToDocTypes(),
        attachedPics = attachedPics
    )


