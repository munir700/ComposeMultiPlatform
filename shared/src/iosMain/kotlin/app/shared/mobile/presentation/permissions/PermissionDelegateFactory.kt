package app.shared.mobile.presentation.permissions

import kmp.core.mobile.permissions.enums.Permission
import kmp.core.mobile.permissions.helpers.IPermissionDelegateFactory
import kmp.core.mobile.permissions.helpers.PermissionDelegate

class PermissionDelegateFactory : IPermissionDelegateFactory {
    private val locationManagerDelegate = LocationManagerDelegate()

    override fun getDelegate(permission: Permission): PermissionDelegate {
        return when (permission) {
            Permission.LOCATION,
            Permission.COARSE_LOCATION,
            Permission.BACKGROUND_LOCATION -> LocationPermissionDelegate(
                locationManagerDelegate = locationManagerDelegate,
                permission = permission
            )

            Permission.REMOTE_NOTIFICATION -> RemoteNotificationPermissionDelegate()

            else -> error("Permission delegate not found for $permission")
        }
    }
}