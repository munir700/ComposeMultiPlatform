package kmp.core.mobile.presentation.dialogs


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import app.core.mobile.resources.Res
import app.core.mobile.resources.cancel
import app.core.mobile.resources.select_time
import kmp.core.mobile.date.now
import kmp.core.mobile.presentation.buttons.FilledPrimaryButton
import kmp.core.mobile.presentation.buttons.StrokedPrimaryButton
import kmp.core.mobile.presentation.theme.colors
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.presentation.theme.typography
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerPickerDialog(
    modifier: Modifier = Modifier,
    time: LocalTime? = null,
    title: String? = null,
    positiveButtonText: String? = null,
    negativeButtonText: String? = null,
    onTimeSelected: (LocalTime) -> Unit,
    onDismiss: () -> Unit
) {
    // Time picker state
    val timePickerState = rememberTimePickerState(
        initialMinute = (time ?: LocalTime.now()).minute,
        initialHour = (time ?: LocalTime.now()).hour,
        is24Hour = false
    )

    DatePickerDialog(
        colors = DatePickerDefaults.colors(
            containerColor = colors.background,
        ),
        onDismissRequest = onDismiss,
        modifier = Modifier.height(spacings.datePickerDialogHeight)
            .then(modifier),
        confirmButton = {
            // Positive button
            FilledPrimaryButton(
                text = positiveButtonText ?: stringResource(Res.string.select_time),
                isSmallHeight = true,
                isFullWidth = false,
                modifier = Modifier
                    .padding(bottom = spacings.paddingXLarge)
                    .padding(end = spacings.paddingXLarge),
                onClick = {
                    val selectedTime = LocalTime(
                        hour = timePickerState.hour,
                        minute = timePickerState.minute,
                    )
                    onTimeSelected(selectedTime)
                }
            )
        },
        dismissButton = {
            // Negative button
            StrokedPrimaryButton(
                text = negativeButtonText ?: stringResource(Res.string.cancel),
                isSmallHeight = true,
                isFullWidth = false,
                modifier = Modifier.padding(bottom = spacings.paddingXLarge),
                onClick = {
                    onDismiss()
                }
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(spacings.paddingXLarge),
            modifier = Modifier
                .fillMaxWidth()
                .height(spacings.datePickerHeight)
        ) {
            // Title
            Text(
                text = title.orEmpty(),
                style = typography.bodyLarge,
                color = colors.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacings.paddingXXLarge)
            )

            // Time picker
            TimePicker(
                state = timePickerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(spacings.datePickerHeight)
            )
        }
    }
}