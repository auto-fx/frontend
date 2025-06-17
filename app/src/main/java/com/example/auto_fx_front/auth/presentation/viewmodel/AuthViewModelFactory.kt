// src/main/java/com/example/auto_fx_front/presentation/viewmodel/AuthViewModelFactory.kt
package com.example.auto_fx_front.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.auto_fx_front.auth.data.repository.IAMRepository

/**
 * Factory para crear instancias de AuthViewModel inyectando el IAMRepository.
 */
class AuthViewModelFactory(
    private val repo: IAMRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Sólo inyectamos el repo, ya no usamos un CoroutineScope externo
        return AuthViewModel(repo) as T
    }
}
