package kmp.core.mobile.di


import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.plugin
import io.ktor.serialization.kotlinx.json.json
import kmp.core.mobile.AppLogger
import kmp.core.mobile.deepLink.DeepLinkManager
import kmp.core.mobile.deepLink.DeepLinkParser
import kmp.core.mobile.deepLink.IDeepLinkManager
import kmp.core.mobile.eventBroadcaster.EventBroadcaster
import kmp.core.mobile.globalState.CoreGlobalState
import kmp.core.mobile.globalState.ICoreGlobalState
import kmp.core.mobile.navigations.DIScreenFactory
import kmp.core.mobile.navigations.NavManager
import kmp.core.mobile.navigations.ScreenFactory
import kmp.core.mobile.navigations.ScreenProviderRegistry
import kmp.core.mobile.network.CurlLogging
import kmp.core.mobile.network.KtorConfigs
import kmp.core.mobile.network.defaultRequestInterceptor
import kmp.core.mobile.network.defaultResponseInterceptor
import kmp.core.mobile.presentation.app.AppCoroutineScope
import kmp.core.mobile.resources.IResourcesManager
import kmp.core.mobile.resources.ResourcesManager
import kmp.core.mobile.sharedPrefs.ISharedPrefsManager
import kmp.core.mobile.sharedPrefs.NormalSharedPrefsManager
import kmp.core.mobile.sharedPrefs.SecuredSharedPrefsManager
import kmp.core.mobile.utils.CoreEnvironment
import kmp.core.mobile.utils.extensions.getValidKtorUrl
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun coreModule(
    environment: CoreEnvironment,
    deepLinkParsers: Map<String, DeepLinkParser> = emptyMap()
) = module {

    includes(corePlatformModule())

    includes(coreViewModelsModule)

    single {
        environment
    }

    // Screen Factory
    single { ScreenProviderRegistry() }
    single<ScreenFactory> { DIScreenFactory(get()) }


    single { AppCoroutineScope() }

    single {
        NavManager(get())
    }

    single {
        AppLogger(
            isDebuggable = environment.isDebuggable
        )
    }

    single<ISharedPrefsManager>(CoreDiQualifiers.SHARED_PREFS_SECURED) {
        SecuredSharedPrefsManager(get())
    }

    single<ISharedPrefsManager>(CoreDiQualifiers.SHARED_PREFS_NORMAL) {
        val factory = get<Settings.Factory>()
        NormalSharedPrefsManager(
            settings = factory.create(environment.title)
        )
    }

    single {
        get<ISharedPrefsManager>(CoreDiQualifiers.SHARED_PREFS_NORMAL)
    }

    single<ICoreGlobalState> {
        CoreGlobalState()
    }

    factory { (configs: KtorConfigs) ->
        // Set http client
        val client = HttpClient {
            defaultRequest {
                url(configs.baseUrl.getValidKtorUrl())
            }

            // Install json serializer
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        encodeDefaults = false
                        useAlternativeNames = false
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }

            // Install logging if required
            if (environment.isDebuggable) {
                // Install the CurlLogging plugin
                install(CurlLogging) {
                    logger = { message -> println(message) }
                }
            }

            // Install default request interceptor
            defaultRequest(
                defaultRequestInterceptor(configs)
            )

            // Install default response interceptor
            HttpResponseValidator(
                defaultResponseInterceptor()
            )
        }

        // Add request interceptors
        client.apply {
            configs.customRequestInterceptors.forEach { interceptor ->
                plugin(HttpSend).intercept(interceptor)
            }
        }
    }

    single(CoreDiQualifiers.BASIC_HTTP_CLIENT) {
        HttpClient {
            // Install logging if required
            if (environment.isDebuggable) {
                install(Logging) {
                    logger = Logger.SIMPLE
                    level = LogLevel.ALL
                }
            }
        }
    }


    /*    single {
            DownloadManager(
                logger = get(),
                client = get(CoreDiQualifiers.BASIC_HTTP_CLIENT),
                fileManager = get()
            )
        }*/

    single {
        EventBroadcaster
    }

    single<IDeepLinkManager> {
        DeepLinkManager(
            appLogger = get(),
            parsers = deepLinkParsers
        )
    }

    single<IResourcesManager> {
        ResourcesManager
    }
}