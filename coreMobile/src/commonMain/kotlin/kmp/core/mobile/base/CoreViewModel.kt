package kmp.core.mobile.base


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import app.core.mobile.resources.Res
import app.core.mobile.resources.error
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kmp.core.mobile.AppLogger
import kmp.core.mobile.globalState.ICoreGlobalState
import kmp.core.mobile.globalState.models.MessagePopupParams
import kmp.core.mobile.globalState.models.SnackBarParams
import kmp.core.mobile.globalState.models.SnackBarType
import kmp.core.mobile.language.ILanguageManager
import kmp.core.mobile.navigations.NavManager
import kmp.core.mobile.resources.IResourcesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

interface ViewEvent

interface ViewState

interface ViewSideEffect

const val SIDE_EFFECTS_KEY = "side_effects_key"

abstract class CoreViewModel<S : ViewState, E : ViewEvent, SF : ViewSideEffect>() :
    ViewModel(), KoinComponent {

    // Decompose lifecycle-aware scope (replacement for screenModelScope)
    protected val componentContext = inject<ComponentContext>().value
    protected val viewModelScope: CoroutineScope = componentContext.coroutineScope()


    protected val logger by inject<AppLogger>()
    protected val navManager by inject<NavManager>()
    protected val languageManager by inject<ILanguageManager>()
    protected val resourcesManager by inject<IResourcesManager>()
    protected val coreGlobalState by inject<ICoreGlobalState>()

    private val initialState by lazy { setInitialState() }
    private val _viewState = mutableStateOf(initialState)
    val viewState: State<S> = _viewState
    protected val currentState: S get() = _viewState.value

    private val _event: MutableSharedFlow<E> = MutableSharedFlow()
    private val _effect: Channel<SF> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    abstract fun setInitialState(): S
    abstract fun handleEvents(event: E): Any

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect { handleEvents(it) }
        }
    }

    fun setEvent(event: E) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reducer: S.() -> S) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    protected fun setEffect(builder: () -> SF) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    fun executeSafe(
        block: suspend () -> Unit,
        scope: CoroutineScope = viewModelScope,
        context: CoroutineContext = EmptyCoroutineContext,
        onError: ((Throwable) -> Unit)? = null,
        onComplete: (() -> Unit)? = null
    ): Job = scope.launch(context) {
        try {
            block()
        } catch (throwable: Throwable) {
            onError?.invoke(throwable) ?: logger.log("CoreViewModel", "Error: ${throwable.message}")
        } finally {
            onComplete?.invoke()
        }
    }

    protected fun showError(
        errorRes: StringResource,
        errorType: ErrorType = getDefaultErrorType()
    ) {
        showError(
            error = resourcesManager.getString(errorRes),
            errorType = errorType
        )
    }

    fun showError(
        error: String,
        errorType: ErrorType = getDefaultErrorType()
    ) {
        when (errorType) {
            ErrorType.Popup -> coreGlobalState.messagePopup(
                MessagePopupParams(
                    title = resourcesManager.getString(Res.string.error),
                    body = error
                )
            )

            ErrorType.SnackBar -> coreGlobalState.snackBar(
                SnackBarParams(
                    message = error,
                    type = SnackBarType.ERROR
                )
            )

            ErrorType.NoError -> {}
        }
    }

    protected open fun getDefaultErrorType(): ErrorType {
        return ErrorType.SnackBar
    }

    protected inline fun <reified D, reified R> NavManager.collectNavResult(
        crossinline onResult: (R) -> Unit
    ) {
        executeSafe({
            // Get nav manager
            val navManager = this@collectNavResult

            // Collect results with view model scope
            navManager.onNavResult<D, R>(callback = onResult)
        })
    }

    /**
     * Called when the screen is being destroyed through navigation (pop).
     * Override this method to clean up resources like cancelling jobs, stopping timers, etc.
     */
    open fun onScreenDestroyed() {
        // Override in subclasses for cleanup
    }
}

