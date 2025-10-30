package app.shared.mobile.presentation.main

import app.shared.mobile.data.remote.models.ItemModel
import kmp.core.mobile.base.ViewEvent
import kmp.core.mobile.base.ViewSideEffect
import kmp.core.mobile.base.ViewState

class MainContract {
    data class State(
        val isInitialized: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        data object Init : Event()
        data object DatePicker : Event()
        data object DateRangePicker : Event()
        data object ChoicePopup : Event()
        data object ConfirmPopup : Event()
        data object SuccessPopup : Event()
        data object TimePopup : Event()
        data object MessagePopup : Event()
        data object OpenMap : Event()
        data class ImagePicked(val bytes: ByteArray, val fileName: String?) : Event()
        data class PdfPicked(val filePath: String?, val fileName: String?) : Event()
    }

    sealed class Effect : ViewSideEffect{
        data object PickImageFromGallery: Effect()
        data object CaptureImageUsingCamera: Effect()
        data object PickPdf: Effect()
    }
}