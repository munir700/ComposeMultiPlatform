package kmp.core.mobile.presentation.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import app.core.mobile.resources.Res
import app.core.mobile.resources.ok
import kmp.core.mobile.presentation.buttons.StrokedPrimaryButton
import kmp.core.mobile.presentation.theme.colors
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.presentation.theme.typography
import org.jetbrains.compose.resources.stringResource

@Composable
fun MessageDialog(
    modifier: Modifier = Modifier,
    isCancellable: Boolean = true,
    title: String? = null,
    body: String,
    buttonText: String? = null,
    onPositiveClick: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
) {
    // Prepare spacings
    val msgSpacing = if (title?.isNotEmpty() == true)
        spacings.paddingXLarge
    else
        spacings.noSpacing

    // Render app dialog
    AppDialog(
        modifier = modifier,
        title = title,
        showToolbar = true,
        onDismiss = onDismiss,
        isCancellable = isCancellable,
    ) {
        // Container column
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Render body text
            Text(
                text = body,
                textAlign = TextAlign.Center,
                color = colors.secondary,
                style = typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = msgSpacing)
            )

            // Render ok positive button
            StrokedPrimaryButton(
                text = buttonText ?: stringResource(Res.string.ok),
                isSmallHeight = true,
                onClick = {
                    onPositiveClick?.invoke()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacings.popupSpacingLarge)
            )
        }
    }
}