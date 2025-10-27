package kmp.core.mobile.filePicker

import kmp.core.mobile.imagePicker.models.DocType
import androidx.compose.runtime.Composable

expect class FilePicker {

    val maxFileSizeInKb: Float

    @Composable
    fun registerFilePicker(onFilePicked: (String, String) -> Unit)

    fun pickFile()
}

@Composable
expect fun rememberFilePicker(fileTypes: List<DocType>?): FilePicker