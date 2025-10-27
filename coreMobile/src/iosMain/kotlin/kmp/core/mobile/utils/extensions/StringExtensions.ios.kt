package kmp.core.mobile.utils.extensions

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSAttributedString
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.NSURLComponents
import platform.Foundation.NSURLQueryItem
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataWithBytes
import platform.Foundation.stringByRemovingPercentEncoding
import platform.UIKit.NSCharacterEncodingDocumentAttribute
import platform.UIKit.NSDocumentTypeDocumentAttribute
import platform.UIKit.NSHTMLTextDocumentType
import platform.UIKit.create

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual fun String.formatHtml(): String {
    val data = this.encodeToByteArray().toNSData()
    val attributedString = NSAttributedString.create(
        data = data,
        options = mapOf(
            NSDocumentTypeDocumentAttribute to NSHTMLTextDocumentType,
            NSCharacterEncodingDocumentAttribute to NSUTF8StringEncoding
        ),
        documentAttributes = null,
        error = null
    )
    return attributedString?.string ?: this
}


actual fun String.getQueryString(): HashMap<String, String> {
    val map = HashMap<String, String>()

    val urlComponents = NSURLComponents(this)
    val queryItems = urlComponents.queryItems as? List<NSURLQueryItem> ?: return map

    for (item in queryItems) {
        val paramName = item.name
        val paramValue = item.value ?: continue
        map[paramName] = paramValue
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

@OptIn(ExperimentalForeignApi::class)
fun ByteArray.toNSData(): NSData = this.usePinned { pinned ->
    NSData.dataWithBytes(pinned.addressOf(0), size.toULong())
}

@OptIn(BetaInteropApi::class)
actual fun String.decodeUrl(): String {
    return try {
        NSString.create(string = this).stringByRemovingPercentEncoding() ?: this
    } catch (e: Exception) {
        this
    }
}