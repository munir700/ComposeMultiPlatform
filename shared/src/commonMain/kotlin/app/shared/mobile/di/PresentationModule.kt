package app.shared.mobile.di

import app.shared.mobile.presentation.attachments.chooser.AttachmentChooserScreen
import app.shared.mobile.presentation.locationScreen.LocationScreen
import org.koin.dsl.module


val presentationModule = module {
    factory { LocationScreen(get()) }
    factory { AttachmentChooserScreen(get(), get()) }
}