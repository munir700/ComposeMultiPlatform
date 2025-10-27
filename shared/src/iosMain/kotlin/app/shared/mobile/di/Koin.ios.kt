package app.shared.mobile.di

import app.shared.mobile.constants.AppEnvironment
import app.shared.mobile.constants.AppInfo
import app.shared.mobile.initializer.IAppInitializer
import app.shared.mobile.presentation.viewsFactory.IViewsFactory
import com.arkivanov.decompose.ComponentContext
import org.koin.core.context.startKoin

fun initKoin(
    environment: AppEnvironment,
    appInfo: AppInfo,
    viewsFactory: IViewsFactory,
    componentContext: ComponentContext, // Keep this parameter
    appInitializer: () -> IAppInitializer,
) = startKoin {
    modules(
        *getCommonModules(environment, appInfo).toTypedArray(),
        getPlatformModule(
            viewsFactory = viewsFactory,
            appInitializer = appInitializer,
            componentContext = componentContext, // Pass it to the module
        )
    )
}