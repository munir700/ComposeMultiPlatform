package app.shared.mobile.domain.models.auth

enum class AuthError(val code: String, val message: String) {
    MISSING_CREDENTIALS("1", "Missing username or password"),
    INVALID_CREDENTIALS("2", "Invalid username or password"),
    INVALID_ANSWER("3", "Invalid security answer"),
    USER_LOCKED("4", "User account is locked"),
    INVALID_GRANT("5", "Invalid grant"),
    UNAUTHORIZED("8", "Unauthorized access"),
    NETWORK_ERROR("NETWORK", "Network connection error"),
    UNKNOWN_ERROR("UNKNOWN", "Unknown error occurred");

    companion object {
        fun fromCode(code: String?): AuthError {
            return values().find { it.code == code } ?: UNKNOWN_ERROR
        }
    }
}