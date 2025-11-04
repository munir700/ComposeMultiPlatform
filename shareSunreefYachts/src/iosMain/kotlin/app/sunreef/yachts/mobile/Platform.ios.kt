package app.sunreef.yachts.mobile

import platform.UIKit.UIDevice

/**
 * iOS platform-specific implementations for Sunreef Yachts
 */

actual class Platform {
    actual val name: String = "iOS"
    val osVersion: String = UIDevice.currentDevice.systemVersion
    val model: String = UIDevice.currentDevice.model
}

internal actual object PlatformContext {
    actual fun getContext(): Any = UIDevice.currentDevice
}
