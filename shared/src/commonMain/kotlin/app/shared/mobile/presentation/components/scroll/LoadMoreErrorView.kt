package app.shared.mobile.presentation.components.scroll

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.shared.mobile.resources.Res
import app.shared.mobile.resources.failed_to_load_more_items
import app.shared.mobile.resources.retry
import kmp.core.mobile.presentation.theme.colors
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.presentation.theme.typography
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoadMoreErrorView(
    message: String,
    btnLabel: String? = null,
    onRetry: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = spacings.spacing8),
        colors = CardDefaults.cardColors(
            containerColor = colors.darkElectricBlue.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacings.spacing16),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacings.spacing12)
        ) {
            Text(
                text = stringResource(Res.string.failed_to_load_more_items),
                style = typography.primaryBold14,
                color = colors.darkElectricBlue
            )
            Text(
                text = message,
                style = typography.labelSmall,
                color = colors.darkElectricBlue.copy(alpha = 0.7f)
            )
            Button(
                onClick = onRetry,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(btnLabel ?: stringResource(Res.string.retry))
            }
        }
    }
}