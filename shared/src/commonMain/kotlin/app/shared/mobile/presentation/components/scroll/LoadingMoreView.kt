package app.shared.mobile.presentation.components.scroll

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kmp.core.mobile.presentation.theme.spacings

@Composable
fun LoadingMoreView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacings.spacing16),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(spacings.spacing32)
        )
    }
}
