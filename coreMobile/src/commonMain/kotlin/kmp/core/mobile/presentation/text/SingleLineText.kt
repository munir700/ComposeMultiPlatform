package kmp.core.mobile.presentation.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import kmp.core.mobile.presentation.theme.typography
import kmp.core.mobile.utils.extensions.noRippleClickable

@Composable
fun SingleLineText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = typography.bodyMedium,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    isUnderlined: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    val myModifier = Modifier
        .noRippleClickable { onClick?.invoke() }
        .takeIf { onClick != null }
        ?.then(modifier)
        ?: modifier

    Text(
        text = text,
        style = style,
        color = color,
        textAlign = textAlign,
        textDecoration = if (isUnderlined) TextDecoration.Underline else TextDecoration.None,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = myModifier
    )
}