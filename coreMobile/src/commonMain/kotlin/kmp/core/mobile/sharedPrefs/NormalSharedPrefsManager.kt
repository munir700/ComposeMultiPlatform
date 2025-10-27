package kmp.core.mobile.sharedPrefs

import com.russhwolf.settings.Settings
import com.russhwolf.settings.contains
import com.russhwolf.settings.set


class NormalSharedPrefsManager(
    private val settings: Settings
) : ISharedPrefsManager {

    override fun getString(key: String): String? {
        return settings.ifExists(key)?.getString(key, "")
    }

    override fun save(key: String, value: String) {
        settings[key] = value
    }

    override fun getInt(key: String): Int? {
        return settings.ifExists(key)?.getInt(key, 0)
    }

    override fun save(key: String, value: Int) {
        settings[key] = value
    }

    override fun getLong(key: String): Long? {
        return settings.ifExists(key)?.getLong(key, 0)
    }

    override fun save(key: String, value: Long) {
        settings[key] = value
    }

    override fun getDouble(key: String): Double? {
        return settings.ifExists(key)?.getDouble(key, 0.0)
    }

    override fun save(key: String, value: Double) {
        settings[key] = value
    }

    override fun getFloat(key: String): Float? {
        return settings.ifExists(key)?.getFloat(key, 0f)
    }

    override fun save(key: String, value: Float) {
        settings[key] = value
    }

    override fun getBoolean(key: String): Boolean? {
        return settings.ifExists(key)?.getBoolean(key, false)
    }

    override fun save(key: String, value: Boolean) {
        settings[key] = value
    }

    override fun delete(key: String) {
        settings.remove(key)
    }

    override fun clearAll() {
        settings.clear()
    }

    override fun contains(key: String): Boolean {
        return settings.contains(key)
    }

    private fun Settings.ifExists(key: String): Settings? {
        return if (contains(key)) this else null
    }
}