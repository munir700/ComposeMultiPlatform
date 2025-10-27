package kmp.core.mobile.permissions.helpers

import kmp.core.mobile.permissions.enums.PermissionState


class AlwaysGrantedPermissionDelegate : PermissionDelegate {
    override suspend fun providePermission() = Unit
    override suspend fun getPermissionState() = PermissionState.Granted
}
