package app.shared.mobile.domain.useCases

import app.shared.mobile.domain.models.uploadImage.UploadPhoto
import app.shared.mobile.domain.models.uploadImage.UploadImageBody
import app.shared.mobile.domain.repositories.ImageUploadRepository
import app.shared.mobile.domain.repositories.UserRepository
import app.shared.mobile.location.ILocationManager
import app.shared.mobile.resources.Res
import app.shared.mobile.resources.error_uploading_the_photo
import app.shared.mobile.resources.pick_location_from_map
import app.shared.mobile.resources.user_not_logged_in
import kmp.core.mobile.resources.IResourcesManager


class UploadImageUseCase(
    private val resourcesManager: IResourcesManager,
    private val locationManager: ILocationManager,
    private val userRepository: UserRepository,
    private val uploadRepository: ImageUploadRepository

) {
    @Throws(IllegalStateException::class)
    suspend operator fun invoke(photo: UploadPhoto, taskId: Int): UploadPhoto {
        // Get & validate logged user
        val loggedUser = userRepository.getCachedUser() ?: throw IllegalArgumentException(
            resourcesManager.getString(Res.string.user_not_logged_in)
        )

        // Get last known location
        val lastKnownLocation = locationManager.getCurrentLocation()

        // Get establishment info and validate it
        val establishmentInfo = uploadRepository.getAddressInputs()

        with(establishmentInfo) {
            if (this?.latitude == null || this.longitude == null
            ) {
                throw IllegalArgumentException(
                    resourcesManager.getString(Res.string.pick_location_from_map)
                )
            }
        }

        // Create the body
        val body = UploadImageBody(
            imageType = photo.fileTypeId,
            base64File = photo.imageBase64,
            taskId = taskId,
            uploadedBy = loggedUser.personCode.orEmpty(),
            latitude = lastKnownLocation?.latitude ?: 0.0,
            longitude = lastKnownLocation?.longitude ?: 0.0
        )

        // Upload & validate
        val uploadedFileId = uploadRepository.uploadCategoryPhoto(body).uploadedFileId
            ?: // Notify error
            throw IllegalArgumentException(
                resourcesManager.getString(Res.string.error_uploading_the_photo)
            )

        return photo.copy(uploadFileId = uploadedFileId)
    }
}