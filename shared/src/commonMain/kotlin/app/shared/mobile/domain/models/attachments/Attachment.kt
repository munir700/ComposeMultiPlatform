package app.shared.mobile.domain.models.attachments


import kmp.core.mobile.imagePicker.models.DocType
import kmp.core.mobile.utils.CommonParcelable
import kmp.core.mobile.utils.CommonParcelize
import kmp.core.mobile.utils.CommonSerializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@CommonParcelize
@Serializable
data class Attachment(
    @SerialName("ButtonText")
    val buttonText: String? = null,
    @SerialName("MaxPage")
    val maxPage: Int? = null,
    @SerialName("MinPage")
    val minPage: Int? = null,
    @SerialName("ScanType")
    val scanType: String? = null,
    @SerialName("ScanDescription")
    val scanDescription: String? = null,
    @SerialName("Mandatory")
    val mandatory: Int? = null,
    @SerialName("previewdoc")
    val previewDoc: String? = null,
    @SerialName("DocType")
    val docType: String? = null,
) : CommonParcelable, CommonSerializable {
    fun isPdfAllow(): Boolean {
        return docType?.convertToDocTypes()?.contains(DocType.PDF) == true
    }
}

fun String.convertToDocTypes(): List<DocType> {
    return this.split(",")
        .mapNotNull { DocType.fromType(it.trim()) }
}
