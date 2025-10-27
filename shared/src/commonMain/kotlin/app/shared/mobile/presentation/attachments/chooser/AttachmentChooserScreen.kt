package app.shared.mobile.presentation.attachments.chooser

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import app.shared.mobile.presentation.attachments.chooser.models.AttachmentChooserUIModel
import app.shared.mobile.presentation.attachments.models.CategoryPhoto
import kmp.core.mobile.base.BaseScreen
import kmp.core.mobile.base.SIDE_EFFECTS_KEY
import kmp.core.mobile.base.rememberViewModel
import kmp.core.mobile.filePicker.rememberFilePicker
import kmp.core.mobile.imagePicker.rememberImagePicker
import app.shared.mobile.presentation.attachments.chooser.AttachmentChooserContract.Event
import app.shared.mobile.presentation.attachments.chooser.AttachmentChooserContract.Effect
import app.shared.mobile.presentation.attachments.chooser.components.AttachmentChooserContent
import kmp.core.mobile.utils.extensions.consume

class AttachmentChooserScreen(
    private val picAttachment: AttachmentChooserUIModel? = null,
    private val evaluationCategoryPhoto: CategoryPhoto? = null,
) : BaseScreen<AttachmentChooserViewModel>() {
    @Composable
    override fun Content() {
        // Init main objects
        val viewModel = rememberViewModel<AttachmentChooserViewModel>()

        // image picker
        val imagePicker = rememberImagePicker(
            enableCropping = true
        )

        // file picker
        val filePicker = rememberFilePicker(fileTypes = picAttachment?.docTypes)


        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(
                AttachmentChooserContract.Event.Init(
                    pictureAttachment = picAttachment,
                    categoryPhoto = evaluationCategoryPhoto
                )
            )
        }

        // Register image picker
        imagePicker.registerPicker { imageBytes, fileName ->
            viewModel.setEvent(
                AttachmentChooserContract.Event.ImagePicked(
                    bytes = imageBytes,
                    fileName = fileName
                )
            )
        }

        // Register image picker
        filePicker.registerFilePicker { filePath, fileName ->
            viewModel.setEvent(
                Event.PdfPicked(
                    filePath = filePath,
                    fileName = fileName
                )
            )
        }

        // Handle side effects
        viewModel.effect.consume { effect ->
            when (effect) {
                Effect.PickImageFromGallery -> imagePicker.pickFromGallery()
                Effect.CaptureImageUsingCamera -> imagePicker.captureUsingCamera()
                Effect.PickPdf -> filePicker.pickFile()
            }
        }

        // Render content
        AttachmentChooserContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}