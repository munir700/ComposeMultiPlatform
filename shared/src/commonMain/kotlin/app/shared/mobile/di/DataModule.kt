package app.shared.mobile.di


import app.shared.mobile.data.local.preferences.AuthPreferences
import app.shared.mobile.data.local.preferences.AuthPreferencesImpl
import app.shared.mobile.data.remote.api.AuthApiService
import app.shared.mobile.domain.DynamicUrlManager
import app.shared.mobile.domain.DynamicUrlManagerImpl
import app.shared.mobile.domain.data.KtorConfigsProvider
import app.shared.mobile.domain.data.UploadImageApi
import app.shared.mobile.domain.data.user.UserApi
import io.ktor.client.HttpClient
import kmp.core.mobile.di.CoreDiQualifiers
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val dataModule = module {
    // KtorConfigsProvider singleton
    factory {
        KtorConfigsProvider(
            securedSharedPrefs = get(CoreDiQualifiers.SHARED_PREFS_SECURED),
            remoteConfigs = get(),
            environment = get(),
            dynamicUrlManager = get()
        )
    }

    // MOCK_SERVER HttpClient singleton
    single(AppDiQualifiers.MOCK_SERVER) {
        val configsProvider = get<KtorConfigsProvider>()
        get<HttpClient> { parametersOf(configsProvider.getMockServerConfigs()) }
    }

    // AUTH_SERVER HttpClient singleton
    single(AppDiQualifiers.AUTH_SERVER) {
        val configsProvider = get<KtorConfigsProvider>()
        get<HttpClient> { parametersOf(configsProvider.getAuthServerConfigs()) }
    }

    // Dynamic URL Manager
    single<DynamicUrlManager> {
        DynamicUrlManagerImpl(
            sharedPrefs = get(CoreDiQualifiers.SHARED_PREFS_SECURED)
        )
    }

    // Auth API Service
    single {
        AuthApiService(
            get(AppDiQualifiers.AUTH_SERVER),
            get(),
            get(),
            get()
        )
    }

    // Auth Preferences
    single<AuthPreferences> {
        AuthPreferencesImpl(
            sharedPrefs = get(CoreDiQualifiers.SHARED_PREFS_SECURED)
        )
    }


    single {
        UploadImageApi(
            get(AppDiQualifiers.AUTH_SERVER)
        )
    }

    single {
        UserApi(
            get(AppDiQualifiers.AUTH_SERVER)
        )
    }
}