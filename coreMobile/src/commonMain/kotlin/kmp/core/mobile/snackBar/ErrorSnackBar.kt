package kmp.core.mobile.snackBar


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import kmp.core.mobile.presentation.theme.colors

@Composable
fun ErrorSnackBar(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector = Icons.Default.ErrorOutline,
    onClick: (() -> Unit)? = null
) {
    AppSnackBar(
        modifier = modifier,
        background = colors.dangerContainer,
        color = colors.danger,
        text = text,
        icon = icon,
        addStatusBarPadding = true,
        onClick = onClick
    )
}