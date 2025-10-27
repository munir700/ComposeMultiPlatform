package app.shared.mobile.presentation.attachments.chooser.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import app.shared.mobile.presentation.attachments.models.AttachmentPicture
import app.shared.mobile.presentation.theme.colors
import app.shared.mobile.presentation.theme.spacings
import app.shared.mobile.presentation.theme.typography
import app.shared.mobile.resources.Res
import app.shared.mobile.resources.ic_attachment
import app.shared.mobile.resources.remove
import coil3.compose.AsyncImage
import kmp.core.mobile.utils.extensions.noRippleClickable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AttachmentChooserItem(
    modifier: Modifier = Modifier,
    attachment: AttachmentPicture,
    onClick: () -> Unit = {},
) {

    // attachment icon
    if (attachment.isPdf) {
        // pdf place holder
        Image(
            painter = painterResource(Res.drawable.ic_attachment),
            contentDescription = null,
            modifier = modifier.aspectRatio(1f)
        )
    } else {
        // attachment icon
        attachment.fileBytes?.let { bytes ->

            Column {
                // Render image
                AsyncImage(
                    model = bytes,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.aspectRatio(1f)
                )

                // Attachment Remove
                Text(
                    text = stringResource(Res.string.remove),
                    style = typography.primaryMedium14,
                    color = colors.gold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = spacings.spacing10)
                        .noRippleClickable(onClick = onClick)
                )
            }
        }
    }
}