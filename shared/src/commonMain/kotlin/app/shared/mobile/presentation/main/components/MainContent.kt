package app.shared.mobile.presentation.main.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.shared.mobile.presentation.main.MainContract.Event
import app.shared.mobile.presentation.main.MainContract.State
import app.shared.mobile.presentation.theme.colors
import app.shared.mobile.presentation.theme.spacings
import app.shared.mobile.presentation.theme.typography
import app.shared.mobile.resources.Res
import app.shared.mobile.resources.confirm_popup
import app.shared.mobile.resources.date_picker
import app.shared.mobile.resources.date_range_picker
import app.shared.mobile.resources.message_popup
import app.shared.mobile.resources.success
import app.shared.mobile.resources.time_popup
import app.shared.mobile.resources.*
import kmp.core.mobile.presentation.buttons.FilledPrimaryButton
import kmp.core.mobile.presentation.container.SafeInsetsColumn
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun MainContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Render container Scrollable column
    SafeInsetsColumn(
        verticalArrangement = Arrangement.spacedBy(spacings.spacing20),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(spacings.spacing20)
    ) {

        Text(
            text = "Main Screen",
            style = typography.primaryRegular18,
            color = colors.darkElectricBlue.copy(alpha = 0.5f),
            modifier = Modifier
                .padding(bottom = spacings.spacing50)
        )

        // Positive button
        FilledPrimaryButton(
            text = stringResource(Res.string.date_picker),
            isSmallHeight = false,
            textStyle = typography.primaryBold16,
            onClick = {
                onEvent.invoke(Event.DatePicker)
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Positive button
        FilledPrimaryButton(
            text = stringResource(Res.string.date_range_picker),
            isSmallHeight = false,
            textStyle = typography.primaryBold16,
            onClick = {
                onEvent.invoke(Event.DateRangePicker)
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Positive button
        FilledPrimaryButton(
            text = stringResource(Res.string.message_popup),
            isSmallHeight = false,
            textStyle = typography.primaryBold16,
            onClick = {
                onEvent.invoke(Event.MessagePopup)
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Positive button
        FilledPrimaryButton(
            text = stringResource(Res.string.success),
            isSmallHeight = false,
            textStyle = typography.primaryBold16,
            onClick = {
                onEvent.invoke(Event.SuccessPopup)
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Positive button
        FilledPrimaryButton(
            text = stringResource(Res.string.time_popup),
            isSmallHeight = false,
            textStyle = typography.primaryBold16,
            onClick = {
                onEvent.invoke(Event.TimePopup)
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Positive button
        FilledPrimaryButton(
            text = stringResource(Res.string.confirm_popup),
            isSmallHeight = false,
            textStyle = typography.primaryBold16,
            onClick = {
                onEvent.invoke(Event.ConfirmPopup)
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Positive button
        FilledPrimaryButton(
            text = stringResource(Res.string.choose_image),
            isSmallHeight = false,
            textStyle = typography.primaryBold16,
            onClick = {
                onEvent.invoke(Event.ChoicePopup)
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Open location
        FilledPrimaryButton(
            text = stringResource(Res.string.open_location),
            isSmallHeight = false,
            textStyle = typography.primaryBold16,
            onClick = {
                onEvent.invoke(Event.OpenMap)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

