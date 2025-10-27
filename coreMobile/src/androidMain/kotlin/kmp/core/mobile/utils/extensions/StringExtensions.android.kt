package kmp.core.mobile.utils.extensions

import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.text.Html
import androidx.compose.ui.text.AnnotatedString
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.io.File
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import androidx.core.net.toUri


fun CharSequence.toAnnotatedString(): AnnotatedString {
    return AnnotatedString(this.toString())
}

fun AnnotatedString.filterDigits(): AnnotatedString {
    return this.filter { it.isDigit() }.toAnnotatedString()
}

actual fun String.formatHtml(): String {
    val spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(this)
    }
    return spanned.toString().trim { it <= ' ' }
}

actual fun String.getQueryString(): HashMap<String, String> {
    val uri = this.toUri()
    val map = HashMap<String, String>()

    for (paramName in uri.queryParameterNames) {
        paramName?.let {
            val paramValue = uri.getQueryParameter(it)
            paramValue?.let { value ->
                map[it] = value
            }
        }
    }
    return map
}

actual fun Map<String, String?>.getParams(): String {
    val params = StringBuilder("?")

    this.keys.forEachIndexed { index, key ->
        if (index > 0) {
            params.append("&")
        }
        params.append(key).append("=").append(this[key])
    }
    return params.toString()
}

actual fun String.decodeUrl(): String {
    return try {
        val decoded = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            URLDecoder.decode(this, StandardCharsets.UTF_8)
        } else {
            URLDecoder.decode(this, "UTF-8")
        }
        decoded ?: this
    } catch (e: Exception) {
        this
    }
}


fun Uri.getFileName(activity: Activity): String? {
    var fileName: String? = null
    when (this.scheme) {
        "content" -> {
            val cursor: Cursor? = try {
                activity.contentResolver.query(this, null, null, null, null)
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }

            cursor?.use {
                if (it.moveToFirst()) {
                    val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex != -1) {
                        fileName = it.getString(nameIndex)
                    }
                }
            }
        }

        "file" -> {
            this.path?.let { path ->
                fileName = File(path).name
            }
        }
    }

    if (fileName == null) {
        fileName = this.path?.substringAfterLast('/')
    }
    return fileName
}

fun Uri.getFilePath(activity: Activity): String? {
    var filePath: String? = null

    when (this.scheme) {
        "content" -> {
            val cursor: Cursor? = try {
                activity.contentResolver.query(this, null, null, null, null)
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }

            cursor?.use {
                if (it.moveToFirst()) {
                    val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex != -1) {
                        val fileName = it.getString(nameIndex)
                        val tempFile = File(activity.cacheDir, fileName)
                        try {
                            activity.contentResolver.openInputStream(this)?.use { inputStream ->
                                tempFile.outputStream().use { outputStream ->
                                    inputStream.copyTo(outputStream)
                                }
                            }
                            filePath = tempFile.absolutePath
                        } catch (e: Exception) {
                            FirebaseCrashlytics.getInstance().recordException(e)
                        }
                    }
                }
            }
        }

        "file" -> {
            // If it's a file URI, just return the path
            filePath = this.path
        }
    }

    return filePath
}