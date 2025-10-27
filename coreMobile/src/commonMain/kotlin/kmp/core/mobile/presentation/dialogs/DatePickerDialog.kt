package kmp.core.mobile.presentation.dialogs


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import app.core.mobile.resources.Res
import app.core.mobile.resources.cancel
import app.core.mobile.resources.select_date
import kmp.core.mobile.date.toLocalDate
import kmp.core.mobile.date.toMillis
import kmp.core.mobile.presentation.buttons.FilledPrimaryButton
import kmp.core.mobile.presentation.buttons.StrokedPrimaryButton
import kmp.core.mobile.presentation.theme.colors
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.presentation.theme.typography
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    modifier: Modifier = Modifier,
    date: LocalDate?,
    startDate: LocalDate?,
    title: String? = null,
    positiveButtonText: String? = null,
    negativeButtonText: String? = null,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    // Main objects
    val startDateMillis = startDate?.toMillis(TimeZone.UTC)
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = date?.toMillis(TimeZone.UTC),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return startDateMillis == null || utcTimeMillis >= startDateMillis
            }
        }
    )

    // Show date dialog
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
                text = positiveButtonText ?: stringResource(Res.string.select_date),
                isSmallHeight = true,
                isFullWidth = false,
                onClick = {
                    datePickerState.selectedDateMillis?.let {
                        onDateSelected(it.toLocalDate())
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
        // Date picker component
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = colors.background
            ),
            title = {
                // Render title
                Text(
                    text = title.orEmpty(),
                    textAlign = TextAlign.Center,
                    color = colors.primary,
                    style = typography.bodyMedium,
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