package kmp.core.mobile.presentation.container


import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import kmp.core.mobile.globalState.models.ChoicesPopupParams
import kmp.core.mobile.presentation.dialogs.ChoicesDialog

@Composable
internal fun ChoicesDialogContainer(
    modifier: Modifier = Modifier,
    popupState: State<ChoicesPopupParams?>,
    onIdle: () -> Unit
) {
    popupState.value?.let { params ->
        ChoicesDialog(
            modifier = modifier,
            isCancellable = params.isCancellable,
            title = params.title,
            choices = params.choices,
            onChoiceSelected = { choice, index ->
                params.onChoiceSelected?.invoke(choice, index)
                onIdle.invoke()
            },
            onDismiss = {
                onIdle.invoke()
                params.onDismiss?.invoke()
            }
        )
    }
}