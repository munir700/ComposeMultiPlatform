package app.shared.mobile.presentation.polling.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import app.shared.mobile.presentation.polling.PollingContract.Event
import app.shared.mobile.presentation.polling.PollingContract.State
import app.shared.mobile.presentation.theme.colors
import app.shared.mobile.presentation.theme.spacings
import app.shared.mobile.presentation.theme.typography
import app.shared.mobile.resources.Res
import app.shared.mobile.resources.bg_splash
import app.shared.mobile.resources.submit
import kmp.core.mobile.presentation.buttons.FilledPrimaryButton
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun PollingContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = spacings.spacing40)
    ) {

        Text(
            text = "${state.items}",
            style = typography.primaryRegular20,
            color = colors.darkElectricBlue,
            modifier = Modifier.align(Alignment.Center)
        )

        // Positive button
        FilledPrimaryButton(
            text = stringResource(Res.string.submit),
            isSmallHeight = false,
            textStyle = typography.primaryBold16,
            onClick = {
                onEvent.invoke(Event.BackClick)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = spacings.spacing50)

                .align(Alignment.BottomCenter)
        )
    }
}


