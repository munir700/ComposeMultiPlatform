package app.shared.mobile.domain.repositories

import app.shared.mobile.constants.UserRoles
import app.shared.mobile.domain.models.user.Employee
import app.shared.mobile.domain.models.user.Employer
import app.shared.mobile.domain.data.user.UserApi
import app.shared.mobile.domain.models.user.IUser
import kmp.core.mobile.sharedPrefs.ISharedPrefsManager
import kmp.core.mobile.sharedPrefs.getObject
import kmp.core.mobile.sharedPrefs.save


class UserRepository(
    private val userApi: UserApi,
    private val sharedPrefsManager: ISharedPrefsManager
) : BaseRepository() {

    private var user: IUser? = null

    suspend fun getCurrentUser(forceGet: Boolean = false): IUser {
        val cachedUser = getCachedUser()
        val currentUser = if (forceGet || cachedUser == null) {
            // Get from the API and cache it
            val user = getUserFromApi()
            cacheUser(user)

            // Then return it
            user
        } else {
            // Just return the cached user
            cachedUser
        }

        user = currentUser

        // Return the current user
        return currentUser
    }

    private suspend fun getUserFromApi(): IUser {
        // Get and validate current token role
        val userRole = sharedPrefsManager.getAuthToken()
            ?.claims
            ?.getRole()
            ?: error("Selected role is null")

        val profileResponse = when (userRole) {
            UserRoles.EMPLOYEE.role -> userApi.getEmployeeBasicProfile()
            UserRoles.EMPLOYER.role -> userApi.getEmployerBasicProfile()
            else -> userApi.getEmployerBasicProfile()
        }

        return profileResponse.body?.body?.firstOrNull() ?: error("Not found")
    }

    fun getCachedUser(): IUser? {
        return user ?: sharedPrefsManager.getObject(KEY_CACHED_USER)
    }

    fun getUserRole(): UserRoles? {
        val role = sharedPrefsManager.getAuthToken()?.claims?.getRole()
        return role?.let { roleName ->
            UserRoles.entries.firstOrNull { it.role == roleName }
        }
    }

    private fun cacheUser(user: IUser) {
        return sharedPrefsManager.save(KEY_CACHED_USER, user)
    }

    fun clearCachedUser() {
        user = null
        return sharedPrefsManager.delete(KEY_CACHED_USER)
    }
    suspend fun getEmployerProfile(): Employer {
        return userApi.getEmployerProfile().body?.body?.firstOrNull() ?: error("Not found")
    }

    suspend fun getEmployeeProfile(): Employee {
        return userApi.getEmployeeProfile().body?.body?.firstOrNull() ?: error("Not found")
    }

    suspend fun getEmployeeProfileByCode(labourCard: String): Employee {
        return userApi.getEmployeeProfileByCode(employeeCode = labourCard).body?.body?.firstOrNull()
            ?: error("Not found")
    }




    fun updateCacheUser(user: IUser) {
        this.user = user
        sharedPrefsManager.save(KEY_CACHED_USER, user)
    }

    companion object {
        private val CLASS_NAME = UserRepository::class.simpleName
        val KEY_CACHED_USER = "$CLASS_NAME-authToken"
    }
}