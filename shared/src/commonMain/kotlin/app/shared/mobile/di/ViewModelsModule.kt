package app.shared.mobile.di

// MARK: Add imports

import app.shared.mobile.presentation.app.AppViewModel
import app.shared.mobile.presentation.attachments.chooser.AttachmentChooserViewModel
import app.shared.mobile.presentation.infiniteScrolling.InfiniteScrollViewModel
import app.shared.mobile.presentation.locationScreen.LocationViewModel
import app.shared.mobile.presentation.login.LoginViewModel
import app.shared.mobile.presentation.main.MainViewModel
import app.shared.mobile.presentation.splash.SplashViewModel
import org.koin.dsl.module

val viewModelsModule = module {
    // MARK: Add view model definitions
    factory { AttachmentChooserViewModel(get(), get(), get(), get()) }
    factory { MainViewModel() }
    factory { LoginViewModel() }
    factory { SplashViewModel(get(), get()) }
    factory { InfiniteScrollViewModel(get()) }
    factory { LocationViewModel(get(), get()) }
    factory { AppViewModel(get(), get()) }
}
