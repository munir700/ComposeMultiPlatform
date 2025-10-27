package app.shared.mobile.presentation.locationScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import app.shared.mobile.location.Location
import app.shared.mobile.presentation.viewsFactory.IViewsFactory
import app.shared.mobile.presentation.viewsFactory.UIViewType
import org.koin.compose.koinInject

@Composable
actual fun NativeMap(
    modifier: Modifier,
    isLocationEnabled: Boolean,
    zoomLevel: Float,
    selectedLocation: Location?,
    onLocationSelected: (Location) -> Unit
) {
    val viewsFactory = koinInject<IViewsFactory>()

    val view = remember {
        viewsFactory.create(
            type = UIViewType.GoogleMapsView(
                isLocationEnabled = isLocationEnabled,
                zoomLevel = zoomLevel,
                selectedLocation = selectedLocation,
                onLocationSelected = onLocationSelected
            )
        )
    }

    LaunchedEffect(selectedLocation) {
        if (selectedLocation == null) return@LaunchedEffect
        (view as? IosNativeMap)?.updateSelectedLocation(selectedLocation)
    }

    UIKitView(
        modifier = modifier,
        factory = { view }
    )
}