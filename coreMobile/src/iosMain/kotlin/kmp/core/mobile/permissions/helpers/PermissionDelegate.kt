package kmp.core.mobile.permissions.helpers

import kmp.core.mobile.permissions.enums.PermissionState


interface PermissionDelegate {
    suspend fun providePermission()
    suspend fun getPermissionState(): PermissionState
}
