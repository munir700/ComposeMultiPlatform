package kmp.core.mobile.utils.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

fun Flow<*>.subscribe(
    onEach: (item: Any) -> Unit,
    onComplete: () -> Unit,
    onThrow: (error: Throwable) -> Unit
): Job = this.subscribe(Dispatchers.Main, onEach, onComplete, onThrow)

fun Flow<*>.subscribe(
    dispatcher: CoroutineDispatcher,
    onEach: (item: Any) -> Unit,
    onComplete: () -> Unit,
    onThrow: (error: Throwable) -> Unit
): Job = this.onEach { onEach(it as Any) }
    .catch { onThrow(it) }
    .onCompletion { onComplete() }
    .launchIn(CoroutineScope(Job() + dispatcher))


fun <T> Flow<T>.asCommonFlow(): CommonFlow<T> = CommonFlow(this)

class CommonFlow<T>(private val origin: Flow<T>) : Flow<T> by origin {
    fun collectCommon(
        coroutineScope: CoroutineScope? = null,
        callback: (T) -> Unit
    ) {
        onEach {
            callback(it)
        }.launchIn(coroutineScope ?: CoroutineScope(Dispatchers.Main))
    }

    fun collectCommon(callback: (T) -> Unit) {
        collectCommon(
            coroutineScope = null,
            callback = callback
        )
    }
}