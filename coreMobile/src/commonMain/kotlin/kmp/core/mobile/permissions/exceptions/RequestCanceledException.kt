package kmp.core.mobile.permissions.exceptions

import kmp.core.mobile.permissions.enums.Permission

class RequestCanceledException(
    val permission: Permission,
    message: String? = null
) : Exception(message)
