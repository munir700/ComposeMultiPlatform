package kmp.core.mobile.navigations

import com.arkivanov.decompose.ComponentContext
import kmp.core.mobile.base.BaseScreen
import kotlin.reflect.KClass

/**
 * DI-based screen factory
 * Uses registered providers to create screens
 */
class DIScreenFactory(
    private val registry: ScreenProviderRegistry
) : ScreenFactory {

    // Map to store screen instances by their tag
    private val screenInstances = mutableMapOf<String, BaseScreen<*>>()

    // Map to store screen class by tag for recreation
    private val screenClassMap = mutableMapOf<String, KClass<*>>()

    override fun createScreen(config: ScreenConfig, componentContext: ComponentContext): BaseScreen<*> {
        // Check if we already have this instance cached
        screenInstances[config.key]?.let { return it }

        // Get the screen class from our map
        val screenClass = screenClassMap[config.key]
            ?: throw IllegalStateException("Screen class not found for key: ${config.key}. Available keys: ${screenClassMap.keys}")

        // Create new instance using registry
        val screen = registry.createScreen(screenClass, componentContext)
            ?: throw IllegalStateException("Failed to create screen for class: ${screenClass.simpleName}")

        // Cache the created screen
        screenInstances[config.key] = screen

        return screen
    }

    override fun registerScreen(screen: BaseScreen<*>) {
        val tag = screen.screenTag
        val screenClass = screen::class

        // Store both the instance and class mapping
        screenInstances[tag] = screen
        screenClassMap[tag] = screenClass

        // Also register by class name for compatibility
        val className = screenClass.simpleName
        if (className != null && className != tag) {
            screenClassMap[className] = screenClass
        }
    }

    /**
     * Register a screen class for later instantiation
     */
    fun registerScreenClass(tag: String, screenClass: KClass<*>) {
        screenClassMap[tag] = screenClass

        // Also register by class name
        val className = screenClass.simpleName
        if (className != null && className != tag) {
            screenClassMap[className] = screenClass
        }
    }

    /**
     * Clear cached instances (useful for memory management)
     */
    fun clearCache() {
        screenInstances.clear()
    }

    /**
     * Clear specific screen instance
     */
    fun clearScreen(tag: String) {
        screenInstances.remove(tag)
    }

    /**
     * Debug method to see registered screens
     */
    fun getRegisteredScreens(): Map<String, KClass<*>> {
        return screenClassMap.toMap()
    }
}