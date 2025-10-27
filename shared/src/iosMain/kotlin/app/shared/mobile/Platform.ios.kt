package app.shared.mobile

import platform.UIKit.UIDevice

actual fun getPlatform(): Platform {
    return IOSPlatform()
}

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}