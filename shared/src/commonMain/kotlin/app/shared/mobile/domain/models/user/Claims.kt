package app.shared.mobile.domain.models.user


import app.shared.mobile.constants.UserRoles
import kmp.core.mobile.date.now
import kmp.core.mobile.date.toMillis
import kmp.core.mobile.utils.extensions.orFalse
import kmp.core.mobile.utils.extensions.orZero
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.milliseconds
import kmp.core.mobile.utils.extensions.contains

@Serializable
class Claims {
    @SerialName("unique_id")
    var uniqueId: String? = null

    @SerialName("security_stamp")
    var securityStamp: String? = null

    @SerialName("exp")
    var expStamp: Long? = null

    @SerialName("role")
    var roles: List<String>? = null


    fun isEmployee(): Boolean {
        return roles?.contains { it == UserRoles.EMPLOYEE.role }.orFalse()
    }


    fun isEmployer(): Boolean {
        return roles?.contains { it == UserRoles.EMPLOYER.role }.orFalse()
    }

    fun isPro(): Boolean {
        return roles?.contains { it == UserRoles.PRO.role }.orFalse()
    }

    fun getRole(): String? {
        return roles?.firstOrNull()
    }

    fun isSessionExpired(securityStamp: String?): Boolean {
        if (this.securityStamp != securityStamp) return true

        val currentStamp = LocalDateTime.now().toMillis().milliseconds.inWholeSeconds
        return currentStamp >= expStamp.orZero()
    }
}

