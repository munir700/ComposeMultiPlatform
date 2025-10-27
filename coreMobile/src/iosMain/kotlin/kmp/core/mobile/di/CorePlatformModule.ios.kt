package kmp.core.mobile.di

import com.arkivanov.decompose.ComponentContext
import com.liftric.kvault.KVault
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import io.ktor.client.engine.darwin.Darwin
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
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import platform.UserNotifications.UNUserNotificationCenter


actual fun corePlatformModule() = module {

    single {
        Darwin.create()
    }

    single {
        KVault()
    }

    single<Settings.Factory> {
        NSUserDefaultsSettings.Factory()
    }


    single<IErrorLogger> {
        FirebaseErrorLogger()
    }

    single<IRemoteConfigs> {
        FirebaseRemoteConfigs(
            sharedPrefsManager = get(CoreDiQualifiers.SHARED_PREFS_SECURED),
            //firebaseConfigs = get(),
            logger = get()
        )
    }

    single<ILanguageManager> {
        LanguageManager(
            get(CoreDiQualifiers.SHARED_PREFS_NORMAL)
        )
    }

    single<IIntentLauncher> {
        IntentLauncher()
    }

    single<IDeviceManager> {
        DeviceManager()
    }

    single<IImageManager> {
        ImageManager()
    }

    single<IFileManager> {
        FileManager()
    }


    single<NotifierManager> {
        val environment = get<CoreEnvironment>()
        NotifierManager.apply {
            initialize(
                NotificationPlatformConfiguration.Ios(
                    showPushNotification = environment.showRemoteNotifications,
                    askNotificationPermissionOnStart = environment.askRemoteNotificationPermissionOnStart
                )
            )
        }
    }

    single<INotificationManager> {
        NotificationManager(
            notificationCenter = UNUserNotificationCenter.currentNotificationCenter(),
            notifier = get()
        )
    }

    single<IPermissionManager> {
        PermissionManager(
            delegateFactory = get()
        )
    }
}

actual inline fun <reified T : ComponentContext> Module.commonViewModel(
    qualifier: Qualifier?,
    noinline definition: Definition<T>
): KoinDefinition<T> {
    return factory(qualifier, definition) // Use factory instead of viewModel
}