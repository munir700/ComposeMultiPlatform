package app.shared.mobile.domain.models.user

import kmp.core.mobile.utils.CommonParcelable
import kmp.core.mobile.utils.CommonParcelize
import kmp.core.mobile.utils.CommonSerializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@CommonParcelize
@Serializable
data class Photo(
    @SerialName("Content")
    val imageUrl: String? = null,

    @SerialName("Type")
    val type: Int? = null,

    @SerialName("Name")
    val name: String? = null,
) : CommonParcelable, CommonSerializable

