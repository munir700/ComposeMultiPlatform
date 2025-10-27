package kmp.core.mobile.presentation.container


import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import kmp.core.mobile.globalState.models.MessagePopupParams
import kmp.core.mobile.presentation.dialogs.MessageDialog

@Composable
internal fun MessageDialogContainer(
    modifier: Modifier = Modifier,
    popupState: State<MessagePopupParams?>,
    onIdle: () -> Unit
) {
    popupState.value?.let { params ->
        MessageDialog(
            modifier = modifier,
            isCancellable = params.isCancellable,
            title = params.title,
            body = params.body,
            buttonText = params.buttonText,
            onPositiveClick = {
                onIdle.invoke()
                params.onPositiveClick?.invoke()
            },
            onDismiss = {
                onIdle.invoke()
                params.onDismiss?.invoke()
            }
        )
    }
}
