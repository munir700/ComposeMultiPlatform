package app.shared.mobile.di


import app.shared.mobile.domain.useCases.DeleteImageUseCase
import app.shared.mobile.domain.useCases.UploadImageUseCase
import org.koin.dsl.module

val useCasesModule = module {
    factory {
        UploadImageUseCase(get(), get(), get(), get())
    }

    factory {
        DeleteImageUseCase(get(), get(), get())
    }
}