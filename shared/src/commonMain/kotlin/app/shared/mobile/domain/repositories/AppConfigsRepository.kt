package app.shared.mobile.domain.repositories

import kmp.core.mobile.utils.extensions.orFalse


class AppConfigsRepository : BaseRepository() {
    fun isBiometricEnabled(): Boolean {
        return normalSharedPrefs.getBoolean(IS_BIOMETRIC_ENABLED).orFalse()
    }

    fun updateBiometricEnabled(isEnabled: Boolean) {
        normalSharedPrefs.save(IS_BIOMETRIC_ENABLED, isEnabled)
    }

    companion object {
        private const val IS_BIOMETRIC_ENABLED = "IS_BIOMETRIC_ENABLED"
    }
}