package kmp.core.mobile.navigations

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kmp.core.mobile.base.BaseScreen
import kotlin.reflect.KClass

interface DecomposeNavigator {
    val childStack: Value<ChildStack<ScreenConfig, BaseScreen<*>>>
    fun navigate(screen: BaseScreen<*>, behaviour: NavigateBehaviour)
    fun popTo(screenClass: KClass<*>)
    fun popCount(count: Int)
    fun goBack()

    val lastItemOrNull: BaseScreen<*>?
}
