package app.sunreef.yachts.mobile.domain.repositories

import kotlinx.serialization.Serializable

@Serializable
data class NMEA2000Frame(
    val pgn: Int,
    val source: Int,
    val destination: Int,
    val priority: Int,
    val payload: ByteArray,
    val timestamp: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NMEA2000Frame) return false
        if (pgn != other.pgn) return false
        if (source != other.source) return false
        if (destination != other.destination) return false
        if (priority != other.priority) return false
        if (!payload.contentEquals(other.payload)) return false
        if (timestamp != other.timestamp) return false
        return true
    }

    override fun hashCode(): Int {
        var result = pgn
        result = 31 * result + source
        result = 31 * result + destination
        result = 31 * result + priority
        result = 31 * result + payload.contentHashCode()
        result = 31 * result + timestamp.hashCode()
        return result
    }
}