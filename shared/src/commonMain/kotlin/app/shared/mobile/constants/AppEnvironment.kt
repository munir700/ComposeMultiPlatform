package app.shared.mobile.constants

import kmp.core.mobile.utils.CoreEnvironment


class AppEnvironment(
    override val isDebuggable: Boolean,
    override val isProd: Boolean,
    override val title: String,
    val mockServerUrl: String = "https://e576ddb9-6acc-47ad-ae9e-6bdd083a0e19.mock.pstmn.io",
    val baseApiUrl: String = "",
) : CoreEnvironment(
    isDebuggable = isDebuggable,
    isProd = isProd,
    title = title
) {
    companion object {
        val dev = AppEnvironment(
            isDebuggable = true,
            isProd = false,
            title = "Compose-App-DEV",
        )

        val prod = AppEnvironment(
            isDebuggable = false,
            isProd = true,
            title = "Compose-App",
        )
    }
}