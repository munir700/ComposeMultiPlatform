package kmp.core.mobile.presentation.container


import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import kmp.core.mobile.globalState.models.ConfirmationPopupParams
import kmp.core.mobile.presentation.dialogs.ConfirmationDialog

@Composable
internal fun ConfirmationDialogContainer(
    modifier: Modifier = Modifier,
    popupState: State<ConfirmationPopupParams?>,
    onIdle: () -> Unit
) {
    popupState.value?.let { params ->
        ConfirmationDialog(
            modifier = modifier,
            isCancellable = params.isCancellable,
            title = params.title,
            body = params.body,
            positiveButtonText = params.positiveButtonText,
            negativeButtonText = params.negativeButtonText,
            onPositiveClick = {
                onIdle.invoke()
                params.onPositiveClick?.invoke()
            },
            onNegativeClick = {
                onIdle.invoke()
                params.onNegativeClick?.invoke()
            },
            onDismiss = {
                onIdle.invoke()
                params.onDismiss?.invoke()
            }
        )
    }
}