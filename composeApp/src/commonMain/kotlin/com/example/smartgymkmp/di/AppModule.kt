package com.example.smartgymkmp.di

import com.example.smartgymkmp.data.repositry.AuthRepositoryImpl
import com.example.smartgymkmp.domain.model.repositry.AuthRepository
import com.example.smartgymkmp.domain.model.usecase.LoginUseCase
import com.example.smartgymkmp.presentation.login.LoginViewModel
import org.koin.dsl.module

val appModule = module {
    // Repositories
    single<AuthRepository> { AuthRepositoryImpl() }

    // UseCases
    factory { LoginUseCase(get()) }

    // ViewModels
    factory {
        LoginViewModel(
            loginUseCase = get(),
            authRepository = get()
        )
    }
}