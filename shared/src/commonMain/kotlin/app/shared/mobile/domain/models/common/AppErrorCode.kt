package app.shared.mobile.domain.models.common


import app.shared.mobile.resources.Res
import app.shared.mobile.resources.something_went_wrong_during_login
import org.jetbrains.compose.resources.StringResource

enum class AppErrorCode(
    val code: String,
    val message: StringResource? = null
) {
    INVALID_ID_TOKEN(
        code = "16",
        message = Res.string.something_went_wrong_during_login
    ),
    INVALID_ACCESS_TOKEN(
        code = "17",
        message = Res.string.something_went_wrong_during_login
    );

    companion object {
        fun get(code: String?) = entries.find { it.code == code }
    }
}