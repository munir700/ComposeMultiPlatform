package kmp.core.mobile.navigations

import com.arkivanov.decompose.ComponentContext
import kmp.core.mobile.base.BaseScreen
import kotlin.reflect.KClass

/**
 * Registry for screen providers
 * Core module provides the registry mechanism
 * Shared module registers the actual screen providers
 */
class ScreenProviderRegistry {
    private val providers = mutableListOf<ScreenProvider>()

    fun registerProvider(provider: ScreenProvider) {
        providers.add(provider)
    }

    fun createScreen(screenClass: KClass<*>, componentContext: ComponentContext): BaseScreen<*> {
        for (provider in providers) {
            if (provider.canCreate(screenClass)) {
                provider.createScreen(screenClass, componentContext)?.let { return it }
            }
        }
        throw IllegalStateException("No provider found for screen: ${screenClass.simpleName}")
    }

    fun canCreate(screenClass: KClass<*>): Boolean {
        return providers.any { it.canCreate(screenClass) }
    }
}