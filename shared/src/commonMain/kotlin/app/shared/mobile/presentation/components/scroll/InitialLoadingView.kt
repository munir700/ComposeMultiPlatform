package app.shared.mobile.presentation.components.scroll

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.shared.mobile.resources.Res
import app.shared.mobile.resources.loading_items
import kmp.core.mobile.presentation.theme.colors
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.presentation.theme.typography
import org.jetbrains.compose.resources.stringResource

@Composable
fun InitialLoadingView(loadingLabel: String? = null) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacings.spacing16)
        ) {
            CircularProgressIndicator()
            Text(
                text = loadingLabel ?: stringResource(Res.string.loading_items),
                style = typography.labelMedium,
                color = colors.darkElectricBlue.copy(alpha = 0.6f)
            )
        }
    }
}