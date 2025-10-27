package kmp.core.mobile.navigations

import kmp.core.mobile.AppLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KClass


@OptIn(DelicateCoroutinesApi::class)
class NavManager(private val appLogger: AppLogger) {
    private val _effects: Channel<NavEffect> = Channel(Channel.UNLIMITED)
    private val effects = _effects.receiveAsFlow()
    private val _results = MutableStateFlow<NavResult<*>?>(null)

    fun navigate(
        destination: NavDestination,
        behaviour: NavigateBehaviour = NavigateBehaviour.Normal
    ) {
        log("navigate: ${destination::class.simpleName}")
        _effects.trySend(NavEffect.NavigateTo(destination, behaviour))
    }

    /**
     * Navigate to a destination (alias for navigate with Normal behavior)
     */
    fun navigateTo(destination: NavDestination) {
        navigate(destination, NavigateBehaviour.Normal)
    }

    /**
     * Replace current screen with new destination if it's the current screen
     */
    fun replaceTo(destination: NavDestination) {
        log("replaceTo: ${destination::class.simpleName}")
        _effects.trySend(NavEffect.NavigateTo(destination, NavigateBehaviour.ReplaceIfCurrent))
    }

    /**
     * Clear entire navigation stack and navigate to destination
     */
    fun clearAndNavigate(destination: NavDestination) {
        log("clearAndNavigate: ${destination::class.simpleName}")
        _effects.trySend(NavEffect.ClearAndNavigate(destination))
    }

    /**
     * Navigate only if the destination is not already the current screen
     */
    fun navigateIfNotCurrent(destination: NavDestination) {
        log("navigateIfNotCurrent: ${destination::class.simpleName}")
        _effects.trySend(NavEffect.NavigateTo(destination, NavigateBehaviour.KeepIfCurrent))
    }

    fun popTo(screenClass: KClass<*>) {
        log("popTo: ${screenClass.simpleName}")
        _effects.trySend(NavEffect.PopToScreen(screenClass))
    }

    fun popCount(count: Int) {
        log("popCount: $count")
        _effects.trySend(NavEffect.PopCount(count))
    }

    fun goBack() {
        log("goBack")
        _effects.trySend(NavEffect.GoBack)
    }

    fun collectNavEffects(scope: CoroutineScope, callback: (NavEffect) -> Unit) {
        scope.launch {
            effects.onEach(callback).collect()
        }
    }

    fun <R> sendResult(source: String?, result: R) {
        _results.value = NavResult(source, result)
    }

    suspend inline fun <reified S, reified R> onNavResult(
        crossinline callback: (R) -> Unit
    ) {
        collectNavResults {
            if (it?.source == S::class.simpleName && it?.result is R) {
                callback.invoke(it?.result as R)
            }
        }
    }

    suspend fun collectNavResults(callback: (NavResult<*>?) -> Unit) {
        _results.onEach(callback).collect()
    }

    fun <R> goBackWithResult(scope: CoroutineScope, source: String?, result: R) {
        scope.launch {
            // Go back
            goBack()
            delay(50)
            // Create the nav results
            val navResult = NavResult(
                source = source,
                result = result
            )
            // Push the nav result then push null value to prevent getting this value again once re-visit same screen
            _results.value = navResult
            delay(1000)
            _results.value = null
        }
    }

    private fun log(msg: String) {
        appLogger.log("NavManager", msg)
    }
}
