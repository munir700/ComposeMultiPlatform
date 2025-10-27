package kmp.core.mobile.remoteConfigs

import dev.gitlive.firebase.remoteconfig.FirebaseRemoteConfig
import dev.gitlive.firebase.remoteconfig.get
import kmp.core.mobile.AppLogger
import kmp.core.mobile.sharedPrefs.ISharedPrefsManager
import kmp.core.mobile.sharedPrefs.save
import kmp.core.mobile.utils.extensions.toJsonObject
import kmp.core.mobile.utils.extensions.toPairs
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlin.time.Duration

class FirebaseRemoteConfigs(
    private val sharedPrefsManager: ISharedPrefsManager,
    private val firebaseConfigs: FirebaseRemoteConfig,
    private val logger: AppLogger
) : IRemoteConfigs {
    private var isInitialized = false

    override fun isInitialized(): Boolean {
        return isInitialized
    }

    override suspend fun init(minFetchIntervalSeconds: Duration) {
        setSettings(minFetchIntervalSeconds)
        loadDefaults()
        fetchConfigsFromRemote()
        isInitialized = true
    }

    private suspend fun setSettings(minimumFetchIntervalSeconds: Duration) {
        try {
            firebaseConfigs.settings {
                minimumFetchInterval = minimumFetchIntervalSeconds
            }
            logger.log("Updated settings")
        } catch (e: Throwable) {
            logger.log("Error: (${e.message})")
        }
    }

    private suspend fun loadDefaults() {
        try {
            // Get and validate cached configs
            val cachedConfigs = sharedPrefsManager.getString(IRemoteConfigs.Companion.CACHED_REMOTE_CONFIGS) ?: return
            val configsObject = Json.parseToJsonElement(cachedConfigs).jsonObject

            // Set defaults
            firebaseConfigs.setDefaults(
                *configsObject.toPairs().toTypedArray()
            )

            logger.log("Loaded defaults ($cachedConfigs)")
        } catch (e: Throwable) {
            logger.log("Error: (${e.message})")
        }
    }

    private suspend fun fetchConfigsFromRemote() {
        try {
            // Fetch
            firebaseConfigs.fetchAndActivate()
            val updatedConfigs = firebaseConfigs.all
                .map { (key, value) -> key to value.asString() }
                .toMap()
                .toJsonObject()

            // Then cache it
            sharedPrefsManager.save(IRemoteConfigs.Companion.CACHED_REMOTE_CONFIGS, updatedConfigs)

            logger.log("Updated configs from remote ($updatedConfigs)")
        } catch (e: Throwable) {
            logger.log("Error: (${e.message})")
        }
    }


    override fun getString(key: String): String? {
        return firebaseConfigs.get(key)
    }

    @Throws(Throwable::class)
    override suspend fun forceGetString(key: String): String? {
        val value = getString(key)
        if (value == null || value == DEF_STRING_VALUE) {
            fetchConfigsFromRemote()
        }

        return getString(key)
    }

    override fun getBoolean(key: String): Boolean? {
        return firebaseConfigs[key] as Boolean?
    }

    @Throws(Throwable::class)
    override suspend fun forceGetBoolean(key: String): Boolean? {
        val value = getBoolean(key)
        if (value == null || !value) {
            fetchConfigsFromRemote()
        }

        return getBoolean(key)
    }

    override fun getDouble(key: String): Double? {
        return firebaseConfigs[key]
    }

    @Throws(Throwable::class)
    override suspend fun forceGetDouble(key: String): Double? {
        val value = getDouble(key)
        if (value == null || value == DEF_DOUBLE_VALUE) {
            fetchConfigsFromRemote()
        }

        return getDouble(key)
    }

    override fun getLong(key: String): Long? {
        return firebaseConfigs[key]
    }

    @Throws(Throwable::class)
    override suspend fun forceGetLong(key: String): Long? {
        val value = getLong(key)
        if (value == null || value == DEF_LONG_VALUE) {
            fetchConfigsFromRemote()
        }

        return getLong(key)
    }

    override fun getInt(key: String): Int? {
        return firebaseConfigs[key]
    }

    @Throws(Throwable::class)
    override suspend fun forceGetInt(key: String): Int? {
        val value = getInt(key)
        if (value == null || value == DEF_INT_VALUE) {
            fetchConfigsFromRemote()
        }

        return getInt(key)
    }

    companion object {
        private const val DEF_STRING_VALUE = ""
        private const val DEF_BOOL_VALUE = false
        private const val DEF_DOUBLE_VALUE = 0.0
        private const val DEF_LONG_VALUE = 0L
        private val DEF_INT_VALUE: Int? = null
    }
}