package kmp.core.mobile.permissions.helpers

import kmp.core.mobile.permissions.enums.Permission


interface IPermissionDelegateFactory {
    fun getDelegate(permission: Permission): PermissionDelegate
}