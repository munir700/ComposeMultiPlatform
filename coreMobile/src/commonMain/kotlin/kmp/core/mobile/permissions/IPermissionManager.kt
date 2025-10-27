package kmp.core.mobile.permissions

import kmp.core.mobile.permissions.enums.Permission
import kmp.core.mobile.permissions.enums.PermissionState

expect interface IPermissionManager {
    suspend fun requestPermission(
        permission: Permission,
        openAppSettingsIfRequired: Boolean = true
    )
    suspend fun isPermissionGranted(permission: Permission): Boolean
    suspend fun getPermissionState(permission: Permission): PermissionState
}
