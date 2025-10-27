package kmp.core.mobile.navigations

import kotlin.reflect.KClass

sealed class NavEffect {
    data class NavigateTo(val destination: NavDestination, val behaviour: NavigateBehaviour) : NavEffect()
    data class ClearAndNavigate(val destination: NavDestination) : NavEffect()
    data class PopToScreen(val screenClass: KClass<*>) : NavEffect()
    data class PopCount(val count: Int) : NavEffect()
    data object GoBack : NavEffect()
}