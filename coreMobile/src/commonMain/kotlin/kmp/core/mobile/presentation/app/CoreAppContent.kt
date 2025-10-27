package kmp.core.mobile.presentation.app


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import kmp.core.mobile.base.BaseScreen
import kmp.core.mobile.globalState.ICoreGlobalState
import kmp.core.mobile.navigations.CoreAppNavigatorDI
import kmp.core.mobile.navigations.DIScreenFactory
import kmp.core.mobile.navigations.NavManager
import kmp.core.mobile.permissions.BindEffect
import kmp.core.mobile.permissions.IPermissionManager
import kmp.core.mobile.presentation.container.ChoicesDialogContainer
import kmp.core.mobile.presentation.container.ConfirmationDialogContainer
import kmp.core.mobile.presentation.container.DateDialogContainer
import kmp.core.mobile.presentation.container.DateRangeDialogContainer
import kmp.core.mobile.presentation.container.MessageDialogContainer
import kmp.core.mobile.presentation.container.SnackBarContainer
import kmp.core.mobile.presentation.container.SuccessDialogContainer
import kmp.core.mobile.presentation.container.TimeDialogContainer
import org.koin.compose.koinInject

@Composable
fun CoreAppContent(
    modifier: Modifier = Modifier,
    globalState: ICoreGlobalState = koinInject(),
    navManager: NavManager = koinInject(),
    startScreen: BaseScreen<*>,
    rootComponentContext: ComponentContext,
    screenFactory: DIScreenFactory
) {
    // Bind permissions manager
    val permissionManager = koinInject<IPermissionManager>()
    BindEffect(
        permissionManager
    )

    // Core container
    CoreAppContainer(
        modifier = modifier.fillMaxSize()
    ) {
        // Render container box
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            // Render navigator
            CoreAppNavigatorDI(
                componentContext = rootComponentContext,
                navManager = navManager,
                startScreen = startScreen,
                screenFactory = screenFactory
            )


            // Date picker dialog
            DateDialogContainer(
                popupState = globalState.datePopupState,
                onIdle = globalState::idle
            )

            // Date Range picker dialog
            DateRangeDialogContainer(
                popupState = globalState.dateRangePopupState,
                onIdle = globalState::idle
            )

            // Message dialog container
            MessageDialogContainer(
                popupState = globalState.messagePopupState,
                onIdle = globalState::idle
            )

            // Message dialog container
            SuccessDialogContainer(
                popupState = globalState.successPopupState,
                onIdle = globalState::idle
            )

            // Time dialog container
            TimeDialogContainer(
                popupState = globalState.timePopupState,
                onIdle = globalState::idle
            )

            // Handle confirmation popup
            ConfirmationDialogContainer(
                popupState = globalState.confirmationPopupState,
                onIdle = globalState::idle
            )

            // Handle choices popup
            ChoicesDialogContainer(
                popupState = globalState.choicesPopupState,
                onIdle = globalState::idle
            )
        }

        // Handle snack bar
        SnackBarContainer(
            snackBarState = globalState.snackBarState,
            onHideSnackBar = globalState::hideSnackBar
        )

        // Config app UI
        ConfigAppUI(
            dismissKeyboardState = globalState.dismissKeyboardState,
            isStatusBarDarkState = globalState.isStatusBarDarkState,
            isNavigationBarDarkState = globalState.isNavigationBarDarkState,
            onResetKeyboardState = globalState::resetDismissKeyboardState
        )
    }
}
