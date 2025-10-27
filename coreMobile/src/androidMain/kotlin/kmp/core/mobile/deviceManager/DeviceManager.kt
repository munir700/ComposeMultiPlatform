package kmp.core.mobile.deviceManager

import android.content.Context
import android.os.Build
import android.provider.Settings

class DeviceManager(
    private val context: Context
) : IDeviceManager {
    override fun getDeviceModel(): String {
        return Build.MODEL
    }

    override fun getUdid(): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    override fun getDeviceName(): String {
        return "Android"
    }

    override fun getManufacturerModel(): String {
        return "${getManufacturer()} ${getModel()}"
    }

    override fun getManufacturer(): String {
        return Build.MANUFACTURER.orEmpty()
    }

    override fun getModel(): String {
        return Build.MODEL.orEmpty()
    }
}