package kmp.core.mobile.presentation.container

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import kmp.core.mobile.globalState.models.DatePopupParams
import kmp.core.mobile.presentation.dialogs.DatePickerDialog

@Composable
internal fun DateDialogContainer(
    modifier: Modifier = Modifier,
    popupState: State<DatePopupParams?>,
    onIdle: () -> Unit
) {
    popupState.value?.let { params ->
        DatePickerDialog(
            modifier = modifier,
            date = params.date,
            startDate = params.startDate,
            title = params.title,
            positiveButtonText = params.positiveButtonText,
            negativeButtonText = params.negativeButtonText,
            onDateSelected = { date ->
                onIdle.invoke()
                params.onDateSelected?.invoke(date)
            },
            onDismiss = {
                onIdle.invoke()
                params.onDismiss?.invoke()
            }
        )
    }
}