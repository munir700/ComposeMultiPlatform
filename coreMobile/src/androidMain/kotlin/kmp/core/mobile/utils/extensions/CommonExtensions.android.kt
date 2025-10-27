package kmp.core.mobile.utils.extensions

import android.os.Build
import android.util.LayoutDirection
import androidx.core.text.layoutDirection
import kmp.core.mobile.PlatformType
import kmp.core.mobile.language.Language
import java.util.Locale
import java.util.UUID

actual fun randomUUID() = UUID.randomUUID().toString()

actual fun PlatformType.Companion.current(): PlatformType {
    return PlatformType.ANDROID
}

actual fun getSystemLanguage(): Language {
    val locale = Locale.getDefault()
    val languageCode = locale.language
    val languageTitle = locale.displayName
    val isRtl = locale.layoutDirection == LayoutDirection.RTL

    return Language(
        code = languageCode,
        name = languageTitle,
        isRtl = isRtl
    )
}

actual fun getOSVersion(): String {
    return Build.VERSION.SDK_INT.toString()
}
