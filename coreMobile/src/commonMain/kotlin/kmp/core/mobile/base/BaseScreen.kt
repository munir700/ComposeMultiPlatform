package kmp.core.mobile.base


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kmp.core.mobile.AppLogger
import kmp.core.mobile.navigations.NavManager
import kmp.core.mobile.utils.extensions.randomUUID
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseScreen<T : CoreViewModel<*, *, *>>() : KoinComponent {

    open val key: String = randomUUID()
    open val screenTag: String = randomUUID()

    protected var viewModel: T? = null

    private val logger by inject<AppLogger>()

    protected fun getNavManager(): NavManager {
        return inject<NavManager>().value
    }

    open fun onBackPressed() {
        getNavManager().goBack()
    }

    /**
     * Called when the screen is being destroyed through navigation.
     * Automatically notifies the ViewModel to clean up resources.
     */
    open fun onDestroyed() {
        try {
            viewModel?.let {
                it.onScreenDestroyed()
                viewModel = null
            }
        } catch (e: Exception) {
            logger.log("Error in ${this::class.simpleName} onDestroyed: ${e.message}")
        }
    }

    @Composable
    abstract fun Content()
}

@Composable
inline fun <reified VM : CoreViewModel<*, *, *>> BaseScreen<*>.rememberViewModel(): VM {
    return remember { inject<VM>().value }
}

inline fun <reified VM : CoreViewModel<*, *, *>> BaseScreen<*>.getViewModel(): VM {
    return inject<VM>().value
}

