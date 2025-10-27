package app.shared.mobile.presentation.components.containers


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import app.shared.mobile.presentation.theme.colors
import app.shared.mobile.presentation.theme.shapes
import app.shared.mobile.presentation.theme.spacings
import kmp.core.mobile.utils.extensions.strokeBackground

@Composable
internal fun AppCardColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(spacings.spacing0),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    bgColor: Color = colors.white,
    strokeColor: Color? = colors.shuttleGrey.copy(alpha = .5f),
    padding: PaddingValues = PaddingValues(all = spacings.spacing20),
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapes.roundedCorner16)
            .then(modifier)
            .strokeBackground(
                shape = shapes.roundedCorner16,
                bgColor = bgColor,
                strokeColor = strokeColor,
                strokeWidth = spacings.spacingPoint15
            )
            .padding(paddingValues = padding),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = content
    )
}