package app.shared.mobile.presentation.splash


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import app.shared.mobile.presentation.splash.components.SplashContent
import com.arkivanov.decompose.ComponentContext
import kmp.core.mobile.base.BaseScreen
import kmp.core.mobile.base.SIDE_EFFECTS_KEY
import kmp.core.mobile.base.rememberViewModel
import kmp.core.mobile.utils.extensions.consume
import org.koin.core.component.inject

// Splash Screen Implementation
class SplashScreen() : BaseScreen<SplashViewModel>() {
    //override val screenTag: String = "SplashScreen" // This must match the key in factory

    @Composable
    override fun Content() {
        val viewModel = rememberViewModel<SplashViewModel>()

        // Handle side effects
        viewModel.effect.consume {
            // TODO
        }

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(SplashContract.Event.Init)
        }

        // Render content
        SplashContent()
    }
}