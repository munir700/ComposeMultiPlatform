package app.shared.mobile.presentation.splash


import app.shared.mobile.domain.repositories.AuthRepository
import app.shared.mobile.initializer.IAppInitializer
import app.shared.mobile.presentation.base.AppBaseViewModel
import app.shared.mobile.presentation.login.LoginScreen
import app.shared.mobile.presentation.main.MainScreen
import app.shared.mobile.presentation.splash.SplashContract.Effect
import app.shared.mobile.presentation.splash.SplashContract.Event
import app.shared.mobile.presentation.splash.SplashContract.State

class SplashViewModel(val authRepository: AuthRepository, val appInitializer: IAppInitializer) :
    AppBaseViewModel<State, Event, Effect>() {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event): Any = when (event) {
        Event.Init -> init()
    }

    private fun init() {
        // Validate if already initialized
        if (currentState.isInitialized) return

        // Init
        setupCurrentLanguage()
        executeSafe({
            appInitializer.initGoogleMaps()
        })


        navigateToNextDestination()
        // Only after everything is complete, set app as loaded and navigate
        globalState.setAppLoaded()
        setState { copy(isInitialized = true) }
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

    private fun navigateToNextDestination() = executeSafe({
        // Add some delay for splash


        // Check if user is logged in to decide the next screen
        val isAuthenticated = authRepository.isAuthenticated()

        // Navigate to appropriate screen
        if (isAuthenticated) {
            navManager.clearAndNavigate(MainScreen())
        } else {
            navManager.clearAndNavigate(LoginScreen())
        }
    })

    companion object {
        // TODO: Update after finish testing
        private const val SPLASH_DELAY = 200L
    }
}
