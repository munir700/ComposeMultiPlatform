package kmp.core.mobile.filePicker

import kmp.core.mobile.imagePicker.models.DocType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.interop.LocalUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURL
import platform.UIKit.UIDocumentPickerDelegateProtocol
import platform.UIKit.UIDocumentPickerMode
import platform.UIKit.UIDocumentPickerViewController
import platform.UIKit.UIViewController
import platform.darwin.NSObject

actual class FilePicker(
    private val rootController: UIViewController,
    private val fileTypes: List<DocType>?,
    actual val maxFileSizeInKb: Float = 500f
) {
    private var onFilePicked: ((filePath: String, fileName: String) -> Unit)? = null
    private val delegate =  object : NSObject(), UIDocumentPickerDelegateProtocol {
        override fun documentPicker(
            controller: UIDocumentPickerViewController,
            didPickDocumentAtURL: NSURL
        ) {
            didPickDocumentAtURL.startAccessingSecurityScopedResource()
            val fileName = didPickDocumentAtURL.lastPathComponent
            val filePath = didPickDocumentAtURL.path
            onFilePicked?.invoke(filePath.orEmpty(), fileName.orEmpty())
            didPickDocumentAtURL.stopAccessingSecurityScopedResource()
            controller.dismissViewControllerAnimated(true, null)
        }

        override fun documentPickerWasCancelled(controller: UIDocumentPickerViewController) {
            controller.dismissViewControllerAnimated(true, null)
        }
    }

    @Composable
    actual fun registerFilePicker(onFilePicked: (String, String) -> Unit) {
        this.onFilePicked = onFilePicked
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun pickFile() {
        // create documentPicker
        val documentPicker = UIDocumentPickerViewController(
            documentTypes = fileTypes?.getDocumentTypes() ?: emptyList<String>(),
            inMode = UIDocumentPickerMode.UIDocumentPickerModeImport
        )
        documentPicker.allowsMultipleSelection = false

        // Present the document picker
        rootController.presentViewController(documentPicker, true) {
            documentPicker.delegate = delegate
        }
    }
}

@Composable
actual fun rememberFilePicker(fileTypes: List<DocType>?): FilePicker {
    val rootController = LocalUIViewController.current
    return remember {
        FilePicker(
            rootController = rootController,
            fileTypes = fileTypes
        )
    }
}

private fun DocType?.toUTI(): String {
    return when (this) {
        DocType.PDF -> "com.adobe.pdf"
        DocType.IMAGE -> "public.image"
        else -> "*/*"
    }
}

private fun List<DocType>.getDocumentTypes() =
    this.map { it.toUTI() }

