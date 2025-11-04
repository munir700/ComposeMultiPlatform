package app.sunreef.yachts.mobile.navigation

import com.arkivanov.decompose.ComponentContext
import kmp.core.mobile.base.BaseScreen
import kmp.core.mobile.navigations.ScreenProvider
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import kotlin.reflect.KClass

// shared/navigation/KoinScreenProvider.kt
class KoinScreenProvider : ScreenProvider, KoinComponent {
    private val supportedScreens = mutableSetOf<KClass<*>>()

    fun registerScreen(screenClass: KClass<*>) {
        supportedScreens.add(screenClass)
    }

    override fun canCreate(screenClass: KClass<*>): Boolean {
        return supportedScreens.contains(screenClass)
    }

    override fun createScreen(
        screenClass: KClass<*>,
        componentContext: ComponentContext
    ): BaseScreen<*>? {
        return inject<BaseScreen<*>>(
            qualifier = named(screenClass.simpleName.orEmpty()),
            parameters = { parametersOf(componentContext) }
        ).value
    }
}