package com.example.smartgymkmp.domain.model.repositry

import com.example.smartgymkmp.domain.model.AuthResult

interface AuthRepository {
    suspend fun login(username: String, password: String): AuthResult
    fun logout()
    fun isLoggedIn(): Boolean
}