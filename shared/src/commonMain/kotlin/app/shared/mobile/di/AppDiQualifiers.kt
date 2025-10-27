package app.shared.mobile.di

import org.koin.core.qualifier.named

object AppDiQualifiers {
    val MOCK_SERVER = named("mock_server")
    val AUTH_SERVER = named("auth_server")


    // Screen Qualifiers
    val SPLASH_SCREEN = named("splash_screen")
    val LOGIN_SCREEN = named("login_screen")
    val MAIN_SCREEN = named("main_screen")
    val INFINITE_SCROLL_SCREEN = named("infinite_scroll_screen")
    val LOCATION_SCREEN = named("location_screen")
}