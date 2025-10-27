package kmp.core.mobile.navigations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kmp.core.mobile.base.BaseScreen

/**
 * DI-based App Navigator
 * Uses ScreenProviderRegistry for screen creation
 */
@Composable
fun CoreAppNavigatorDI(
    modifier: Modifier = Modifier,
    componentContext: ComponentContext,
    navManager: NavManager,
    startScreen: BaseScreen<*>,
    screenFactory: DIScreenFactory
) {
    val navigator = remember {
        DIDecomposeNavigatorImpl(
            componentContext = componentContext,
            initialScreen = startScreen,
            screenFactory = screenFactory
        )
    }

    val childStack by navigator.childStack.subscribeAsState()

    // Handle navigation effects
    LaunchedEffect(navigator) {
        navManager.collectNavEffects(this) { effect ->
            when (effect) {
                is NavEffect.NavigateTo -> {
                    navigator.navigateTo(
                        destination = effect.destination,
                        behaviour = effect.behaviour
                    )
                }

                is NavEffect.ClearAndNavigate -> {
                    navigator.clearAndNavigate(effect.destination)
                }

                is NavEffect.PopToScreen -> {
                    navigator.popToExclusive(effect.screenClass)
                }

                is NavEffect.PopCount -> {
                    navigator.popByCount(effect.count)
                }

                is NavEffect.GoBack -> {
                    navigator.pop()
                }
            }
        }
    }

    // Render app navigator with Decompose Children
    Children(
        stack = childStack,
        modifier = modifier,
        animation = stackAnimation(fade())
    ) { child ->
        Box(modifier = Modifier.fillMaxSize()) {
            child.instance.Content()
        }
    }
}