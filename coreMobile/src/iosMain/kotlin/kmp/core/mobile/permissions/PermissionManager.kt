package kmp.core.mobile.permissions

import kmp.core.mobile.permissions.enums.Permission
import kmp.core.mobile.permissions.enums.PermissionState
import kmp.core.mobile.permissions.exceptions.DeniedAlwaysException
import kmp.core.mobile.permissions.helpers.IPermissionDelegateFactory
import kmp.core.mobile.permissions.helpers.PermissionDelegate
import kmp.core.mobile.utils.extensions.openAppSettings


class PermissionManager(
    private val delegateFactory: IPermissionDelegateFactory
) : IPermissionManager {

    override suspend fun requestPermission(
        permission: Permission,
        openAppSettingsIfRequired: Boolean
    ) = when {
        openAppSettingsIfRequired -> handlePermissionRequestWithSettings(permission)
        else -> handlePermissionRequest(permission)
    }

    override suspend fun isPermissionGranted(permission: Permission): Boolean {
        return getPermissionState(permission) == PermissionState.Granted
    }

    override suspend fun getPermissionState(permission: Permission): PermissionState {
        return getDelegate(permission).getPermissionState()
    }

    private suspend fun handlePermissionRequest(permission: Permission) {
        getDelegate(permission).providePermission()
    }

    private suspend fun handlePermissionRequestWithSettings(permission: Permission) {
        val initialState = getPermissionState(permission)
        try {
            handlePermissionRequest(permission)
        } catch (exception: DeniedAlwaysException) {
            if (initialState == PermissionState.DeniedAlways) {
                openAppSettings()
            }
            throw exception
        }
    }

    private fun getDelegate(permission: Permission): PermissionDelegate {
         return delegateFactory.getDelegate(permission)
    }
}