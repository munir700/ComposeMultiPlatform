package app.sunreef.yachts.mobile.di


import app.sunreef.yachts.mobile.constants.AppEnvironment
import app.sunreef.yachts.mobile.constants.AppInfo
import kmp.core.mobile.di.coreModule

fun getCommonModules(
    environment: AppEnvironment,
    appInfo: AppInfo
) = listOf(
    navigationModule,
    screensModule,
    dataModule,
    domainsModule,
    coreModule(environment),
    sunreefYachtsAppModule(
        environment = environment,
        appInfo = appInfo
    ),
)