package app.shared.mobile.di

import app.shared.mobile.data.repositories.AuthRepositoryImpl
import app.shared.mobile.data.repositories.InfiniteScrollRepositoryImpl
import app.shared.mobile.domain.repositories.AppConfigsRepository
import app.shared.mobile.domain.repositories.AuthRepository
import app.shared.mobile.domain.repositories.ImageUploadRepository
import app.shared.mobile.domain.repositories.InfiniteScrollRepository
import app.shared.mobile.domain.repositories.UserRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single { AppConfigsRepository() }

    single<AuthRepository> {
        AuthRepositoryImpl(get(), get())
    }

    single {
        UserRepository(get(), get())
    }

    factory<InfiniteScrollRepository> {
        InfiniteScrollRepositoryImpl()
    }

    factory {
        ImageUploadRepository(get())
    }

}

