package kmp.core.mobile.filePicker

import kmp.core.mobile.imagePicker.models.DocType
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kmp.core.mobile.utils.extensions.getFileName
import kmp.core.mobile.utils.extensions.getFilePath

actual class FilePicker(
    private val activity: Activity,
    private val fileTypes: List<DocType>?,
    actual val maxFileSizeInKb: Float = 500f
) {
    private var onFilePicked: ((filePath: String, fileName: String) -> Unit)? = null

    private var pickerLauncher: ActivityResultLauncher<Intent>? = null

    @SuppressLint("ComposableNaming")
    @Composable
    actual fun registerFilePicker(onFilePicked: (filePath: String, fileName: String) -> Unit) {
        // Set onImagePicked
        this.onFilePicked = onFilePicked

        // Create file picker launcher
        pickerLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val uri = result.data?.data
            if (result.resultCode == Activity.RESULT_OK && uri != null) {
                onFilePicked.invoke(
                    uri.getFilePath(activity = activity).orEmpty(),
                    uri.getFileName(activity = activity).orEmpty()
                )
            }
        }
    }

    actual fun pickFile() {
        val contentIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)

            // If allowedFileTypes is null or empty, allow all file types ("*/*").
            type = fileTypes?.firstOrNull()?.toIntentFileType()

            // Put extra types if more than one is provided.
            if (!fileTypes.isNullOrEmpty()) {
                putExtra(Intent.EXTRA_MIME_TYPES, fileTypes.toIntentFileTypes())
            }
            putExtra(Intent.EXTRA_LOCAL_ONLY, true) //Return data on the local device
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false) //If select one or more files
                .addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        pickerLauncher?.launch(contentIntent)
    }
}

@Composable
actual fun rememberFilePicker(fileTypes: List<DocType>?): FilePicker {
    val activity = LocalActivity.current as Activity
    return remember(activity) {
        FilePicker(
            activity = activity,
            fileTypes = fileTypes
        )
    }
}

fun DocType?.toIntentFileType(): String {
    return when (this) {
        DocType.PDF -> "application/pdf"
        DocType.IMAGE -> "image/*"
        else -> "*/*"
    }
}

fun List<DocType>.toIntentFileTypes(): Array<String> {
    return this.map { it.toIntentFileType() }.toTypedArray()
}