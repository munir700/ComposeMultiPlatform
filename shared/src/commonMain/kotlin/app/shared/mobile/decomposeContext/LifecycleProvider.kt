package app.shared.mobile.decomposeContext

import com.arkivanov.essenty.lifecycle.LifecycleRegistry



import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import kotlin.jvm.JvmStatic

object ComponentContextProvider {
    private val defaultComponentContext: ComponentContext by lazy {
        DefaultComponentContext(LifecycleRegistry())
    }

    @JvmStatic
    fun provide(): ComponentContext = defaultComponentContext
}
