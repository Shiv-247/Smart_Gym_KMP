package com.example.smartgymkmp.data.repositry

import com.example.smartgymkmp.database.SmartGymDatabase
import com.example.smartgymkmp.domain.model.AuthResult
import com.example.smartgymkmp.domain.model.repositry.AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {
    private var isUserLoggedIn = false

    override suspend fun login(username: String, password: String): AuthResult {
        // Use the proper way to access companion object method
        return platformLogin(username, password)
        // Alternatively, you can use: AuthRepositoryImpl.platformLogin(username, password)
    }

    override fun logout() {
        isUserLoggedIn = false
    }

    override fun isLoggedIn(): Boolean {
        return isUserLoggedIn
    }

}

expect suspend fun platformLogin(email: String, password: String): AuthResult
