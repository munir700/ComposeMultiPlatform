package app.shared.mobile.presentation.login


import app.shared.mobile.presentation.base.AppBaseViewModel
import app.shared.mobile.presentation.login.LoginContract.Effect
import app.shared.mobile.presentation.login.LoginContract.Event
import app.shared.mobile.presentation.login.LoginContract.State
import app.shared.mobile.presentation.main.MainScreen
import app.shared.mobile.resources.Res
import app.shared.mobile.resources.please_enter_the_password
import app.shared.mobile.resources.please_enter_the_user_name

class LoginViewModel() : AppBaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
        Event.BackClicked -> handleBackClick()
        Event.Login -> handleLoginClick()
        is Event.UserNameChanged -> onUserNameChange(event.userName)
        is Event.PasswordChanged -> onPasswordChange(event.password)
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init
        setupCurrentLanguage()


    }

    private fun setupCurrentLanguage() {
        languageManager.getCurrentLanguage().let {
            languageManager.changeLanguage(it)
        }
    }


    private suspend fun refreshCurrentUserIfPossible() = try {
        // TODO: To be implemented
    } catch (_: Throwable) {
    }

    private fun navigateToNextDestination() {
        // Add some delay for splash


        // Check if user is logged in to decide the next screen
        val isAuthenticated = false

        // Navigate to appropriate screen
        if (isAuthenticated) {
            //navManager.clearAndNavigate(MainScreen())
        } else {
            //navManager.clearAndNavigate(LoginScreen())
        }
    }


    private fun handleBackClick() {
        navManager.goBack()
    }

    private fun handleLoginClick() = executeSafe({
        val userName = currentState.userName
        val password = currentState.password

        if (validateInputs()) {
            // Handle missing input (optional: show error message or log)
            return@executeSafe
        }

        handleLoginWithSecretAnswer(questionId = "")
    })

    private fun validateInputs(): Boolean {
        var hasError = false

        // Validate userName
        if (currentState.userName.isNullOrEmpty()) {
            setState {
                copy(userNameError = resourcesManager.getString(Res.string.please_enter_the_user_name))
            }
            hasError = true
        } else {
            setState { copy(userNameError = null) }
        }

        // Validate password
        if (currentState.password.isNullOrEmpty()) {
            setState {
                copy(passwordError = resourcesManager.getString(Res.string.please_enter_the_password))
            }
            hasError = true
        } else {
            setState { copy(passwordError = null) }
        }

        // Stop execution if any error exists
        return hasError
    }

    private suspend fun handleLoginWithSecretAnswer(questionId: String) {
        val userName = currentState.userName
        val password = currentState.password



        // After get auth token fetch user basic profile and land to previous screen
        getUserBasicProfileNavBack()
    }

    private suspend fun getUserBasicProfileNavBack() {
        // required user profile from API

        //navManager.goBack()
        navManager.navigate(MainScreen())
    }

    private fun onUserNameChange(userName: String) {
        setState { copy(userName = userName) }
    }

    private fun onPasswordChange(password: String) {
        setState { copy(password = password) }
    }
}
