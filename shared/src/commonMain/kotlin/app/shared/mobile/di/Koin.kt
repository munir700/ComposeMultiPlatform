package app.shared.mobile.di


import app.shared.mobile.constants.AppEnvironment
import app.shared.mobile.constants.AppInfo
import kmp.core.mobile.di.coreModule

fun getCommonModules(
    environment: AppEnvironment,
    appInfo: AppInfo
) = listOf(
    navigationModule,
    viewModelsModule,
    screensModule,
    helpersModule,
    repositoriesModule,
    dataModule,
    useCasesModule,
    coreModule(environment),
    appModule(
        environment = environment,
        appInfo = appInfo
    ),
    presentationModule
)