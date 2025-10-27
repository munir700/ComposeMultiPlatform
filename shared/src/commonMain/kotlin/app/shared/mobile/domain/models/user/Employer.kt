package app.shared.mobile.domain.models.user


import kmp.core.mobile.utils.CommonParcelable
import kmp.core.mobile.utils.CommonParcelize
import kmp.core.mobile.utils.CommonSerializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@CommonParcelize
data class Employer(

    @SerialName("username")
    override val username: String? = null,

    @SerialName("UnifiedNumber")
    override val unifiedNumber: String? = null,

    @SerialName("NameEn")
    val nameEn: String? = null,

    @SerialName("NameAr")
    val nameAr: String? = null,

    @SerialName("PersonCode")
    override val personCode: String? = null,

    override val labourCardNumber: String? = "",

    @SerialName("EIDA")
    override val eida: String? = null,

    @SerialName("Name")
    override val name: String? = null,

    @SerialName("TasheelUserId")
    override val tasheelUserId: String? = null,

    @SerialName("Photo")
    override val photo: Photo? = null,

    @SerialName("PassportNo")
    var passportNo: String? = null,

    @SerialName("Profession")
    val profession: String? = null,

    @SerialName("PassportExpiryDate")
    val passportExpiryDate: Long? = null,

    @SerialName("PassportIssueDate")
    val passportIssueDate: Long? = null,

    @SerialName("EmployeeCardExpire")
    val employeeCardExpire: Long? = null,

    @SerialName("Birthdate")
    val birthdate: Long? = null,

    @SerialName("Nationality")
    override val nationality: String? = null,

    @SerialName("EmployeesCount")
    val employeesCount: Int? = null,

    @SerialName("EstablishmentsCount")
    val establishmentsCount: Int? = null,

    @SerialName("DomesticWorkersCount")
    val domesticWorkersCount: Int? = null,

    @SerialName("ProCount")
    val proCount: Int? = null,

    @SerialName("Contact")
    val contact: Contact? = null,

    ) : IUser, CommonParcelable, CommonSerializable {
    override val mobileNumber: String?
        get() = contact?.mobile // Custom getter implementation

    val mail: String?
        get() = contact?.email // Custom getter implementation
}