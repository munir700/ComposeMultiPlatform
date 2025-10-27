package app.shared.mobile.data.local.preferences

import app.shared.mobile.domain.models.auth.AuthSession
import kmp.core.mobile.sharedPrefs.ISharedPrefsManager
import kmp.core.mobile.sharedPrefs.getObject
import kmp.core.mobile.sharedPrefs.save


class AuthPreferencesImpl(
    private val sharedPrefs: ISharedPrefsManager
) : AuthPreferences {
    
    companion object {
        private const val AUTH_SESSION_KEY = "auth_session"
        private const val SAVED_USERNAME_KEY = "saved_username"
        private const val SAVED_PASSWORD_KEY = "saved_password"
    }
    
    override suspend fun saveAuthSession(session: AuthSession) {
        sharedPrefs.save(AUTH_SESSION_KEY, session)
    }
    
    override suspend fun getAuthSession(): AuthSession? {
        return sharedPrefs.getObject<AuthSession>(AUTH_SESSION_KEY)
    }
    
    override suspend fun clearAuthSession() {
        sharedPrefs.delete(AUTH_SESSION_KEY)
    }
    
    override suspend fun saveCredentials(username: String, password: String) {
        sharedPrefs.save(SAVED_USERNAME_KEY, username)
        sharedPrefs.save(SAVED_PASSWORD_KEY, password)
    }
    
    override suspend fun getSavedUsername(): String? {
        return sharedPrefs.getString(SAVED_USERNAME_KEY)
    }
    
    override suspend fun getSavedPassword(): String? {
        return sharedPrefs.getString(SAVED_PASSWORD_KEY)
    }
    
    override suspend fun clearSavedCredentials() {
        sharedPrefs.delete(SAVED_USERNAME_KEY)
        sharedPrefs.delete(SAVED_PASSWORD_KEY)
    }
}