package app.shared.mobile.data.repositories

import app.shared.mobile.data.local.preferences.AuthPreferences
import app.shared.mobile.data.remote.api.AuthApiService
import app.shared.mobile.domain.models.auth.AuthError
import app.shared.mobile.domain.models.auth.AuthSession
import app.shared.mobile.domain.models.auth.LoginRequest
import app.shared.mobile.domain.models.auth.LoginResponse
import app.shared.mobile.domain.models.auth.RefreshTokenRequest
import app.shared.mobile.domain.models.auth.User
import app.shared.mobile.domain.models.auth.UserRole
import app.shared.mobile.domain.repositories.AuthRepository
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val authPreferences: AuthPreferences
) : AuthRepository {

    override suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = authApiService.login(request)

            // Check for errors in response
            if (!response.errors.isNullOrEmpty() || !response.errorDescription.isNullOrEmpty()) {
                val error = AuthError.Companion.fromCode(response.errors ?: response.errorDescription)
                Result.failure(Exception(error.message))
            } else {
                Result.success(response)
            }
        } catch (e: ClientRequestException) {
            val error = when (e.response.status.value) {
                400 -> AuthError.INVALID_CREDENTIALS
                401 -> AuthError.UNAUTHORIZED
                else -> AuthError.UNKNOWN_ERROR
            }
            Result.failure(Exception(error.message))
        } catch (e: ServerResponseException) {
            Result.failure(Exception(AuthError.UNKNOWN_ERROR.message))
        } catch (e: Throwable) {
            Result.failure(Exception(AuthError.NETWORK_ERROR.message))
        }
    }

    override suspend fun refreshToken(request: RefreshTokenRequest): Result<LoginResponse> {
        return try {
            val response = authApiService.refreshToken(request)

            if (!response.errors.isNullOrEmpty() || !response.errorDescription.isNullOrEmpty()) {
                val error = AuthError.Companion.fromCode(response.errors ?: response.errorDescription)
                Result.failure(Exception(error.message))
            } else {
                Result.success(response)
            }
        } catch (e: Throwable) {
            Result.failure(Exception(AuthError.NETWORK_ERROR.message))
        }
    }

    override suspend fun getUserProfile(accessToken: String): Result<User> {
        return try {
            // For now, extract user info from JWT token
            // In the future, this can be replaced with actual API call
            val user = extractUserFromToken(accessToken)
            Result.success(user)
        } catch (e: Throwable) {
            // Fallback to creating user from token or returning error
            Result.failure(Exception("Failed to get user profile"))
        }
    }

    override suspend fun saveAuthSession(session: AuthSession) {
        authPreferences.saveAuthSession(session)
    }

    override suspend fun getAuthSession(): AuthSession? {
        return authPreferences.getAuthSession()
    }

    override suspend fun clearAuthSession() {
        authPreferences.clearAuthSession()
    }

    override suspend fun isAuthenticated(): Boolean {
        val session = getAuthSession()
        return session != null && !isTokenExpired(session)
    }

    override suspend fun getAccessToken(): String? {
        val session = getAuthSession()
        return if (session != null && !isTokenExpired(session)) {
            session.accessToken
        } else {
            null
        }
    }

    override suspend fun saveCredentials(username: String, password: String) {
        authPreferences.saveCredentials(username, password)
    }

    override suspend fun getSavedCredentials(): Pair<String?, String?> {
        val username = authPreferences.getSavedUsername()
        val password = authPreferences.getSavedPassword()
        return Pair(username, password)
    }

    override suspend fun clearSavedCredentials() {
        authPreferences.clearSavedCredentials()
    }

    @OptIn(ExperimentalTime::class)
    private fun isTokenExpired(session: AuthSession): Boolean {
        val currentTime = Clock.System.now().epochSeconds
        return currentTime >= session.expiresIn
    }

    private fun extractUserFromToken(accessToken: String): User {
        // TODO: Implement JWT token parsing to extract user information
        // For now, return a basic user object
        return User(
            id = "user_id",
            username = "username",
            email = null,
            firstName = null,
            lastName = null,
            roles = listOf(UserRole.SELF_SERVICE_USER),
            isActive = true
        )
    }
}