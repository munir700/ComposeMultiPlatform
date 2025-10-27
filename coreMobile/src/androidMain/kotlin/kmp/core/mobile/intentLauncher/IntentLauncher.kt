package kmp.core.mobile.intentLauncher


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.Composable
//import androidx.browser.customtabs.CustomTabsIntent
import java.util.Locale
import androidx.core.net.toUri
import app.core.mobile.R

class IntentLauncher(private val context: Context) : IIntentLauncher {

    override fun launchEmail(
        email: String,
        subject: String?,
        body: String?
    ) = try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:".toUri()
            flags = Intent.FLAG_ACTIVITY_NEW_TASK

            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            subject?.let {
                putExtra(Intent.EXTRA_SUBJECT, it)
            }
            body?.let {
                putExtra(Intent.EXTRA_TEXT, it)
            }
        }

        context.startActivity(intent)
    } catch (_: Throwable) {
        Toast.makeText(
            context,
            R.string.no_email_apps_found_on_your_device,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun launchShareText(text: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(shareIntent)
    }

    override fun launchPhone(phone: String) = try {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    } catch (t: Throwable) {
        Toast.makeText(
            context,
            R.string.no_phone_apps_found_on_your_device,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun launchBrowser(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (_: Throwable) {
            val message = context.getString(R.string.no_browser_installed)
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun launchCustomTab(url: String) {
        //TODO Uncomment
       /* try {
            val intent = CustomTabsIntent.Builder()
                .setShowTitle(true)
                .build()

            intent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.launchUrl(context, Uri.parse(url))
        } catch (_: Throwable) {
            Toast.makeText(
                context,
                R.string.no_browser_installed,
                Toast.LENGTH_LONG
            ).show()
        }*/
    }

    override fun launchMap(latitude: Double, longitude: Double, name: String?) {
        try {
            // Attempt to launch a geo intent
            val mapIntentUri = String.format(
                Locale.ENGLISH,
                "geo:0,0?q=%f,%f(%s)",
                latitude,
                longitude,
                Uri.encode(name)
            ).toUri()
            val intent = Intent(Intent.ACTION_VIEW, mapIntentUri).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)

        } catch (_: Throwable) {

            val mapUrl = String.format(
                Locale.ENGLISH,
                "http://maps.google.com/maps?q=loc:%f,%f(%s)",
                latitude,
                longitude,
                Uri.encode(name)
            )

            // Launch browser
            launchBrowser(mapUrl)
        }
    }

    override fun isAppInstalled(appId: String): Boolean {
        val packageManager = context.applicationContext.packageManager
        return try {
            packageManager.getPackageInfo(appId, 0)
            true
        } catch (e: Throwable) {
            false
        }
    }

    override fun openApp(appId: String) = try {
        val packageManager = context.applicationContext.packageManager
        val intent = packageManager.getLaunchIntentForPackage(appId)

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            true
        } else {
            false
        }
    } catch (_: Throwable) {
        false
    }

    override fun openStore(appId: String): Boolean {
        return try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$appId")
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)

            true
        } catch (_: Throwable) {
            false
        }
    }
}