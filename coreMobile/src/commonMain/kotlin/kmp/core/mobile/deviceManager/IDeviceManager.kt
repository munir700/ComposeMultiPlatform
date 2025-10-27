package kmp.core.mobile.deviceManager

interface IDeviceManager {
    fun getDeviceModel(): String
    fun getUdid(): String
    fun getDeviceName(): String
    fun getManufacturerModel(): String
    fun getManufacturer(): String
    fun getModel(): String
}