package app.shared.mobile.di


import app.shared.mobile.constants.AppEnvironment
import app.shared.mobile.constants.AppInfo
import app.shared.mobile.globalState.AppGlobalState
import app.shared.mobile.globalState.IAppGlobalState
import kmp.core.mobile.globalState.ICoreGlobalState
import org.koin.dsl.module

fun appModule(
    environment: AppEnvironment,
    appInfo: AppInfo
) = module {
    single { environment }
    single { appInfo }
    single<IAppGlobalState> { AppGlobalState() }
    single<ICoreGlobalState> { get<IAppGlobalState>() }
}