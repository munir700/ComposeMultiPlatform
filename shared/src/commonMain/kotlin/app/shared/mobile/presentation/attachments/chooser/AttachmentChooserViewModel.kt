package app.shared.mobile.presentation.attachments.chooser

import app.shared.mobile.domain.models.uploadImage.UploadPhoto
import app.shared.mobile.domain.useCases.DeleteImageUseCase
import app.shared.mobile.domain.useCases.UploadImageUseCase
import app.shared.mobile.presentation.base.AppBaseViewModel
import kmp.core.mobile.imagePicker.IImageManager
import kmp.core.mobile.permissions.IPermissionManager

import app.shared.mobile.presentation.attachments.chooser.AttachmentChooserContract.Event
import app.shared.mobile.presentation.attachments.chooser.AttachmentChooserContract.State
import app.shared.mobile.presentation.attachments.chooser.AttachmentChooserContract.Effect
import app.shared.mobile.presentation.attachments.chooser.models.AttachmentChooserUIModel
import app.shared.mobile.presentation.attachments.models.AttachmentPicture
import app.shared.mobile.presentation.attachments.models.AttachmentResult
import app.shared.mobile.presentation.attachments.models.CategoryPhoto
import app.shared.mobile.presentation.login.LoginScreen
import app.shared.mobile.presentation.main.MainScreen
import app.shared.mobile.resources.Res
import app.shared.mobile.resources.capture_from_camera
import app.shared.mobile.resources.choose_image
import app.shared.mobile.resources.max_images_reached
import app.shared.mobile.resources.min_max_satisfied
import app.shared.mobile.resources.pick_from_gallery
import kmp.core.mobile.globalState.models.ChoicesPopupParams
import kmp.core.mobile.permissions.enums.Permission
import kmp.core.mobile.utils.extensions.orFalse
import kmp.core.mobile.utils.extensions.orZero


const val GALLERY_INDEX = 0
const val CAMERA_INDEX = 1

