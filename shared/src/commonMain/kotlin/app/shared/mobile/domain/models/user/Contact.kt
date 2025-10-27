package app.shared.mobile.domain.models.user


import kmp.core.mobile.utils.CommonParcelable
import kmp.core.mobile.utils.CommonParcelize
import kmp.core.mobile.utils.CommonSerializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@CommonParcelize
data class Contact(
    @SerialName("Address")
    val address: String? = null,

    @SerialName("Telephone")
    val telephone: String? = null,

    @SerialName("Mobile")
    val mobile: String? = null,

    @SerialName("Fax")
    val fax: String? = null,

    @SerialName("Email")
    val email: String? = null,

    @SerialName("Website")
    val website: String? = null,

    @SerialName("Area")
    val area: String? = null,

    @SerialName("Emirate")
    val emirate: Int? = null,

    @SerialName("Instagram")
    val instagram: String? = null,

    @SerialName("Twitter")
    val twitter: String? = null,

    @SerialName("Facebook")
    val facebook: String? = null
) : CommonParcelable, CommonSerializable