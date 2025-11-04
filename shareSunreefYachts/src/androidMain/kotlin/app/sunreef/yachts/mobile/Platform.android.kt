package app.sunreef.yachts.mobile

import android.content.Context
import android.os.Build

/**
 * Android platform-specific implementations for Sunreef Yachts
 */

actual class Platform {
    actual val name: String = "Android"
    val osVersion: Int = Build.VERSION.SDK_INT
    val manufacturer: String = Build.MANUFACTURER
    val model: String = Build.MODEL
}

internal actual object PlatformContext {
    private lateinit var context: Context

    fun init(ctx: Context) {
        context = ctx
    }

    actual fun getContext(): Any = context
}
