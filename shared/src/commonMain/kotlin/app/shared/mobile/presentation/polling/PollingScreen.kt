package app.shared.mobile.presentation.polling


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import app.shared.mobile.presentation.polling.PollingContract.Event
import app.shared.mobile.presentation.polling.components.PollingContent
import kmp.core.mobile.base.BaseScreen
import kmp.core.mobile.base.SIDE_EFFECTS_KEY
import kmp.core.mobile.base.rememberViewModel
import kmp.core.mobile.utils.extensions.consume

// Splash Screen Implementation
class PollingScreen() : BaseScreen<PollingViewModel>() {
    //override val screenTag: String = "SplashScreen" // This must match the key in factory

    @Composable
    override fun Content() {
        val viewModel = rememberViewModel<PollingViewModel>()
        this@PollingScreen.viewModel = viewModel

        // Handle side effects
        viewModel.effect.consume {
            // TODO
        }

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        PollingContent(
            viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}