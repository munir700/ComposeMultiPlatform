package app.shared.mobile.presentation.attachments.chooser.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import app.shared.mobile.presentation.attachments.chooser.AttachmentChooserContract
import app.shared.mobile.presentation.components.Toolbar
import app.shared.mobile.presentation.theme.spacings
import app.shared.mobile.resources.Res
import kmp.core.mobile.presentation.container.SafeInsetsColumn
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import app.shared.mobile.presentation.attachments.chooser.AttachmentChooserContract.Event
import app.shared.mobile.presentation.attachments.chooser.AttachmentChooserContract.State
import app.shared.mobile.presentation.theme.colors
import app.shared.mobile.presentation.theme.shapes
import app.shared.mobile.presentation.theme.typography
import app.shared.mobile.resources.attachments_pictures
import app.shared.mobile.resources.ic_add_attachment
import app.shared.mobile.resources.submit
import kmp.core.mobile.presentation.buttons.StrokedPrimaryButton
import kmp.core.mobile.utils.extensions.noRippleClickable

@Composable
internal fun AttachmentChooserContent(
    state: State,
    onEvent: (Event) -> Unit
) {
    // Container
    SafeInsetsColumn(
        verticalArrangement = Arrangement.spacedBy(spacings.spacing18),
        modifier = Modifier
            .fillMaxSize()
            .padding(all = spacings.spacing10)
    ) {

        Toolbar(
            title = stringResource(Res.string.attachments_pictures),
            onBackClick = {
                onEvent.invoke(AttachmentChooserContract.Event.BackClicked)
            }
        )

        // Header card
        state.attachment?.let { picAttachment ->
            AttachmentChooserCard(
                title = picAttachment.title,
                minAttachment = picAttachment.minPage,
                maxAttachment = picAttachment.maxPage,
                modifier = Modifier.padding(top = spacings.spacing20)
            )
        }


        // Container grid
        LazyVerticalGrid(
            verticalArrangement = Arrangement.spacedBy(spacings.spacing20),
            horizontalArrangement = Arrangement.spacedBy(spacings.spacing20),
            columns = GridCells.Adaptive(minSize = spacings.spacing80),
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            state.attachment?.attachedPics?.let { items ->
                itemsIndexed(items) { index, item ->
                    AttachmentChooserItem(
                        attachment = item,
                        onClick = {
                            onEvent.invoke(Event.RemoveAttachmentClicked(index = index))
                        }
                    )
                }
            }.also {
                item {
                    // Use a Box to ensure the image is centered both horizontally and vertically
                    Box(
                        contentAlignment = Alignment.Center, // Center the content inside the box
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(spacings.spacing10)
                            .border(
                                border = BorderStroke(
                                    width = spacings.spacing1,
                                    color = colors.gold
                                ),
                                shape = shapes.roundedCorner16
                            )
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.ic_add_attachment),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = colors.gold),
                            modifier = Modifier.size(spacings.spacing50)
                                .noRippleClickable {
                                    onEvent.invoke(Event.AddAttachmentClicked)
                                }
                        )
                    }
                }
            }
        }

        // upload
        StrokedPrimaryButton(
            isSmallHeight = true,
            text = stringResource(Res.string.submit),
            textColor = colors.elPaso,
            textStyle = typography.primaryMedium14,
            strokeWidth = spacings.spacingHalf,
            strokeColor = colors.shuttleGrey,
            onClick = {
                onEvent.invoke(Event.SubmitAttachmentsClicked)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = spacings.spacing20)
                .padding(horizontal = spacings.spacing20)
        )
    }
}