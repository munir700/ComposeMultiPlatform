package kmp.core.mobile.intentLauncher


import kmp.core.mobile.utils.extensions.runOnMainThread
import platform.Foundation.NSURL
import platform.SafariServices.SFSafariViewController
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice
import platform.UIKit.UIModalPresentationCustom

class IntentLauncher : IIntentLauncher {

    override fun launchEmail(email: String, subject: String?, body: String?) = runOnMainThread {
        val url = "mailto:$email?subject=${subject.orEmpty()}&body=${body.orEmpty()}"
        openUrl(url)
    }

    override fun launchShareText(text: String) = runOnMainThread {
        // Get and validate the root view controller
        val rootViewController = UIApplication.sharedApplication
            .keyWindow
            ?.rootViewController
            ?: return@runOnMainThread

        // Create the activity controller
        val activityController = UIActivityViewController(
            activityItems = listOf(text),
            applicationActivities = null
        )

        // Then present it
        rootViewController.presentViewController(
            viewControllerToPresent = activityController,
            animated = true,
            completion = null
        )
    }

    override fun launchPhone(phone: String) = runOnMainThread {
        val url = "tel://$phone"
        openUrl(url)
    }

    override fun launchBrowser(url: String) = runOnMainThread {
        openUrl(url)
    }

    override fun launchCustomTab(url: String) = runOnMainThread {
        // Create and validate the url
        val nsUrl = NSURL.URLWithString(url) ?: return@runOnMainThread

        if (UIDevice.currentDevice.systemVersion.toDouble() >= 9.0) {
            // Get and validate the root view controller
            val rootViewController = UIApplication.sharedApplication
                .keyWindow
                ?.rootViewController
                ?: return@runOnMainThread

            val safariVC = SFSafariViewController(nsUrl)
            safariVC.modalPresentationStyle = UIModalPresentationCustom
            rootViewController.presentViewController(safariVC, animated = true, completion = null)
        } else {
            // Just open in the browser
            launchBrowser(url)
        }
    }

    private fun openUrl(url: String): Boolean {
        // Create the url
        val nsUrl = NSURL.URLWithString(url) ?: return false

        // Validate can open url
        if (UIApplication.sharedApplication.canOpenURL(nsUrl).not()) return false

        // Then open it
        runOnMainThread {
            UIApplication.sharedApplication.openURL(nsUrl, emptyMap<Any?, Any?>(), null)
        }
        return true
    }

    override fun launchMap(latitude: Double, longitude: Double, name: String?) {
        val geoUrl = "geo:0,0?q=${latitude},${longitude}(${name})"
        val encodedUrl = geoUrl.replace(" ", "%20") // Ensure proper encoding
        val nsUrl = NSURL(string = encodedUrl)

        if (UIApplication.sharedApplication.canOpenURL(nsUrl)) {
            UIApplication.sharedApplication.openURL(nsUrl)
        } else {
            val namePart = name?.let { "(${name})" } ?: ""
            val mapUrl = "http://maps.google.com/maps?q=loc:${latitude},${longitude}$namePart"
            openUrl(mapUrl)
        }
    }

    override fun isAppInstalled(appId: String): Boolean {
        val url = NSURL(string = "$appId://app")
        return UIApplication.sharedApplication.canOpenURL(url)
    }

    override fun openApp(appId: String): Boolean {
        val url = NSURL(string = "$appId://")
        return openUrl(url.absoluteString.orEmpty())
    }

    override fun openStore(appId: String): Boolean {
        val url = NSURL(string = "https://apps.apple.com/app/id$appId")
        return openUrl(url.absoluteString.orEmpty())
    }
}