package kmp.core.mobile.di

import com.arkivanov.decompose.ComponentContext
import com.liftric.kvault.KVault
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.crashlytics.crashlytics
import dev.gitlive.firebase.remoteconfig.remoteConfig
import io.ktor.client.engine.android.Android
import kmp.core.mobile.deviceManager.DeviceManager
import kmp.core.mobile.deviceManager.IDeviceManager
import kmp.core.mobile.fileManager.FileManager
import kmp.core.mobile.fileManager.IFileManager
import kmp.core.mobile.imagePicker.IImageManager
import kmp.core.mobile.imagePicker.ImageManager
import kmp.core.mobile.intentLauncher.IIntentLauncher
import kmp.core.mobile.intentLauncher.IntentLauncher
import kmp.core.mobile.language.ILanguageManager
import kmp.core.mobile.language.LanguageManager
import kmp.core.mobile.logger.FirebaseErrorLogger
import kmp.core.mobile.logger.IErrorLogger
import kmp.core.mobile.notifications.INotificationManager
import kmp.core.mobile.notifications.NotificationManager
import kmp.core.mobile.permissions.IPermissionManager
import kmp.core.mobile.permissions.PermissionManager
import kmp.core.mobile.remoteConfigs.FirebaseRemoteConfigs
import kmp.core.mobile.remoteConfigs.IRemoteConfigs
import kmp.core.mobile.utils.CoreEnvironment
import org.koin.android.ext.koin.androidContext
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

actual fun corePlatformModule() = module {
    single {
        Android.create()
    }

    single {
        KVault(
            context = androidContext(),
            fileName = get<CoreEnvironment>().title + "_secured"
        )
    }

    single<Settings.Factory> {
        SharedPreferencesSettings.Factory(
            androidContext().applicationContext
        )
    }


    single {
        Firebase.crashlytics
    }

    single {
        Firebase.remoteConfig
    }

    single<IErrorLogger> {
        FirebaseErrorLogger(get())
    }

    single<IRemoteConfigs> {
        FirebaseRemoteConfigs(
            sharedPrefsManager = get(CoreDiQualifiers.SHARED_PREFS_SECURED),
            firebaseConfigs = get(),
            logger = get()
        )
    }

    single<IIntentLauncher> {
        IntentLauncher(androidContext())
    }

    single<IDeviceManager> {
        DeviceManager(context = androidContext())
    }

    single<IImageManager> {
        ImageManager()
    }

    single<IFileManager> {
        FileManager(context = androidContext())
    }

    single<NotifierManager> {
        val environment = get<CoreEnvironment>()
        NotifierManager.apply {
            initialize(
                configuration = NotificationPlatformConfiguration.Android(
                    showPushNotification = environment.showRemoteNotifications,
                    notificationChannelData = NotificationPlatformConfiguration.Android.NotificationChannelData(),
                    notificationIconResId = environment.androidRemoteNotificationIcon
                        ?: 0// TODO Uncomment R.drawable.ic_default_notifications_icon,
                )
            )
        }
    }

    single<INotificationManager> {
        NotificationManager(
            context = androidContext(),
            notifier = get()
        )
    }

    single<IPermissionManager> {
        PermissionManager(androidContext())
    }

    single<ILanguageManager> {
        LanguageManager(
            get(CoreDiQualifiers.SHARED_PREFS_NORMAL)
        )
    }
}


actual inline fun <reified T : ComponentContext> Module.commonViewModel(
    qualifier: Qualifier?,
    noinline definition: Definition<T>
): KoinDefinition<T> {
    return factory(qualifier, definition) // Use factory instead of viewModel
}