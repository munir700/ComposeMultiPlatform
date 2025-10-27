package app.shared.mobile.presentation.infiniteScrolling


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import app.shared.mobile.presentation.infiniteScrolling.InfiniteScrollContract.Event
import app.shared.mobile.presentation.infiniteScrolling.components.InfiniteScrollContent
import kmp.core.mobile.base.BaseScreen
import kmp.core.mobile.base.SIDE_EFFECTS_KEY
import kmp.core.mobile.base.rememberViewModel
import kmp.core.mobile.utils.extensions.consume

// Login Screen Implementation
class InfiniteScrollScreen() : BaseScreen<InfiniteScrollViewModel>() {
    override val screenTag: String = "InfiniteScrollingScreen" // This must match the key in factory

    @Composable
    override fun Content() {
        val viewModel = rememberViewModel<InfiniteScrollViewModel>()

        // Handle side effects
        viewModel.effect.consume {
            // TODO
        }

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(Event.Init)
        }

        // Render content
        InfiniteScrollContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}