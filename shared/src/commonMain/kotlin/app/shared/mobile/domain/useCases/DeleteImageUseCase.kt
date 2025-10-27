package app.shared.mobile.domain.useCases

import app.shared.mobile.domain.repositories.ImageUploadRepository
import app.shared.mobile.domain.repositories.UserRepository
import app.shared.mobile.resources.Res
import app.shared.mobile.resources.user_not_logged_in
import kmp.core.mobile.resources.IResourcesManager


class DeleteImageUseCase(
    private val resourcesManager: IResourcesManager,
    private val userRepository: UserRepository,
    private val imageRepository: ImageUploadRepository,
) {
    @Throws(IllegalStateException::class)
    suspend operator fun invoke(uploadFileId: Int): Boolean {

        // Get person code of login  user
        val personCode = userRepository
            .getCachedUser()
            ?.personCode
            ?: throw IllegalArgumentException(resourcesManager.getString(Res.string.user_not_logged_in))

        // Delete evaluation company attachment category image
        val response = imageRepository.deletePhoto(
            uploadFileId = uploadFileId,
            deletedBy = personCode
        )

        // Return success if image has been deleted
        return response.code == 200
    }
}