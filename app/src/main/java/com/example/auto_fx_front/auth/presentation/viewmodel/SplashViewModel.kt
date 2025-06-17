// src/main/java/com/example/auto_fx_front/presentation/viewmodel/SplashViewModel.kt
package com.example.auto_fx_front.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auto_fx_front.auth.data.repository.IAMRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class SessionState {
    object Checking     : SessionState()
    object Authenticated: SessionState()
    object Unauthenticated: SessionState()
}

class SplashViewModel(private val repo: IAMRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<SessionState>(SessionState.Checking)
    val uiState: StateFlow<SessionState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(repo.keepSignedIn, repo.token) { keep, token ->
                if (keep && !token.isNullOrEmpty()) SessionState.Authenticated
                else SessionState.Unauthenticated
            }.first().let { state ->
                _uiState.value = state
            }
        }
    }
}
