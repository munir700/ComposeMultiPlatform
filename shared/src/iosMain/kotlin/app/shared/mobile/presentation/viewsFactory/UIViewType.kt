package app.shared.mobile.presentation.viewsFactory

import app.shared.mobile.location.Location

sealed class UIViewType {
    data class GoogleMapsView(
        val isLocationEnabled: Boolean = false,
        val zoomLevel: Float = 18f,
        val selectedLocation: Location? = null,
        val onLocationSelected: (Location) -> Unit
    ) : UIViewType()
}
