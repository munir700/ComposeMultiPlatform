package app.sunreef.yachts.mobile.constants

/**
 * Application Constants for Sunreef Yachts
 * Centralized configuration and constants
 */

object SunreefYachtsConstants {

    // App Configuration
    const val APP_NAME = "Sunreef Yachts"
    const val APP_VERSION = "1.0.0"

    // Engine Constants
    const val DEFAULT_RPM_CHECK_INTERVAL = 500L // milliseconds
    const val RPM_WARNING_THRESHOLD = 3500
    const val TEMPERATURE_WARNING_CELSIUS = 95f
    const val OIL_PRESSURE_WARNING_BAR = 0.5f

    // Navigation Constants
    const val GPS_UPDATE_INTERVAL = 1000L // milliseconds
    const val AUTOPILOT_RESPONSE_TIME = 500L // milliseconds
    const val WAYPOINT_PROXIMITY_METERS = 50f

    // Electrical Constants
    const val BATTERY_WARNING_VOLTAGE = 20f // volts
    const val BATTERY_CRITICAL_VOLTAGE = 18f // volts
    const val LOW_BATTERY_PERCENTAGE = 20f

    // Water System Constants
    const val FRESHWATER_LOW_PERCENTAGE = 25f
    const val BLACKWATER_HIGH_PERCENTAGE = 75f

    // Climate Constants
    const val MIN_TEMPERATURE_CELSIUS = 16f
    const val MAX_TEMPERATURE_CELSIUS = 30f
    const val DEFAULT_TEMPERATURE_CELSIUS = 22f
    const val HUMIDITY_WARNING_PERCENTAGE = 75f

    // Security Constants
    const val ALARM_TEST_DURATION_SECONDS = 10
    const val MOTION_DETECTION_TIMEOUT_SECONDS = 30

    // Protocol Constants
    const val NMEA_2000_BROADCAST_INTERVAL = 1000L // milliseconds
    const val MODBUS_RESPONSE_TIMEOUT = 5000L // milliseconds
    const val MQTT_RECONNECT_INTERVAL = 5000L // milliseconds

    // Network Constants
    const val NETWORK_TIMEOUT = 10000L // milliseconds
    const val MAX_RETRY_ATTEMPTS = 3
    const val RETRY_DELAY = 1000L // milliseconds

    // Data Constants
    const val LOCATION_UPDATE_INTERVAL = 1000L // milliseconds
    const val TELEMETRY_UPDATE_INTERVAL = 2000L // milliseconds
    const val HISTORY_RETENTION_DAYS = 30
}

