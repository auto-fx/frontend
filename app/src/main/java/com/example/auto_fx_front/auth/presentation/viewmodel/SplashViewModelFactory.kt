// src/main/java/com/example/auto_fx_front/presentation/viewmodel/SplashViewModelFactory.kt
package com.example.auto_fx_front.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.auto_fx_front.auth.data.repository.IAMRepository

class SplashViewModelFactory(
    private val repo: IAMRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(repo) as T
    }
}
