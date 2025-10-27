package kmp.core.mobile.navigations

import kmp.core.mobile.base.BaseScreen
import kotlin.reflect.KClass

/**
 * Extension to create ScreenConfig from screen class
 */
fun KClass<out BaseScreen<*>>.toScreenConfig(data: String? = null): ScreenConfig {
    return ScreenConfig(
        key = this.simpleName ?: "UnknownScreen",
        data = data
    )
}

/**
 * Extension to create ScreenConfig from BaseScreen instance
 */
fun BaseScreen<*>.toScreenConfig(data: String? = null): ScreenConfig {
    return ScreenConfig(
        key = this.screenTag,
        data = data
    )
}