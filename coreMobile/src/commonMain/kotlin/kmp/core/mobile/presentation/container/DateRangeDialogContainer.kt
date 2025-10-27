package kmp.core.mobile.presentation.container


import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import kmp.core.mobile.globalState.models.DateRangePopupParams
import kmp.core.mobile.presentation.dialogs.DateRangePickerDialog
import kmp.core.mobile.utils.extensions.orZero

@Composable
internal fun DateRangeDialogContainer(
    modifier: Modifier = Modifier,
    popupState: State<DateRangePopupParams?>,
    onIdle: () -> Unit
) {
    popupState.value?.let { params ->
        DateRangePickerDialog(
            modifier = modifier,
            fromDate = params.fromDate.orZero(),
            toDate = params.toDate.orZero(),
            title = params.title,
            positiveButtonText = params.positiveButtonText,
            negativeButtonText = params.negativeButtonText,
            onDateRangeSelected = { fromDate, toDate ->
                onIdle.invoke()
                params.onDateRangeSelected?.invoke(fromDate, toDate)
            },
            onDismiss = {
                onIdle.invoke()
                params.onDismiss?.invoke()
            }
        )
    }
}