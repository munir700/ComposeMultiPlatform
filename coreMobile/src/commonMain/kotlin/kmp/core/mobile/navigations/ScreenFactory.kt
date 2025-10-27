package kmp.core.mobile.navigations

import com.arkivanov.decompose.ComponentContext
import kmp.core.mobile.base.BaseScreen


interface ScreenFactory {
    fun createScreen(config: ScreenConfig, componentContext: ComponentContext): BaseScreen<*>
    fun registerScreen(screen: BaseScreen<*>)
}