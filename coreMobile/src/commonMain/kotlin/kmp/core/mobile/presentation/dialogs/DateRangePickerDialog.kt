package kmp.core.mobile.presentation.dialogs


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import app.core.mobile.resources.Res
import app.core.mobile.resources.cancel
import app.core.mobile.resources.select_date_range
import kmp.core.mobile.presentation.buttons.FilledPrimaryButton
import kmp.core.mobile.presentation.buttons.StrokedPrimaryButton
import kmp.core.mobile.presentation.theme.colors
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.presentation.theme.typography
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerDialog(
    modifier: Modifier = Modifier,
    fromDate: Long,
    toDate: Long,
    title: String? = null,
    positiveButtonText: String? = null,
    negativeButtonText: String? = null,
    onDateRangeSelected: (Long?, Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = fromDate,
        initialSelectedEndDateMillis = toDate
    )

    DatePickerDialog(
        colors = DatePickerDefaults.colors(
            containerColor = colors.background,
        ),
        onDismissRequest = onDismiss,
        modifier = Modifier.height(spacings.datePickerDialogHeight)
            .then(modifier),
        confirmButton = {
            val startDate = dateRangePickerState.selectedStartDateMillis
            val endDate = dateRangePickerState.selectedEndDateMillis

            val isDateRangeSelected = startDate != null && endDate != null
            // Positive button
            FilledPrimaryButton(
                text = positiveButtonText ?: stringResource(Res.string.select_date_range),
                isSmallHeight = true,
                isFullWidth = false,
                isEnabled = isDateRangeSelected,
                onClick = {
                    if (isDateRangeSelected) {
                        onDateRangeSelected(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                    }
                }
            )
        },
        dismissButton = {
            // Negative button
            StrokedPrimaryButton(
                text = negativeButtonText ?: stringResource(Res.string.cancel),
                isSmallHeight = true,
                isFullWidth = false,
                onClick = {
                    onDismiss()
                }
            )
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = colors.background,
                dayInSelectionRangeContainerColor = colors.tertiaryContainer
            ),
            title = {
                // Render title
                Text(
                    text = title.orEmpty(),
                    textAlign = TextAlign.Center,
                    color = colors.primary,
                    style = typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacings.paddingXXLarge)
                )
            },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(spacings.datePickerHeight)
        )
    }
}