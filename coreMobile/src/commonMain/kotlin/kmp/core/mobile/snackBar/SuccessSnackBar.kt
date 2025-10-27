package kmp.core.mobile.snackBar


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import kmp.core.mobile.presentation.theme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUpOffAlt


@Composable
fun SuccessSnackBar(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector = Icons.Default.ThumbUpOffAlt,
    onClick: (() -> Unit)? = null
) {
    AppSnackBar(
        modifier = modifier,
        background = colors.successContainer,
        color = colors.success,
        text = text,
        icon = icon,
        addStatusBarPadding = true,
        onClick = onClick
    )
}