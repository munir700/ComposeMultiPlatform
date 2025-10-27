package app.shared.mobile.di


import app.shared.mobile.initializer.AppInitializer
import app.shared.mobile.initializer.IAppInitializer
import app.shared.mobile.location.ILocationManager
import app.shared.mobile.location.LocationManager
import com.arkivanov.decompose.ComponentContext
import kmp.core.mobile.di.CoreDiQualifiers
import kmp.core.mobile.language.ILanguageManager
import kmp.core.mobile.language.LanguageManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal fun getPlatformModule(componentContext: ComponentContext?) = module {

    componentContext?.let {
        single<ComponentContext> { componentContext }
    }

    single<ILocationManager> {
        LocationManager(androidContext())
    }

    single<IAppInitializer> {
        AppInitializer()
    }

    single<ILanguageManager> {
        LanguageManager(
            get(CoreDiQualifiers.SHARED_PREFS_NORMAL)
        )
    }
}