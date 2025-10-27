package kmp.core.mobile.presentation.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import kmp.core.mobile.presentation.theme.colors
import kmp.core.mobile.presentation.theme.shapes
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.presentation.theme.typography

@Composable
fun FilledPrimaryButton(
    modifier: Modifier = Modifier,
    isFullWidth: Boolean = true,
    text: String,
    iconSize: Dp = spacings.iconLarge,
    minHeight: Dp? = null,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    isSmallHeight: Boolean = false,
    textStyle: TextStyle = if (isSmallHeight) typography.bodyMedium else typography.bodyLarge,
    onClick: () -> Unit = {}
) {
    val requiredHeight = when {
        minHeight != null -> minHeight
        isSmallHeight -> spacings.btnMinHeightSmall
        else -> spacings.btnMinHeightNormal
    }
    BaseButton(
        modifier = modifier,
        isFullWidth = isFullWidth,
        text = text,
        textStyle = textStyle,
        iconSize = iconSize,
        textColor = colors.onPrimary,
        iconColor = colors.onPrimary,
        backgroundColor = colors.primary,
        startIconPainter = startIconPainter,
        startIconVector = startIconVector,
        endIconPainter = endIconPainter,
        endIconVector = endIconVector,
        isEnabled = isEnabled,
        isDimmed = isDimmed,
        onClick = onClick,
        minHeight = requiredHeight,
        shape = if (isSmallHeight) shapes.xSmall else shapes.medium
    )
}