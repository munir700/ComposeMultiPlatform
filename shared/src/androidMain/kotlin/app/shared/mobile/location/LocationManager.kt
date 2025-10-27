package app.shared.mobile.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location as AndroidLocation

class LocationManager(
    private val context: Context
) : ILocationManager {

   /* private val locationClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }*/

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation() = null //suspendCancellableCoroutine { cont ->
  /*      locationClient
            .getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
            .addOnCompleteListener {
                cont.resumeIfActive(
                    it.result?.toLocation()
                )
            }
            .addOnCanceledListener {
                cont.cancelIfActive()
            }
    }*/
}

private fun AndroidLocation.toLocation() = Location(
    latitude = latitude,
    longitude = longitude
)