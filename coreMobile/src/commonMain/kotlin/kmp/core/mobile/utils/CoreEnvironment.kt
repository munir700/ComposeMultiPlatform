package kmp.core.mobile.utils



open class CoreEnvironment(
    open val isDebuggable: Boolean,
    open val isProd: Boolean,
    open val title: String,
    open val uaePassClientId: String = "",
    open val uaePassClientSecret: String = "",
    open val uaePassAppId: String = "",
    open val uaePassBaseUrl: String = "",
    open val uaePassScheme: String = "",
    open val uaePassFailureHost: String = "",
    open val uaePassSuccessHost: String = "",
    open val uaePassRedirectUrl: String = "",
    open val uaePassAuthScope: String = "",
    open val uaePassAcrMobile: String = "",
    open val uaePassAcrWeb: String = "",
    open val uaePassStaticState: String = "",
    open val deepLinkDomains: List<String> = emptyList(),
    open val androidRemoteNotificationIcon: Int? = null,
    open val showRemoteNotifications: Boolean = false,
    open val askRemoteNotificationPermissionOnStart: Boolean = false,
)
