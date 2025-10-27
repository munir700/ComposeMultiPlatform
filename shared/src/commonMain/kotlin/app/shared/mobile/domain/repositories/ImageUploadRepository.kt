package app.shared.mobile.domain.repositories

import app.shared.mobile.domain.data.UploadImageApi
import app.shared.mobile.domain.models.uploadImage.UploadImageResponse
import app.shared.mobile.domain.models.uploadImage.UploadImageBody
import app.shared.mobile.domain.models.uploadImage.UploadResponse
import app.shared.mobile.domain.models.uploadImage.requests.AddressInputs
import kmp.core.mobile.utils.extensions.objectToMap

class ImageUploadRepository(
    private val selfEvaluationApi: UploadImageApi
) : BaseRepository() {

    private var addressInputs: AddressInputs? = null
    fun getAddressInputs() = addressInputs

    fun updateAddressInputs(addressInputs: AddressInputs) {
        this.addressInputs = addressInputs
    }

    // Clear local cache
    fun clearCache() {
        addressInputs = null
    }

    suspend fun uploadCategoryPhoto(imageRequest: UploadImageBody): UploadImageResponse {
        return selfEvaluationApi.uploadPhoto(imageRequest.objectToMap())
    }

    suspend fun deletePhoto(
        uploadFileId: Int,
        deletedBy: String
    ): UploadResponse<Unit> {
        return selfEvaluationApi.deletePhoto(uploadFileId, deletedBy)
    }
}


