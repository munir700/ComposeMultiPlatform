package app.shared.mobile.di


import app.shared.mobile.constants.AppEnvironment
import app.shared.mobile.constants.AppInfo
import com.arkivanov.decompose.ComponentContext
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    environment: AppEnvironment,
    appInfo: AppInfo,
    componentContext: ComponentContext?  = null,
    appDeclaration: KoinAppDeclaration = {}
) = startKoin {
    appDeclaration()
    modules(
        *getCommonModules(environment, appInfo).toTypedArray(),
        getPlatformModule(componentContext)
    )
}