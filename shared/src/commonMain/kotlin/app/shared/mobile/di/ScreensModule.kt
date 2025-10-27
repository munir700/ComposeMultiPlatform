package app.shared.mobile.di

import app.shared.mobile.di.AppDiQualifiers.INFINITE_SCROLL_SCREEN
import app.shared.mobile.di.AppDiQualifiers.LOCATION_SCREEN
import app.shared.mobile.di.AppDiQualifiers.LOGIN_SCREEN
import app.shared.mobile.di.AppDiQualifiers.MAIN_SCREEN
import app.shared.mobile.di.AppDiQualifiers.SPLASH_SCREEN
import app.shared.mobile.location.Location
import app.shared.mobile.presentation.infiniteScrolling.InfiniteScrollScreen
import app.shared.mobile.presentation.locationScreen.LocationScreen
import app.shared.mobile.presentation.locationScreen.models.LocationParams
import app.shared.mobile.presentation.login.LoginScreen
import app.shared.mobile.presentation.main.MainScreen
import app.shared.mobile.presentation.splash.SplashScreen
import org.koin.dsl.module

// shared/di/ScreensModule.kt
val screensModule = module {
    factory(SPLASH_SCREEN) { params ->
        SplashScreen()
    }

    factory(LOGIN_SCREEN) { params ->
        LoginScreen()
    }

    factory(MAIN_SCREEN) { params ->
        MainScreen()
    }

    factory(INFINITE_SCROLL_SCREEN) { params ->
        InfiniteScrollScreen()
    }

    factory(LOCATION_SCREEN) { (params: LocationParams) ->
        LocationScreen(
            locationParams = params
        )
    }
}