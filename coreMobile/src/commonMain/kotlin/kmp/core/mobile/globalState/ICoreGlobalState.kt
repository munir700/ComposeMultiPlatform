package kmp.core.mobile.globalState


import androidx.compose.runtime.State
import kmp.core.mobile.globalState.models.ChoicesPopupParams
import kmp.core.mobile.globalState.models.ConfirmationPopupParams
import kmp.core.mobile.globalState.models.DatePopupParams
import kmp.core.mobile.globalState.models.DateRangePopupParams
import kmp.core.mobile.globalState.models.LoadingType
import kmp.core.mobile.globalState.models.MessagePopupParams
import kmp.core.mobile.globalState.models.SnackBarParams
import kmp.core.mobile.globalState.models.SuccessPopupParams
import kmp.core.mobile.globalState.models.TimePopupParams

interface ICoreGlobalState {
    val appLoadedState: State<Boolean>
    val navigateToLogin: State<Boolean>
    val loadingState: State<LoadingType>
    val messagePopupState: State<MessagePopupParams?>
    val successPopupState: State<SuccessPopupParams?>
    val confirmationPopupState: State<ConfirmationPopupParams?>
    val choicesPopupState: State<ChoicesPopupParams?>
    val snackBarState: State<SnackBarParams>
    val dismissKeyboardState: State<Boolean>
    val isStatusBarDarkState: State<Boolean>
    val isNavigationBarDarkState: State<Boolean>
    val dateRangePopupState: State<DateRangePopupParams?>
    val datePopupState: State<DatePopupParams?>
    val timePopupState: State<TimePopupParams?>

    fun idle()
    fun navigateToLogin()
    fun resetNavigateToLogin()
    fun setAppLoaded()
    fun loading(type: LoadingType)
    fun messagePopup(params: MessagePopupParams)
    fun successPopup(params: SuccessPopupParams)
    fun confirmationPopup(params: ConfirmationPopupParams)
    fun choicesPopup(params: ChoicesPopupParams)
    fun snackBar(params: SnackBarParams)
    fun hideSnackBar(delay: Long = 0)
    fun dismissKeyboard()
    fun resetDismissKeyboardState()
    fun setStatusBarDark()
    fun setStatusBarLight()
    fun setNavigationBarDark()
    fun setNavigationBarLight()
    fun dateRangePopup(params: DateRangePopupParams)
    fun datePopup(params: DatePopupParams)
    fun timePopup(params: TimePopupParams)
}
