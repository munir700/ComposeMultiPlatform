package kmp.core.mobile.presentation.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import kmp.core.mobile.presentation.theme.colors
import kmp.core.mobile.presentation.theme.shapes
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.presentation.theme.typography

@Composable
fun StrokedPrimaryButton(
    modifier: Modifier = Modifier,
    isFullWidth: Boolean = true,
    text: String,
    iconSize: Dp = spacings.iconLarge,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isSmallHeight: Boolean = false,
    textStyle: TextStyle = if (isSmallHeight) typography.bodyMedium else typography.bodyLarge,
    textColor: Color = colors.primary,
    iconColor: Color = colors.primary,
    strokeColor: Color = colors.primary,
    strokeWidth: Dp = spacings.stroke,
    onClick: () -> Unit = {}
) {
    BaseButton(
        modifier = modifier,
        isFullWidth = isFullWidth,
        text = text,
        textStyle = textStyle,
        textColor = textColor,
        iconSize = iconSize,
        iconColor = iconColor,
        startIconPainter = startIconPainter,
        startIconVector = startIconVector,
        endIconPainter = endIconPainter,
        endIconVector = endIconVector,
        isEnabled = isEnabled,
        isDimmed = isDimmed,
        onClick = onClick,
        backgroundColor = colors.background,
        minHeight = if (isSmallHeight) spacings.btnMinHeightSmall else spacings.btnMinHeightNormal,
        shape = if (isSmallHeight) shapes.xSmall else shapes.medium,
        border = BorderStroke(
            width = strokeWidth,
            color = strokeColor
        )
    )
}