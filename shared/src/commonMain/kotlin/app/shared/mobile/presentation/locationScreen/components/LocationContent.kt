package app.shared.mobile.presentation.locationScreen.components


import app.shared.mobile.presentation.locationScreen.LocationContract.Event
import app.shared.mobile.presentation.locationScreen.LocationContract.State
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.core.mobile.resources.Res
import app.core.mobile.resources.confirm
import app.shared.mobile.presentation.locationScreen.NativeMap

import app.shared.mobile.presentation.theme.colors
import app.shared.mobile.presentation.theme.spacings
import app.shared.mobile.presentation.toolbars.AppToolbar
import kmp.core.mobile.presentation.buttons.PrimaryTextButton

import kmp.core.mobile.presentation.container.SafeInsetsBox
import org.jetbrains.compose.resources.stringResource

@Composable
fun LocationContent(
    state: State,
    onEvent: (Event) -> Unit,
) {

    // Box container
    SafeInsetsBox(
        modifier = Modifier.fillMaxSize()
    ) {

        // Native map
        NativeMap(
            selectedLocation = state.selectedLocation,
            isLocationEnabled = state.isLocationEnabled,
            onLocationSelected = { location ->
                onEvent.invoke(Event.MapLocationClicked(location))
            },
            modifier = Modifier.fillMaxSize()
        )

        AppToolbar(
            showBack = true,
            title = state.title,
            titleColor = colors.gold,
            modifier = Modifier
                .padding(horizontal = spacings.spacing16)
                .align(Alignment.TopCenter),
            onBackClick = {
                onEvent.invoke(Event.BackClicked)
            }
        )
        if (state.pickLocation)
            PrimaryTextButton(
                text = stringResource(Res.string.confirm),
                modifier = Modifier.padding(spacings.spacing16)
                    .align(Alignment.BottomCenter),
                onClick = {
                    onEvent.invoke(Event.LocationConfirmed)
                })
    }
}