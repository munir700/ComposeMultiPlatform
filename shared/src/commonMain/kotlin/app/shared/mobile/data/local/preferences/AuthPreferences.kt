package app.shared.mobile.data.local.preferences

import app.shared.mobile.domain.models.auth.AuthSession

interface AuthPreferences {

    /**
     * Save authentication session
     */
    suspend fun saveAuthSession(session: AuthSession)

    /**
     * Get authentication session
     */
    suspend fun getAuthSession(): AuthSession?

    /**
     * Clear authentication session
     */
    suspend fun clearAuthSession()

    /**
     * Save user credentials for remember me functionality
     */
    suspend fun saveCredentials(username: String, password: String)

    /**
     * Get saved username
     */
    suspend fun getSavedUsername(): String?

    /**
     * Get saved password
     */
    suspend fun getSavedPassword(): String?

    /**
     * Clear saved credentials
     */
    suspend fun clearSavedCredentials()
}