package app.shared.mobile

import android.os.Build

actual fun getPlatform(): Platform {
    return AndroidPlatform()
}

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}