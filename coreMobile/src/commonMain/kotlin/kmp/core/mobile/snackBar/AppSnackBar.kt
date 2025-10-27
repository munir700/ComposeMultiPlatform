package kmp.core.mobile.snackBar


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.presentation.theme.typography
import kmp.core.mobile.utils.extensions.noRippleClickable

@Composable
fun AppSnackBar(
    modifier: Modifier = Modifier,
    background: Color,
    color: Color,
    text: String,
    icon: ImageVector,
    paddingValues: PaddingValues = PaddingValues(
        horizontal = spacings.paddingXXXLarge,
        vertical = spacings.paddingXXLarge
    ),
    addStatusBarPadding: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    // Prepare status bar padding
    val statusBarInsetModifier = if (addStatusBarPadding) {
        Modifier.windowInsetsPadding(WindowInsets.statusBars)
    } else {
        Modifier
    }

    // Container row
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacings.paddingMedium),
        modifier = modifier
            .fillMaxWidth()
            .background(background)
            .padding(paddingValues)
            .noRippleClickable(onClick = onClick)
            .then(statusBarInsetModifier)
    ) {
        // Icon
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color),
            modifier = Modifier.size(spacings.iconLarge)
        )

        // Message text
        Text(
            text = text,
            color = color,
            style = typography.snackBarMsg,
            modifier = Modifier.weight(1f)
        )
    }
}