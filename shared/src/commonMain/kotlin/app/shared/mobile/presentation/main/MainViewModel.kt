package app.shared.mobile.presentation.main


import app.shared.mobile.location.Location
import app.shared.mobile.presentation.attachments.chooser.AttachmentChooserScreen
import app.shared.mobile.presentation.attachments.chooser.models.AttachmentChooserUIModel
import app.shared.mobile.presentation.attachments.models.AttachmentResult
import app.shared.mobile.presentation.base.AppBaseViewModel
import app.shared.mobile.presentation.locationScreen.LocationScreen
import app.shared.mobile.presentation.locationScreen.models.LocationParams
import app.shared.mobile.presentation.main.MainContract.Effect
import app.shared.mobile.presentation.main.MainContract.Event
import app.shared.mobile.presentation.main.MainContract.State
import app.shared.mobile.presentation.main.models.ChooseImage
import app.shared.mobile.resources.Res
import app.shared.mobile.resources.app_is_outdated
import app.shared.mobile.resources.capture_from_camera
import app.shared.mobile.resources.choose_image
import app.shared.mobile.resources.from_date
import app.shared.mobile.resources.message
import app.shared.mobile.resources.message_detail
import app.shared.mobile.resources.pick_from_gallery
import app.shared.mobile.resources.please_update_app_to_continue
import app.shared.mobile.resources.select_date_range
import app.shared.mobile.resources.select_time
import app.shared.mobile.resources.success
import app.shared.mobile.resources.success_details
import app.shared.mobile.resources.update
import kmp.core.mobile.date.now
import kmp.core.mobile.date.toMillis
import kmp.core.mobile.globalState.models.ChoicesPopupParams
import kmp.core.mobile.globalState.models.ConfirmationPopupParams
import kmp.core.mobile.globalState.models.DatePopupParams
import kmp.core.mobile.globalState.models.DateRangePopupParams
import kmp.core.mobile.globalState.models.MessagePopupParams
import kmp.core.mobile.globalState.models.SnackBarParams
import kmp.core.mobile.globalState.models.SnackBarType
import kmp.core.mobile.globalState.models.SuccessPopupParams
import kmp.core.mobile.globalState.models.TimePopupParams
import kmp.core.mobile.imagePicker.models.DocType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class MainViewModel() : AppBaseViewModel<State, Event, Effect>() {

    init {
        observePictureAttachmentResult()
    }

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        Event.ConfirmPopup -> handleAppRequireUpdate()
        Event.DatePicker -> handleDatePicker()
        Event.DateRangePicker -> handleDateRangePicker()
        Event.MessagePopup -> handleMessagePopup()
        Event.ChoicePopup -> handleAddAttachmentClick()
        Event.SuccessPopup -> handleSuccessPopup()
        Event.TimePopup -> handleTimePopup()
        is Event.ImagePicked -> handleImagePicker()
        is Event.PdfPicked -> handlePdfPicker()
        Event.OpenMap -> openLocation()
    }

    private fun observePictureAttachmentResult() {
        navManager.collectNavResult<AttachmentChooserScreen, AttachmentResult> { result ->

            // Create updated attachment info

            logger.log("data captured ${result.attachmentPics} ")

        }
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        setState { copy(isInitialized = true) }
    }

    private fun handleDatePicker() {
        val today = LocalDate.now()
        globalState.datePopup(
            DatePopupParams(
                date = today,
                title = resourcesManager.getString(Res.string.from_date),
                onDateSelected = ::setFromDate
            )
        )
    }

    private fun setFromDate(selectedDate: LocalDate) {
        globalState.snackBar(
            SnackBarParams(
                type = SnackBarType.SUCCESS, message = selectedDate.toString()
            )
        )
    }

    private fun handleDateRangePicker() {
        val today = LocalDateTime.now().toMillis()
        val fromDate = today
        val toDate = today

        globalState.dateRangePopup(
            DateRangePopupParams(
                title = resourcesManager.getString(Res.string.select_date_range),
                fromDate = fromDate,
                toDate = toDate,
                onDateRangeSelected = onDateRangeSelected
            )
        )

    }

    private val onDateRangeSelected: ((Long?, Long?) -> Unit) =
        { fromDate, toDate ->


        }

    private fun handleMessagePopup() {
        globalState.messagePopup(
            params = MessagePopupParams(
                title = resourcesManager.getString(Res.string.message),
                body = resourcesManager.getString(Res.string.message_detail)
            )
        )
    }

    private fun handleSuccessPopup() {
        globalState.successPopup(
            SuccessPopupParams(
                title = resourcesManager.getString(Res.string.success),
                body = resourcesManager.getString(Res.string.success_details),
                onPositiveClick = {
                    navManager.goBack()
                }
            )
        )
    }

    private fun handleTimePopup() {
        globalState.timePopup(
            TimePopupParams(
                title = resourcesManager.getString(Res.string.select_time),
                body = resourcesManager.getString(Res.string.select_time),
                onTimeSelected = ::setTime
            )
        )
    }

    private fun setTime(selectedTime: LocalTime) {
        globalState.snackBar(
            SnackBarParams(
                type = SnackBarType.SUCCESS, message = selectedTime.toString()
            )
        )
    }

    private fun handleAppRequireUpdate() {
        globalState.confirmationPopup(
            ConfirmationPopupParams(
                isCancellable = false,
                title = resourcesManager.getString(Res.string.app_is_outdated),
                body = resourcesManager.getString(Res.string.please_update_app_to_continue),
                positiveButtonText = resourcesManager.getString(Res.string.update),
                onPositiveClick = {

                },
                onNegativeClick = {
                }
            ))
    }

    private fun handleAddAttachmentClick() {
        globalState.choicesPopup(
            ChoicesPopupParams(
                title = resourcesManager.getString(Res.string.choose_image),
                choices = listOf(
                    resourcesManager.getString(Res.string.pick_from_gallery),
                    resourcesManager.getString(Res.string.capture_from_camera)
                ),
                onChoiceSelected = { _, index ->
                    when (index) {
                        ChooseImage.GALLERY_INDEX.type -> pickImageFromGallery()
                        ChooseImage.CAMERA_INDEX.type -> captureImageUsingCamera()
                    }
                })
        )
    }

    private fun pickImageFromGallery() {
        setEffect { Effect.PickImageFromGallery }
    }

    private fun captureImageUsingCamera() {
        //setEffect { Effect.CaptureImageUsingCamera }
        val attachmentChooser: AttachmentChooserScreen = get<AttachmentChooserScreen> {
            parametersOf(
                AttachmentChooserUIModel(
                    isMandatory = false,
                    title = "test",
                    description = "working for screen chooser",
                    minPage = 2,
                    maxPage = 5,
                    docTypes = listOf(DocType.IMAGE),
                    attachedPics = null
                )
            )
        }
        navManager.navigate(attachmentChooser)
    }

    private fun handlePdfPicker() {

    }

    private fun handleImagePicker() {

    }

    private fun openLocation() {

        val locationScreen: LocationScreen = get<LocationScreen> {
            parametersOf(LocationParams("My Title", true, Location()))
        }
        navManager.navigate(locationScreen)
    }

}
