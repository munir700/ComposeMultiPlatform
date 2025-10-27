package app.shared.mobile.presentation.locationScreen


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import app.shared.mobile.presentation.locationScreen.LocationContract.Event
import app.shared.mobile.presentation.locationScreen.components.LocationContent
import app.shared.mobile.presentation.locationScreen.models.LocationParams
import kmp.core.mobile.base.BaseScreen
import kmp.core.mobile.base.SIDE_EFFECTS_KEY
import kmp.core.mobile.base.rememberViewModel

class LocationScreen(
    private val locationParams: LocationParams
) : BaseScreen<LocationViewModel>() {

    override val screenTag: String = "LocationScreen" // This must match the key in factory


    @Composable
    override fun Content() {
        // Get the view model
        val viewModel = rememberViewModel<LocationViewModel>()

        // Init view model
        LaunchedEffect(SIDE_EFFECTS_KEY) {
            viewModel.setEvent(
                Event.Init(
                    pickLocation = locationParams.pickLocation,
                    title = locationParams.title,
                    location = locationParams.location
                )
            )
        }

        // Render content
        LocationContent(
            state = viewModel.viewState.value,
            onEvent = viewModel::setEvent
        )
    }
}