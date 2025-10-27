package app.shared.mobile.presentation.locationScreen


import app.shared.mobile.location.ILocationManager
import app.shared.mobile.location.Location
import app.shared.mobile.presentation.base.AppBaseViewModel
import app.shared.mobile.presentation.locationScreen.LocationContract.Effect
import app.shared.mobile.presentation.locationScreen.LocationContract.Event
import app.shared.mobile.presentation.locationScreen.LocationContract.State
import kmp.core.mobile.permissions.IPermissionManager
import kmp.core.mobile.permissions.enums.Permission


class LocationViewModel(
    private val permissionManager: IPermissionManager,
    private val locationManager: ILocationManager,
) :
    AppBaseViewModel<State, Event, Effect>() {
    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        is Event.Init -> init(
            pickLocation = event.pickLocation,
            title = event.title,
            location = event.location
        )

        Event.BackClicked -> handleBackClick()
        is Event.MapLocationClicked -> handleMapLocationClick(event.location)
        is Event.LocationConfirmed -> handleLocationConfirm()
    }

    private fun init(
        pickLocation: Boolean,
        title: String?,
        location: Location?
    ) {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Set default params states
        setState {
            copy(
                pickLocation = pickLocation,
                title = title,
                selectedLocation = location
            )
        }

        // Fetch location
        fetchLocation()

        // set state initialized
        setState { copy(isInitialized = true) }

    }

    private fun handleBackClick() {
        navManager.goBack()
    }

    private fun handleMapLocationClick(location: Location) {
        setState { copy(selectedLocation = location) }
    }

    private fun handleLocationConfirm() {
        currentState.selectedLocation ?: return
        /* navManager.goBackWithResult(
             LocationScreen::class.simpleName,
             currentState.selectedLocation
         )*/
    }

    private fun fetchLocation() = executeSafe({
        // Request location permission first
        permissionManager.requestPermission(Permission.LOCATION)
        val myLocationEnabled = permissionManager.isPermissionGranted(Permission.LOCATION)
        val currentLocation = locationManager.getCurrentLocation() ?: return@executeSafe
        setState {
            copy(
                isLocationEnabled = myLocationEnabled,
                currentLocation = currentLocation
            )
        }
        updateSelectedLocation(currentLocation = currentLocation)
    })

    private fun updateSelectedLocation(currentLocation: Location) {
        if (currentState.selectedLocation?.latitude == 0.0) {
            setState { copy(selectedLocation = currentLocation) }
        }
    }
}