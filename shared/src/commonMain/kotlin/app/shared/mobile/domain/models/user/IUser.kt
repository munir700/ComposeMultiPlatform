package app.shared.mobile.domain.models.user


interface IUser {
    val username: String?
    val mobileNumber: String?
    val personCode: String?
    val labourCardNumber: String?
    val eida: String?
    val unifiedNumber: String?
    val nationality: String?
    val name: String?
    val tasheelUserId: String?
    val photo: Photo?
}