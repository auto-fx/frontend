package com.example.auto_fx_front.presentation.auth.update_password

data class UpdatePasswordState(
    val currentPassword: String = "",
    val newPassword: String = "",
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)
