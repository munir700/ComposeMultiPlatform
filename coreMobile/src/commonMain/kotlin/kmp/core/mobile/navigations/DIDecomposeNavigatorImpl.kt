package kmp.core.mobile.navigations

// ============================================
// CORE MODULE - Updated Navigator Implementation
// ============================================

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import kmp.core.mobile.base.BaseScreen
import kotlin.reflect.KClass

class DIDecomposeNavigatorImpl(
    componentContext: ComponentContext,
    initialScreen: BaseScreen<*>,
    private val screenFactory: DIScreenFactory
) : DecomposeNavigator {

    private val stackNavigation = StackNavigation<ScreenConfig>()

    init {
        // IMPORTANT: Register initial screen BEFORE creating childStack
        screenFactory.registerScreen(initialScreen)
    }

    override val childStack: Value<ChildStack<ScreenConfig, BaseScreen<*>>> by lazy {
        componentContext.childStack(
            source = stackNavigation,
            serializer = ScreenConfig.serializer(),
            initialConfiguration = ScreenConfig(
                key = initialScreen.screenTag,
                data = null
            ),
            handleBackButton = true,
            childFactory = ::createChild
        )
    }

    override val lastItemOrNull: BaseScreen<*>?
        get() = childStack.value.active.instance

    private fun createChild(
        config: ScreenConfig,
        componentContext: ComponentContext
    ): BaseScreen<*> {
        return screenFactory.createScreen(config, componentContext)
    }

    /**
     * Helper method to convert NavDestination to ScreenConfig
     */
    private fun createScreenConfig(destination: NavDestination): ScreenConfig {
        return when (destination) {
            is BaseScreen<*> -> ScreenConfig(key = destination.screenTag, data = null)
            else -> {
                val key = destination::class.simpleName ?: "UnknownScreen"
                ScreenConfig(key = key, data = null)
            }
        }
    }

    @OptIn(DelicateDecomposeApi::class)
    fun push(screen: BaseScreen<*>) {
        // Register screen class for future recreation
        screenFactory.registerScreen(screen)

        stackNavigation.push(
            ScreenConfig(key = screen.screenTag, data = null)
        )
    }

    fun pop(): Boolean {
        if (childStack.value.backStack.isNotEmpty()) {
            // Notify current screen that it's being destroyed
            notifyScreenDestroyed(lastItemOrNull)
            stackNavigation.pop()
            return true
        }
        return false
    }

    private fun notifyScreenDestroyed(screen: BaseScreen<*>?) {
        if (screen != null) {
            try {
                screen.onDestroyed()  // ← Trigger cleanup hook
            } catch (e: Exception) {
                println("Error notifying screen destruction: ${e.message}")
            }
        }
    }

    fun popByCount(popCount: Int): Boolean {
        if (childStack.value.backStack.size >= popCount) {
            repeat(popCount) {
                stackNavigation.pop()
            }
            return true
        }
        return false
    }

    fun popToExclusive(screenClass: KClass<*>): Boolean {
        val backStack = childStack.value.backStack
        var foundTarget = false

        for (i in backStack.indices.reversed()) {
            if (backStack[i].instance::class == screenClass) {
                foundTarget = true
                break
            }
        }

        if (foundTarget) {
            var currentScreen = lastItemOrNull
            while (currentScreen != null && currentScreen::class != screenClass) {
                if (!pop()) break
                currentScreen = lastItemOrNull
            }
            return true
        }
        return false
    }

    fun clearAndNavigate(destination: NavDestination) {
        val screenConfig = createScreenConfig(destination)

        // Register the screen before navigation
        if (destination is BaseScreen<*>) {
            screenFactory.registerScreen(destination)
        }

        stackNavigation.replaceAll(screenConfig)
    }

    @OptIn(DelicateDecomposeApi::class)
    fun navigateTo(destination: Any, behaviour: NavigateBehaviour) {
        when (destination) {
            is BaseScreen<*> -> {
                val isSameCurrentScreen = lastItemOrNull?.screenTag == destination.screenTag

                when(behaviour) {
                    NavigateBehaviour.Normal -> {
                        push(destination)
                    }
                    NavigateBehaviour.ReplaceIfCurrent -> {
                        if (isSameCurrentScreen) {
                            screenFactory.registerScreen(destination)
                            stackNavigation.replaceCurrent(
                                ScreenConfig(key = destination.screenTag, data = null)
                            )
                        } else {
                            push(destination)
                        }
                    }
                    NavigateBehaviour.KeepIfCurrent -> {
                        if (!isSameCurrentScreen) {
                            push(destination)
                        }
                    }
                }
            }
            is NavDestination -> {
                val screenConfig = createScreenConfig(destination)
                val isSameCurrentScreen = lastItemOrNull?.screenTag == screenConfig.key

                when(behaviour) {
                    NavigateBehaviour.Normal -> {
                        stackNavigation.push(screenConfig)
                    }
                    NavigateBehaviour.ReplaceIfCurrent -> {
                        if (isSameCurrentScreen) {
                            stackNavigation.replaceCurrent(screenConfig)
                        } else {
                            stackNavigation.push(screenConfig)
                        }
                    }
                    NavigateBehaviour.KeepIfCurrent -> {
                        if (!isSameCurrentScreen) {
                            stackNavigation.push(screenConfig)
                        }
                    }
                }
            }
            else -> throw IllegalArgumentException("Unsupported destination type: ${destination::class}")
        }
    }

    // Legacy methods for compatibility
    override fun navigate(screen: BaseScreen<*>, behaviour: NavigateBehaviour) {
        navigateTo(screen, behaviour)
    }

    override fun popTo(screenClass: KClass<*>) {
        popToExclusive(screenClass)
    }

    override fun popCount(count: Int) {
        popByCount(count)
    }

    override fun goBack() {
        pop()
    }
}