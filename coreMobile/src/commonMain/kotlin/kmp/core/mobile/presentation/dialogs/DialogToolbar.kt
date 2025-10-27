package kmp.core.mobile.presentation.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import kmp.core.mobile.presentation.theme.colors
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.presentation.theme.typography
import kmp.core.mobile.utils.extensions.noRippleClickable

@Composable
fun DialogToolbar(
    modifier: Modifier = Modifier,
    title: String? = null,
    showClose: Boolean = false,
    onCloseClicked: () -> Unit = {}
) {
    val closeSize = spacings.paddingXLarge

    // Toolbar
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = spacings.paddingXLarge)
            .padding(top = spacings.iconMedium)
            .padding(bottom = spacings.paddingMedium)
    ) {
        // Render close icon if required
        if (showClose) {
            Image(
                imageVector = Icons.Filled.Close,
                colorFilter = ColorFilter.tint(colors.secondary),
                contentDescription = null,
                modifier = Modifier.size(closeSize)
                    .noRippleClickable(onClick = onCloseClicked)
                    .align(Alignment.CenterStart)
            )
        }

        // Render title
        Text(
            text = title.orEmpty(),
            textAlign = TextAlign.Center,
            color = colors.secondary,
            style = typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = closeSize)
                .align(Alignment.Center)
        )
    }
}