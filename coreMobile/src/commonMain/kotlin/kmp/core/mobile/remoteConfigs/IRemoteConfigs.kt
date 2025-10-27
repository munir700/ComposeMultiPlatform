package kmp.core.mobile.remoteConfigs

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

val Class = IRemoteConfigs::class

interface IRemoteConfigs {
    fun isInitialized(): Boolean

    suspend fun init(minFetchIntervalSeconds: Duration = DEFAULT_MIN_FETCH_INTERVAL_SECONDS)

    fun getString(key: String): String?

    @Throws(Throwable::class)
    suspend fun forceGetString(key: String): String?

    fun getBoolean(key: String): Boolean?

    @Throws(Throwable::class)
    suspend fun forceGetBoolean(key: String): Boolean?

    fun getDouble(key: String): Double?

    @Throws(Throwable::class)
    suspend fun forceGetDouble(key: String): Double?

    fun getLong(key: String): Long?

    @Throws(Throwable::class)
    suspend fun forceGetLong(key: String): Long?

    fun getInt(key: String): Int?

    @Throws(Throwable::class)
    suspend fun forceGetInt(key: String): Int?

    companion object {
        private val DEFAULT_MIN_FETCH_INTERVAL_SECONDS = 0L.toDuration(
            unit = DurationUnit.SECONDS
        )
        const val CACHED_REMOTE_CONFIGS = "cached_remote_configs"
    }
}