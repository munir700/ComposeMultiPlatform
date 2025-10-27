package app.shared.mobile.domain.models.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    // Basic Information  
    val id: String = "",
    val username: String = "",
    @SerialName("EmployeeId")
    val employeeId: String? = null,
    @SerialName("EmployeeNo") 
    val employeeNo: String? = null,
    @SerialName("FirstName")
    val firstName: String? = null,
    @SerialName("FirstNameEn")
    val firstNameEn: String? = null,
    @SerialName("FirstNameAr")
    val firstNameAr: String? = null,
    @SerialName("LastName")
    val lastName: String? = null,
    @SerialName("Name")
    val fullName: String? = null,
    @SerialName("NameEn")
    val fullNameEn: String? = null,
    @SerialName("NameAr") 
    val fullNameAr: String? = null,
    @SerialName("FullName")
    val fullNameDisplay: String? = null,
    @SerialName("Department")
    val department: String? = null,
    @SerialName("Grade")
    val grade: String? = null,
    @SerialName("Gender")
    val gender: String? = null,
    @SerialName("Office")
    val office: String? = null,
    
    // Contact Information
    @SerialName("Email")
    val email: String? = null,
    @SerialName("Phone")
    val phoneNumber: String? = null,
    @SerialName("PhoneExtension")
    val phoneExtension: String? = null,
    @SerialName("MobileNo")
    val mobileNo: String? = null,
    
    // Profile Image
    @SerialName("ImageURL")
    val imageURL: String? = null,
    @SerialName("Photo")
    val photo: Photo? = null,
    
    // System Information
    val roles: List<UserRole> = emptyList(),
    val isActive: Boolean = true
)

@Serializable
data class Photo(
    @SerialName("Type")
    val type: Int? = null,
    @SerialName("ContentType") 
    val contentType: Int? = null,
    @SerialName("Content")
    val content: String? = null,
    @SerialName("Bytes")
    val bytes: String? = null
)

@Serializable
enum class UserRole {
    ACTIVE_DIRECTORY_MANAGER,
    ACTIVE_DIRECTORY_USER,
    LANDESK_ANALYST_MOL_MANAGER,
    LANDESK_ANALYST_LEVEL_1,
    LANDESK_ANALYST_LEVEL_2,
    SELF_SERVICE_USER,
    UNKNOWN
}

@Serializable
data class AuthSession(
    val user: User,
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val tokenType: String = "Bearer"
)