package app.shared.mobile.presentation.login


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import app.shared.mobile.presentation.login.components.LoginContent
import kmp.core.mobile.base.BaseScreen
import kmp.core.mobile.base.SIDE_EFFECTS_KEY
import kmp.core.mobile.base.rememberViewModel
import kmp.core.mobile.utils.extensions.consume

// Login Screen Implementation
class LoginScreen() : BaseScreen<LoginViewModel>() {
    override val screenTag: String = "LoginScreen" // This must match the key in factory

    @Composable
    override fun Content() {
        val viewModel = rememberViewModel<LoginViewModel>()

        // Handle side effects
        viewModel.effect.consume {
            // TODO
        }

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(LoginContract.Event.Init)
        }

        // Render content
        LoginContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}