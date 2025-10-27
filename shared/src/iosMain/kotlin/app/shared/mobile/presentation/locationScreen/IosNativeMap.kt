package app.shared.mobile.presentation.locationScreen

import app.shared.mobile.location.Location


interface IosNativeMap {
    fun updateSelectedLocation(location: Location)
}