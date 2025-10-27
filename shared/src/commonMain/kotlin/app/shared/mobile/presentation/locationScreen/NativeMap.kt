package app.shared.mobile.presentation.locationScreen


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.shared.mobile.location.Location

@Composable
expect fun NativeMap(
    modifier: Modifier = Modifier,
    isLocationEnabled: Boolean = false,
    zoomLevel: Float = 18f,
    selectedLocation: Location?,
    onLocationSelected: (Location) -> Unit
)