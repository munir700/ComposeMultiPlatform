package app.shared.mobile.di


import app.shared.mobile.initializer.IAppInitializer
import app.shared.mobile.location.ILocationManager
import app.shared.mobile.presentation.location.LocationManager
import app.shared.mobile.presentation.permissions.PermissionDelegateFactory
import app.shared.mobile.presentation.viewsFactory.IViewsFactory
import com.arkivanov.decompose.ComponentContext
import kmp.core.mobile.permissions.helpers.IPermissionDelegateFactory
import org.koin.dsl.module

internal fun getPlatformModule(
    componentContext: ComponentContext, // Keep this parameter
    viewsFactory: IViewsFactory,
    appInitializer: () -> IAppInitializer
) = module {


    single<ComponentContext> { componentContext }

    single {
        viewsFactory
    }

    single<IPermissionDelegateFactory> {
        PermissionDelegateFactory()
    }

    single<IAppInitializer> {
        appInitializer()
    }

    single<ILocationManager> {
        LocationManager()
    }

}