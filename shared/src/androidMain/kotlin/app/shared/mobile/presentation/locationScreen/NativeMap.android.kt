package app.shared.mobile.presentation.locationScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import app.shared.mobile.location.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kmp.core.mobile.utils.extensions.orZero

@Composable
actual fun NativeMap(
    modifier: Modifier,
    isLocationEnabled: Boolean,
    zoomLevel: Float,
    selectedLocation: Location?,
    onLocationSelected: (Location) -> Unit
) {
    val cameraPositionState = rememberCameraPositionState()

    val properties = remember {
        MapProperties(
            isMyLocationEnabled = isLocationEnabled
        )
    }

    val uiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = true,
            myLocationButtonEnabled = isLocationEnabled
        )
    }

    // Update camera position when selectedLocation or zoomLevel changes
    LaunchedEffect(selectedLocation, zoomLevel) {
        selectedLocation?.let { location ->
            val latLng = LatLng(location.latitude.orZero(), location.longitude.orZero())
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel)
            )
        }
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings,
        onMapClick = { latLng ->
            onLocationSelected(Location(latLng.latitude, latLng.longitude))
        }
    ) {
        // Add marker for selected location
        selectedLocation?.let { location ->
            val markerPosition = LatLng(location.latitude.orZero(), location.longitude.orZero())
            Marker(
                state = MarkerState(position = markerPosition)
            )
        }
    }
}