package app.shared.mobile.domain.repositories

import app.shared.mobile.domain.models.user.AuthToken
import app.shared.mobile.domain.models.auth.AuthSession
import app.shared.mobile.domain.models.auth.LoginRequest
import app.shared.mobile.domain.models.auth.LoginResponse
import app.shared.mobile.domain.models.auth.RefreshTokenRequest
import app.shared.mobile.domain.models.auth.User
import kmp.core.mobile.sharedPrefs.ISharedPrefsManager
import kmp.core.mobile.sharedPrefs.getObject
import kmp.core.mobile.sharedPrefs.save


interface AuthRepository {
    
    /**
     * Authenticate user with username and password
     */
    suspend fun login(request: LoginRequest): Result<LoginResponse>
    
    /**
     * Refresh access token using refresh token
     */
    suspend fun refreshToken(request: RefreshTokenRequest): Result<LoginResponse>
    
    /**
     * Get user profile information
     */
    suspend fun getUserProfile(accessToken: String): Result<User>
    
    /**
     * Save authentication session locally
     */
    suspend fun saveAuthSession(session: AuthSession)
    
    /**
     * Get saved authentication session
     */
    suspend fun getAuthSession(): AuthSession?
    
    /**
     * Clear authentication session (logout)
     */
    suspend fun clearAuthSession()
    
    /**
     * Check if user is authenticated
     */
    suspend fun isAuthenticated(): Boolean
    
    /**
     * Get current access token
     */
    suspend fun getAccessToken(): String?
    
    /**
     * Save credentials for auto-login (if user chooses to remember)
     */
    suspend fun saveCredentials(username: String, password: String)
    
    /**
     * Get saved credentials
     */
    suspend fun getSavedCredentials(): Pair<String?, String?>
    
    /**
     * Clear saved credentials
     */
    suspend fun clearSavedCredentials()
    
}

private const val KEY_AUTH_TOKEN = "main-auth-token"

internal fun ISharedPrefsManager.getAuthToken(): AuthToken? {
    return getObject(KEY_AUTH_TOKEN)
}

private fun ISharedPrefsManager.saveAuthToken(authToken: AuthToken) {
    this.save(KEY_AUTH_TOKEN, authToken)
}

private fun ISharedPrefsManager.deleteAuthToken() {
    this.delete(KEY_AUTH_TOKEN)
}