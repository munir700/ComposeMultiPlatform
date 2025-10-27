package app.shared.mobile.presentation.attachments.chooser

import app.shared.mobile.presentation.attachments.chooser.models.AttachmentChooserUIModel
import app.shared.mobile.presentation.attachments.models.CategoryPhoto
import kmp.core.mobile.base.ViewEvent
import kmp.core.mobile.base.ViewSideEffect
import kmp.core.mobile.base.ViewState

class AttachmentChooserContract {
    data class State(
        val isInitialized: Boolean = false,
        val isMoreUploadRequired: Boolean = false,
        val attachment: AttachmentChooserUIModel? = null,
        val categoryPhoto: CategoryPhoto? = null
    ) : ViewState

    sealed class Event : ViewEvent {
        data class Init(
            val pictureAttachment: AttachmentChooserUIModel? = null,
            val categoryPhoto: CategoryPhoto? = null,
        ) : Event()

        data object BackClicked : Event()
        data object SubmitAttachmentsClicked : Event()
        data object AddAttachmentClicked : Event()
        data class RemoveAttachmentClicked(val index: Int) : Event()
        data class ImagePicked(val bytes: ByteArray, val fileName: String?) : Event()
        data class PdfPicked(val filePath: String?, val fileName: String?) : Event()
    }

    sealed class Effect : ViewSideEffect {
        data object PickImageFromGallery : Effect()
        data object CaptureImageUsingCamera : Effect()
        data object PickPdf : Effect()
    }
}