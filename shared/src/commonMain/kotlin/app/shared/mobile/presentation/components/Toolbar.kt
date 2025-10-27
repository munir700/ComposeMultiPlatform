package app.shared.mobile.presentation.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import app.shared.mobile.presentation.theme.colors
import app.shared.mobile.presentation.theme.spacings
import app.shared.mobile.presentation.theme.typography
import app.shared.mobile.resources.Res
import app.shared.mobile.resources.ic_back
import kmp.core.mobile.presentation.text.SingleLineText
import kmp.core.mobile.utils.extensions.flipIfRtl
import kmp.core.mobile.utils.extensions.noRippleClickable

import org.jetbrains.compose.resources.painterResource

@Composable
internal fun Toolbar(
    modifier: Modifier = Modifier,
    title: String? = null,
    colorFilter: Color? = null,
    style: TextStyle = typography.primaryMedium16,
    color: Color = colors.graniteGray,
    onBackClick: () -> Unit,
) {
    // Prepare spacings
    val toolbarIconSize = spacings.spacing24

    // Container
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        // Back screen icon
        Image(
            painter = painterResource(Res.drawable.ic_back),
            contentDescription = null,
            colorFilter = if (colorFilter != null) ColorFilter.tint(color = colorFilter) else null,
            modifier = Modifier.size(toolbarIconSize)
                .noRippleClickable(onClick = onBackClick)
                .flipIfRtl()
        )

        // main navigation title
        if (title != null) {
            SingleLineText(
                text = title,
                style = style,
                color = color,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = toolbarIconSize)
            )
        }
    }
}