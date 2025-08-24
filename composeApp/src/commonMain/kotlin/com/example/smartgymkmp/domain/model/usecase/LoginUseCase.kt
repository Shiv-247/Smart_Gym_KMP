package com.example.smartgymkmp.domain.model.usecase

import com.example.smartgymkmp.domain.model.AuthResult
import com.example.smartgymkmp.domain.model.repositry.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): AuthResult {
        if (username.isBlank()) {
            return AuthResult.Error("Username cannot be empty")
        }
        if (password.isBlank()) {
            return AuthResult.Error("Password cannot be empty")
        }
        return repository.login(username, password)
    }
}