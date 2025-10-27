@file:OptIn(DelicateCoroutinesApi::class)

package kmp.core.mobile.globalState


import androidx.compose.runtime.mutableStateOf
import kmp.core.mobile.globalState.models.ChoicesPopupParams
import kmp.core.mobile.globalState.models.ConfirmationPopupParams
import kmp.core.mobile.globalState.models.DatePopupParams
import kmp.core.mobile.globalState.models.DateRangePopupParams
import kmp.core.mobile.globalState.models.LoadingType
import kmp.core.mobile.globalState.models.MessagePopupParams
import kmp.core.mobile.globalState.models.SnackBarParams
import kmp.core.mobile.globalState.models.SuccessPopupParams
import kmp.core.mobile.globalState.models.TimePopupParams
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class CoreGlobalState : ICoreGlobalState {

    override val appLoadedState = mutableStateOf(false)
    override val navigateToLogin = mutableStateOf(false)
    override val loadingState = mutableStateOf<LoadingType>(LoadingType.NoLoading)
    override val messagePopupState = mutableStateOf<MessagePopupParams?>(null)
    override val successPopupState = mutableStateOf<SuccessPopupParams?>(null)
    override val confirmationPopupState = mutableStateOf<ConfirmationPopupParams?>(null)
    override val choicesPopupState = mutableStateOf<ChoicesPopupParams?>(null)
    override val snackBarState = mutableStateOf(SnackBarParams.hidden())
    override val dismissKeyboardState = mutableStateOf(false)
    override val isStatusBarDarkState = mutableStateOf(false)
    override val isNavigationBarDarkState = mutableStateOf(false)
    override val dateRangePopupState = mutableStateOf<DateRangePopupParams?>(null)
    override val datePopupState = mutableStateOf<DatePopupParams?>(null)
    override val timePopupState = mutableStateOf<TimePopupParams?>(null)

    private var hideSnackBarJob: Job? = null

    override fun idle() {
        navigateToLogin.value = false
        loadingState.value = LoadingType.NoLoading
        messagePopupState.value = null
        successPopupState.value = null
        confirmationPopupState.value = null
        choicesPopupState.value = null
        dateRangePopupState.value = null
        datePopupState.value = null
        timePopupState.value = null
    }

    override fun navigateToLogin() {
        navigateToLogin.value = true
    }

    override fun resetNavigateToLogin() {
        navigateToLogin.value = false
    }

    override fun setAppLoaded() {
        appLoadedState.value = true
    }

    override fun loading(type: LoadingType) {
        loadingState.value = type
    }

    override fun messagePopup(params: MessagePopupParams) {
        messagePopupState.value = params
    }

    override fun successPopup(params: SuccessPopupParams) {
        successPopupState.value = params
    }

    override fun confirmationPopup(params: ConfirmationPopupParams) {
        confirmationPopupState.value = params
    }

    override fun choicesPopup(params: ChoicesPopupParams) {
        choicesPopupState.value = params
    }

    override fun snackBar(params: SnackBarParams) {
        // Show the snack bar
        snackBarState.value = params

        // Hide snack bar after duration
        hideSnackBar(SNACK_BAR_DURATION)
    }

    override fun hideSnackBar(delay: Long) {
        // Schedule a job to dismiss it after delay time
        hideSnackBarJob?.cancel()
        hideSnackBarJob = GlobalScope.launch {
            delay(delay)
            snackBarState.value = snackBarState.value.copy(isVisible = false)
        }
    }

    override fun dismissKeyboard() {
        dismissKeyboardState.value = true
    }

    override fun resetDismissKeyboardState() {
        dismissKeyboardState.value = false
    }

    override fun setStatusBarDark() {
        isStatusBarDarkState.value = true
    }

    override fun setStatusBarLight() {
        isStatusBarDarkState.value = false
    }

    override fun setNavigationBarDark() {
        isNavigationBarDarkState.value = true
    }

    override fun setNavigationBarLight() {
        isNavigationBarDarkState.value = false
    }

    override fun dateRangePopup(params: DateRangePopupParams) {
        dateRangePopupState.value = params
    }

    override fun datePopup(params: DatePopupParams) {
        datePopupState.value = params
    }

    override fun timePopup(params: TimePopupParams) {
        timePopupState.value = params
    }

    companion object {
        private const val SNACK_BAR_DURATION = 5000L
    }
}