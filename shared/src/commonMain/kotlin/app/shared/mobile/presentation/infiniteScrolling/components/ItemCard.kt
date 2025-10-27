package app.shared.mobile.presentation.infiniteScrolling.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.shared.mobile.data.remote.models.ItemModel
import app.shared.mobile.presentation.theme.colors
import app.shared.mobile.presentation.theme.spacings
import app.shared.mobile.presentation.theme.typography

@Composable
internal fun ItemCard(item: ItemModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = spacings.spacing2)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacings.spacing16)
        ) {
            Text(
                text = item.title,
                style = typography.primaryBold16,
                color = colors.darkElectricBlue
            )
            Spacer(modifier = Modifier.height(spacings.spacing8))
            Text(
                text = item.description,
                style = typography.primaryRegular14,
                color = colors.darkElectricBlue.copy(alpha = 0.7f)
            )
        }
    }
}