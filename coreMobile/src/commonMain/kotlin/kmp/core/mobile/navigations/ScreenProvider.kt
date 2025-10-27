package kmp.core.mobile.navigations

import com.arkivanov.decompose.ComponentContext
import kmp.core.mobile.base.BaseScreen
import kotlin.reflect.KClass

/**
 * Screen provider interface for DI-based screen creation
 * Implementations should be provided in the shared module
 */
interface ScreenProvider {
    /**
     * Creates a screen instance for the given class
     * @param screenClass The KClass of the screen to create
     * @param componentContext Decompose component context
     * @return BaseScreen instance or null if this provider doesn't handle this screen type
     */
    fun createScreen(screenClass: KClass<*>, componentContext: ComponentContext): BaseScreen<*>?

    /**
     * Check if this provider can create the given screen type
     */
    fun canCreate(screenClass: KClass<*>): Boolean
}