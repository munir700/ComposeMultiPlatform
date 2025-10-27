package app.shared.mobile.presentation.components.scroll

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.shared.mobile.resources.Res
import app.shared.mobile.resources.error
import app.shared.mobile.resources.retry
import kmp.core.mobile.presentation.theme.colors
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.presentation.theme.typography
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorView(
    message: String,
    error: String? = null,
    btnLabel: String? = null,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacings.spacing16),
            modifier = Modifier.padding(spacings.spacing24)
        ) {
            Text(
                text = "⚠️",
                style = typography.headline
            )
            Text(
                text = error ?: stringResource(Res.string.error),
                style = typography.sheetAction,
                color = colors.darkElectricBlue
            )
            Text(
                text = message,
                style = typography.labelMedium,
                color = colors.darkElectricBlue.copy(alpha = 0.7f)
            )
            Button(onClick = onRetry) {
                Text(btnLabel ?: stringResource(Res.string.retry))
            }
        }
    }
}