package app.sunreef.yachts.mobile.di



import com.arkivanov.decompose.ComponentContext
import kmp.core.mobile.di.CoreDiQualifiers
import kmp.core.mobile.language.ILanguageManager
import kmp.core.mobile.language.LanguageManager
import org.koin.dsl.module

internal fun getPlatformModule(componentContext: ComponentContext?) = module {

    componentContext?.let {
        single<ComponentContext> { componentContext }
    }


    single<ILanguageManager> {
        LanguageManager(
            get(CoreDiQualifiers.SHARED_PREFS_NORMAL)
        )
    }
}