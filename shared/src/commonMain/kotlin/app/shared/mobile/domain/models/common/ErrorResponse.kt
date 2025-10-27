package app.shared.mobile.domain.models.common


import kmp.core.mobile.network.errors.NetworkResponseError
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("error")
    val error: String? = null,
    @SerialName("error_description")
    val errorDescription: String? = null
)

fun NetworkResponseError.isUaePassHasNoLabourCard(): Boolean {
    val errorResponse = this.body<ErrorResponse>()
    return errorResponse.errorDescription == "TODO"
}