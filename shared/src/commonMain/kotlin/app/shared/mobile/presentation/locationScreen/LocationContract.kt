package app.shared.mobile.presentation.locationScreen

import app.shared.mobile.location.Location
import kmp.core.mobile.base.ViewEvent
import kmp.core.mobile.base.ViewSideEffect
import kmp.core.mobile.base.ViewState


class LocationContract {
    data class State(
        val isInitialized: Boolean = false,
        val pickLocation: Boolean = false,
        val isLocationEnabled: Boolean = false,
        val title: String? = null,
        val selectedLocation: Location? = null,
        val currentLocation: Location? = null,
    ) : ViewState

    sealed class Event : ViewEvent {
        data class Init(
            val pickLocation: Boolean,
            val title: String?,
            val location: Location
        ) : Event()

        data object BackClicked : Event()
        data class MapLocationClicked(val location: Location) : Event()
        data object LocationConfirmed : Event()
    }

    sealed class Effect : ViewSideEffect
}