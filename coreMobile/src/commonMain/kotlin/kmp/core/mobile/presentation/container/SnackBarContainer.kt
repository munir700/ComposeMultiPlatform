package kmp.core.mobile.presentation.container

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import kmp.core.mobile.globalState.models.SnackBarParams
import kmp.core.mobile.globalState.models.SnackBarType
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.snackBar.ErrorSnackBar
import kmp.core.mobile.snackBar.SuccessSnackBar
import kmp.core.mobile.visibilities.TopSlideVisibility

@Composable
internal fun SnackBarContainer(
    modifier: Modifier = Modifier,
    snackBarState: State<SnackBarParams>,
    onHideSnackBar: () -> Unit
) {
    val snackBarParams = snackBarState.value

    TopSlideVisibility(
        modifier = modifier,
        visible = snackBarParams.isVisible
    ) {
        when (snackBarParams.type) {
            SnackBarType.ERROR -> ErrorSnackBar(
                modifier = Modifier.padding(top = spacings.noSpacing),
                text = snackBarParams.message,
                onClick = onHideSnackBar
            )

            SnackBarType.SUCCESS -> SuccessSnackBar(
                text = snackBarParams.message,
                onClick = onHideSnackBar
            )
        }
    }
}