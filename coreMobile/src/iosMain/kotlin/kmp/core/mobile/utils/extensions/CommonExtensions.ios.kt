package kmp.core.mobile.utils.extensions

import kmp.core.mobile.PlatformType
import kmp.core.mobile.language.Language
import kotlinx.coroutines.Dispatchers
import platform.Foundation.NSLocale
import platform.Foundation.NSLocaleLanguageDirectionRightToLeft
import platform.Foundation.NSThread
import platform.Foundation.NSURL
import platform.Foundation.NSUUID
import platform.Foundation.characterDirectionForLanguage
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.Foundation.localizedStringForLanguageCode
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString
import platform.UIKit.UIDevice
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

actual fun randomUUID(): String = NSUUID().UUIDString()
actual fun PlatformType.Companion.current() = PlatformType.IOS
actual fun getSystemLanguage(): Language {
    val locale = NSLocale.currentLocale
    val languageCode = locale.languageCode ?: "en"
    val languageTitle = locale.localizedStringForLanguageCode(languageCode) ?: "English"
    val isRtl = NSLocale.characterDirectionForLanguage(languageCode) == NSLocaleLanguageDirectionRightToLeft

    return Language(
        code = languageCode,
        name = languageTitle,
        isRtl = isRtl
    )
}

actual fun getOSVersion(): String {
    return UIDevice.currentDevice.systemVersion
}

fun runOnMainThreadCatching(block: () -> Unit) {
    try {
        runOnMainThread(block)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

fun runOnMainThread(block: () -> Unit) {
    dispatch_async(dispatch_get_main_queue()) {
        block()
    }
}
inline fun <T1> mainContinuation(
    noinline block: (T1) -> Unit
): (T1) -> Unit = { arg1 ->
    if (NSThread.isMainThread()) {
        block.invoke(arg1)
    } else {
        Dispatchers.Main.run {
            block.invoke(arg1)
        }
    }
}

inline fun <T1, T2> mainContinuation(
    noinline block: (T1, T2) -> Unit
): (T1, T2) -> Unit = { arg1, arg2 ->
    if (NSThread.isMainThread()) {
        block.invoke(arg1, arg2)
    } else {
        Dispatchers.Main.run {
            block.invoke(arg1, arg2)
        }
    }
}

fun openUrl(url: String) = runOnMainThreadCatching {
    val settingsUrl = NSURL.URLWithString(url)!!
    if (UIApplication.sharedApplication.canOpenURL(settingsUrl)) {
        UIApplication.sharedApplication.openURL(
            url = settingsUrl,
            options = emptyMap<Any?, Any?>(),
            completionHandler = null
        )
    }
}

fun openAppSettings() {
    openUrl(UIApplicationOpenSettingsURLString)
}