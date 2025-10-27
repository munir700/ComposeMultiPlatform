package app.shared.mobile.presentation.location

import app.shared.mobile.location.ILocationManager
import app.shared.mobile.location.Location
import kmp.core.mobile.utils.extensions.resumeIfActive
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.CoreLocation.kCLAuthorizationStatusRestricted
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.darwin.NSObject

class LocationManager : ILocationManager {
    private val locationManager = CLLocationManager()

    init {
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
    }

    override suspend fun getCurrentLocation() = suspendCancellableCoroutine { cont ->
        val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                val location = (didUpdateLocations.lastOrNull() as? CLLocation)
                cont.resumeIfActive(location?.toLocation())
                manager.stopUpdatingLocation()
            }

            override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
                cont.resumeIfActive(null)
            }
        }

        locationManager.delegate = delegate
        when (CLLocationManager.authorizationStatus()) {
            kCLAuthorizationStatusNotDetermined -> locationManager.requestWhenInUseAuthorization()
            kCLAuthorizationStatusDenied, kCLAuthorizationStatusRestricted -> cont.resumeIfActive(
                null
            )

            else -> locationManager.startUpdatingLocation()
        }

        cont.invokeOnCancellation {
            locationManager.stopUpdatingLocation()
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun CLLocation.toLocation() = Location(
    latitude = coordinate.useContents { latitude },
    longitude = coordinate.useContents { longitude }
)