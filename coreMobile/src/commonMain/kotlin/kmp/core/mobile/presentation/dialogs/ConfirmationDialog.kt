package kmp.core.mobile.presentation.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import app.core.mobile.resources.Res
import app.core.mobile.resources.cancel
import app.core.mobile.resources.confirm
import kmp.core.mobile.presentation.buttons.FilledPrimaryButton
import kmp.core.mobile.presentation.buttons.StrokedPrimaryButton
import kmp.core.mobile.presentation.theme.colors
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.presentation.theme.typography
import org.jetbrains.compose.resources.stringResource

@Composable
fun ConfirmationDialog(
    modifier: Modifier = Modifier,
    isCancellable: Boolean = true,
    title: String? = null,
    body: String? = null,
    positiveButtonText: String? = null,
    negativeButtonText: String? = null,
    onPositiveClick: (() -> Unit)? = null,
    onNegativeClick: (() -> Unit)? = null,
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
                text = body.orEmpty(),
                textAlign = TextAlign.Center,
                color = colors.secondary,
                style = typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = msgSpacing)
            )

            // Render buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(spacings.paddingXLarge),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacings.popupSpacingLarge)
            ) {
                // Negative button
                StrokedPrimaryButton(
                    text = negativeButtonText ?: stringResource(Res.string.cancel),
                    isSmallHeight = true,
                    onClick = {
                        onNegativeClick?.invoke()
                    },
                    modifier = Modifier.weight(1f)
                )

                // Positive button
                FilledPrimaryButton(
                    text = positiveButtonText ?: stringResource(Res.string.confirm),
                    isSmallHeight = true,
                    onClick = {
                        onPositiveClick?.invoke()
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}