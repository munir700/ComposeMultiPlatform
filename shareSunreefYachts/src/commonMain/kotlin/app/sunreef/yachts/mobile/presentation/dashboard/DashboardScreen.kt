package app.sunreef.yachts.mobile.presentation.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import app.sunreef.yachts.mobile.presentation.dashboard.components.DashboardContent
import kmp.core.mobile.base.BaseScreen
import kmp.core.mobile.base.SIDE_EFFECTS_KEY
import kmp.core.mobile.base.rememberViewModel
import kmp.core.mobile.utils.extensions.consume

/**
 * Dashboard Screen
 * Main overview of yacht systems
 * Follows the same pattern as LoginScreen in shared module
 */

class DashboardScreen() : BaseScreen<DashboardViewModel>() {
    override val screenTag: String = "DashboardScreen"

    @Composable
    override fun Content() {
        val viewModel = rememberViewModel<DashboardViewModel>()
        val state = viewModel.viewState.value

        // Handle side effects
        viewModel.effect.consume { sideEffect ->
            when (sideEffect) {
                is DashboardContract.Effect.NavigateToSystem -> {
                    // Navigation handled by NavigationManager
                }
                is DashboardContract.Effect.NavigateToSettings -> {
                    // Navigation handled by NavigationManager
                }
            }
        }

        // Initialize screen
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(DashboardContract.Event.Init)
        }

        DashboardContent(state = state, onEvent = viewModel::setEvent)
    }
}








