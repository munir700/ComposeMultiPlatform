package kmp.core.mobile.presentation.buttons


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import kmp.core.mobile.presentation.theme.shapes
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.presentation.theme.typography
import kmp.core.mobile.utils.extensions.applyIf
import kmp.core.mobile.utils.extensions.tintIfNotNull

@Composable
internal fun BaseButton(
    modifier: Modifier = Modifier,
    isFullWidth: Boolean = true,
    text: String = "",
    textStyle: TextStyle = typography.bodyLarge,
    textColor: Color,
    shape: Shape = shapes.large,
    minHeight: Dp = spacings.btnMinHeightNormal,
    backgroundColor: Color,
    disabledBackgroundColor: Color = backgroundColor.copy(alpha = 0.3f),
    border: BorderStroke? = null,
    iconSize: Dp = spacings.iconLarge,
    iconColor: Color?,
    startIconPainter: Painter? = null,
    startIconVector: ImageVector? = null,
    endIconPainter: Painter? = null,
    endIconVector: ImageVector? = null,
    isEnabled: Boolean = true,
    isDimmed: Boolean = false,
    elevation: Dp = spacings.noSpacing,
    contentAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    onClick: () -> Unit = {}
) {
    // Prepare real values
    val realBgColor = if (isDimmed) disabledBackgroundColor else backgroundColor

    // Render button
    Button(
        modifier = modifier
            .applyIf(isFullWidth) {
                fillMaxWidth()
            }
            .heightIn(min = minHeight),
        shape = shape,
        border = border,
        enabled = isEnabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = realBgColor,
            disabledContainerColor = disabledBackgroundColor
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = elevation,
            pressedElevation = elevation,
            hoveredElevation = elevation
        ),
        contentPadding = PaddingValues(
            vertical = spacings.paddingXSmall,
            horizontal = spacings.paddingMedium
        )
    ) {
        // Container box
        Box(
            modifier = Modifier.applyIf(isFullWidth) {
                fillMaxWidth()
            },
            contentAlignment = Alignment.Center
        ) {
            // Content row
            Row(
                modifier = Modifier.applyIf(isFullWidth) {
                    fillMaxWidth()
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = spacings.paddingXLarge,
                    alignment = contentAlignment
                )
            ) {
                // Render start icon if required
                if (startIconVector != null) {
                    Image(
                        imageVector = startIconVector,
                        colorFilter = tintIfNotNull(iconColor),
                        contentDescription = text,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(iconSize)
                    )
                } else if (startIconPainter != null) {
                    Image(
                        painter = startIconPainter,
                        colorFilter = tintIfNotNull(iconColor),
                        contentDescription = text,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(iconSize)
                    )
                }

                // Render text
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    color = textColor,
                    style = textStyle,
                    modifier = Modifier.weight(
                        weight = 1f,
                        fill = false
                    )
                )

                // Render end icon if required
                if (endIconVector != null) {
                    Image(
                        imageVector = endIconVector,
                        colorFilter = tintIfNotNull(iconColor),
                        contentDescription = text,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(iconSize)
                    )
                } else if (endIconPainter != null) {
                    Image(
                        painter = endIconPainter,
                        colorFilter = tintIfNotNull(iconColor),
                        contentDescription = text,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
        }
    }
}