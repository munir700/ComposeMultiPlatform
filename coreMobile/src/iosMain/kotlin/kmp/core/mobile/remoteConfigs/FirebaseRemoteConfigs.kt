package kmp.core.mobile.remoteConfigs

import kmp.core.mobile.AppLogger
import kmp.core.mobile.sharedPrefs.ISharedPrefsManager
import kotlin.time.Duration

class FirebaseRemoteConfigs(
    private val sharedPrefsManager: ISharedPrefsManager,
    //private val firebaseConfigs: FirebaseRemoteConfig,
    private val logger: AppLogger
) : IRemoteConfigs {
    override fun isInitialized(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun init(minFetchIntervalSeconds: Duration) {
        TODO("Not yet implemented")
    }

    override fun getString(key: String): String? {
        TODO("Not yet implemented")
    }

    override suspend fun forceGetString(key: String): String? {
        TODO("Not yet implemented")
    }

    override fun getBoolean(key: String): Boolean? {
        TODO("Not yet implemented")
    }

    override suspend fun forceGetBoolean(key: String): Boolean? {
        TODO("Not yet implemented")
    }

    override fun getDouble(key: String): Double? {
        TODO("Not yet implemented")
    }

    override suspend fun forceGetDouble(key: String): Double? {
        TODO("Not yet implemented")
    }

    override fun getLong(key: String): Long? {
        TODO("Not yet implemented")
    }

    override suspend fun forceGetLong(key: String): Long? {
        TODO("Not yet implemented")
    }

    override fun getInt(key: String): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun forceGetInt(key: String): Int? {
        TODO("Not yet implemented")
    }
}