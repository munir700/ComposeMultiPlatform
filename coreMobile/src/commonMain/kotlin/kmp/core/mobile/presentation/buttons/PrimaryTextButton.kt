package kmp.core.mobile.presentation.buttons


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import kmp.core.mobile.presentation.theme.colors
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.presentation.theme.typography

@Composable
fun PrimaryTextButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    isEnabled: Boolean = true,
    color: Color = colors.primaryTextButton.textColor,
    disabledColor: Color = color.copy(alpha = 0.3f),
    textStyle: TextStyle = typography.primaryTextButton.textStyle,
    iconSize: Dp = spacings.primaryTextButton.iconSize,
    iconColor: Color? = colors.primaryTextButton.iconColor,
    spacing: Dp = spacings.primaryTextButton.spacing,
    padding: PaddingValues = PaddingValues(vertical = spacings.primaryTextButton.paddingVertical),
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    onClick: (() -> Unit)? = null
) {
    BaseTextButton(
        modifier = modifier,
        text = text,
        isEnabled = isEnabled,
        color = color,
        disabledColor = disabledColor,
        textStyle = textStyle,
        iconSize = iconSize,
        iconColor = iconColor,
        spacing = spacing,
        padding = padding,
        startIconPainter = startIconPainter,
        startIconVector = startIconVector,
        endIconPainter = endIconPainter,
        endIconVector = endIconVector,
        onClick = onClick
    )
}