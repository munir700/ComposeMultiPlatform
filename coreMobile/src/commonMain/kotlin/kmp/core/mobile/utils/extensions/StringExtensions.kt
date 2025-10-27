package kmp.core.mobile.utils.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import app.core.mobile.resources.Res
import app.core.mobile.resources.na
import io.ktor.utils.io.core.String
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.stringResource
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


fun String.format(vararg args: Any): String {
    var formattedString = this
    args.forEach { arg ->
        formattedString = formattedString.replaceFirst("%s", arg.toString())
    }
    return formattedString
}

fun String?.isNotNullOrEmpty(): Boolean {
    return this.isNullOrEmpty().not()
}

fun String?.ifNotNullOrEmpty(callback: (String) -> Unit) {
    if (!this.isNullOrEmpty()) {
        callback(this)
    }
}

fun String.removeAllWhiteSpaces(): String {
    return this.replace(" ", "")
}

fun String.isImageUrl(): Boolean {
    return Regex("([^\\s]+(\\.(?i)(jpe?g|png|gif|svg|bmp))$)").matches(this)
}

fun String.removeLeadingZeros(): String {
    var i = 0
    while (i < this.length && this[i] == '0') i++
    return if (i == this.length) "0" else this.substring(i)
}

private val PASSWORD_REGEX = Regex(
    "^(?=.*?[A-Z\u0621-\u064A])(?=.*?[a-z\u0621-\u064A])(?=.*?[0-9])(?=.*?[@_&,.:$!-]).{8,}\$"
)

fun String.isValidPassword(): Boolean {
    return this.isNotEmpty() && PASSWORD_REGEX.matches(this)
}

private val EMAIL_REGEX = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
fun String.isValidEmail(): Boolean {
    return !isNullOrEmpty() && EMAIL_REGEX.matches(this.trim())
}

private val US_PHONE_REGEX =
    Regex("^(?:\\+1\\s*)?(?:\\(\\d{3}\\)\\s*|\\d{3}[.-]?)?\\d{3}[.-]?\\d{4}\$")

fun String.isValidUsPhone(): Boolean {
    return !isNullOrEmpty() && US_PHONE_REGEX.matches(this.trim())
}

inline fun String.remove(substring: String) = replace(substring, "")

fun String?.nullIfEmpty() = if (isNullOrEmpty()) null else this

fun String.appendRequiredAsterisk() = "$this*"

fun String.dashIfEmpty() = this.ifEmpty { "-" }

fun String?.dashIfNullOrEmpty() = if (this?.isNotEmpty() == true) this else "-"

fun String.doubleDashIfEmpty() = this.ifEmpty { "--" }

fun String?.orDoubleDash() = if (this?.isNotEmpty() == true) this else "--"

fun String.takeIfNotEmpty() = this.takeIf { it.isNotEmpty() }

fun Char?.orEmpty(): String = this?.toString() ?: ""

fun String.getValidUrl(): String {
    return if (!this.startsWith("http://") && !this.startsWith("https://")) {
        "http://$this"
    } else {
        this
    }
}

fun String.capitalizeFirstLetter(): String {
    return try {
        this[0].uppercase() + substring(1).lowercase()
    } catch (e: Throwable) {
        this
    }
}

fun String.capitalizeEachWord(): String {
    return this.split(" ").joinToString(" ") { word ->
        word.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}

private val nonNumericRegex = Regex("[^0-9]")
fun String.removeAllNonNumeric(): String {
    return this.replace(nonNumericRegex, "")
}

fun String.removeLastChar(): String {
    if (this.isEmpty()) {
        return this
    }
    return this.substring(0, this.length - 1)
}

fun String.compareVersion(version: String): Int {
    val v1Components = this.split('.').map { it.toIntOrNull() ?: 0 }
    val v2Components = version.split('.').map { it.toIntOrNull() ?: 0 }

    val maxLength = maxOf(v1Components.size, v2Components.size)

    for (i in 0 until maxLength) {
        val v1 = if (i < v1Components.size) v1Components[i] else 0
        val v2 = if (i < v2Components.size) v2Components[i] else 0

        if (v1 < v2) return -1
        if (v1 > v2) return 1
    }

    return 0 // Versions are equal
}

fun String.isVersionGreaterThan(version: String): Boolean {
    val result = this.compareVersion(version)
    return result == 1
}

fun String?.containsIgnoreCase(other: String): Boolean {
    return this?.contains(other, true) == true
}

@OptIn(ExperimentalEncodingApi::class)
fun String.decodeBase64(): String {
    val decodedBytes = Base64.decode(this.subSequence(0, this.length))
    return String(decodedBytes)
}

inline fun <T> Iterable<T>.contains(predicate: (T) -> Boolean): Boolean {
    return firstOrNull(predicate) != null
}

fun String?.getValidKtorUrl(): String {
    val url = this.orEmpty().getValidUrl()
    return if (url.lastOrNull() == '/') {
        url
    } else {
        "$url/"
    }
}


fun String.formatWithMask(mask: String, maskChar: Char): String {
    val maxLength = mask.count { it == maskChar }
    val trimmed = this.take(maxLength)
    if (trimmed.isEmpty()) return ""

    return buildString {
        var maskIndex = 0
        var textIndex = 0
        while (textIndex < trimmed.length && maskIndex < mask.length) {
            if (mask[maskIndex] != maskChar) {
                val nextDigitIndex = mask.indexOf(maskChar, maskIndex)
                append(mask.substring(maskIndex, nextDigitIndex))
                maskIndex = nextDigitIndex
            }
            append(trimmed[textIndex++])
            maskIndex++
        }
    }
}

fun AnnotatedString.formatWithMask(mask: String, maskChar: Char): AnnotatedString {
    return buildAnnotatedString {
        val formatted = text.formatWithMask(
            mask = mask,
            maskChar = maskChar
        )

        append(formatted)
    }
}

fun String?.orEmptyIfNull(): String {
    return if (this == "null") "" else this ?: ""
}

@Composable
fun String?.orNA(): String {
    return this ?: stringResource(Res.string.na)
}

fun String.isValidUAEMobileNumber(): Boolean {
    return this.matches("^(?:\\+971|00971|0)?(?:50|51|52|54|55|56|58|2|3|4|6|7|9)\\d{7}\$".toRegex())
}

fun String.isJson(): Boolean {
    return trim().startsWith("{") && trim().endsWith("}")
}

@OptIn(ExperimentalSerializationApi::class)
private val jsonFormatter = Json {
    prettyPrint = true
    prettyPrintIndent = "  "
}

fun String.formatJson(): String {
    return try {
        val jsonElement = Json.parseToJsonElement(this)
        jsonFormatter.encodeToString(jsonElement)
    } catch (e: Exception) {
        this
    }
}
fun String.isImagePath(): Boolean {
    val imageExtensions = listOf("jpg", "jpeg", "png", "gif", "bmp", "webp", "heic", "heif")
    return this.substringAfterLast('.', "").lowercase() in imageExtensions
}

fun String.isVideoPath(): Boolean {
    val videoExtensions = listOf("mp4", "mkv", "avi", "mov", "flv", "wmv", "webm", "3gp", "m4v")
    return this.substringAfterLast('.', "").lowercase() in videoExtensions
}


expect fun String.formatHtml(): String

expect fun String.getQueryString(): HashMap<String, String>

expect fun Map<String, String?>.getParams(): String

expect fun String.decodeUrl(): String
