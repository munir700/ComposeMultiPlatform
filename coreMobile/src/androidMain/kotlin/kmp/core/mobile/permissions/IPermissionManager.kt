package kmp.core.mobile.permissions

import androidx.activity.ComponentActivity
import kmp.core.mobile.permissions.enums.Permission
import kmp.core.mobile.permissions.enums.PermissionState

actual interface IPermissionManager {
    actual suspend fun requestPermission(
        permission: Permission,
        openAppSettingsIfRequired: Boolean
    )
    actual suspend fun isPermissionGranted(permission: Permission): Boolean
    actual suspend fun getPermissionState(permission: Permission): PermissionState
    fun bind(activity: ComponentActivity)
}