package app.shared.mobile.presentation.attachments.chooser.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import app.shared.mobile.presentation.components.containers.AppCardColumn
import app.shared.mobile.presentation.theme.colors
import app.shared.mobile.presentation.theme.spacings
import app.shared.mobile.presentation.theme.typography
import app.shared.mobile.resources.Res
import app.shared.mobile.resources.max_pages
import app.shared.mobile.resources.min_pages
import org.jetbrains.compose.resources.stringResource

@Composable
fun AttachmentChooserCard(
    modifier: Modifier = Modifier,
    title: String,
    minAttachment: Int,
    maxAttachment: Int,
) {
    // container
    AppCardColumn(
        verticalArrangement = Arrangement.spacedBy(spacings.spacing10),
        modifier = Modifier
            .then(modifier)
    ) {

        // Attachment title
        Text(
            text = title,
            style = typography.primaryMedium18,
            color = colors.shuttleGrey,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing20)
        ) {

            Column {
                // Attachment min pages
                Text(
                    text = stringResource(Res.string.min_pages),
                    style = typography.primaryRegular14,
                    color = colors.shuttleGrey,
                )


                // Attachment max pages
                Text(
                    text = minAttachment.toString(),
                    style = typography.primaryRegular12,
                    color = colors.shuttleGrey,
                    modifier = Modifier
                        .padding(top = spacings.spacing5)
                )
            }

            Spacer(Modifier.weight(1f))

            Column {
                // Attachment min pages
                Text(
                    text = stringResource(Res.string.max_pages),
                    style = typography.primaryRegular14,
                    color = colors.shuttleGrey,
                )


                // Attachment max pages
                Text(
                    text = maxAttachment.toString(),
                    style = typography.primaryRegular12,
                    color = colors.shuttleGrey,
                    modifier = Modifier
                        .padding(top = spacings.spacing5)
                )
            }
        }
    }
}