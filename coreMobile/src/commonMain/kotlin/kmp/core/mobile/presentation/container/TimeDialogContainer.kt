package kmp.core.mobile.presentation.container


import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import kmp.core.mobile.globalState.models.TimePopupParams
import kmp.core.mobile.presentation.dialogs.TimerPickerDialog

@Composable
internal fun TimeDialogContainer(
    modifier: Modifier = Modifier,
    popupState: State<TimePopupParams?>,
    onIdle: () -> Unit
) {
    popupState.value?.let { params ->
        TimerPickerDialog(
            modifier = modifier,
            time = params.time,
            title = params.title,
            positiveButtonText = params.positiveButtonText,
            negativeButtonText = params.negativeButtonText,
            onTimeSelected = { time ->
                onIdle.invoke()
                params.onTimeSelected?.invoke(time)
            },
            onDismiss = {
                onIdle.invoke()
                params.onDismiss?.invoke()
            }
        )
    }
}