package app.shared.mobile.presentation.app

import androidx.compose.runtime.Composable
import app.shared.mobile.globalState.IAppGlobalState
import app.shared.mobile.presentation.splash.SplashScreen
import com.arkivanov.decompose.ComponentContext
import kmp.core.mobile.navigations.DIScreenFactory
import kmp.core.mobile.navigations.NavManager
import kmp.core.mobile.presentation.app.CoreAppContent
import org.koin.compose.koinInject

@Composable
internal fun AppContent() {
    // Get main objects
    val globalState = koinInject<IAppGlobalState>()
    val rootComponentContext = koinInject<ComponentContext>()
    val navManager: NavManager = koinInject()
    val screenFactory: DIScreenFactory = koinInject()

    val startScreen = SplashScreen()

    CoreAppContent(
        globalState = globalState,
        rootComponentContext = rootComponentContext,
        navManager = navManager,
        startScreen = startScreen,
        screenFactory = screenFactory
    )
}
