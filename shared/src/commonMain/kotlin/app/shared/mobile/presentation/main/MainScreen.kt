package app.shared.mobile.presentation.main


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import app.shared.mobile.presentation.main.components.MainContent
import kmp.core.mobile.base.BaseScreen
import kmp.core.mobile.base.SIDE_EFFECTS_KEY
import kmp.core.mobile.base.rememberViewModel
import kmp.core.mobile.imagePicker.rememberImagePicker
import kmp.core.mobile.utils.extensions.consume

import app.shared.mobile.presentation.main.MainContract.Effect
import app.shared.mobile.presentation.main.MainContract.Event
import kmp.core.mobile.filePicker.rememberFilePicker


class MainScreen() : BaseScreen<MainViewModel>() {
    override val screenTag: String = "MainScreen" // This must match the key in factory

    @Composable
    override fun Content() {
        val viewModel = rememberViewModel<MainViewModel>()

        // image picker
        val imagePicker = rememberImagePicker(
            enableCropping = true
        )

        // file picker
        val filePicker = rememberFilePicker(fileTypes = listOf())

        // Register image picker
        imagePicker.registerPicker { imageBytes, fileName ->
            viewModel.setEvent(
                Event.ImagePicked(
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

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(MainContract.Event.Init)
        }

        // Render content
        MainContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}