class AttachmentChooserViewModel(
    private val imageManager: IImageManager,
    private val permissionManager: IPermissionManager,
    private val uploadImageUseCase: UploadImageUseCase,
    private val deleteImageUseCase: DeleteImageUseCase,
) : AppBaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        is Event.Init -> init(
            pictureAttachment = event.pictureAttachment,
            categoryPhoto = event.categoryPhoto
        )

        Event.BackClicked -> handleBackClick()

        Event.SubmitAttachmentsClicked -> handleUploadAttachmentsClick()

        Event.AddAttachmentClicked -> handleAddAttachmentClick()

        is Event.RemoveAttachmentClicked -> handleRemoveAttachmentClick(index = event.index)

        is Event.ImagePicked -> handleImagePick(
            bytes = event.bytes,
            name = event.fileName
        )

        is Event.PdfPicked -> handlePdfPick(
            filePath = event.filePath,
            name = event.fileName
        )
    }

    private fun init(
        pictureAttachment: AttachmentChooserUIModel?,
        categoryPhoto: CategoryPhoto?
    ) {
        // Validate if already initialized
        if (currentState.isInitialized) return

        setState {
            copy(
                attachment = pictureAttachment,
                categoryPhoto = categoryPhoto
            )
        }

        // set state initialized
        setState { copy(isInitialized = true) }
    }

    private fun updateAttachedPicsState(attachedPictures: List<AttachmentPicture>?) {
        val currentPictureAttachment =
            currentState.attachment?.copy(attachedPics = attachedPictures)
        setState {
            copy(
                attachment = currentPictureAttachment,
            )
        }
    }

    private fun handleBackClick() {
        navManager.goBack()
    }

    private fun handleUploadAttachmentsClick() {
        val validation = validateAttachments()

        // Check if pic attachments is empty and not required then go back without result
        if (validation && currentState.attachment?.attachedPics?.isEmpty().orFalse()) {
            navManager.goBack()
            return
        }

        // Go back with attachments
        if (validation) {
            val attachmentResult = AttachmentResult(
                attachmentPics = currentState.attachment?.attachedPics ?: emptyList()
            )

            navManager.goBackWithResult(
                scope = viewModelScope,
                source = MainScreen::class.simpleName,
                result = attachmentResult
            )
        } else showError(resourcesManager.getString(Res.string.min_max_satisfied))
    }

    private fun validateAttachments(): Boolean {
        with(currentState.attachment) {
            if (this?.isMandatory == false)
                return true
            if (this?.minPage.orZero() > this?.attachedPics?.size.orZero())
                return false
        }
        return true
    }

    private fun handleAddAttachmentClick() {

        // Check required max page attached
        with(currentState.attachment) {
            val requiredMaxPage = this?.maxPage.orZero()
            val attachedPage = this?.attachedPics?.size.orZero()
            if (attachedPage >= requiredMaxPage) {
                showError(Res.string.max_images_reached)
                return
            }
        }

        if (currentState.attachment?.isPdfAllow() == true) {
            pickPdf()
            return
        }

        globalState.choicesPopup(
            ChoicesPopupParams(
                title = resourcesManager.getString(Res.string.choose_image),
                choices = listOf(
                    resourcesManager.getString(Res.string.pick_from_gallery),
                    resourcesManager.getString(Res.string.capture_from_camera)
                ),
                onChoiceSelected = { _, index ->
                    when (index) {
                        GALLERY_INDEX -> pickImageFromGallery()
                        CAMERA_INDEX -> captureImageUsingCamera()
                    }
                })
        )
    }

    private fun handleRemoveAttachmentClick(index: Int) {

        val uploadFileId =
            currentState
                .attachment
                ?.attachedPics
                ?.getOrNull(index)
                ?.uploadFileId

        if (uploadFileId != null) {
            deleteEvaluationCategoryPhoto(
                index = index,
                uploadFileId = uploadFileId
            )
        } else {
            removeAttachedCompanyCategory(index)
        }
    }

    private fun deleteEvaluationCategoryPhoto(index: Int, uploadFileId: Int) = executeSafe({

        // Delete company attachment category
        val result = deleteImageUseCase.invoke(
            uploadFileId = uploadFileId
        )

        // Remove from the attachment list
        if (result) {
            removeAttachedCompanyCategory(index)
        }
    })


    // Remove from the attachment list
    private fun removeAttachedCompanyCategory(index: Int) {
        currentState.attachment?.attachedPics?.let { pics ->
            // Removed selected attachment by index
            val updatedPics = pics.toMutableList().apply { removeAt(index) }

            // update state
            updateAttachedPicsState(updatedPics)
        }
    }

    private fun pickImageFromGallery() {
        setEffect { Effect.PickImageFromGallery }
    }

    private fun captureImageUsingCamera() {
        setEffect { Effect.CaptureImageUsingCamera }
    }

    private fun pickPdf() {
        setEffect { Effect.PickPdf }
    }

    private fun handleImagePick(bytes: ByteArray, name: String?) {
        val attachedPic = AttachmentPicture(
            name = name,
            fileBytes = bytes
        )

        // Handle attached image
        handleAttachedPics(attachedPic)
    }

    private fun handlePdfPick(filePath: String?, name: String?) {
        val attachedPic = AttachmentPicture(
            isPdf = true,
            name = name,
            filePath = filePath
        )

        // Handle attached pdf
        handleAttachedPics(attachedPic)
    }

    private fun handleAttachedPics(attachedPic: AttachmentPicture) {

        if (currentState.categoryPhoto != null) {
            uploadCompanyCategoryAttachment(attachedPic = attachedPic)
        } else {
            //Set state for attached pictures
            addAttachedPicToList(attachedPic)
        }
    }

    private fun addAttachedPicToList(attachedPic: AttachmentPicture) {
        val attachedPics: MutableList<AttachmentPicture> =
            currentState.attachment?.attachedPics?.toMutableList() ?: mutableListOf()

        // Add current selected or capture pic
        attachedPics.add(attachedPic)

        // Update pics state
        updateAttachedPicsState(attachedPics)
    }

    private fun uploadCompanyCategoryAttachment(attachedPic: AttachmentPicture) = executeSafe({
        val photo = buildEvaluationPhoto(attachedPic)
        val taskId = currentState.categoryPhoto?.taskId.orZero()

        // Check location permission
        permissionManager.requestPermission(Permission.LOCATION)
        // Upload the attached image to server
        val uploadedCategoryPic = uploadImageUseCase.invoke(photo, taskId)

        //Set state for attached pictures
        addAttachedPicToList(
            attachedPic = attachedPic
                .copy(
                    uploadFileId = uploadedCategoryPic.uploadFileId
                )
        )
    })

    private fun buildEvaluationPhoto(attachedPic: AttachmentPicture): UploadPhoto {
        // Validation
        val imageBase64 = attachedPic.fileBytes?.let { imageManager.toBase64(it) }
            ?: throw IllegalArgumentException("Failed to create imageBase64")

        // Create Evaluation photo object
        return UploadPhoto(
            fileTypeId = currentState.categoryPhoto?.evaluationType.orZero(),
            imageBase64 = imageBase64
        )
    }
}