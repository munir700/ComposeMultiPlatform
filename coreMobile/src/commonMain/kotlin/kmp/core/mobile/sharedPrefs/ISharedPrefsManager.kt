package kmp.core.mobile.sharedPrefs

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface ISharedPrefsManager {
    fun getString(key: String): String?

    fun save(key: String, value: String)

    fun getInt(key: String): Int?

    fun save(key: String, value: Int)

    fun getLong(key: String): Long?

    fun save(key: String, value: Long)

    fun getDouble(key: String): Double?

    fun save(key: String, value: Double)

    fun getFloat(key: String): Float?

    fun save(key: String, value: Float)

    fun getBoolean(key: String): Boolean?

    fun save(key: String, value: Boolean)

    fun delete(key: String)

    fun clearAll()

    fun contains(key: String): Boolean
}

inline fun <reified T> ISharedPrefsManager.getObject(key: String): T? {
    return try {
        val serializedObj = getString(key)
        serializedObj?.let { Json.decodeFromString(it) }
    } catch (_: Throwable) {
        null
    }
}

inline fun <reified T> ISharedPrefsManager.save(key: String, value: T) {
    val serializedObj = Json.encodeToString(value)
    save(key, serializedObj)
}

inline fun <reified T> ISharedPrefsManager.getList(key: String): List<T>? {
    return try {
        val serializedList = getString(key)
        serializedList?.let { Json.decodeFromString(it) }
    } catch (_: Throwable) {
        null
    }
}

inline fun <reified T> ISharedPrefsManager.save(key: String, value: List<T>) {
    val serializedList = Json.encodeToString(value)
    save(key, serializedList)
}