package kmp.core.mobile.sharedPrefs

import com.liftric.kvault.KVault

class SecuredSharedPrefsManager(
    private val kVault: KVault
) : ISharedPrefsManager {

    override fun getString(key: String): String? {
        return kVault.string(key)
    }

    override fun save(key: String, value: String) {
        kVault.set(key, value)
    }

    override fun getInt(key: String): Int? {
        return kVault.int(key)
    }

    override fun save(key: String, value: Int) {
        kVault.set(key, value)
    }

    override fun getLong(key: String): Long? {
        return kVault.long(key)
    }

    override fun save(key: String, value: Long) {
        kVault.set(key, value)
    }

    override fun getDouble(key: String): Double? {
        return kVault.double(key)
    }

    override fun save(key: String, value: Double) {
        kVault.set(key, value)
    }

    override fun getFloat(key: String): Float? {
        return kVault.float(key)
    }

    override fun getBoolean(key: String): Boolean? {
        return kVault.bool(key)
    }

    override fun save(key: String, value: Boolean) {
        kVault.set(key, value)
    }

    override fun save(key: String, value: Float) {
        kVault.set(key, value)
    }

    override fun delete(key: String) {
        kVault.deleteObject(key)
    }

    override fun clearAll() {
        kVault.clear()
    }

    override fun contains(key: String): Boolean {
        return kVault.existsObject(key)
    }
}