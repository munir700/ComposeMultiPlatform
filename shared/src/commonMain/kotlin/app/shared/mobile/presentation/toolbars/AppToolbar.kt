package app.shared.mobile.presentation.toolbars


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import app.shared.mobile.presentation.theme.colors
import app.shared.mobile.presentation.theme.spacings
import app.shared.mobile.presentation.theme.typography
import app.shared.mobile.resources.Res
import app.shared.mobile.resources.ic_back
import app.shared.mobile.resources.ic_close
import kmp.core.mobile.presentation.text.SingleLineText
import kmp.core.mobile.utils.extensions.noRippleClickable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

internal val DEFAULT_BACK_ICON = Res.drawable.ic_back
internal val DEFAULT_CLOSE_ICON = Res.drawable.ic_close

@Composable
internal fun AppToolbar(
    modifier: Modifier = Modifier,
    title: String? = null,
    showBack: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    backIcon: DrawableResource = DEFAULT_BACK_ICON,
    showClose: Boolean = false,
    onCloseClick: (() -> Unit)? = null,
    closeIcon: DrawableResource = DEFAULT_CLOSE_ICON,
    titleStyle: TextStyle = typography.primaryMedium22,
    titleColor: Color = colors.independence,
    textAlign: TextAlign = TextAlign.Start,
    topPadding: Dp = spacings.spacing10,
    startPadding: Dp = spacings.spacing16,
    endPadding: Dp = spacings.spacing16,
    padding: PaddingValues = PaddingValues(
        top = topPadding,
        start = startPadding,
        end = endPadding,
    )
) {
    // Prepare dimensions
    val iconSize = spacings.spacing44

    // Container row
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacings.spacing4),
        modifier = modifier.fillMaxWidth().padding(padding)
    ) {

        // Back icon
        if (showBack) {
            Box(
                modifier = Modifier.size(iconSize)
                    .noRippleClickable(enabled = showBack, onClick = onBackClick)
            ) {
                Image(
                    painter = painterResource(backIcon),
                    contentDescription = null,
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center)
                )
            }
        }

        // Title text
        SingleLineText(
            text = title.orEmpty(),
            textAlign = textAlign,
            style = titleStyle,
            color = titleColor
        )

        // Spacer to push content to end
        Spacer(
            modifier = Modifier.weight(1f)
        )

        // Close icon
        if (showClose) {
            Box(
                modifier = Modifier
                    .padding(vertical = spacings.spacing4)
                    .size(iconSize)
            ) {
                Image(
                    painter = painterResource(closeIcon),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .noRippleClickable(onClick = onCloseClick)
                )
            }
        }
    }
}