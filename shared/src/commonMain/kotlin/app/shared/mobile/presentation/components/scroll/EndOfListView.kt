package app.shared.mobile.presentation.components.scroll

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.shared.mobile.resources.Res
import app.shared.mobile.resources.no_more_items_to_load
import kmp.core.mobile.presentation.theme.colors
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.presentation.theme.typography
import org.jetbrains.compose.resources.stringResource


@Composable
fun EndOfListView(endListLabel: String? = null) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacings.spacing24),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = endListLabel ?: stringResource(Res.string.no_more_items_to_load),
            style = typography.labelSmall,
            color = colors.darkElectricBlue.copy(alpha = 0.5f)
        )
    }
}
