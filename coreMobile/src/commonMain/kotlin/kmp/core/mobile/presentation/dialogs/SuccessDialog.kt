package kmp.core.mobile.presentation.dialogs


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import app.core.mobile.resources.Res
import app.core.mobile.resources.ok
import kmp.core.mobile.presentation.buttons.StrokedPrimaryButton
import kmp.core.mobile.presentation.theme.colors
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.presentation.theme.typography
import org.jetbrains.compose.resources.stringResource

@Composable
fun SuccessDialog(
    modifier: Modifier = Modifier,
    isCancellable: Boolean = true,
    title: String? = null,
    body: String? = null,
    buttonText: String? = null,
    onPositiveClick: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
) {
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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Render Image
            Image(
                imageVector = Icons.Default.TaskAlt,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                colorFilter = ColorFilter.tint(colors.success),
                modifier = Modifier.size(
                    spacings.popupIconLarge
                )
            )

            // Render body text
            Text(
                text = body.orEmpty(),
                textAlign = TextAlign.Center,
                color = colors.secondary,
                style = typography.bodyLarge,
                modifier = Modifier.padding(
                    top = spacings.popupSpacingMedium
                )
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