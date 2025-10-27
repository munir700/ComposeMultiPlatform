package kmp.core.mobile.deviceManager

import platform.UIKit.UIDevice

class DeviceManager : IDeviceManager {
    override fun getDeviceModel(): String {
        return UIDevice.currentDevice.model
    }

    override fun getUdid(): String {
        return UIDevice.currentDevice.identifierForVendor?.UUIDString.orEmpty()
    }

    override fun getDeviceName(): String {
        return "iOS"
    }

    override fun getManufacturerModel(): String {
        return "${getManufacturer()} ${getModel()}"
    }

    override fun getManufacturer(): String {
        return "Apple"
    }

    override fun getModel(): String {
        return UIDevice.currentDevice.model
    }
}
