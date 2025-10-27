package app.shared.mobile.location

interface ILocationManager {
    suspend fun getCurrentLocation(): Location?
}