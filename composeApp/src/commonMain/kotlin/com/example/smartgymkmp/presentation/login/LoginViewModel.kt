package com.example.smartgymkmp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartgymkmp.domain.model.AuthResult
import com.example.smartgymkmp.domain.model.repositry.AuthRepository
import com.example.smartgymkmp.domain.model.usecase.LoginUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val authRepository: AuthRepository,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
): ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    // Remove these lines from LoginViewModel.kt
    private val _uiState = MutableStateFlow<LoginState>(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    fun onUsernameChange(username: String) {
        _state.update { it.copy(username = username, error = null) }
    }

    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password, error = null) }
    }

    // In your LoginViewModel
    fun login() {
        val email = state.value.username
        val password = state.value.password

        coroutineScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val result = authRepository.login(email, password)

                when (result) {
                    is AuthResult.Success -> {
                        _state.update { it.copy(isLoading = false, isLoggedIn = true) }
                    }
                    is AuthResult.Error -> {
                        _state.update { it.copy(isLoading = false, error = result.message) }
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
            }
        }
    }
}