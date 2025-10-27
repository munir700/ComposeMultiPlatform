package kmp.core.mobile.intentLauncher

import androidx.compose.runtime.Composable

interface IIntentLauncher {
    fun launchEmail(
        email: String,
        subject: String? = null,
        body: String? = null
    )

    fun launchShareText(text: String)

    fun launchPhone(phone: String)

    fun launchBrowser(url: String)

    fun launchCustomTab(url: String)

    fun launchMap(latitude: Double, longitude: Double, name: String? = null)

    fun isAppInstalled(appId: String): Boolean

    fun openApp(appId: String): Boolean

    fun openStore(appId: String): Boolean
}